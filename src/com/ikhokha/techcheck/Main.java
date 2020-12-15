package com.ikhokha.techcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) {

		// Thread pool of 10
		ExecutorService pool = Executors.newFixedThreadPool(10);

		Map<String, Integer> totalResults = new HashMap<>();

		File docPath = new File("docs");
		File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));

		// List of futures
		List<Future<Map<String, Integer>>> results = new ArrayList<>();

		for (File commentFile : commentFiles) {

			final Future<Map<String, Integer>> future = pool.submit(new CommentAnalyzer(commentFile));
			results.add(future);
		}

		for (Future<Map<String, Integer>> result : results) {
			
			try {
				
				Map<String, Integer> r = result.get();
				addReportResults(r, totalResults);
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		pool.shutdown();

		System.out.println("RESULTS\n=======");
		totalResults.forEach((k, v) -> System.out.println(k + " : " + v));
	}

	/**
	 * This method adds the result counts from a source map to the target map
	 * 
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {

		// Merge the two maps together, sum the values with the same key
		source.forEach((k, v) -> target.merge(k, v, Integer::sum));
	}

}
