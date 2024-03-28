package com.zh.collection.pojo;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CachePojo<K, UnitV, AreaV, DeviceV, tagV, ruleV, timlyV, vehicleV> {
	private HashMap<K, UnitV> unitCache;
	private HashMap<K, AreaV> areaCache;
	private HashMap<K, DeviceV> deviceCache;
	private HashMap<K, tagV> tagCache;
	private HashMap<K, ruleV> ruleCache;
	private HashMap<K, timlyV> timlyCache;
	private HashMap<K, vehicleV> vehicleCache;
}
