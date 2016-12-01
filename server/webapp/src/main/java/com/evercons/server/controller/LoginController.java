package com.evercons.server.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.model.UserDetails;
import com.evercons.commons.utils.HTTPUtils;
import com.evercons.commons.utils.JSONUtils;

public class LoginController extends EverconsControllerServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost called");
		resp.setHeader("Access-Control-Allow-Origin","*");
		JSONObject input = getInput(req);
		String cmd = getCmd(input);
		String userData = getdata(input);
		System.out.println("cmd:" + cmd);
		System.out.println("userData:" + userData);
		if (StringUtils.equalsIgnoreCase(cmd, "authenticate")) {
			logger.debug(userData);
			JSONObject data = new JSONObject(userData);
			String username = data.getString("username");
			String password = data.getString("password");
			String status = "failed";
			int userId = mService_.authenticateUser(username, password);

			JSONObject result = new JSONObject();

			if (userId != -1) {
				status = "success";
				UserDetails user = mService_.getUserDetails(userId);
				result = user.getJson(null);
				JSONArray userDevices = mService_.getUserDevices(userId);
				if (JSONUtils.isNotEmpty(userDevices))
					result.put("devices", userDevices);
			}
			result.put("status", status);

			IOUtils.write(result.toString(), resp.getOutputStream());
		} else if (StringUtils.equalsIgnoreCase(cmd, "ac")) {
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Do Get called");
		System.out.println(req.getRequestURL());

		String loginId = (String) req.getParameter("u");
		String activationKey = (String) req.getParameter("t");
		int updateCount = mService_.validateRegister(loginId, activationKey);
		if (updateCount > 0) {
			resp.sendRedirect("http://localhost/www/index.html");
		} else {
			resp.sendRedirect("http://localhost/www/index.html?registerfailed=true");			
		}
	}
}
