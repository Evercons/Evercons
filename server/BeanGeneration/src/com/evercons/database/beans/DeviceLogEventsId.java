package com.evercons.database.beans;
// Generated Nov 29, 2016 9:20:09 PM by Hibernate Tools 5.1.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * DeviceLogEventsId generated by hbm2java
 */
public class DeviceLogEventsId implements java.io.Serializable {

	private int deviceId;
	private int logid;
	private String event;
	private int eventType;
	private String latitude;
	private String longitude;
	private Integer locType;
	private Date eventTime;
	private String result;
	private String bac;
	private String image;
	private String info;
	private BigDecimal speed;

	public DeviceLogEventsId() {
	}

	public DeviceLogEventsId(int deviceId, int logid, int eventType, String latitude, String longitude,
			BigDecimal speed) {
		this.deviceId = deviceId;
		this.logid = logid;
		this.eventType = eventType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
	}

	public DeviceLogEventsId(int deviceId, int logid, String event, int eventType, String latitude, String longitude,
			Integer locType, Date eventTime, String result, String bac, String image, String info, BigDecimal speed) {
		this.deviceId = deviceId;
		this.logid = logid;
		this.event = event;
		this.eventType = eventType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.locType = locType;
		this.eventTime = eventTime;
		this.result = result;
		this.bac = bac;
		this.image = image;
		this.info = info;
		this.speed = speed;
	}

	public int getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getLogid() {
		return this.logid;
	}

	public void setLogid(int logid) {
		this.logid = logid;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getEventType() {
		return this.eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getLocType() {
		return this.locType;
	}

	public void setLocType(Integer locType) {
		this.locType = locType;
	}

	public Date getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBac() {
		return this.bac;
	}

	public void setBac(String bac) {
		this.bac = bac;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public BigDecimal getSpeed() {
		return this.speed;
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DeviceLogEventsId))
			return false;
		DeviceLogEventsId castOther = (DeviceLogEventsId) other;

		return (this.getDeviceId() == castOther.getDeviceId()) && (this.getLogid() == castOther.getLogid())
				&& ((this.getEvent() == castOther.getEvent()) || (this.getEvent() != null
						&& castOther.getEvent() != null && this.getEvent().equals(castOther.getEvent())))
				&& (this.getEventType() == castOther.getEventType())
				&& ((this.getLatitude() == castOther.getLatitude()) || (this.getLatitude() != null
						&& castOther.getLatitude() != null && this.getLatitude().equals(castOther.getLatitude())))
				&& ((this.getLongitude() == castOther.getLongitude()) || (this.getLongitude() != null
						&& castOther.getLongitude() != null && this.getLongitude().equals(castOther.getLongitude())))
				&& ((this.getLocType() == castOther.getLocType()) || (this.getLocType() != null
						&& castOther.getLocType() != null && this.getLocType().equals(castOther.getLocType())))
				&& ((this.getEventTime() == castOther.getEventTime()) || (this.getEventTime() != null
						&& castOther.getEventTime() != null && this.getEventTime().equals(castOther.getEventTime())))
				&& ((this.getResult() == castOther.getResult()) || (this.getResult() != null
						&& castOther.getResult() != null && this.getResult().equals(castOther.getResult())))
				&& ((this.getBac() == castOther.getBac()) || (this.getBac() != null && castOther.getBac() != null
						&& this.getBac().equals(castOther.getBac())))
				&& ((this.getImage() == castOther.getImage()) || (this.getImage() != null
						&& castOther.getImage() != null && this.getImage().equals(castOther.getImage())))
				&& ((this.getInfo() == castOther.getInfo()) || (this.getInfo() != null && castOther.getInfo() != null
						&& this.getInfo().equals(castOther.getInfo())))
				&& ((this.getSpeed() == castOther.getSpeed()) || (this.getSpeed() != null
						&& castOther.getSpeed() != null && this.getSpeed().equals(castOther.getSpeed())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDeviceId();
		result = 37 * result + this.getLogid();
		result = 37 * result + (getEvent() == null ? 0 : this.getEvent().hashCode());
		result = 37 * result + this.getEventType();
		result = 37 * result + (getLatitude() == null ? 0 : this.getLatitude().hashCode());
		result = 37 * result + (getLongitude() == null ? 0 : this.getLongitude().hashCode());
		result = 37 * result + (getLocType() == null ? 0 : this.getLocType().hashCode());
		result = 37 * result + (getEventTime() == null ? 0 : this.getEventTime().hashCode());
		result = 37 * result + (getResult() == null ? 0 : this.getResult().hashCode());
		result = 37 * result + (getBac() == null ? 0 : this.getBac().hashCode());
		result = 37 * result + (getImage() == null ? 0 : this.getImage().hashCode());
		result = 37 * result + (getInfo() == null ? 0 : this.getInfo().hashCode());
		result = 37 * result + (getSpeed() == null ? 0 : this.getSpeed().hashCode());
		return result;
	}

}
