package com.eclipsesource.jshint.ui.quickfixes;



import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;

import com.eclipsesource.jshint.ui.internal.builder.MarkerAdapter;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;

public class QuickFix implements IMarkerResolution {
	String label;

	QuickFix(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	// public void run(IMarker marker) {
	// MessageDialog.openInformation(null, "QuickFix Demo",
	// "This quick-fix is not yet implemented");
	// }

	public void run(IMarker marker) {
		
		FileEditorInput input= new FileEditorInput((IFile) marker.getResource());
		IAnnotationModel model= JavaScriptUI.getDocumentProvider().getAnnotationModel(input);
		if (model != null) {
			// resource is open in editor

			Position pos = new Position(0);

			if (pos != null) {
				
				IDocument doc= JavaScriptUI.getDocumentProvider().getDocument(input);
				try {

					String str= doc.get(0, 6);

					doc.replace(0, 6, str.toUpperCase());
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		} else {
			// resource is not open in editor
			// to do: work on the resource
		}
		try {
			marker.delete();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}


}