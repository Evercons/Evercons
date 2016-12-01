package com.evercons.server.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.json.JSONObject;

import com.evercons.commons.utils.CommonUtils;
import com.evercons.commons.utils.HTTPUtils;
import com.evercons.commons.utils.SendMail;
import com.evercons.database.beans.Users;

public class UserController extends EverconsControllerServlet {

	private static final long serialVersionUID = 1L;
	private SendMail mMailSender_ = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mMailSender_ = SendMail.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("doPost called");
		resp.setHeader("Access-Control-Allow-Origin","*");
		// if(isValidSession(req)) {
		// return;
		// }
		JSONObject input = HTTPUtils.readRequest(req);

		String cmd = input.getString("cmd");
		if (StringUtils.equalsIgnoreCase(cmd, "createUser")) {
			String userData = input.getString("data");
			logger.debug(userData);
			JSONObject data = new JSONObject(userData);
			Users user = new Users();
			user.setFirstName(data.getString("firstName"));
			user.setLastName(data.getString("lastName"));
			user.setEmailId(data.getString("emailId"));
			user.setLoginId(data.getString("loginId"));
			user.setPasswd(data.getString("passwd"));
			user.setStatus('R');
			user.setActivationKey(UUID.randomUUID().toString());
			user.setPrimaryContact(data.optString("primaryContact"));
			user.setSecondaryContact(data.optString("secondaryContact"));
			user.setCreatedTime(CommonUtils.getCurrentTimeStamp());
			user.setUpdatedTime(CommonUtils.getCurrentTimeStamp());
			user.setFailedLoginCount(0);
			mService_.addUser(user);
			sendMail(req, user);
		} else if (StringUtils.equalsIgnoreCase(cmd, "updateUserDeviceAssociation")) {
			String userData = input.getString("data");
			logger.debug(userData);
			JSONObject data = new JSONObject(userData);
			String action = data.getString("action");
			int userId = NumberUtils.toInt(data.getString("userId"));
			int deviceId = NumberUtils.toInt(data.getString("deviceId"));
			if (StringUtils.equalsIgnoreCase(action, "remove")) {
				mService_.removeUserDeviceAssociation(userId, deviceId);
			} else if (StringUtils.equalsIgnoreCase(action, "add")) {
				mService_.addUserDeviceAssociation(userId, deviceId);
			}
		} else if (StringUtils.equalsIgnoreCase(cmd, "updateUserAlertConfiguration")) {
			String userData = input.getString("data");
			logger.debug(userData);
			JSONObject data = new JSONObject(userData);
			int userId = NumberUtils.toInt(data.getString("userId"));
			int alertType = NumberUtils.toInt(data.getString("alertType"));
			String configuration = data.getString("configuration");
			mService_.insertOrUpdateAlertConfiguration(userId, alertType, configuration);
		} else if (StringUtils.equalsIgnoreCase(cmd, "getDevices")) {
			String userId = input.getString("userId");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Do Get called");
	}

	private void sendMail(HttpServletRequest req, Users user) {
		String[] toAddress = new String[] { user.getEmailId() };
		String subject = "Evercon Register Success. Activate your account ";
		StringBuilder message = new StringBuilder();
		message.append("Dear ");
		message.append(CommonUtils.getName(user.getFirstName(), user.getLastName()));
		message.append(
				", \nYour Evercon Registeration is successful. To activate your account please click link below.");
		String registerUrl = getBaseUri(req) + "login?t=" + user.getActivationKey() + "&u="+user.getLoginId();
//		message.append("<html><body><a href=\"").append(registerUrl + "?t=" + user.getActivationKey() + "&u="+user.getLoginId()).append("\" target=\"new\">");
//		message.append(registerUrl);
//		message.append("</a></body><html>");
		message.append(registerUrl);
		mMailSender_.sendMail(toAddress, subject, message.toString());
	}
}
