package com.zh.middware.init.service;

public interface ISourceService {

	public <T, P> T loadSource(P p, Class<T> clezz) throws Exception;
}
