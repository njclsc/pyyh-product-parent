package com.zh.middware.init.service;

public interface ISourceService {

	public <T, P> T loadSource(P p) throws Exception;
}
