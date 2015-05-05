package com.htmlpagedemo.entity;

import org.json.JSONObject;

interface JSONSerializable
{
	JSONObject serializeJSON() throws Exception;
	void deserializeJSON(JSONObject jsonObject) throws Exception;
}