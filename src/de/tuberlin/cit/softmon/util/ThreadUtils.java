package de.tuberlin.cit.softmon.util;

public class ThreadUtils {

	public static void dumpThreads() {
		
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		int activeCount = group.activeCount();
		Thread[] threads = new Thread[activeCount];
		group.enumerate(threads);
		
		System.out.println("Thread-Group " + group + " contains " + activeCount + " threads");
		
		for (Thread thread : threads) {
			System.out.println("Thread " + thread);
		}
	}
}