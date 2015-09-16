package com.cisco.patterns;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Service {

	private Date released;

	private String version;

	private String serviceName;
	private String servicePath;

	private Connection connection;

	private Map<String, String> serviceprops = new HashMap<String, String>();

	public Map<String, String> getServiceprops() {
		return serviceprops;
	}

	public void setServiceprops(Map<String, String> serviceprops) {
		this.serviceprops = serviceprops;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public String toString() {
		return "Service [serviceName=" + serviceName + ", servicePath="
				+ servicePath + ", released=" + released + ", version="
				+ version + ", connection=" + connection + ", serviceprops="
				+ serviceprops + "]";
	}

}
