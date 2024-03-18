package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.TagPojo;

@Repository
public interface ITagManagerDao {
	public int add(TagPojo rp);
	public int addTimly(TagPojo rp);
	public void delete(TagPojo rp);
	public void deleteTimly(TagPojo rp);
	public void update(TagPojo rp);
	public TagPojo findById(TagPojo rp);
	public List<TagPojo> findAll(TagPojo rp);
}
