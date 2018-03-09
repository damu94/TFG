package com.eclipsesource.jshint.ui.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.mozilla.javascript.NativeGenerator.GeneratorClosedException;

import com.eclipsesource.jshint.IChecker;
import com.eclipsesource.jshint.JSHint;
import com.eclipsesource.jshint.ProblemHandler;
import com.eclipsesource.jshint.Text;
import com.eclipsesource.jshint.ui.internal.builder.ConfigLoader;
import com.eclipsesource.jshint.ui.internal.builder.MarkerAdapter;
import com.eclipsesource.jshint.ui.internal.builder.MarkerHandler;
import com.eclipsesource.jshint.ui.internal.preferences.JSHintPreferences;



public class JSHintAdapterChecker implements IChecker {

	private JSHint checker;
	
	
	
	public JSHintAdapterChecker() {
		this.checker =new JSHint();
	}
	
	public void createChecker(IProject project) throws CoreException {
		try {
			InputStream inputStream = getCustomLib();
			if (inputStream != null) {
				try {
					checker.load(inputStream);
				} finally {
					inputStream.close();
				}
			} else {
				checker.load();
			}
			checker.configure(new ConfigLoader(project).getConfiguration());
		} catch (IOException exception) {
			String message = "Failed to intialize JSHint";
			throw new CoreException((IStatus) new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, exception));
		}
		
	}

	public void checkFile(IFile file) throws CoreException {
		Text code = readContent(file);
		ProblemHandler handler = new MarkerHandler(new MarkerAdapter(file), code);
		checker.check(code, handler);
		
				
	}
	

	
	
	private static InputStream getCustomLib() throws FileNotFoundException {
		JSHintPreferences globalPrefs = new JSHintPreferences();
		if (globalPrefs.getUseCustomLib()) {
			File file = new File(globalPrefs.getCustomLibPath());
			return new FileInputStream(file);
		}
		return null;
	}
	

	private static Text readContent(IFile file) throws CoreException {
		try {
			InputStream inputStream = file.getContents();
			String charset = file.getCharset();
			return readContent(inputStream, charset);
		} catch (IOException exception) {
			String message = "Failed to read resource";
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, exception));
		}
	}
	
	
	private static Text readContent(InputStream inputStream, String charset)
			throws UnsupportedEncodingException, IOException {
		Text result;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
		try {
			result = new Text(reader);
		} finally {
			reader.close();
		}
		return result;
	}

	public void cleanMarkers(IResource resource) throws CoreException {
		new MarkerAdapter(resource).removeMarkers();
		
	}
	
	
}
