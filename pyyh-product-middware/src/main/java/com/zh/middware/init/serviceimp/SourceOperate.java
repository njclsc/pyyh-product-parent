package com.zh.middware.init.serviceimp;

import com.zh.middware.init.service.ISourceService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SourceOperate implements ISourceService{
	private String root;
	
	public SourceOperate(){
		this.root = System.getProperty("user.dir");
	}
}
