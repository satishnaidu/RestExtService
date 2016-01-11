package com.sample.patterns;


import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

@Path("/api")
public class ConfigService {

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllEnvironmentDetails() {
		System.out.println("getAllEnvironmentDetails entered");
		Map<String, Map<String, Service>> envlist = ConfigServiceImpl.INSTANCE
				.getAllEnvironmentDetails();

		return Response.ok().entity(envlist).build();
	}

	@GET
	@Path("{env}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getEnvDetails(@PathParam("env") String environment) {

		System.out.println("getEnvDetails entered");
		Map<String, Service> servicesList = ConfigServiceImpl.INSTANCE
				.getEnvDetails(environment);

		return Response.ok().entity(servicesList).build();
	}

	@GET
	@Path("{env}/{service}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServiceDetails(@PathParam("env") String environment,
			@PathParam("service") String service) {
		System.out.println("getServiceDetails entered");
		Service serviceDetails = new Service();

		try {
			serviceDetails = ConfigServiceImpl.INSTANCE.getServiceDetails(
					environment, service);

			System.out.println("output:: " + serviceDetails);
		} catch (RestServiceException ex) {
			return buildError(ex.getMessage());
		}
		return Response.ok().entity(serviceDetails).build();
	}

	@GET
	@Path("{env}/{service}/connection")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDatabaseProps(@PathParam("env") String environment,
			@PathParam("service") String service) {

		System.out.println("getDatabaseProps entered");
		Connection connection = ConfigServiceImpl.INSTANCE.getDatabaseProps(
				environment, service);

		return Response.ok().entity(connection).build();
	}

	@GET
	@Path("{env}/{service}/properties")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServiceProps(@PathParam("env") String environment,
			@PathParam("service") String service) {

		System.out.println("getServiceProps entered");
		Map<String, String> serviceProps = ConfigServiceImpl.INSTANCE
				.getServiceProps(environment, service);

		return Response.ok().entity(serviceProps).build();
	}

	@POST
	@Path("{service}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateConfigProps(ConfigProps configProps) {
		ConfigServiceImpl.INSTANCE.updateConfigProps(configProps);
		return Response.ok().entity(configProps).build();
	}

	@POST
	@Path("/service/{serviceName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response registerService(
			@PathParam("serviceName") String serviceName, String serviceUrl) {
		ConfigServiceImpl.INSTANCE.registerService(serviceName, serviceUrl);
		return Response.ok().entity(Status.OK).build();
	}

	@POST
	@Path("/service")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response unRegisterService(
			@QueryParam("serviceName") String serviceName) {
		ConfigServiceImpl.INSTANCE.unRegisterService(serviceName);
		return Response.ok().entity(Status.OK).build();
	}

	public static Response buildError(String message) {
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		builder.status(Response.Status.INTERNAL_SERVER_ERROR);
		builder.entity(message);
		return builder.build();
	}

}
