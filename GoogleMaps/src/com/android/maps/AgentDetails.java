package com.android.maps;

import com.google.android.maps.GeoPoint;

public class AgentDetails {
	private GeoPoint geoPoint;
	private String agentName;
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
}
