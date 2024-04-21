package com.zh.middware.business.task;

import java.util.concurrent.LinkedBlockingQueue;

import com.zh.middware.pojos.TagPojo;

public class ParseDataTask implements Runnable{
	private LinkedBlockingQueue<Object> inQueue;
	public ParseDataTask(LinkedBlockingQueue<Object> inQueue){
		this.inQueue = inQueue;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(1);
				TagPojo tp = (TagPojo)inQueue.poll();
				if(tp == null){
					continue;
				}
				
				System.out.println(inQueue.size() + "--->" + tp.getStationId() + "   " + tp.getTagId());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
