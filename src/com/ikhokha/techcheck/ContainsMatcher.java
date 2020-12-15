package com.ikhokha.techcheck;

public class ContainsMatcher extends BaseMatcher {

	private String checkString = null;
	private boolean matchCase;

	public ContainsMatcher(String criteria, String checkString, boolean matchCase) {
		
		super.criteria = criteria;
		this.matchCase = matchCase;
		this.checkString = checkString;
	}

	@Override
	public boolean check(String line) {
		
		if (matchCase) {

			return line.contains(checkString);

		} else {

			return line.toLowerCase().contains(checkString.toLowerCase());
		}
	}
}
