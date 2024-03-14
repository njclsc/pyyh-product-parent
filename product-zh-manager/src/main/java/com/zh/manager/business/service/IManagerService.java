package com.zh.manager.business.service;

public interface IManagerService {

	public <T, P> T add(P p);
	public <T, P> T delete(P p);
	public <T, P> T update(P p);
	public <T, P> T findById(P p);
	public <T, P> T findAll(P p);
}
