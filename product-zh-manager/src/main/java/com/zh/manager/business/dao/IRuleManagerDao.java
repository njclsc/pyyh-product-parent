package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.RulePojo;

@Repository
public interface IRuleManagerDao {

	public int add(RulePojo rp);
	public void update(RulePojo rp);
	public void delete(RulePojo rp);
	public RulePojo findById(RulePojo rp);
	public List<RulePojo> findAll(RulePojo rp);
}
