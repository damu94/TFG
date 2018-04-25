package com.eclipsesource.jshint.ui.internal;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTNode;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;


import com.eclipsesource.jshint.Text;


public class JSAnalyzer {

	private ASTParser parser;

	public JSAnalyzer() {
		parser = ASTParser.newParser(AST.JLS3);
	}

	public void check(Text code) {

		
	}

	
 
	
}
