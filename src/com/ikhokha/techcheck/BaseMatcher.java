package com.ikhokha.techcheck;

public abstract class BaseMatcher implements Matcher {
	
	protected String criteria = null;
	
	@Override
	public String getCriteria() {
		return criteria;
	}
}
