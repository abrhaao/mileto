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
	@Path("/comunica")
	@Produces(MediaType.TEXT_PLAIN)
	public Response comunicaRESTService(
				@QueryParam("enterprise") String pEnterprise, 
				@QueryParam("assunto") String pAssunto) {
				
		String result = new BusinessDelegate().comunicaJSON( pEnterprise, pAssunto );
		return Response.status(200).entity(result).build();
	}	

}