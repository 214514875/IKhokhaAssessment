package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CommentAnalyzer implements Callable<Map<String, Integer>>{

	private File file;
	private List<Matcher> matchers = new ArrayList<Matcher>();

	public CommentAnalyzer(File file) {
		
		this.file = file;
		addMatchers(matchers);
	}

	public Map<String, Integer> analyze() {

		Map<String, Integer> resultsMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				for(Matcher mtr : matchers) {
					if(mtr.check(line)) {
						incOccurrence(resultsMap, mtr.getCriteria());
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error processing file: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return resultsMap;
	}

	/**
	 * This method increments a counter by 1 for a match type on the countMap.
	 * Uninitialized keys will be set to 1
	 * 
	 * @param countMap the map that keeps track of counts
	 * @param key      the key for the value to increment
	 */
	private void incOccurrence(Map<String, Integer> countMap, String key) {

		countMap.putIfAbsent(key, 0);
		countMap.put(key, countMap.get(key) + 1);
	}
	
	/**
	 * Implementation for Callable interface
	 */
	@Override
	public Map<String, Integer> call() throws Exception {
		
		return analyze();
	}

	/**
	 * 
	 * @param matchers - List of matchers we will be checking against
	 */
	private void addMatchers(List<Matcher> matchers) {

		RangeMatcher rangeMatcher = new RangeMatcher("SHORTER_THAN_15", -1, 15);
		ContainsMatcher moverMatcher = new ContainsMatcher("MOVER_MENTIONS", "mover", false);
		ContainsMatcher shakerMatcher = new ContainsMatcher("SHAKER_MENTIONS", "shaker", false);
		ContainsMatcher questionMatcher = new ContainsMatcher("QUESTIONS", "?", false);
		SpamWebsiteMatcher spamMatcher = new SpamWebsiteMatcher("SPAM");
	
		matchers.add(moverMatcher);
		matchers.add(shakerMatcher);
		matchers.add(questionMatcher);
		matchers.add(rangeMatcher);
		matchers.add(spamMatcher);
	}
}
