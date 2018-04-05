package com.eclipsesource.jshint.ui.internal;

import java.io.BufferedReader;
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

import com.eclipsesource.jshint.IChecker;
import com.eclipsesource.jshint.ProblemHandler;
import com.eclipsesource.jshint.Text;
import com.eclipsesource.jshint.ui.internal.builder.MarkerAdapter;
import com.eclipsesource.jshint.ui.internal.builder.MarkerHandler;


public class JSAdapterChecker implements IChecker {

	private JSAnalyzer checker;
	
	@Override
	public void createChecker(IProject project) throws CoreException {
		checker= new JSAnalyzer();
		
	}

	@Override
	public void checkFile(IFile file) throws CoreException {
		Text code = readContent(file);
		ProblemHandler handler = new MarkerHandler(new MarkerAdapter(file), code);
		checker.check(code,handler);

	}

	@Override
	public void cleanMarkers(IResource resource) throws CoreException {
		// TODO Auto-generated method stub

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
	
}
