package com.ikhokha.techcheck;

public interface Matcher {
	
	public String getCriteria();

	public boolean check(String line);
}
