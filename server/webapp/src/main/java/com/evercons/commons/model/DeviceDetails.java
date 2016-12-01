package com.evercons.commons.model;

import org.json.JSONArray;

public class DeviceDetails {

	private int id;
	private String deviceId;
	private String apiKey;
	private JSONArray fencingData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public JSONArray getFencingData() {
		return fencingData;
	}

	public void setFencingData(JSONArray fencingData) {
		this.fencingData = fencingData;
	}

}
