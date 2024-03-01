package com.pyyh.product.login.business.dao;

import org.springframework.stereotype.Repository;

import com.pyyh.product.login.pojo.UserPojo;

@Repository
public interface ILoginDao {

	public UserPojo findUser(UserPojo user);
}
