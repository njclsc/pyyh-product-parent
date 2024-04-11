package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.DevicePojo;

@Repository
public interface IDeviceManagerDao {
	public int add(DevicePojo rp);
	public void update(DevicePojo rp);
	public void delete(DevicePojo rp);
	public DevicePojo findById(DevicePojo rp);
	public List<DevicePojo> findAll(DevicePojo rp);
	public int count(DevicePojo rp);
}
