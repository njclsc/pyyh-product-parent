package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.MenuPojo;
import com.zh.manager.business.pojo.RolePojo;

@Repository
public interface IRoleManagerDao {

	public int add(RolePojo rp);
	public void delete(RolePojo rp);
	public void update(RolePojo rp);
	public List<RolePojo> find(RolePojo rp);
	public int count(RolePojo rp);
	public List<MenuPojo> loadMenu(MenuPojo menu);
	
}
