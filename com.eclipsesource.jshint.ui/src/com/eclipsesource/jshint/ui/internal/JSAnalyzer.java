package com.eclipsesource.jshint.ui.internal;



import com.eclipsesource.jshint.Problem;
import com.eclipsesource.jshint.ProblemHandler;
import com.eclipsesource.jshint.Text;
import com.eclipsesource.jshint.internal.ProblemImpl;

@SuppressWarnings("restriction")
public class JSAnalyzer {

	public void check(Text code, ProblemHandler handler) {
		if(code.getContent().contains("fallo")) {
			Problem problem = new ProblemImpl(1,5,"cadena prohibida","E004");
			handler.handleProblem(problem);
		}
		
	}

	
	
}
