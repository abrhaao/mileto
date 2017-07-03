package com.mileto.delegate;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

import com.mileto.pattern.Conexao;
import com.mileto.persistence.PrcEleicaoDAO;

public class EleicaoDelegate {
	

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


	public String recuperaCandidatosElegiveisJSON( String pEleicao, String pOpcao ) { 

		//JsonObjectBuilder value  = Json.createObjectBuilder();		
		//try { 			  
		//	value = PrcSigaWmsDAO.getStatusCarregamento(pFilial, pPedido);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		

		/**
		jsonArray = Json.createArrayBuilder()
				.add(Json.createObjectBuilder()
						.add("idOpcao", "ABE")
						.add("descricao", "JOÃO DE DEUS")
						.add("proposta", ""))						
				.add(Json.createObjectBuilder()
						.add("idOpcao", "ABC")
						.add("descricao", "DAMIANA VANDROYA")
						.add("proposta", ""))
				.add(Json.createObjectBuilder()
						.add("idOpcao", "LDS")
						.add("descricao", "FABIOLA DENIED")
						.add("proposta", ""))
				.add(Json.createObjectBuilder()
						.add("idOpcao", "BEG")
						.add("descricao", "BRUCE DICKINSON")
						.add("proposta", ""))
				;
		 **/


		try {
			File f = new File("/home/abrhaao/Imagens/avatar-pinguino.png");
			FileInputStream input = new FileInputStream(f);

			List<Object> arrayParameters = new ArrayList<Object>();			
			//arrayParameters.add( input );			//OPCAO VOTO
			//arrayParameters.add(11);			
			
			Conexao cx = new Conexao( "MILENIA" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!! 
			//PrcEleicaoDAO.gravaFigura(arrayParameters, cx);
			
			arrayParameters = new ArrayList<Object>();
			arrayParameters.add( Integer.parseInt(pEleicao));
			if (pOpcao != null) {
				arrayParameters.add( pOpcao );
			}
			
			jsonArray = PrcEleicaoDAO.getCandidatosElegiveis(arrayParameters, cx);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return jsonArray.build().toString();
	}


}
