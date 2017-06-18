package com.mileto.services.json;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.richfaces.json.JSONObject;

import com.mileto.pattern.Conexao;
import com.mileto.persistence.DemoDAO;
import com.mileto.persistence.PrcEleicaoDAO;
import com.mileto.persistence.PrcSigaWmsDAO;


/**
 * Esta classe deve fazer a abstração do banco de dados. O cliente dela não sabe como ela implementa o acesso aos dados, ao singleton ou aos arquivos JSON
 * @author abrhaao
 *
 */
public class BusinessDelegate {


	/** 
	 * Método de acesso à base que retorna o caboclo autenticado. 
	 * @return
	 */
	public String recuperaFiliaisJSON( String pEnterpriseKey ) { 
		JsonObject value; 


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		
		JSONObject formDetailsJson; 

		if (pEnterpriseKey.equals("9001")) {


			jsonArray = Json.createArrayBuilder()
					.add(Json.createObjectBuilder()
							.add("nome", "BANGU")
							.add("codigo", "A1")
							.add("uf", "RJ")
							.add("cnpj", "09094585000799"))
					.add(Json.createObjectBuilder()
							.add("nome", "PADRE MIGUEL")
							.add("codigo", "A2")
							.add("uf", "RJ")
							.add("cnpj", "09094585000799"))
					.add(Json.createObjectBuilder()
							.add("nome", "REALENGO")
							.add("codigo", "A3")
							.add("uf", "RJ")
							.add("cnpj", "09094585000799"))
					.add(Json.createObjectBuilder()
							.add("nome", "ROCINHA")
							.add("codigo", "A4")
							.add("uf", "RJ")
							.add("cnpj", "09094585000799"))
					;
		} else {
			value = Json.createObjectBuilder()
					.add("filial", Json.createArrayBuilder()
							.add(Json.createObjectBuilder()
									.add("nome", "SARACURUNA")
									.add("codigo", "A1")
									.add("uf", "RJ")
									.add("cnpj", "09094585000799"))
							.add(Json.createObjectBuilder()
									.add("nome", "COMENDADOR SOARES")
									.add("codigo", "A2")
									.add("uf", "RJ")
									.add("cnpj", "09094585000799"))
							.add(Json.createObjectBuilder()
									.add("nome", "JAPERI")
									.add("codigo", "A3")
									.add("uf", "RJ")
									.add("cnpj", "09094585000799"))
							.add(Json.createObjectBuilder()
									.add("nome", "QUEIMADOS")
									.add("codigo", "A4")
									.add("uf", "RJ")
									.add("cnpj", "09094585000799"))
							).build();			
		}
		//jsonArray
		//return value.toString();

		//value = Json.createObjectBuilder().add("",jsonArray).build();
		return jsonArray.build().toString();
	}

	public String recuperaFrotaJSON( String pEnterpriseKey ) { 
		JsonObject value; 


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		
		JSONObject formDetailsJson; 

		//if (pEnterpriseKey.equals("9001")) {


		jsonArray = Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
						.add("placa", "ABE 8075")
						.add("tipo", "CAVALO MECÂNICO")
						.add("documentacao", "RENAVAN / Mopp")
						.add("capacidadeMaxima", "28.500kg")
						.add("capacidadeNominal", "25.000kg")
						.add("status", "Em trânsito")
						.add("rota", "Rota: Maceió(AL) - São Paulo(SP)"))
				.add(Json.createObjectBuilder()
						.add("placa", "WSD 8080")
						.add("tipo", "CARRETA CONJUGADA")
						.add("documentacao", "RENAVAN / Mopp")
						.add("capacidadeMaxima", "28.500kg")
						.add("capacidadeNominal", "25.000kg")
						.add("status", "Em trânsito")
						.add("rota", "Rota: Maceió(AL) - Valinhos(SP)"))
				.add(Json.createObjectBuilder()
						.add("placa", "DMN 2386")
						.add("tipo", "CAVALO MECÂNICO")
						.add("documentacao", "RENAVAN / Mopp")
						.add("capacidadeMaxima", "28.500kg")
						.add("capacidadeNominal", "25.000kg")
						.add("status", "Em trânsito")
						.add("rota", "Rota: Maceió(AL) - Campinas(SP)"))
				.add(Json.createObjectBuilder()
						.add("placa", "DER 9752")
						.add("tipo", "CAVALO MECÂNICO")
						.add("documentacao", "RENAVAN / Mopp")
						.add("capacidadeMaxima", "28.500kg")
						.add("capacidadeNominal", "25.000kg")
						.add("status", "Em trânsito")
						.add("rota", "Rota: Aracaju(SR) - Belo Horizonte(MG)"))
				;
		return jsonArray.build().toString();
	}






		/**
		 * Busca a programação de vendas do dia, para o monitor
		 * @param pEnterpriseKey
		 * @return
		 */
		public String recuperaProgramacaoVendasJSON( String pEnterpriseKey ) { 

		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		 

		if (pEnterpriseKey.equals("9001")) {

			try { 			  
				jsonArray = PrcSigaWmsDAO.getProgramacaoVendas("20170305");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonArray.build().toString();

		} else if (pEnterpriseKey.equals("DEMO"))  {

			return DemoDAO.getWMSProgramacaoVendas("20170305").toString();

		} else if (pEnterpriseKey.equals("PAN"))  {

			try { 			  
				jsonArray = PrcSigaWmsDAO.getProgramacaoVendas("20170617");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonArray.build().toString();

		} else {

			return "";
		}
	}

	/**
	 * Atualiza os dados de um carregamento
	 * @param pEnterpriseKey
	 * @param pFilial
	 * @param pPedido
	 * @param pDoca
	 * @param pStatus
	 * @param pInstrucao
	 * @return
	 */
	public String atualizaWMSCarregamentoJSON( String pEnterpriseKey , String pFilial, String pPedido, String pDoca, String pStatus, String pInstrucao) { 


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		 

		//if (pEnterpriseKey.equals("9001")) {

		//try { 			  
		//	jsonArray = PrcSigaWmsDAO.getProgramacaoVendas("20170305");
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		//return jsonArray.build().toString();

		//} else 
		if (pEnterpriseKey.equals("DEMO"))  {

			return DemoDAO.atualizaWMSCarregamento(pPedido, pDoca, pStatus).toString();

		} else {

			return "";
		}
	}





	public String recuperaStatusCarregamentoJSON( String pEnterpriseKey , String pFilial, String pPedido ) { 

		JsonObjectBuilder value  = Json.createObjectBuilder();		

		//if (pEnterpriseKey.equals("9001")) {

		try { 			  
			value = PrcSigaWmsDAO.getStatusCarregamento(pFilial, pPedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value.build().toString();

		//}		  
	}






	/**
	 * Recupera todas a lista de possíveis status para carregamentos
	 * @param pEnterpriseKey
	 * @return
	 */
	public String recuperaListaStatusCarregamentoJSON( String pEnterpriseKey ) { 


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		 
		/**
		if (pEnterpriseKey.equals("9001")) {

			try { 			  
				jsonArray = PrcSigaWmsDAO.getProgramacaoVendas("20170305");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonArray.build().toString();

		} else **/ if (pEnterpriseKey.equals("DEMO"))  {

			return DemoDAO.getWMSListaStatusCarregamento().toString();

		} else {

			return "";
		}
	}



	/////////////////////////////////////////////////////////////////////////////////
	////////////////// ELEIÇÕES /////////////////////////////////////////////////////

	public String atualizaEleicaoVotoJSON( Integer enquete, String opcaoVoto, String identificacao, String terminal ) { 

		List<Object> arrayParameters = new ArrayList<Object>();
		arrayParameters.add(enquete);			//ENQUETE
		arrayParameters.add(opcaoVoto);			//OPCAO VOTO
		arrayParameters.add(identificacao);		//IDENTIFICAÇÃO
		arrayParameters.add(terminal);			//TERMINAL
		
		try {
			Conexao cx = new Conexao( "MILENIA" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!! 
			PrcEleicaoDAO.vota(arrayParameters, cx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}
	
	
	public String recuperaCandidatosElegiveisJSON( String pEleicao ) { 

		//JsonObjectBuilder value  = Json.createObjectBuilder();		
		//try { 			  
		//	value = PrcSigaWmsDAO.getStatusCarregamento(pFilial, pPedido);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		
		
		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		
		
		jsonArray = Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
						.add("idOpcao", "ABE")
						.add("descricao", "CAVALO MECÂNICO")
						.add("proposta", ""))						
				.add(Json.createObjectBuilder()
						.add("idOpcao", "ABC")
						.add("descricao", "VANDROYA")
						.add("proposta", ""))
				.add(Json.createObjectBuilder()
						.add("idOpcao", "LDS")
						.add("descricao", "DENIED")
						.add("proposta", ""))
				.add(Json.createObjectBuilder()
						.add("idOpcao", "BEG")
						.add("descricao", "TIME AFTER DEATH")
						.add("proposta", ""))
				;
		return jsonArray.build().toString();
		//return value.build().toString();

			  
	}

}