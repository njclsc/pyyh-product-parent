package com.zh.collection.util;

import javax.sql.DataSource;

import com.zh.collection.pojo.CachePojo;

public class ContainerUtil {
	private static DataSource dataSource;
	private static String rootPath;
	private static CachePojo cache;
	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		ContainerUtil.dataSource = dataSource;
	}

	public static String getRootPath() {
		return rootPath;
	}

	public static void setRootPath(String rootPath) {
		ContainerUtil.rootPath = rootPath;
	}

	public static CachePojo getCache() {
		return cache;
	}

	public static void setCache(CachePojo cache) {
		ContainerUtil.cache = cache;
	}
}
