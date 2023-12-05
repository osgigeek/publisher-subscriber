package pubsub;

import java.util.concurrent.ThreadFactory;

public class ChannelsThreadFactory implements ThreadFactory{

	private int i = 0;
	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "MyThread-" + i++);
	}

}
