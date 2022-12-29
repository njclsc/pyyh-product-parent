package com.pyyh.product.init.service;

public interface ISourceService {

	public <T, P> T loadSource(P p)throws Exception;
	public <T, P> T registSource(P p)throws Exception;
	public String getSourceLocation();
}
