package com.zh.collection.util;

import javax.sql.DataSource;

public class ContainerUtil {
	private static DataSource dataSource;
	private static String rootPath;
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
}
