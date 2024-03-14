package com.zh.manager.util;

import java.text.SimpleDateFormat;

public class ContainerUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		ContainerUtil.sdf = sdf;
	}
}
