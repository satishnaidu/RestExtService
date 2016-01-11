package com.sample.patterns;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.yaml.snakeyaml.Yaml;

public enum ConfigServiceImpl {

	INSTANCE;
	
	
	public Map<String, Map<String, Service>> getAllEnvironmentDetails() {
		ConfigProps configProps = readFile();
		Map<String, Map<String, Service>> envList = configProps
				.getEnvironment();
		return envList;
	}

	public Map<String, Service> getEnvDetails(String environment) {
		ConfigProps configProps = readFile();
		Map<String, Map<String, Service>> envList = configProps
				.getEnvironment();
		Map<String, Service> services = envList.get(environment);
		return services;
	}

	public Service getServiceDetails(String environment, String serviceName)
			throws RestServiceException {

		System.out
				.println("getServiceDetails " + environment + " " + serviceName);
		ConfigProps configProps = readFile();
		Map<String, Map<String, Service>> envList = configProps
				.getEnvironment();
		if (envList.isEmpty()) {
			throw new RestServiceException("No environment details found");
		}
		System.out.println("env list: " + envList);
		Map<String, Service> services = envList.get(environment);
		if (services.isEmpty()) {
			throw new RestServiceException("No services present for the service");
		}
		Service service = services.get(serviceName);
		if (service == null) {
			throw new RestServiceException("No service props found for the service");
		}

		return service;
	}

	public Connection getDatabaseProps(String environment, String serviceName) {
		System.out.println("getDatabaseProps " + environment + " "
				+ serviceName);
		ConfigProps configProps = readFile();
		Map<String, Map<String, Service>> envList = configProps
				.getEnvironment();
		Map<String, Service> services = envList.get(environment);
		Service service = services.get(serviceName);
		Connection connection = service.getConnection();
		return connection;
	}
	
	public Map<String,String> getServiceProps(String environment, String serviceName) {
		System.out.println("getServiceProps " + environment + " "
				+ serviceName);
		ConfigProps configProps = readFile();
		Map<String, Map<String, Service>> envList = configProps
				.getEnvironment();
		Map<String, Service> services = envList.get(environment);
		Service service = services.get(serviceName);
		Map<String,String> serviceProps = service.getServiceprops();
		return serviceProps;
	}

	public ConfigProps updateConfigProps(ConfigProps configProps) {
		writeFile(configProps);
		return configProps;
	}

	public Response registerService(String serviceName, String serviceUrl) {
		return null;
	}

	public Response unRegisterService(String serviceName) {
		return null;
	}

	public ConfigProps readFile() {
		String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
		Yaml yaml = new Yaml();
		ConfigProps configProps = new ConfigProps();
		try (InputStream in = Files.newInputStream(Paths.get(filePath))) {

			configProps = yaml.loadAs(in, ConfigProps.class);
			System.out.println(configProps);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return configProps;
	}

	public void writeFile(ConfigProps configProps) {
		try {
			String filePath = "C:/Cisco/Projects/CodeBase/GIT_REPORTS/Checkin/reportframework/src/main/java/com/cisco/vcs/reports/restIF/sample.yaml";
			Yaml yaml = new Yaml();
			FileWriter fileWriter = new FileWriter(filePath);
			yaml.dump(configProps, fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("main invoked");
		ConfigProps configProps = new ConfigProps();
		Map<String, Map<String, Service>> envlist = new LinkedHashMap<String, Map<String, Service>>();
		Map<String, Service> services = new LinkedHashMap<String, Service>();
		Service service = new Service();
		service.setServiceName("reports");
		service.setServicePath("/opt/vcs/reports");
		service.setVersion("1.0");
		service.setReleased(new Date());

		Map<String, String> serviceprops = new LinkedHashMap<String, String>();
		serviceprops.put("key1", "value1");
		serviceprops.put("key2", "value2");
		service.setServiceprops(serviceprops);

		Connection connection = new Connection();
		connection.setDriverClass("oracledriver");
		connection.setPassword("reportsdba");
		connection.setUrl("https://thin:driver");
		connection.setUsername("reportsdba");
		service.setConnection(connection);
		services.put(service.getServiceName(), service);

		Service service2 = new Service();
		service2.setServiceName("alarms");
		service2.setServicePath("/opt/vcs/alarms");
		service2.setVersion("2.0");
		service2.setReleased(new Date());

		Map<String, String> serviceprops2 = new LinkedHashMap<String, String>();
		serviceprops2.put("key1", "value1");
		serviceprops2.put("key2", "value2");
		service2.setServiceprops(serviceprops2);

		services.put(service2.getServiceName(), service2);
		envlist.put("dev", services);

		configProps.setEnvironment(envlist);

		ConfigServiceImpl.INSTANCE.writeFile(configProps);
	}
}
