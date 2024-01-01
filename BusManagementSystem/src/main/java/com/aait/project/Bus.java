package com.aait.project;

public class Bus {
    private String busId;
    private String busName;
    private String busNumber;
    private String destination;
    private double latitude;
    private double longitude;
	public Bus(String busId, String busNumber, String busName, String destination, double latitude,
			double longitude) {
		this.busId = busId;
		this.busName = busName;
		this.busNumber = busNumber;
		this.destination = destination;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getBusNumber() {
		return busNumber;
	}
	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    
}