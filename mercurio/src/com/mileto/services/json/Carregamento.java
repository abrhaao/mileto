package com.mileto.services.json;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mileto.delegate.CarregamentoDelegate;
import com.mileto.persistence.DataProviderSingleton;

@Path("/carregamento")
public class Carregamento {
	
	@GET
	@Path("/recuperaProgramacaoVendas")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaProgramacaoVendasRESTService(
			@QueryParam("enterprise") String pEnterpriseKey, @QueryParam("horas") String pHoras ) {

		JsonArray result = new CarregamentoDelegate().recuperaProgramacaoVendasJSON( pEnterpriseKey, pHoras );
		return Response.status(200).entity(result.toString()).build();
	}

	@GET
	@Path("/recuperaCarregamento/{enterpriseKey}/{filial}/{pedido}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaCarregamentoRESTService(
			@PathParam("enterpriseKey") String pEnterpriseKey, @PathParam("filial") String pFilial, @PathParam("pedido") String pPedido) {

		JsonArray result = new CarregamentoDelegate().recuperaProgramacaoVendasJSON( pEnterpriseKey, "9999" );
		for (int i = 0 ; i < result.size(); i++) {
	        JsonObject jo = result.getJsonObject(i);
	        if ( jo.getString("pedido").equals(pPedido) ) {
	        	JsonObject joFull = new CarregamentoDelegate().recuperaCarregamentoJSON( pEnterpriseKey, jo );
	        	return Response.status(200).entity(joFull.toString()).build();
	        }
	    }
		return Response.status(200).entity(result.toString()).build();		
	}
	
	@POST
	@Path("/atualizaCarregamento")
	@Produces(MediaType.TEXT_PLAIN)
	public Response atualizaCarregamento ( @FormParam("enterprise") String enterprise, @FormParam("filial") String filial,  
										   @FormParam("pedido") String pedido, @FormParam("evento") String evento, 
										   @FormParam("doca") String doca, @FormParam("lote") String lote, @FormParam("lacre") String lacre) {
		System.out.println("Estou dentro no method POST atualizaCarregamento!!!!!!! "  );
		
		//StringBuilder msg = new StringBuilder();
	    //msg.append( "<strong></strong>");

	    //DataProviderSingleton provider = DataProviderSingleton.getInstance();
	    
	    /** Chama a mensagem de carregamento finalizado **/
		//DataProviderSingleton provider = DataProviderSingleton.getInstance();
		//provider.putEvento( key, new BoardMessage(msg.toString(), "HIGHLIGHT", enterprise, "HIGHLIGHT"));
		
	    /** Chama a mensagem de carregamento finalizado **/
		//rovider.putMessage(new BoardMessage(msg.toString(), "Assunto", enterprise, "K1", speech));
	    
	    
	    JsonObject result = new CarregamentoDelegate().atualizaCarregamentoJSON ( enterprise, filial, pedido, evento, doca, lote, lacre );
	    
	    
		//String result = "OK. EVENTO REGISTRADO";
		return Response.status(200).entity(result.toString()).build();
	}

}
