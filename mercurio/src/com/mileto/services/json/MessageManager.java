package com.mileto.services.json;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mileto.persistence.DataProviderSingleton;

@Path("/messagemanager")
public class MessageManager {


	@GET
	@Path("/informa")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaFrotaRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new String();

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		
		
		JsonObject jsonMsg = provider.getFirstMessage(pEnterpriseKey, "K1");
		result = jsonMsg.toString();
		return Response.status(200).entity(result).build();
	}

}