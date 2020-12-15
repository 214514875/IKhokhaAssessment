Question 1.

1. The values were too low, this was because the results of each file overwrote each other instead of adding them. Fixed in Main.java line 37
2. The values were too low also because checks were not done independently (i.e. more than one criteria could be true) and checks where type sensitive. Fixed in CommentAnalyzer.java 
   (line 25 - 46)

Question 2.

In order to make the checks more scalable, interfaces was used. Namely the Matcher interface.

    public interface Matcher 
	{
		public String getCriteria();         -- The test name
		public boolean check(String line);   -- The check method that returns whether a match occured.
	}
	
    The idea was to remove the check away from a specific implementation, rather tie it down to expected behaviour. This way we can Implement as many checks as we want
	and as long as it complies to what the interface needs, we can painlessly add it to the list of checks as an object of the interface and not the specific class.
	
	Four matchers were written along with a base matcher class that contained the common behaviour.
	
		I) Contains Matcher (ContainsMatcher.java)
		II) Ranger Matcher (RangerMatcher.java)
		III) Spam Website Matcher (SpamWebsiteMatcher.java)
		
		I) Was used for - "MOVER", "SHAKER" and the "QUESTIONS" criteria, just the search term, and test name needed to be changed.
		II) Was used for - "SHORTER_THAN_15". The class was extended for custom values, either a low bound, a high bound or a range.
		III) Was used for - "SPAM". This was done using "java.net.URL" which has a nice in house URL validation method.
		
	
	The adding of checks was done in CommentAnalyzer.java in the addMatchers method. This also eliminated the need for numerous 'if' statements and instead required a simple
	iterative loop over all the matchers in the list and the 'check' method was called and acted on, seen in (CommentAnalyzer.java method "analyze").
	
Question 3.
	
	The naive solution to this problem would be to spawn a new thread for each file encountered, but as stated in the problem this doesnt scale very well for a large number of files.
	
	Instead a Executor Service along with a Future system was used. I opted for a FixedThreadPool with a specified pool size as my Executor Service, as I thought it was the simplest
	but at the same time the best option for our use case. The called class implements "Callable<T>" this was done so that results can be stored, in order to Consolidate and print 
	and the end. The idea was to iterate over all the comment files in the directory and for each one add it to the thread pool, also add the returned Future into a list of Futures.
	If the pool happend to be completely full at some point this would be fine as the remaining tasks would then be queued and when a thread is available, tasks would be allocated
	accordingly. Once this process is over we just need to iterate over the list of Futures and increment the neccessary values, taking the blocking nature of Futures in account it
	is safe to assume this Future iteration is quite optimal.
	
	This process can be seen in.
	
	Main.java (Throughout)
	CommentAnalyzer.java ('call' method)