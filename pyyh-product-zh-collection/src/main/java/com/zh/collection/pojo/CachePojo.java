package com.zh.collection.pojo;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CachePojo<UnitK, UnitV, AreaK, AreaV, DeviceK, DeviceV, tagK, tagV, ruleK, ruleV> {
	
	private HashMap<UnitK, UnitV> unitCache;
	private HashMap<AreaK, AreaV> areaCache;
	private HashMap<DeviceK, DeviceV> deviceCache;
	private HashMap<DeviceK, DeviceV> tagCache;
	private HashMap<ruleK, ruleV> ruleCache;
}
