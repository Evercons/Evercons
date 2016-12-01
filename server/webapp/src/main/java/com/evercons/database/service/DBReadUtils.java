package com.evercons.database.service;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.model.DeviceDetails;
import com.evercons.commons.model.UserDetails;

public class DBReadUtils {

	public static DeviceDetails readDeviceAndFencingData(Object[] values) {
		/*
		 * <return-scalar column="id" type="int"/> <return-scalar
		 * column="device_id" type="string"/> <return-scalar column="api_key"
		 * type="string"/> <return-scalar column="fencing_data" type="binary"/>
		 */

		DeviceDetails device = new DeviceDetails();
		device.setId(((Integer) values[0]).intValue());
		device.setDeviceId((String) values[1]);
		device.setApiKey((String) values[2]);
		device.setFencingData(readFencingData(values[3]));

		return device;
	}

	public static JSONArray getDevices(List<Object[]> result) {
		JSONArray devices = null;
		if (CollectionUtils.isNotEmpty(result)) {
			devices = new JSONArray();
			for (Object[] values : result) {
				JSONObject device = new JSONObject();
				device.put("id", ((Integer) values[0]).intValue());
				device.put("deviceId", (String) values[1]);
				devices.put(device);
			}
		}
		return devices;
	}

	public static JSONArray getDeviceAlertInfo(List<Object[]> result) {
		JSONArray alertInfo = null;
		if (CollectionUtils.isNotEmpty(result)) {
			alertInfo = new JSONArray();
			for (Object[] values : result) {
				alertInfo.put(readDeviceAlertInfo(values));
			}
		}
		return alertInfo;
	}

	public static JSONObject readDeviceAlertInfo(Object[] values) {
		/*
		 * <return-scalar column="user_id" type="int"/> <return-scalar
		 * column="name" type="string"/> <return-scalar column="type"
		 * type="string"/> <return-scalar column="device_configuration"
		 * type="string"/>
		 */

		JSONObject alert = new JSONObject();
		alert.put("userId", (Integer) values[0]);
		alert.put("name", (String) values[1]);
		alert.put("alertType", (String) values[2]);
		alert.put("configuration", (String) values[3]);

		return alert;
	}

	public static UserDetails readUserDetails(Object[] values) {
		/*
		 * <resultset name="userDetails"> <return-scalar column="userId"
		 * type="int"/> <return-scalar column="loginId" type="string"/>
		 * <return-scalar column="emailId" type="string"/> <return-scalar
		 * column="primaryContact" type="string"/> <return-scalar
		 * column="secondaryContact" type="string"/> <return-scalar
		 * column="name" type="string"/> </resultset>
		 */

		UserDetails user = new UserDetails();
		user.setId((Integer) values[0]);
		user.setLoginId((String) values[1]);
		user.setEmailId((String) values[2]);
		user.setPrimaryContact((String) values[3]);
		user.setSecondaryContact((String) values[4]);
		user.setFirstName((String) values[5]);
		user.setLastName((String) values[6]);

		return user;
	}

	private static JSONArray readFencingData(Object value) {
		JSONArray fencingData = null;
		byte[] data = readByteArray(value);
		if (data != null) {
			String dataStr = new String(data);
			fencingData = new JSONArray(dataStr);
		}

		return fencingData;
	}

	private static byte[] readByteArray(Object value) {
		byte[] data = null;
		if (value != null) {
			if (value instanceof Blob)
				data = getByteArray((Blob) value);
			else
				data = (byte[]) value;
		}

		return data;
	}

	private static byte[] getByteArray(Blob blob) {
		byte[] data = null;
		try {
			data = IOUtils.toByteArray(blob.getBinaryStream());
		} catch (Exception e) {
		}

		return data;
	}

	public static JSONArray getLogSummary(List<Object[]> result) {
		JSONArray logSummary = null;
		if (CollectionUtils.isNotEmpty(result)) {
			logSummary = new JSONArray();
			for (Object[] values : result) {
				logSummary.put(readLogSummary(values));
			}
		}
		return logSummary;
	}

	public static JSONObject readLogSummary(Object[] values) {
		/*
		 * <return-scalar column="logId" type="string" />
		<return-scalar column="latitude" type="string" />
		<return-scalar column="longitude" type="string" />
		<return-scalar column="etype" type="int" />
		<return-scalar column="time" type="datetime" />
		 */

		JSONObject data = new JSONObject();
		data.put("logId", (String) values[0]);
		data.put("latitude", (String) values[1]);
		data.put("longitude", (String) values[2]);
		data.put("etype", (Integer) values[3]);
		data.put("time", (Timestamp) values[4]);

		return data;
	}

}
