package com.ikhokha.techcheck;

/**
 * Matcher class for lines containing a given phrase/string
 * 
 *
 */
public class ContainsMatcher extends BaseMatcher {

	private String checkString = null;
	private boolean matchCase;

	/**
	 * 
	 * @param criteria - Test name
	 * @param checkString - String we are for looking for
	 * @param matchCase - true - check case : false - ignore case
	 */
	public ContainsMatcher(String criteria, String checkString, boolean matchCase) {
		
		super.criteria = criteria;
		this.matchCase = matchCase;
		this.checkString = checkString;
	}

	/**
	 * @param line - String we are searching against
	 */
	@Override
	public boolean check(String line) {
		
		if (matchCase) {

			return line.contains(checkString);

		} else {

			return line.toLowerCase().contains(checkString.toLowerCase());
		}
	}
}
