package com.evercons.commons.utils;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class HTTPUtils {

	public static JSONObject readRequest(HttpServletRequest req) {
		InputStream is = null;
		JSONObject input = null;
		try {
			input = JSONUtils.toJSON(IOUtils.toString(req.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

		return input;
	}
}
