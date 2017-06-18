package com.mileto.services.json;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mileto.domain.business.BoardMessage;
import com.mileto.persistence.DataProviderSingleton;

@Path("/messagemanager")
public class MessageManager {


	@GET
	@Path("/informa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response informaRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new String();

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		
		
		JsonObject jsonMsg = provider.getFirstMessage(pEnterpriseKey, "K1");
		result = jsonMsg.toString();
		return Response.status(200).entity(result).build();
	}
	
	
	@POST
	@Path("/put")
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(@FormParam("enterprise") String enterprise,
	                   @FormParam("aviso") String aviso) {
		System.out.println("Estou dentro no method POST!!!!!!! " + enterprise );
		
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		provider.putMessage(new BoardMessage(aviso, "Assunto", enterprise, "K1"));
		
		String result = "OK";
		return Response.status(200).entity(result).build();
	}

}