package com.mileto.services.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.richfaces.json.JSONObject;

<<<<<<< HEAD
import com.mileto.delegate.EleicaoDelegate;

@Path("/eleicao")
public class Eleicao {


	@POST
	@Path("/vota")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response votaRESTService (JSONObject inputJsonObjt) {
			//@QueryParam("enquete") String enquete, @QueryParam("opcaoVoto") String opcaoVoto, @QueryParam("identificacao") String identificacao, @QueryParam("terminal") String terminal) {

		String result = new String();

		//DataProviderSingleton provider = DataProviderSingleton.getInstance();
		
		//JsonObject jsonMsg = provider.getFirstMessage(pEnterpriseKey, "K1");
		//result = jsonMsg.toString();
		String enquete = "98";
		String opcaoVoto =	"ZYJ";
		String identificacao = "POST METHOD";
		String terminal  = "NAVIGATOR";
		(new EleicaoDelegate()).atualizaEleicaoVotoJSON ( Integer.parseInt(enquete), opcaoVoto,identificacao,terminal );
		
		return Response.status(200).entity(result).build();
	}
	
	
	@GET
	@Path("/recuperaCandidatosElegiveis")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaCandidatosElegiveisRESTService(	@QueryParam("eleicao") String pEleicao, @QueryParam("opcao") String pOpcao ) {
		
		String result = new EleicaoDelegate().recuperaCandidatosElegiveisJSON( pEleicao , pOpcao );
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("/recuperaEleicoesVigentes/{enterprise}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaEleicoesVigentesRESTService(@QueryParam("enterprise") String pEnterprise ) {
		
		String result = new EleicaoDelegate().recuperaCandidatosElegiveisJSON( pEleicao , pOpcao );
=======
@Path("/eleicao")
public class Eleicao {


	@POST
	@Path("/vota")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response votaRESTService (JSONObject inputJsonObjt) {
			//@QueryParam("enquete") String enquete, @QueryParam("opcaoVoto") String opcaoVoto, @QueryParam("identificacao") String identificacao, @QueryParam("terminal") String terminal) {

		String result = new String();

		//DataProviderSingleton provider = DataProviderSingleton.getInstance();
		
		//JsonObject jsonMsg = provider.getFirstMessage(pEnterpriseKey, "K1");
		//result = jsonMsg.toString();
		String enquete = "98";
		String opcaoVoto =	"ZYJ";
		String identificacao = "POST METHOD";
		String terminal  = "NAVIGATOR";
		(new BusinessDelegate()).atualizaEleicaoVotoJSON ( Integer.parseInt(enquete), opcaoVoto,identificacao,terminal );
		
		return Response.status(200).entity(result).build();
	}
	
	
	@GET
	@Path("/recuperaCandidatosElegiveis")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaCandidatosElegiveisRESTService(
				@QueryParam("eleicao") String pEleicao) {
		
		String result = new BusinessDelegate().recuperaCandidatosElegiveisJSON( pEleicao );
>>>>>>> refs/remotes/origin/develop
		return Response.status(200).entity(result).build();
	}

}