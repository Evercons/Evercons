package com.evercons.server.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.model.DeviceDetails;
import com.evercons.commons.utils.CommonUtils;
import com.evercons.commons.utils.GCMService;
import com.evercons.commons.utils.SendMail;
import com.evercons.database.beans.Device;
import com.evercons.database.beans.DeviceLogEvents;
import com.evercons.database.beans.DeviceLogEventsId;
import com.evercons.database.beans.EventTypes;
import com.evercons.database.beans.LocationTypes;

public class EventLogController extends EverconsControllerServlet {

	private static final long serialVersionUID = 1L;

	private SendMail mMailSender_ = null;
	private GCMService mGcmService_ = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mMailSender_ = SendMail.getInstance();
		mGcmService_ = new GCMService();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("doPost called");
		resp.setHeader("Access-Control-Allow-Origin","*");

		String logData = (String) req.getParameter("data");
		logger.debug(logData);
		JSONObject data = new JSONObject(logData);

		String deviceId = data.getString("devid");
		DeviceDetails device = mService_.getDeviceAndFencingData(deviceId);
		if (device == null) {
			logger.error("Device is not found for device_id" + deviceId);
			return;
		}

		DeviceLogEventsId event = new DeviceLogEventsId();
		event.setDeviceId(device.getId());
		System.out.println("logid=" + data.getInt("logid"));
		event.setLogid(data.getInt("logid"));
		event.setEvent(data.optString("event"));
		event.setEventTime(new Timestamp(CommonUtils.getTime(data.getString("time")).getTime()));
		event.setResult(data.optString("result"));
		event.setBac(data.optString("bac"));
		event.setEventType(mService_.getEventTypeId(data.getString("etype")));
		event.setImage(data.optString("image"));
		event.setLatitude(data.getString("lat"));
		event.setLongitude(data.getString("lon"));
		event.setSpeed(new BigDecimal(data.getString("speed")));
		event.setInfo(data.optString("info"));
		event.setLocType(mService_.getLocationTypeId(data.getString("ltype")));

		DeviceLogEvents logEvent = new DeviceLogEvents();
		logEvent.setId(event);

		Device deviceObj = new Device();
		deviceObj.setId(device.getId());

		logEvent.setDevice(deviceObj);

		EventTypes eventType = new EventTypes();
		eventType.setId(event.getEventType());
		logEvent.setEventTypes(eventType);

		LocationTypes locationType = new LocationTypes();
		locationType.setId(event.getLocType());
		logEvent.setLocationTypes(locationType);

		mService_.addDeviceLogEvent(logEvent);

		// Check for boundary breach
		boolean isFencingBreached = mService_.isFencingBreached(device, event);
		if (isFencingBreached) {
			// Send alert
			JSONArray alertInfo = mService_.getDeviceAlertInfo(device.getId());
			if (alertInfo != null && alertInfo.length() > 0) {
				for (int i = 0; i < alertInfo.length(); i++) {
					JSONObject alert = alertInfo.getJSONObject(i);
					String alertType = alert.getString("alertType");
					if (alertType.equalsIgnoreCase("email")) {
						String[] toAddress = new String[] { alert.getString("configuration") };
						String subject = "Boundary breached by device " + deviceId;
						String message = "Dear " + alert.getString("name") + ", \nBoundary breached by device "
								+ deviceId + " @ time : " + data.getString("time");
						mMailSender_.sendMail(toAddress, subject, message);
					} else if (alertType.equalsIgnoreCase("Android")) {
						ArrayList<String> toAddress = new ArrayList<String>();
						toAddress.add(alert.getString("configuration"));
						String subject = "Boundary breached by device " + deviceId;
						String message = "Dear " + alert.getString("name") + ", \nBoundary breached by device "
								+ deviceId + " @ time : " + data.getString("time");
						mGcmService_.sendMessage(toAddress, message);
					}
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Do Get called");
	}
}
