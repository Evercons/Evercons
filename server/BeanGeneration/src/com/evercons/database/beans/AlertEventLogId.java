package com.evercons.database.beans;
// Generated Nov 29, 2016 9:20:09 PM by Hibernate Tools 5.1.0.CR1

/**
 * AlertEventLogId generated by hbm2java
 */
public class AlertEventLogId implements java.io.Serializable {

	private int deviceId;
	private int userId;
	private String eventData;

	public AlertEventLogId() {
	}

	public AlertEventLogId(int deviceId, int userId) {
		this.deviceId = deviceId;
		this.userId = userId;
	}

	public AlertEventLogId(int deviceId, int userId, String eventData) {
		this.deviceId = deviceId;
		this.userId = userId;
		this.eventData = eventData;
	}

	public int getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEventData() {
		return this.eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AlertEventLogId))
			return false;
		AlertEventLogId castOther = (AlertEventLogId) other;

		return (this.getDeviceId() == castOther.getDeviceId()) && (this.getUserId() == castOther.getUserId())
				&& ((this.getEventData() == castOther.getEventData()) || (this.getEventData() != null
						&& castOther.getEventData() != null && this.getEventData().equals(castOther.getEventData())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeviceId();
		result = 37 * result + this.getUserId();
		result = 37 * result + (getEventData() == null ? 0 : this.getEventData().hashCode());
		return result;
	}

}
