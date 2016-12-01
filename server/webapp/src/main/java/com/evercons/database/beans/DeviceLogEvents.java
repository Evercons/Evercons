package com.evercons.database.beans;
// Generated Nov 29, 2016 9:20:09 PM by Hibernate Tools 5.1.0.CR1

/**
 * DeviceLogEvents generated by hbm2java
 */
public class DeviceLogEvents implements java.io.Serializable {

	private DeviceLogEventsId id;
	private Device device;
	private EventTypes eventTypes;
	private LocationTypes locationTypes;

	public DeviceLogEvents() {
	}

	public DeviceLogEvents(DeviceLogEventsId id, Device device, EventTypes eventTypes) {
		this.id = id;
		this.device = device;
		this.eventTypes = eventTypes;
	}

	public DeviceLogEvents(DeviceLogEventsId id, Device device, EventTypes eventTypes, LocationTypes locationTypes) {
		this.id = id;
		this.device = device;
		this.eventTypes = eventTypes;
		this.locationTypes = locationTypes;
	}

	public DeviceLogEventsId getId() {
		return this.id;
	}

	public void setId(DeviceLogEventsId id) {
		this.id = id;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public EventTypes getEventTypes() {
		return this.eventTypes;
	}

	public void setEventTypes(EventTypes eventTypes) {
		this.eventTypes = eventTypes;
	}

	public LocationTypes getLocationTypes() {
		return this.locationTypes;
	}

	public void setLocationTypes(LocationTypes locationTypes) {
		this.locationTypes = locationTypes;
	}

}
