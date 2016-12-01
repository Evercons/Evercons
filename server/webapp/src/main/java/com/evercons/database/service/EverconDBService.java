package com.evercons.database.service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evercons.commons.model.DeviceDetails;
import com.evercons.commons.model.UserDetails;
import com.evercons.database.beans.AlertType;
import com.evercons.database.beans.Device;
import com.evercons.database.beans.DeviceLogEvents;
import com.evercons.database.beans.EventTypes;
import com.evercons.database.beans.LocationTypes;
import com.evercons.database.beans.Users;

@Service
public class EverconDBService {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private EverconDAOImpl everconDAOImpl;

	private Hashtable<String, Integer> alertTypes = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> eventTypes = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> locationTypes = new Hashtable<String, Integer>();
	private Hashtable<String, DeviceDetails> deviceIds = new Hashtable<String, DeviceDetails>();

	@Transactional(rollbackFor = Exception.class)
	public void addDevice(Device device) {
		everconDAOImpl.addDevice(device);
	}

	@Transactional(rollbackFor = Exception.class)
	public void addUser(Users user) {
		everconDAOImpl.addUser(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void addDeviceLogEvent(DeviceLogEvents event) {
		everconDAOImpl.addDeviceLogEvent(event);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateCache() {
		logger.debug("updateCache start: ");
		List<AlertType> types = everconDAOImpl.getAlertTypes();
		if (CollectionUtils.isNotEmpty(types)) {
			for (AlertType type : types) {
				alertTypes.put(type.getType(), type.getId());
			}
		}

		List<LocationTypes> locTypes = everconDAOImpl.getLocTypes();
		if (CollectionUtils.isNotEmpty(types)) {
			for (LocationTypes type : locTypes) {
				locationTypes.put(type.getType(), type.getId());
			}
		}

		List<EventTypes> eTypes = everconDAOImpl.getEventTypes();
		if (CollectionUtils.isNotEmpty(types)) {
			for (EventTypes type : eTypes) {
				eventTypes.put(type.getType(), type.getId());
			}
		}

		logger.debug(alertTypes);
		logger.debug(locationTypes);
		logger.debug(eventTypes);
		logger.debug("updateCache end : ");
	}

	public int getLocationTypeId(String type) {
		logger.debug("getLocationTypeId start : ");
		return locationTypes.get(type);
	}

	public int getAlertTypeId(String type) {
		logger.debug("getAlertTypeId start : ");
		return alertTypes.get(type);
	}

	public int getEventTypeId(String type) {
		logger.debug("getEventTypeId start : ");
		return eventTypes.get(type);
	}

	@Transactional(rollbackFor = Exception.class)
	public DeviceDetails getDeviceDetails(String deviceId) {
		logger.debug("getDeviceDetails start : " + deviceId);
		DeviceDetails device = deviceIds.get(deviceId);
		if (device == null) {
			device = everconDAOImpl.getDeviceAndFencingData(deviceId);
			deviceIds.put(deviceId, device);
		}
		logger.debug("getDeviceDetails end : " + device);

		return device;
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertOrUpdateFencingData(int deviceId, JSONArray fencingData) {
		logger.debug("insertOrUpdateFencingData start : ");
		everconDAOImpl.insertOrUpdateFencingData(deviceId, fencingData);
		logger.debug("insertOrUpdateFencingData end : ");
	}

	@Transactional(rollbackFor = Exception.class)
	public int removeUserDeviceAssociation(int userId, int deviceId) {
		logger.debug("removeUserDeviceAssociation start : ");
		return everconDAOImpl.removeUserDeviceAssociation(userId, deviceId);
	}

	@Transactional(rollbackFor = Exception.class)
	public int addUserDeviceAssociation(int userId, int deviceId) {
		logger.debug("addUserDeviceAssociation start : ");
		return everconDAOImpl.addUserDeviceAssociation(userId, deviceId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertOrUpdateAlertConfiguration(int userId, int alertType, String data) {
		logger.debug("insertOrUpdateAlertConfiguration start : ");
		everconDAOImpl.insertOrUpdateAlertConfiguration(userId, alertType, data);
		logger.debug("insertOrUpdateAlertConfiguration end : ");
	}
	
	@Transactional(rollbackFor = Exception.class)
	public JSONArray getDeviceAlertInfo(int deviceId) {
		return everconDAOImpl.getDeviceAlertInfo(deviceId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int authenticateUser(String username, String password) {
		return everconDAOImpl.authenticateUser(username, password);
	}

	@Transactional(rollbackFor = Exception.class)
	public UserDetails getUserDetails(int userId) {
		return everconDAOImpl.getUserDetails(userId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public JSONArray getUserDevices(int userId) {
		return everconDAOImpl.getUserDevices(userId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int validateRegister(String loginId, String activationKey) {
		return everconDAOImpl.validateRegister(loginId, activationKey);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public JSONArray getDeviceLogData(int deviceId, Date startDate, Date endDate) {
		return everconDAOImpl.getDeviceLogData(deviceId, startDate, endDate);
	}
	
}
