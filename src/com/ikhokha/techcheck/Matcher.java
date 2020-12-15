package com.ikhokha.techcheck;

/**
 * 
 * Interface we use for matching comparisons
 *
 */
public interface Matcher {
	
	public String getCriteria();

	public boolean check(String line);
}
