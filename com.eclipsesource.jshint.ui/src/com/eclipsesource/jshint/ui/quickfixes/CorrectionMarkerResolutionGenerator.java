package com.eclipsesource.jshint.ui.quickfixes;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.wst.jsdt.core.IJavaScriptModelMarker;

public class CorrectionMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	public IMarkerResolution[] getResolutions(IMarker mk) {
		try {
			String problem = (String) mk.getAttribute(IJavaScriptModelMarker.ID);

			switch (problem) {
			case "E017":
				return new IMarkerResolution[] { new QuickFix("Fix #1 for " + problem) };
			case "W117":
				return new IMarkerResolution[] { new QuickFix("Fix #2 for " + problem) };
			case "E004":
				return new IMarkerResolution[] { new QuickFix("Fix #3 for " + problem) };
			default:
				return new IMarkerResolution[0];
			}

		} catch (CoreException e) {
			return new IMarkerResolution[0];
		}

	}

}
