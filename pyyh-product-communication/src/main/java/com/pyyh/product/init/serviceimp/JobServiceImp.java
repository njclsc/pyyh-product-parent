package com.pyyh.product.init.serviceimp;

import com.pyyh.product.init.pojo.QuartzConfigPojo;

public class JobServiceImp extends AbstractJobServiceImp{
	private QuartzConfigPojo qcp;
	public JobServiceImp(QuartzConfigPojo qcp){
		this.qcp = qcp;
	}
	@Override
	public <T, E> T createJob(E e) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, E> T createTrigger(E e) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, E> T registJob(E e) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("---->>>" + qcp);
		return null;
	}

}
