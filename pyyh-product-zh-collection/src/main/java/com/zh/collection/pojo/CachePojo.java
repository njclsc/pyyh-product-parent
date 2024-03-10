package com.zh.collection.pojo;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CachePojo<UnitK, UnitV, AreaK, AreaV, DeviceK, DeviceV, tagK, tagV, ruleK, ruleV, timlyK, timlyV> {
	private HashMap<UnitK, UnitV> unitCache;
	private HashMap<AreaK, AreaV> areaCache;
	private HashMap<DeviceK, DeviceV> deviceCache;
	private HashMap<tagK, tagV> tagCache;
	private HashMap<ruleK, ruleV> ruleCache;
	private HashMap<timlyK, timlyV> timlyCache;
}
