package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.UnitPojo;

@Repository
public interface IUnitManagerDao {

	public int add(UnitPojo UnitPojo);
	public void delete(UnitPojo UnitPojo);
	public void dropTable(UnitPojo UnitPojo);
	public void update(UnitPojo UnitPojo);
	public List<UnitPojo> find(UnitPojo UnitPojo);
	public int count(UnitPojo UnitPojo);
}
