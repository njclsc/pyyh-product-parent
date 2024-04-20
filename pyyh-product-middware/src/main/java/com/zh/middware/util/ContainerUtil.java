package com.zh.middware.util;

import com.zh.middware.pojos.RemoteConfigPojo;

public class ContainerUtil {
	private static RemoteConfigPojo remoteConfig;

	public static RemoteConfigPojo getRemoteConfig() {
		return remoteConfig;
	}

	public static void setRemoteConfig(RemoteConfigPojo remoteConfig) {
		ContainerUtil.remoteConfig = remoteConfig;
	}
}
