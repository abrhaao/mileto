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
	@Produces(MediaType.TEXT_PLAIN)
	public Response informaRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new String();

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		
		JsonObject jsonMsg = provider.getFirstMessage(pEnterpriseKey, "K1");
		result = jsonMsg.toString();
		return Response.status(200).entity(result).build();
	}
	
	
	@POST
	@Path("/putMessage")
	@Produces(MediaType.TEXT_PLAIN)
	public Response putMessage( @FormParam("titulo") String titulo,	@FormParam("subTitulo") String subTitulo,
							@FormParam("tituloHonorario") String tituloHonorario, @FormParam("destinatario") String destinatario,
							@FormParam("enterprise") String enterprise, @FormParam("aviso") String aviso, @FormParam("fala") String speech) {
		System.out.println("Estou dentro no method POST!!!!!!! " + enterprise );
		
		StringBuilder msg = new StringBuilder();
	    msg.append( "<strong>" + titulo + "</strong><br>" + subTitulo + "<hr><br>");
	    msg.append(tituloHonorario + " <strong>" + destinatario + "</strong><br>");
	    msg.append(aviso);

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		provider.putMessage(new BoardMessage(msg.toString(), "Assunto", enterprise, "K1", speech));
		
		String result = "OK";
		return Response.status(200).entity(result).build();
	}
	
	
	@POST
	@Path("/putEvento")
	@Produces(MediaType.TEXT_PLAIN)
	public Response putEvento ( @FormParam("enterprise") String enterprise, @FormParam("key") String key ) {
		System.out.println("Estou dentro no method POST!!!!!!! "  );
		
		StringBuilder msg = new StringBuilder();
	    msg.append( "<strong></strong>");

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		provider.putEvento( key, new BoardMessage(msg.toString(), "HIGHLIGHT", enterprise, "HIGHLIGHT", ""));
		
		String result = "OK. EVENTO REGISTRADO";
		return Response.status(200).entity(result).build();
	}

}