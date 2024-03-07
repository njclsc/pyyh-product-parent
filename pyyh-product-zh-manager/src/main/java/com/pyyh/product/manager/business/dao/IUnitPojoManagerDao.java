package com.pyyh.product.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pyyh.product.manager.pojo.UnitPojo;

@Repository
public interface IUnitPojoManagerDao {
	public List<UnitPojo> findAll(UnitPojo UnitPojo);
	public UnitPojo findById(UnitPojo UnitPojo);
	public void add(UnitPojo UnitPojo);
	public UnitPojo delete(UnitPojo UnitPojo);
	public UnitPojo update(UnitPojo UnitPojo);
}
