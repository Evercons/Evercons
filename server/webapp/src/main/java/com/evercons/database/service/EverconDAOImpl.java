package com.evercons.database.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evercons.commons.model.DeviceDetails;
import com.evercons.commons.model.UserDetails;
import com.evercons.database.beans.AlertType;
import com.evercons.database.beans.Device;
import com.evercons.database.beans.DeviceLogEvents;
import com.evercons.database.beans.EventTypes;
import com.evercons.database.beans.GeoFencingData;
import com.evercons.database.beans.LocationTypes;
import com.evercons.database.beans.Users;

@Service
public class EverconDAOImpl {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	public void addDevice(Device device) {
		try {
			logger.debug("add Device");
			Session session = sessionFactory.getCurrentSession();
			session.save(device);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addUser(Users user) {
		try {
			logger.debug("add Device");
			Session session = sessionFactory.getCurrentSession();
			session.save(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addDeviceLogEvent(DeviceLogEvents event) {
		try {
			logger.debug("addDeviceLogEvent");
			Session session = sessionFactory.getCurrentSession();
			session.save(event);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public DeviceDetails getDeviceAndFencingData(String deviceId) {
		logger.debug("getDeviceAndFencingData start : " + deviceId);
		DeviceDetails device = null;
		try {
			logger.debug("add Device");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("getDeviceAndFencingData");
			query.setString("deviceId", deviceId);
			List<Object[]> result = query.list();
			if (!result.isEmpty()) {
				device = DBReadUtils.readDeviceAndFencingData(result.get(0));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("getDeviceAndFencingData end : " + device);
		return device;
	}

	public List<AlertType> getAlertTypes() {
		List<AlertType> types = null;
		try {
			logger.debug("getAlertTypes");
			Session session = sessionFactory.getCurrentSession();
			types = session.createQuery("from AlertType").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return types;
	}

	public List<LocationTypes> getLocTypes() {
		List<LocationTypes> types = null;
		try {
			logger.debug("getAlertTypes");
			Session session = sessionFactory.getCurrentSession();
			types = session.createQuery("from LocationTypes").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return types;
	}

	public List<EventTypes> getEventTypes() {
		List<EventTypes> types = null;
		try {
			logger.debug("getAlertTypes");
			Session session = sessionFactory.getCurrentSession();
			types = session.createQuery("from EventTypes").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return types;
	}

	public void insertOrUpdateFencingData(int deviceId, JSONArray fencingData) {
		logger.debug("insertOrUpdateFencingData start : " + deviceId + "::" + fencingData);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from GeoFencingData WHERE device.id= :deviceId");
			query.setInteger("deviceId", deviceId);
			GeoFencingData result = (GeoFencingData) query.uniqueResult();
			if (result == null) {
				Device device = new Device();
				device.setId(deviceId);
				result = new GeoFencingData();
				result.setDevice(device);
			}

			result.setFencingData(fencingData.toString().getBytes());

			session.saveOrUpdate(result);
			session.flush();
			logger.debug("insertOrUpdateFencingData result : " + result.getId());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("insertOrUpdateFencingData end : ");
	}

	public int removeUserDeviceAssociation(int userId, int deviceId) {
		logger.debug("removeUserDeviceAssociation start : " + userId + "::" + deviceId);
		int result = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("removeUserDeviceAssociation");
			query.setInteger("userId", userId);
			query.setInteger("deviceId", deviceId);
			result = query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("removeUserDeviceAssociation end : " + result);
		return result;
	}

	public int addUserDeviceAssociation(int userId, int deviceId) {
		logger.debug("addUserDeviceAssociation start : " + userId + "::" + deviceId);
		int result = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("addUserDeviceAssociation");
			query.setInteger("userId", userId);
			query.setInteger("deviceId", deviceId);
			result = query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("addUserDeviceAssociation end : " + result);
		return result;
	}

	public void insertOrUpdateAlertConfiguration(int userId, int alertType, String data) {
		logger.debug("insertOrUpdateAlertConfiguration start : " + userId + "::" + alertType + "::" + data);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("isAlertConfigurationExists");
			query.setInteger("userId", userId);
			query.setInteger("alertTypeId", alertType);

			String queryName = null;
			if (CollectionUtils.isNotEmpty(query.list())) {
				queryName = "updateUserAlertConfiguration";
			} else {
				queryName = "addUserAlertConfiguration";
			}
			query = session.getNamedQuery(queryName);
			query.setInteger("userId", userId);
			query.setInteger("alertTypeId", alertType);
			query.setString("configuration", data);
			int result = query.executeUpdate();
			logger.debug("insertOrUpdateAlertConfiguration  : " + queryName + " : " + result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("insertOrUpdateAlertConfiguration end : ");
	}

	public JSONArray getDeviceAlertInfo(int deviceId) {
		JSONArray alertInfo = null;
		logger.debug("getDeviceAlertInfo start : " + deviceId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("getDeviceAlertInfo");
			query.setInteger("deviceId", deviceId);
			List<Object[]> result = query.list();
			if (!result.isEmpty()) {
				alertInfo = DBReadUtils.getDeviceAlertInfo(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("getDeviceAlertInfo end : " + alertInfo);

		return alertInfo;
	}

	public int authenticateUser(String username, String password) {
		int userId = -1;

		logger.debug("authenticateUser start : " + username);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("authenticateUser");
			query.setString("loginId", username);
			query.setString("password", password);

			if (query.list() != null && query.list().size() > 0) {
				userId = (Integer) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("authenticateUser end : " + userId);

		return userId;
	}

	public UserDetails getUserDetails(int userId) {
		UserDetails user = null;

		logger.debug("getUserDetails start : " + userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("getUserDetails");
			query.setInteger("userId", userId);
			List<Object[]> result = query.list();
			if (!result.isEmpty()) {
				user = DBReadUtils.readUserDetails(result.get(0));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("getUserDetails end : " + user);

		return user;
	}

	public JSONArray getUserDevices(int userId) {
		JSONArray userDevices = null;
		logger.debug("getUserDevices start : " + userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("getUserDevices");
			query.setInteger("userId", userId);
			List<Object[]> result = query.list();
			if (!result.isEmpty()) {
				userDevices = DBReadUtils.getDevices(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("getUserDevices end : " + userDevices);

		return userDevices;
	}

	public int validateRegister(String loginId, String activationKey) {
		int retValue = -1;

		logger.debug("validateRegister start : " + loginId + "::" + activationKey);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("validateRegister");
			query.setString("loginId", loginId);
			query.setString("activationKey", activationKey);
			retValue = query.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("validateRegister end : " + retValue);

		return retValue;
	}

	public JSONArray getDeviceLogData(int deviceId, Date startDate, Date endDate) {
		JSONArray retValue = null;

		logger.debug("getDeviceLogData start : " + deviceId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.getNamedQuery("getDeviceLogData");
			query.setInteger("deviceId", deviceId);
			query.setDate("startDate", startDate);
			query.setDate("endDate", endDate);
			List<Object[]> result = query.list();
			if (!result.isEmpty()) {
				retValue = DBReadUtils.getLogSummary(result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("getDeviceLogData end : " + retValue);

		return retValue;
	}
}
