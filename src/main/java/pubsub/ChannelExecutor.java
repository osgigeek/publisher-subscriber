package pubsub;

public class ChannelExecutor {
	
	public static final void main(String args[]) {
		Channels c = Channels.newChannel();
		final int workItems = Integer.parseInt(args[0]);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i< workItems; i++) {
					c.doWork("Work " + i);
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		t.start();
		try {
			t.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
