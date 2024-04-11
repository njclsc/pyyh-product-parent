package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.AreaPojo;

@Repository
public interface IAreaManagerDao {
	public int add(AreaPojo ap);
	public void delete(AreaPojo ap);
	public void update(AreaPojo ap);
	public List<AreaPojo> find(AreaPojo ap);
	public int count(AreaPojo ap);
}
