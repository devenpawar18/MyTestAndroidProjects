package com.android.maps;

import com.google.android.maps.GeoPoint;

public class GlobalData {

	public static AgentDetails[] agentItems;

	static {
		agentItems = new AgentDetails[7];

		agentItems[0] = new AgentDetails();
		agentItems[0].setAgentName("Agent1");
		agentItems[0].setGeoPoint(new GeoPoint((int) (59.597521 * 1E6), (int) (16.528865 * 1E6)));

		agentItems[1] = new AgentDetails();
		agentItems[1].setAgentName("Agent2");
		agentItems[1].setGeoPoint(new GeoPoint((int) (59.51624 * 1E6), (int) (15.96609 * 1E6)));

		agentItems[2] = new AgentDetails();
		agentItems[2].setAgentName("Agent3");
		agentItems[2].setGeoPoint(new GeoPoint((int) (59.589092 * 1E6), (int) (16.50907 * 1E6)));

		agentItems[3] = new AgentDetails();
		agentItems[3].setAgentName("Agent4");
		agentItems[3].setGeoPoint(new GeoPoint((int) (59.597324 * 1E6), (int) (16.528286 * 1E6)));

		agentItems[4] = new AgentDetails();
		agentItems[4].setAgentName("Agent5");
		agentItems[4].setGeoPoint(new GeoPoint((int) (59.589265 * 1E6), (int) (16.514102 * 1E6)));

		agentItems[5] = new AgentDetails();
		agentItems[5].setAgentName("Agent6");
		agentItems[5].setGeoPoint(new GeoPoint((int) (59.582334 * 1E6), (int) (16.622565 * 1E6)));

		agentItems[6] = new AgentDetails();
		agentItems[6].setAgentName("Agent7");
		agentItems[6].setGeoPoint(new GeoPoint((int) (59.61711 * 1E6), (int) (16.21501 * 1E6)));
	}

}
