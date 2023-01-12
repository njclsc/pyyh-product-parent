package com.pyyh.product.init.service;

public interface IJobService {

	public <T, E> T createJob(E e) throws Exception;
	public <T, E> T createTrigger(E e) throws Exception;
	public <T, E> T registJob(E e) throws Exception;
}
