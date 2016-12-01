package com.evercons.commons.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.evercons.commons.utils.CommonUtils;

public class UserDetails {

	private int id;
	private String loginId;
	private String passwd;
	private String emailId;
	private String primaryContact;
	private String secondaryContact;
	private String firstName;
	private String lastName;
	private JSONArray userAlertConfigurations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public JSONArray getUserAlertConfigurations() {
		return userAlertConfigurations;
	}

	public void setUserAlertConfigurations(JSONArray userAlertConfigurations) {
		this.userAlertConfigurations = userAlertConfigurations;
	}

	public JSONObject getJson(UserDetails user) {
		JSONObject data = new JSONObject();
		if (user == null)
			user = this;

		data.put("id", id);
		data.put("loginId", loginId);
		data.put("emailId", emailId);
		data.put("primaryContact", primaryContact);
		data.put("secondaryContact", secondaryContact);
		data.put("name", CommonUtils.getName(firstName, lastName));
		if (userAlertConfigurations != null && userAlertConfigurations.length() > 0)
			data.put("userAlertConfigurations", userAlertConfigurations);
		
		return data;
	}

}
