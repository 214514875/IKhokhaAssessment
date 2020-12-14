package com.ikhokha.techcheck;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class SpamWebsiteMatcher extends BaseMatcher {
	
    public SpamWebsiteMatcher(String criteria) {
		super.criteria = criteria;
	}

	@Override
	public boolean check(String line) {
		//Split line using space delimiter
		String[] lines = line.split(Pattern.quote(" "));
		
		for(String ln : lines) {
			
			try 
			{
				URL url = new URL(ln);
				url.toURI();
				return true;
			}
			catch(URISyntaxException | MalformedURLException ex) {
				//Not a valid URL
			}			
		}
		
		//No valid URL found
		return false;
	}

}
