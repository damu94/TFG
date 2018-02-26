package com.eclipsesource.jshint;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public interface Ichecker {

	public void createChecker(IProject project) throws CoreException;
	public void checkFile(IFile file) throws CoreException ;
	
}
