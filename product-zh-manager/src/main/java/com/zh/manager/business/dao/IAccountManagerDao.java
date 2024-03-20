package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.AccountPojo;

@Repository
public interface IAccountManagerDao {

	public int add(AccountPojo ap);
	public void delete(AccountPojo ap);
	public void update(AccountPojo ap);
	public List<AccountPojo> find(AccountPojo ap);
	public int count(AccountPojo ap);
}
