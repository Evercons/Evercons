package com.evercons.server.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.model.UserDetails;
import com.evercons.commons.utils.CommonUtils;
import com.evercons.commons.utils.HTTPUtils;
import com.evercons.commons.utils.JSONUtils;
import com.evercons.database.beans.Device;

public class DeviceController extends EverconsControllerServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("doPost called");
		resp.setHeader("Access-Control-Allow-Origin","*");
		// if(isValidSession(req)) {
		// return;
		// }
		JSONObject input = getInput(req);
		String cmd = getCmd(input);
		String deviceData = getdata(input);

		if (StringUtils.equalsIgnoreCase(cmd, "createDevice")) {
			logger.debug(deviceData);
			JSONObject data = new JSONObject(deviceData);
			Device device = new Device();
			device.setDeviceId(data.getString("deviceId"));
			device.setApiKey(UUID.randomUUID().toString());
			device.setCreatedTime(CommonUtils.getCurrentTimeStamp());
			device.setDeviceStatus('A');
			device.setImei(data.optString("imei"));
			mService_.addDevice(device);
		} else if (StringUtils.equalsIgnoreCase(cmd, "updateFencingData")) {
			logger.debug(deviceData);
			JSONObject data = new JSONObject(deviceData);
			int deviceId = data.getInt("deviceId");
			JSONArray fencingData = data.getJSONArray("fencingData");
			mService_.insertOrUpdateFencingData(deviceId, fencingData);
		} else if (StringUtils.equalsIgnoreCase(cmd, "deviceData")) {
			logger.debug(deviceData);
			JSONObject data = new JSONObject(deviceData);
			int deviceId = data.getInt("deviceId");
			Date startDate = getDate(data.optString("startDate"));
			Date endDate = getEndDate(getDate(data.optString("endDate")));
			JSONArray logData = mService_.getDeviceLogData(deviceId, startDate, endDate);
			
			JSONObject result = new JSONObject();
			result.put("status", "success");
			result.put("logData", logData);
			IOUtils.write(result.toString(), resp.getOutputStream());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Do Get called");
	}

	private Date getDate(String date) {
		Date value = CommonUtils.getDate(date);
//		return (value != null) ? value : new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime((value != null) ? value : new Date());
		calendar.add(Calendar.YEAR, -1);
		return calendar.getTime();
	}

	private Date getEndDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
}