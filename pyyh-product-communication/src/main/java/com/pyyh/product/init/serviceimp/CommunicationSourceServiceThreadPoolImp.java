package com.pyyh.product.init.serviceimp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.ThreadConfigPojo;

public class CommunicationSourceServiceThreadPoolImp extends AbstractSourceServiceImp{
	private ThreadPoolExecutor pool = null;
	private BlockingQueue<Runnable> queue;
	@SuppressWarnings("unchecked")
	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		ThreadConfigPojo tcp = ccp.getThreadPool();
		queue = new ArrayBlockingQueue<Runnable>(tcp.getQueueSize());
		pool = new ThreadPoolExecutor(
					tcp.getCorePoolSize(),
					tcp.getMaximumPoolSize(),
					tcp.getKeepAliveTime(),
					TimeUnit.SECONDS,
					queue
				);
		tcp.setPool(pool);
		tcp.setQueue(queue);
		return (T)tcp;
	}

}
