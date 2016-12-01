package com.evercons.database.service;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.model.DeviceDetails;
import com.evercons.commons.model.UserDetails;
import com.evercons.database.beans.Device;
import com.evercons.database.beans.DeviceLogEvents;
import com.evercons.database.beans.DeviceLogEventsId;
import com.evercons.database.beans.Users;

public class EverconService {

	private Log logger = LogFactory.getLog(getClass());

	private static EverconService mInstance_;

	private EverconDBService mDbService_;

	private EverconService() {
		SpringBeanUtils beanUtils = new SpringBeanUtils();
		mDbService_ = (EverconDBService) beanUtils.getBean("everconDBService");
		mDbService_.updateCache();
	}

	public static EverconService getInstance() {
		if (mInstance_ == null)
			mInstance_ = new EverconService();

		return mInstance_;
	}

	public void addDevice(Device device) {
		mDbService_.addDevice(device);
	}

	public void addUser(Users user) {
		mDbService_.addUser(user);
	}

	public void addDeviceLogEvent(DeviceLogEvents event) {
		mDbService_.addDeviceLogEvent(event);
	}

	public Integer getLocationTypeId(String type) {
		if (type != null)
			return mDbService_.getLocationTypeId(type);
		else
			return null;
	}

	public Integer getAlertTypeId(String type) {
		if (type != null)
			return mDbService_.getAlertTypeId(type);
		else
			return null;
	}

	public Integer getEventTypeId(String type) {
		if (type != null)
			return mDbService_.getEventTypeId(type);
		else
			return null;
	}

	public DeviceDetails getDeviceAndFencingData(String deviceId) {
		return mDbService_.getDeviceDetails(deviceId);
	}

	public void insertOrUpdateFencingData(int deviceId, JSONArray fencingData) {
		logger.debug("Service - insertOrUpdateAlertConfiguration start : ");
		mDbService_.insertOrUpdateFencingData(deviceId, fencingData);
		logger.debug("Service - insertOrUpdateAlertConfiguration end : ");
	}

	public boolean isFencingBreached(DeviceDetails device, DeviceLogEventsId event) {
		boolean isFencingBreached = false;
		JSONArray fencingData = device.getFencingData();
		if (fencingData != null && fencingData.length() > 0) {
			for (int i = 0; i < fencingData.length(); i++) {
				JSONObject dataPoint = fencingData.getJSONObject(i);
				float radius = NumberUtils.toFloat(dataPoint.getString("radius"));
				float longitude1 = NumberUtils.toFloat(dataPoint.getString("longitude"));
				float latitude1 = NumberUtils.toFloat(dataPoint.getString("latitude"));
				float longitude2 = NumberUtils.toFloat(event.getLongitude());
				float latitude2 = NumberUtils.toFloat(event.getLatitude());
				isFencingBreached = calculateDistance(longitude1, latitude1, longitude2, latitude2) > radius;
				if (isFencingBreached) {
					logger.error("Fencing breached for device " + event.getDeviceId() + " for boundary " + dataPoint);
					break;
				}
			}
		}
		logger.debug("Service - isFencingBreached end : " + isFencingBreached);
		return isFencingBreached;
	}

	private double calculateDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		logger.debug("Service - calculateDistance start : " +longitude1 + "::" + longitude2 + "--" + latitude1 + "::" + latitude2);
		double c = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2))
				+ Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
						* Math.cos(Math.toRadians(longitude2) - Math.toRadians(longitude1));
		c = c > 0 ? Math.min(1, c) : Math.max(-1, c);
		double distance = 3959 * 1.609 * 1000 * Math.acos(c);
		logger.debug("Service - calculateDistance end : " + distance);
		return distance;
	}

	public int removeUserDeviceAssociation(int userId, int deviceId) {
		return mDbService_.removeUserDeviceAssociation(userId, deviceId);
	}

	public int addUserDeviceAssociation(int userId, int deviceId) {
		return mDbService_.addUserDeviceAssociation(userId, deviceId);
	}

	public void insertOrUpdateAlertConfiguration(int userId, int alertType, String data) {
		mDbService_.insertOrUpdateAlertConfiguration(userId, alertType, data);
	}
	
	public JSONArray getDeviceAlertInfo(int deviceId) {
		return mDbService_.getDeviceAlertInfo(deviceId);
	}
	
	public int authenticateUser(String username, String password) {
		return mDbService_.authenticateUser(username, password);
	}
	
	public UserDetails getUserDetails(int userId) {
		return mDbService_.getUserDetails(userId);
	}
	
	public JSONArray getUserDevices(int userId) {
		return mDbService_.getUserDevices(userId);
	}

	public int validateRegister(String loginId, String activationKey) {
		return mDbService_.validateRegister(loginId, activationKey);
	}

	public JSONArray getDeviceLogData(int deviceId, Date startDate, Date endDate) {
		return mDbService_.getDeviceLogData(deviceId, startDate, endDate);
	}
	
}
