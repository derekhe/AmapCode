package com.autonavi.minimap.route.voice.model;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RoutePlanModel {
    public PoiModel endPoi;
    public String rideType = "bike";
    public PoiModel startPoi;
    public int t = 1;
}
