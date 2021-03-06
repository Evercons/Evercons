package com.evercons.database.beans;
// Generated Nov 29, 2016 9:20:09 PM by Hibernate Tools 5.1.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * AlertType generated by hbm2java
 */
public class AlertType implements java.io.Serializable {

	private int id;
	private String type;
	private String description;
	private Set userAlertConfigurations = new HashSet(0);

	public AlertType() {
	}

	public AlertType(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public AlertType(int id, String type, String description, Set userAlertConfigurations) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.userAlertConfigurations = userAlertConfigurations;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getUserAlertConfigurations() {
		return this.userAlertConfigurations;
	}

	public void setUserAlertConfigurations(Set userAlertConfigurations) {
		this.userAlertConfigurations = userAlertConfigurations;
	}

}
