package com.mileto.services.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
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
	@Path("/recuperaProgramacaoVendas")
	@Produces(MediaType.TEXT_PLAIN)
	public Response recuperaProgramacaoVendasRESTService(
				@QueryParam("enterprise") String pEnterpriseKey) {
		
		String result = new BusinessDelegate().recuperaProgramacaoVendasJSON( pEnterpriseKey );
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
	@Produces(MediaType.TEXT_PLAIN)
	public Response atualizaStatusCarregamentoRESTService(
				@QueryParam("enterprise") String pEnterpriseKey) {
									
		String result = new BusinessDelegate().recuperaListaStatusCarregamentoJSON(pEnterpriseKey);
		return Response.status(200).entity(result).build();
		
	}
		
		
	@GET
	@Path("/autentica")
	@Produces(MediaType.TEXT_PLAIN)
	public Response autenticapRESTService(
				@QueryParam("login") String pLogin, 
				@QueryParam("login") String pSenha) {
		
		
		String result = new LearningFactory().jsonAutentica( pLogin, pSenha );
		return Response.status(200).entity(result).build();
	}
	
	
	
	
	/***********************************************************************************/
	
 

}