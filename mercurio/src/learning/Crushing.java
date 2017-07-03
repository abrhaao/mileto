package learning;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/crushing")
public class Crushing {


	@GET
	@Path("/recuperaFiliais")
	@Produces(MediaType.TEXT_PLAIN)
	public Response autenticapRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new BusinessDelegate().recuperaFiliaisJSON( pEnterpriseKey );
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/recuperaFrota")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaFrotaRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new BusinessDelegate().recuperaFrotaJSON( pEnterpriseKey );
		return Response.status(200).entity(result).build();
	}
	
	

	


	@GET
	@Path("/recuperaStatusCarregamento")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaStatusCarregamentoRESTService(
			@QueryParam("enterprise") String pEnterpriseKey, 
			@QueryParam("filial") String pFilial,  
			@QueryParam("pedido") String pPedido) {


		String result = new BusinessDelegate().recuperaStatusCarregamentoJSON( pEnterpriseKey , pFilial, pPedido );
		return Response.status(200).entity(result).build();		
	}




	@GET
	@Path("/atualizaStatusCarregamento")
	@Produces(MediaType.TEXT_PLAIN)
	public Response atualizaStatusCarregamentoRESTService(
			@QueryParam("enterprise") String pEnterpriseKey, 
			@QueryParam("filial") String pFilial,  
			@QueryParam("pedido") String pPedido,
			@QueryParam("doca") String pDoca,
			@QueryParam("status") String pStatus, 
			@QueryParam("instrucao") String pInstrucao)  {



		String result = new BusinessDelegate().atualizaWMSCarregamentoJSON(pEnterpriseKey, pFilial, pPedido, pDoca, pStatus, pInstrucao);
		return Response.status(200).entity(result).build();

	}

	@GET
	@Path("/recuperaListaStatusCarregamento")
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizaStatusCarregamentoRESTService(
			@QueryParam("enterprise") String pEnterpriseKey) {

		String result = new BusinessDelegate().recuperaListaStatusCarregamentoJSON(pEnterpriseKey);
		return Response.status(200).entity(result).build();

	}




	/***********************************************************************************/



}