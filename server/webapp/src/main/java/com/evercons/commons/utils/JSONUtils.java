package com.evercons.commons.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {

	public static boolean isNotEmpty(JSONArray data) {
		return (data != null && data.length() > 0);
	}

	public static JSONObject toJSON(String data) {
		return new JSONObject(data);
	}
}
