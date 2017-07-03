package com.mileto.services.json;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mileto.delegate.CarregamentoDelegate;

@Path("/carregamento")
public class Carregamento {
	
	@GET
	@Path("/recuperaProgramacaoVendas")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaProgramacaoVendasRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		JsonArray result = new CarregamentoDelegate().recuperaProgramacaoVendasJSON( pEnterpriseKey );
		return Response.status(200).entity(result.toString()).build();
	}

	@GET
	@Path("/recuperaCarregamento/{enterpriseKey}/{filial}/{pedido}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaCarregamentoRESTService(
			@PathParam("enterpriseKey") String pEnterpriseKey, @PathParam("filial") String pFilial, @PathParam("pedido") String pPedido) {

		JsonArray result = new CarregamentoDelegate().recuperaProgramacaoVendasJSON( pEnterpriseKey );
		for (int i = 0 ; i < result.size(); i++) {
	        JsonObject jo = result.getJsonObject(i);
	        if ( jo.getString("pedido").equals(pPedido) ) {
	        	JsonObject joFull = new CarregamentoDelegate().recuperaCarregamentoJSON( pEnterpriseKey, jo );
	        	return Response.status(200).entity(joFull.toString()).build();
	        }
	    }
		return Response.status(200).entity(result.toString()).build();		
	}

}
