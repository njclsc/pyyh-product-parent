package com.zh.manager.business.dao;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.UnitPojo;

@Repository
public interface IUnitManagerDao {

	public int add(UnitPojo UnitPojo);
}
