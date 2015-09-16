package com.cisco.patterns;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConfigProps {

	private Map<String, Map<String, Service>> environment = new LinkedHashMap<>();

	public Map<String, Map<String, Service>> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, Map<String, Service>> environment) {
		this.environment = environment;
	}

}
