package com.pyyh.product.login.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pyyh.product.login.pojo.MenuPojo;
import com.pyyh.product.login.pojo.RolePojo;
import com.pyyh.product.login.pojo.UserPojo;

@Repository
public interface ILoginDao {

	public UserPojo findUser(UserPojo user);
	public RolePojo findRole(UserPojo user);
	public List<MenuPojo> loadMenu(MenuPojo menu);
}
