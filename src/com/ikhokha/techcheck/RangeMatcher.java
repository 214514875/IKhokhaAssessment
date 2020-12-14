package com.ikhokha.techcheck;

public class RangeMatcher extends BaseMatcher {

	private int greaterThan, lessThan;

	public RangeMatcher(String criteria, int greaterThan, int lessThan) {
		super.criteria = criteria;
		this.greaterThan = greaterThan;
		this.lessThan = lessThan;
	}

	public void checkLimits(int greaterThan, int lessThan) {

		if (greaterThan < -1 || lessThan < -1) {
			throw new IllegalArgumentException("Please check argument values");
		}

		if (greaterThan > lessThan && lessThan != -1) {
			throw new IllegalArgumentException("Please check argument values");
		}

		// Set values
		this.greaterThan = greaterThan;
		this.lessThan = lessThan;

	}

	@Override
	public boolean check(String line) {
		
		int length = line.length();
		
		if (greaterThan == -1) {
			return length < lessThan;
		}

		if (lessThan == -1) {
			return length > greaterThan;
		}
		
		return length > greaterThan && length < lessThan;
	}

}