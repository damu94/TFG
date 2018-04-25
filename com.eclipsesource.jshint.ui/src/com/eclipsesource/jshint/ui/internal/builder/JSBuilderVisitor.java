/*******************************************************************************
 * Copyright (c) 2012, 2013 EclipseSource.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ralf Sternberg - initial implementation and API
 ******************************************************************************/
package com.eclipsesource.jshint.ui.internal.builder;



import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Status;
import org.osgi.service.prefs.Preferences;

import com.eclipsesource.jshint.IChecker;
import com.eclipsesource.jshint.ui.internal.JSAdapterChecker;
//import com.eclipsesource.jshint.JSHint;
//import com.eclipsesource.jshint.ProblemHandler;
//import com.eclipsesource.jshint.Text;
//import com.eclipsesource.jshint.ui.internal.Activator;
import com.eclipsesource.jshint.ui.internal.JSHintAdapterChecker;
import com.eclipsesource.jshint.ui.internal.builder.JSBuilder.CoreExceptionWrapper;
import com.eclipsesource.jshint.ui.internal.preferences.EnablementPreferences;
//import com.eclipsesource.jshint.ui.internal.preferences.JSHintPreferences;
import com.eclipsesource.jshint.ui.internal.preferences.PreferencesFactory;
import com.eclipsesource.jshint.ui.internal.preferences.ResourceSelector;

class JSBuilderVisitor implements IResourceVisitor, IResourceDeltaVisitor {

	private final IChecker checkerJSHint;
	private final IChecker checkerJS;
	private final ResourceSelector selector;
	private final IProgressMonitor monitor;

	public JSBuilderVisitor(IProject project, IProgressMonitor monitor) throws CoreException {
		Preferences node = PreferencesFactory.getProjectPreferences(project);
		new EnablementPreferences(node);
		selector = new ResourceSelector(project);
		checkerJSHint = new JSHintAdapterChecker();
		checkerJS = new JSAdapterChecker();
		if (selector.allowVisitProject()) {
			checkerJSHint.createChecker(project);
			checkerJS.createChecker(project);
		}
		this.monitor = monitor;
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		return visit(resource);
	}

	public boolean visit(IResource resource) throws CoreException {
		boolean descend = false;
		if (resource.exists() && selector.allowVisitProject() && !monitor.isCanceled()) {
			if (resource.getType() != IResource.FILE) {
				descend = selector.allowVisitFolder(resource);
			} else {
				checkerJSHint.cleanMarkers(resource);
				if (selector.allowVisitFile(resource)) {
					check((IFile) resource);
				}
				descend = true;
			}
		}
		return descend;
	}

	
	private void check(IFile file) throws CoreException {
		
		try {
			checkerJSHint.checkFile(file);
			checkerJS.checkFile(file);
			
		} catch (CoreExceptionWrapper wrapper) {
			throw (CoreException) wrapper.getCause();
		} catch (RuntimeException exception) {
			String message = "Failed checking file " + file.getFullPath().toPortableString();
			throw new RuntimeException(message, exception);
		}
	}

	

}
