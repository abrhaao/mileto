package com.mileto.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.mileto.util.MyBean;
import org.mileto.util.StringAcol;
import org.richfaces.json.JSONObject;

import com.mileto.domain.business.BoardMessage;
import com.mileto.pattern.Conexao;

public class DemoDAO extends BaseDB {

	/**
	 * Busca programação de vendas
	 * @param dataProgramacao
	 * @see WMS
	 * @return
	 */
	public static JsonArray getWMSProgramacaoVendas_FromFile (String dataProgramacao ) {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();

		if (provider.get("listaDemoProgramacaoVendas") == null) { 

			try {

				/** Faz a leitura do banco de dados inicial, que é o arquivo JSON **/
				JsonReader reader = Json.createReader(new FileReader("/var/spool/mercurio/wmsprogramacao.json"));
				JsonArray jsonArray	= reader.readArray();

				/** Salva a lista no contexto da aplicação **/
				provider.save("listaDemoProgramacaoVendas", jsonArray);

				/** Refaz o método para recuperar a lista salva **/
				return getWMSProgramacaoVendas_FromFile(dataProgramacao);

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 

		} else { 

			//atualizaWMSCarregamento("000084-01");

			return (JsonArray)provider.get("listaDemoProgramacaoVendas");
		}

	}


	public static JsonArrayBuilder getProgramacaoVendas ( Conexao cx, List<Object> arrayParameters ) throws Exception { //LocalDate dataPosicao, , byte horasAtrasFaturadas ) 

		Connection db = cx.getConnection();
		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( " SELECT * FROM HR.BI_CARREGAMENTO ");

		try {

			PreparedStatement pstmt = db.prepareStatement(sqlBuilder.toString());		
			java.sql.ResultSet rs = pstmt.executeQuery();			
			//rs.beforeFirst();
			while(rs.next()){

				JsonObjectBuilder j = Json.createObjectBuilder()
						.add("placa", rs.getString("PLACA"))
						.add("veiculo", rs.getString("VEICULO"))
						.add("veiculoCidade", rs.getString("VEICULO_CIDADE"))
						.add("transportadora", rs.getString("TRANSP"))	
						.add("cliente", rs.getString("CLIENTE"))
						.add("clienteCidade", rs.getString("CLIENTE_CIDADE"))
						.add("doca", rs.getString("ENDERECO"))				
						.add("motorista", rs.getString("MOTORISTA").trim())
						.add("pedido", rs.getString("PEDIDO") )
						.add("produto", rs.getString("DESCRICAO").trim())
						.add("produtoCodigo", rs.getString("PRODUTO"))
						.add("status", rs.getString("STATUS"))
						.add("instrucao", " " )
						.add("filial", rs.getString("FILIAL"))
						.add("produtoOnu", StringAcol.nvl( rs.getString("NUMONU"), " ") )
						.add("produtoTes", StringAcol.nvl( rs.getString("TES"), " ") )
						.add("quantidade", rs.getDouble("QUANT") )
						.add("icone", PrcSigaWmsDAO.getLogo( rs.getString("TRANSP")) );

				if (rs.getString("STATUS").equals("NF EMITIDA")) {
					j.add("hora", StringAcol.nvl( rs.getString("HORA_NF"), "") );
				} else if ( rs.getString("STATUS").equals("CARREGANDO")) {
					j.add("hora", StringAcol.nvl( rs.getString("HORA_TICKET"), "") );
				}

				BoardMessage evento = DataProviderSingleton.getInstance().getEvento(  rs.getString("PEDIDO") );
				if ( evento instanceof BoardMessage ) {
					j.add("highlightStatus", "shining");
				}

				jsonArray.add(j);

			}
			rs.close();
			pstmt.close();					

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		return jsonArray;
	}	



	public static JsonObject getCarregamento ( Conexao cx, JsonObject joCarregamento ) throws Exception {

		Connection db = cx.getConnection();

		//DynaClass dynaClass = new BasicDynaClass("Carregamento", null, new DynaProperty[]
		//{new DynaProperty("pedido", ("").getClass()), 
		// new DynaProperty("ticket", ("").getClass()) });


		//DynaBean car = dynaClass.newInstance();
		DynaBean car = MyBean.instantiate("Carregamento", new Object[][] {
			{"filial", " "},
			{"pedido", " "},
			{"ticket", " "},
			{"placa", " "}, 
			{"cliente", " "},
			{"clienteCidade", " "},
			{"doca", " "},
			{"motorista", " "},
			{"transportadora", " "},
			{"produto", " "},
			{"produtoCodigo", " "},
			{"produtoOnu", " "},
			{"notaFiscal", " "},
			{"quantidade", new Double(0) },
			{"inicioCagto", ""},
			{"finalCagto", ""},
		});
		//car.set("pedido", "P1");
		//car.set("ticket", "T54748");

		//DynaProperty[] d = car.getDynaClass().getDynaProperties();
		//for ( DynaProperty property: d) {
		//}

		//System.out.println("Number of wheels: " + car.get("pedido"));
		//System.out.println("Number of wheels: " + car.get("ticket"));  


		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( " SELECT * FROM HR.BI_CARREGAMENTO WHERE pedido = ? AND filial = ? ");    		

		PreparedStatement pstmtCagto = db.prepareStatement(sqlBuilder.toString());	
		pstmtCagto.setString(1, joCarregamento.getString("pedido"));
		pstmtCagto.setString(2, joCarregamento.getString("filial"));
		java.sql.ResultSet rsCagto = pstmtCagto.executeQuery();			
		while(rsCagto.next()){
			car.set("placa", rsCagto.getString("PLACA"));    						
			car.set("transportadora", rsCagto.getString("TRANSP"));
			car.set("cliente", rsCagto.getString("CLIENTE"));
			car.set("clienteCidade", rsCagto.getString("CLIENTE_CIDADE"));
			car.set("doca", rsCagto.getString("ENDERECO"));				
			car.set("motorista", rsCagto.getString("MOTORISTA").trim());
			car.set("pedido", rsCagto.getString("PEDIDO") );
			car.set("produto", rsCagto.getString("DESCRICAO").trim());
			car.set("produtoCodigo", rsCagto.getString("PRODUTO"));
			car.set("produtoOnu", StringAcol.nvl( rsCagto.getString("NUMONU"), " ") );
			car.set("quantidade", rsCagto.getDouble("QUANT"));
			car.set("filial", rsCagto.getString("FILIAL"));
		}
		rsCagto.close();
		pstmtCagto.close();

		//StringBuilder sqlBuilderDoca = new StringBuilder();
		//sqlBuilderDoca.append( " SELECT * FROM HR.BI_CARREGAMENTO_DOCA WHERE PEDIDO = ? AND FILIAL = ?	");  

		StringBuilder sqlBuilderTicket = new StringBuilder();
		sqlBuilderTicket.append( " SELECT * FROM HR.BI_CARREGAMENTO_SZP SZP, HR.BI_CARREGAMENTO_TICKET SZQ ");
		sqlBuilderTicket.append( " WHERE SZP.FILIAL = SZQ.ZQ_FILIAL ");
		sqlBuilderTicket.append( " AND   SZP.TICKET = SZQ.ZQ_CODIGO ");
		sqlBuilderTicket.append( " AND   SZP.PEDIDO = ? AND SZP.FILIAL = ? ");

		try {

			PreparedStatement pstmt = db.prepareStatement( sqlBuilderTicket.toString() );			
			pstmt.setString(1, joCarregamento.getString("pedido"));
			pstmt.setString(2, joCarregamento.getString("filial"));
			java.sql.ResultSet rs = pstmt.executeQuery();			

			while (rs.next()){			
				
				car.set("ticket", rs.getString("TICKET"));
				car.set("notaFiscal", rs.getString("NOTAF"));
				car.set("inicioCagto", "03/07 12:50");
				car.set("finalCagto", "");
				//.add("carregamento_dtinicio", rs.getString("DTINICIO").trim())
				//.add("carregamento_dtfim", 	rs.getString("DTFIM").trim());							
			}
			rs.close();

			//pstmt.setString(1, joCarregamento.getString("pedido"));
			//pstmt.setString(2, joCarregamento.getString("filial"));

			//rs.beforeFirst();
			JsonObject jTicket = Json.createObjectBuilder().add("ticketCod", "T3546467").build();

			//while(rs.next()){
				//joCarregamento.put("ticket", jTicket);
				//joCarregamento = jsonObjectToBuilder(joCarregamento)
				//	.add("ticket", rs.getString("TICKET")).build();


				//joCarregamento.put("doca", rs.getString("DOCA").trim())
				//.add("carregamento_dtinicio", rs.getString("DTINICIO").trim())
				//.add("carregamento_dtfim", 	rs.getString("DTFIM").trim());							
			//}
			//rs.close();
			pstmt.close();					

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		JsonObject jo = MyBean.makeJson( (BasicDynaBean) car );
		return jo;

	}



















	public static JsonObject atualizaWMSCarregamento (String pPedido, String pDoca, String pStatus) {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();


		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	
		JsonObjectBuilder j = Json.createObjectBuilder();


		if (provider.get("listaDemoProgramacaoVendas") != null) { 


			Map<String,String> statusCarregamento = new HashMap<String, String>();
			statusCarregamento.put("SA", "Aguardando tramits");
			statusCarregamento.put("SB", "Aguardando Pesagem Inicial");
			statusCarregamento.put("SC", "Encaminhando para as Docas");
			statusCarregamento.put("SD", "Carregando");
			statusCarregamento.put("SE", "Trocando de Docas");
			statusCarregamento.put("SF", "Retirando Veículo");
			statusCarregamento.put("SG", "Aguardando Pesagem Final");
			statusCarregamento.put("SH", "Aguardando Emissão da Nota Fiscal");
			statusCarregamento.put("SK", "Finalizado");




			try { 
				for (JsonValue jo: (JsonArray)provider.get("listaDemoProgramacaoVendas")) {
					//Json.createObjectBuilder().add("uno", jo);

					//JsonObject jobo = Json.createObjectBuilder().
					JSONObject jobject = new JSONObject(jo.toString());
					if ( jobject.getString("pedido").equals(pPedido) ) {
						jobject.put( "doca", pDoca );
						jobject.put( "status", statusCarregamento.get(pStatus));
					}					



					//JsonBuilderFactory factory = Json.createBuilderFactory(config);
					//JsonArrayBuilder value = Json.createArrayBuilder()
					//  .add(Json.createObjectBuilder().
					//    .add("type", "home")
					//  .add("number", "212 555-1234");


					j = Json.createObjectBuilder()
							.add("placa", jobject.getString("placa"))
							.add("veiculo", jobject.getString("veiculo"))
							.add("veiculoCidade", jobject.getString("veiculo"))
							.add("transportadora", jobject.getString("transportadora"))
							.add("cliente", jobject.getString("cliente"))
							.add("clienteCidade", jobject.getString("clienteCidade"))
							.add("motorista", jobject.getString("motorista"))
							.add("pedido", jobject.getString("pedido"))
							.add("produto", jobject.getString("produto"))
							.add("status", jobject.getString("status"))
							.add("instrucao", jobject.getString("instrucao"))
							.add("doca", jobject.getString("doca"));							


					//jsonArray.add(j.build());




				}
				//provider.save("listaDemoProgramacaoVendas", jsonArray.build());

			} catch (Exception e) {
				e.printStackTrace();
			}


		}
		return j.build();

	}



	/**
	 * Busca a lista de todos os possíveis status de carregamento
	 * @see WMS
	 * @return
	 */
	public static JsonArray getWMSListaStatusCarregamento () {

		try {

			/** Faz a leitura do banco de dados inicial, que é o arquivo JSON **/
			JsonReader reader = Json.createReader(new FileReader("/var/spool/mercurio/wmslistastatus.json"));
			JsonArray jsonArray	= reader.readArray();

			/** Refaz o método para recuperar a lista salva **/
			return jsonArray;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 


	}


	private static JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
		JsonObjectBuilder job = Json.createObjectBuilder();

		for (Entry<String, JsonValue> entry : jo.entrySet()) {
			job.add(entry.getKey(), entry.getValue());
		}

		return job;
	}

}