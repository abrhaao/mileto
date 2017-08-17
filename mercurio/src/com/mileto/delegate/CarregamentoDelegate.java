package com.mileto.delegate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.mileto.pattern.Conexao;
import com.mileto.persistence.DemoDAO;
import com.mileto.persistence.PrcSigaWmsDAO;

public class CarregamentoDelegate {

	/**
	 * Busca a programação de vendas do dia, para o monitor
	 * @param pEnterpriseKey
	 * @return
	 */
	public JsonArray recuperaProgramacaoVendasJSON( String pEnterpriseKey, String pHoras ) { 

		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		 

		try { 	
			if (pEnterpriseKey.equals("DEMO"))  {

				Conexao cx = new Conexao( "BYYOU" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!!
				jsonArray = DemoDAO.getProgramacaoVendas(cx, null);
				//return DemoDAO.getWMSProgramacaoVendas("20170305"); //.toString();
				

			} else if (pEnterpriseKey.equals("PAN") || pEnterpriseKey.equals("KATRIUM"))  {


				List<Object> arrayParameters = new ArrayList<Object>();
				arrayParameters.add(LocalDate.now());			//HOJE
				arrayParameters.add((byte) 0);					//DIAS ATRÁS
				arrayParameters.add((double) Double.parseDouble(pHoras) );				//CONSIDERAR APENAS NFS FATURADAS NAS ÚLTIMAS X HORAS

				Conexao cx = new Conexao( "PAN" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!!
				jsonArray = PrcSigaWmsDAO.getProgramacaoVendas(cx, arrayParameters);

				//return jsonArray.build(); //.toString();

			} else {

				
			}
			return jsonArray.build(); //.toString();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}

	/**
	 * Obtém informações detalhadas de um carregamento
	 * @param pEnterpriseKey
	 * @param joCarregamento
	 * @return
	 */
	public JsonObject recuperaCarregamentoJSON( String pEnterpriseKey , JsonObject joCarregamento ) { 

		//JsonArrayBuilder jsonArray  = Json.createArrayBuilder();		 

		try { 

			if (pEnterpriseKey.equals("DEMO"))  {

				Conexao cx = new Conexao( "BYYOU" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!! 
				joCarregamento = DemoDAO.getCarregamento( cx, joCarregamento ) ;			
				return joCarregamento; //.toString();

			} else if (pEnterpriseKey.equals("PAN") || pEnterpriseKey.equals("KATRIUM"))  {


				Conexao cx = new Conexao( "PAN" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!! 
				PrcSigaWmsDAO.getCarregamento( cx, joCarregamento ) ;			
				return joCarregamento; //.toString();

			} else {

				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public JsonObject atualizaCarregamentoJSON( String pEnterpriseKey, String pFilial, String pPedido, String pEvento, String pDoca, String pLote, String pLacre ) { 		 

		
		JsonObject joCarregamento = Json.createObjectBuilder().build();
		

		try { 

			if (pEnterpriseKey.equals("DEMO"))  {				
				
				Conexao cx = new Conexao( "BYYOU" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!!
				
				
				joCarregamento = DemoDAO.atualizaCarregamento( cx, pFilial, pPedido, pEvento, pDoca, pLote, pLacre ) ;
				
				
				
				return joCarregamento; //.toString();

				/**} else if (pEnterpriseKey.equals("PAN") || pEnterpriseKey.equals("KATRIUM"))  {

				Conexao cx = new Conexao( "PAN" );			// Lembre-se que esta classe BusinessDelegate é quem deve ser responsável por TODAS AS FONTES DE DADOS!!! 
				PrcSigaWmsDAO.getCarregamento( cx, joCarregamento ) ;			
				return joCarregamento; //.toString();

			**/ 
			} else {
				return null;
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
