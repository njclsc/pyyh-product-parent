package com.pyyh.product.init.serviceimp;

import com.pyyh.product.init.service.ISourceService;

import lombok.Getter;

@Getter
public class AbstractSourceServiceImp implements ISourceService{
	private String sourceLocation;
	
	public AbstractSourceServiceImp(){
		this.sourceLocation = System.getProperty("user.dir");
	}
	@Override
	public <T, P> T loadSource(P p) throws Exception {
		throw new Exception();
	}

	@Override
	public <T, P> T registSource(P p) throws Exception{
		// TODO Auto-generated method stub
		throw new Exception();
	}

}
