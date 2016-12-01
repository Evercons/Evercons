package com.evercons.server.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.evercons.commons.utils.HTTPUtils;
import com.evercons.database.service.EverconService;

public class EverconsControllerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Log logger = LogFactory.getLog(getClass());
	protected EverconService mService_ = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		mService_ = EverconService.getInstance();
	}

	protected boolean isValidSession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return session != null;
	}

	protected String getBaseUri(HttpServletRequest req) {
		StringBuffer url = req.getRequestURL();
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
		return base;
	}
	
	protected JSONObject getInput(HttpServletRequest req) {
		return HTTPUtils.readRequest(req);
	}
	
	protected String getCmd(JSONObject input) {
		return input.optString("cmd");
	}
	
	protected String getdata(JSONObject input) {
		return input.optString("data");
	}
	
}
