package com.pyyh.product.init.serviceimp;

import org.quartz.Scheduler;

import com.pyyh.product.init.service.IJobService;
import com.pyyh.product.util.OperateUtil;

import lombok.Getter;
@Getter
public class AbstractJobServiceImp implements IJobService{
	private Scheduler scheduler;
	
	public AbstractJobServiceImp(){
		this.scheduler = OperateUtil.getScheduler();
	}
	@Override
	public <T, E> T createJob(E e) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception();
	}

	@Override
	public <T, E> T createTrigger(E e) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception();
	}

	@Override
	public <T, E> T registJob(E e) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception();
	}


}
