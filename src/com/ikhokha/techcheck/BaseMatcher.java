package com.ikhokha.techcheck;

/**
 * Base class which implements the Matcher interface
 * 
 *
 */
public abstract class BaseMatcher implements Matcher {
	
	//The identifier name for the tested metric
	protected String criteria = null;
	
	@Override
	public String getCriteria() {
		return criteria;
	}
}
