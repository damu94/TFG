package com.eclipsesource.jshint.ui.quickfixes;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.ui.text.java.IInvocationContext;
import org.eclipse.wst.jsdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.wst.jsdt.ui.text.java.IProblemLocation;
import org.eclipse.wst.jsdt.ui.text.java.IQuickFixProcessor;

public class QuickFixProcessor implements IQuickFixProcessor {

	public IJavaCompletionProposal[] getCorrections(IInvocationContext arg0, IProblemLocation[] arg1)
			throws CoreException {
		// TODO Auto-generated method stub
		System.out.println("QuickFixProcessor2.getCorrections()" + arg1.length);
		return null;
	}

	public boolean hasCorrections(IJavaScriptUnit arg0, int arg1) {
		System.out.println("QuickFixProcessor2.hasCorrections() " + arg1);
		// TODO Auto-generated method stub
		return true;
	}
	
	
}