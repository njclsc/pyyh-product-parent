package com.zh.middware.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevicePojo {
    private int id;
    private int campusId;
    private String campusName;
    private int zoneId;
    private String zoneName;
    private String deviceId;
    private String senseIn;
    private String senseOut;
    private String deviceSite;
    private String deviceSN;
    private int deviceType;
    private String createTime;
    private int operGid;
}
