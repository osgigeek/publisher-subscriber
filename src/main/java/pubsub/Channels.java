package pubsub;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class Channels {

	private static Channels channel;
	private List<String> workItems;
	private ExecutorService executor;
	private Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();
	
	public Channels(List<String> workItems, ExecutorService s) {
		this.workItems = workItems;
		executor = s;
	}
	
	public static final Channels newChannel() {
		channel = new Channels(new ArrayList<String>(), Executors.newFixedThreadPool(5, new ChannelsThreadFactory()));
		return channel;
	}
	
	public void doWork(String message) {
		workItems.add(message);
		Thread p = waiters.peek();
		if(p != null) {
			System.out.println("UnParking thread " + p.getName());
			LockSupport.unpark(p);
		}else {
			System.out.println("Nothing to unpark");
			executeWork(message);
		}
	}
	
	public void executeWork(String message) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Thread t = Thread.currentThread();
				while(true) {
					String item = null;
					try{
						item =workItems.get(0);
						System.out.println("Executing " + item + " on thread " + t.getName());
							Thread.sleep(1500);
					}catch(IndexOutOfBoundsException e) {
						waiters.add(t);
						System.out.println("Parking thread " + t.getName());
						LockSupport.park(t);
					}catch(InterruptedException e) {}
				}
			}
			
		});
	}
}
