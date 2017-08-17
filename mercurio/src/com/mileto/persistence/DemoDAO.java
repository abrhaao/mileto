package com.mileto.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
		sqlBuilder.append( " SELECT * FROM HR.BI_CARREGAMENTO, HR.BI_CARREGAMENTO_SZP SZP ");// WHERE DESCRICAO LIKE '%CLORO%' ");
		sqlBuilder.append( " WHERE BI_CARREGAMENTO.FILIAL = SZP.ZP_FILIAL ");
		sqlBuilder.append( " AND   BI_CARREGAMENTO.PEDIDO = ZP_PEDIDO || '-' || ZP_ITEM ");

		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM - HH:mm");
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

			PreparedStatement pstmt = db.prepareStatement(sqlBuilder.toString());		
			java.sql.ResultSet rs = pstmt.executeQuery();			
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

				/** status TEM QUE VIR PERFEITO **/
				if (rs.getString("STATUS").equals("NF EMITIDA")) {
					j.add("hora", StringAcol.nvl( rs.getString("HORA_NF"), "") );
				} else if ( rs.getString("STATUS").equals("CARREGANDO")) {
					//if (rs.getDate("DTINICIO") == null ) {yyyyMMddHHmmss");
					//j.add("status", "NAO INICIADO");
					//} else if ( rs.getDate("DTFIM") != null ) {
					//j.add("status", "CONCLUÍDO");
					//}
					j.add("hora", StringAcol.nvl( rs.getString("HORA_TICKET"), "") );
				}


				if ( rs.getString("STATUS").trim().equals("NAO INICIADO") ) {				
					j.add("inicioCagto", "");
					j.add("finalCagto", "");
				}


				String hInicio 	=	rs.getString("ZP_DTCG") + " " + rs.getString("ZP_HRCG");
				String hFinal 	=	rs.getString("ZP_DTCGS") + " " + rs.getString("ZP_HRCGS");

				//System.out.println("PEDIDO " + rs.getString("PEDIDO") + " STATUS = " + rs.getString("STATUS")) ;
				if ( rs.getString("STATUS").trim().equals("CARREGANDO") ) {
					try { 
						Instant tInicio = sf.parse(hInicio).toInstant();
						j.add("inicioCagto", formatter.format( ZonedDateTime.ofInstant(tInicio, ZoneId.systemDefault())) );
					} catch (ParseException pe) {
						j.add("inicioCagto", "");
					}
					j.add("finalCagto", "");
				} else if ( rs.getString("STATUS").trim().equals("CONCLUIDO") ) {
					
					Instant tInicio = sf.parse(hInicio).toInstant();
					Instant tFinal = sf.parse(hFinal).toInstant();

					j.add("inicioCagto", formatter.format( ZonedDateTime.ofInstant(tInicio, ZoneId.systemDefault())) );
					j.add("finalCagto", formatter.format( ZonedDateTime.ofInstant(tFinal, ZoneId.systemDefault())) );
				} else { 
					j.add("inicioCagto", "") ;
					j.add("finalCagto", "") ;
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
			car.set("inicioCagto", joCarregamento.getString("inicioCagto"));
			car.set("finalCagto", joCarregamento.getString("finalCagto"));
		}
		rsCagto.close();
		pstmtCagto.close();

		//StringBuilder sqlBuilderDoca = new StringBuilder();
		//sqlBuilderDoca.append( " SELECT * FROM HR.BI_CARREGAMENTO_DOCA WHERE PEDIDO = ? AND FILIAL = ?	");  

		StringBuilder sqlBuilderTicket = new StringBuilder();
		sqlBuilderTicket.append( " SELECT * FROM HR.BI_CARREGAMENTO_SZP SZP, HR.BI_CARREGAMENTO_TICKET SZQ ");
		sqlBuilderTicket.append( " WHERE SZP.ZP_FILIAL (+) = SZQ.ZQ_FILIAL ");
		sqlBuilderTicket.append( " AND   SZP.ZP_TICKET (+) = SZQ.ZQ_CODIGO ");
		sqlBuilderTicket.append( " AND   SZP.ZP_PEDIDO = ? AND SZP.ZP_FILIAL = ? ");

		try {

			PreparedStatement pstmt = db.prepareStatement( sqlBuilderTicket.toString() );			
			pstmt.setString(1, joCarregamento.getString("pedido"));
			pstmt.setString(2, joCarregamento.getString("filial"));
			java.sql.ResultSet rs = pstmt.executeQuery();			

			while (rs.next()){			

				/** Já fiz na etapa anterior 
				if ( rs.getDate("INICIO_CAGTO") == null ) {
					car.set("inicioCagto", "");
				} else { 
					car.set("inicioCagto", rs.getDate("INICIO_CAGTO").toLocaleString());
				}
				if ( rs.getDate("FINAL_CAGTO") == null ) {
					car.set("finalCagto", "");
				} else { 
					car.set("finalCagto", rs.getDate("INICIO_CAGTO").toLocaleString());
				}
				 **/
				car.set("ticket", rs.getString("TICKET"));
				car.set("notaFiscal", rs.getString("NOTAF"));						
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



















	public static JsonObject atualizaCarregamento ( Conexao cx, String pFilial, String pPedido, String pEvento, String pDoca, String pLote, String pLacre) {

		JsonObjectBuilder j = Json.createObjectBuilder();

		try {

			String fieldData	=	"";
			String fieldHora	=	"";
			String novoStatus	=	"";

			if (pEvento.equals("inicio")) {
				fieldData =	"zp_dtcg";
				fieldHora =	"zp_hrcg";
				novoStatus	=	"CARREGANDO";			
			} else if ( pEvento.equals("fim")) {				
				fieldData =	"zp_dtcgs";
				fieldHora =	"zp_hrcgs";
				novoStatus	=	"CONCLUIDO";												
			}

			StringBuilder builder = new StringBuilder();
			builder.append( " UPDATE hr.bi_carregamento_szp SET " + fieldData + " = to_char ( sysdate, 'yyyymmdd' ), " + fieldHora + " = to_char ( sysdate, 'hh24:mi:ss' ), "); //, doca = '" + pDoca + "' ");
			builder.append( " zp_lote = '" + pLote + "', zp_lacres = '" + pLacre + "' ");
			builder.append( " WHERE zp_filial = '" + pFilial + "' AND zp_pedido = '" + pPedido.substring(0,6) + "' and zp_item = '" + pPedido.substring(7,9) + "' ");

			cx.prepareCall( builder.toString() );
			cx.executeProc();					
			cx.commit();

			builder = new StringBuilder();
			builder.append( " UPDATE hr.bi_carregamento SET status = '" + novoStatus+ "' "); 
			builder.append( " WHERE filial = '" + pFilial + "' AND pedido = '" + pPedido + "' ");

			cx.prepareCall( builder.toString() );
			cx.executeProc();					
			cx.commit();
			cx.closeConnection();

			j = Json.createObjectBuilder()
					.add("ficouLegal", "OK");													

		} catch (Exception e) {
			e.printStackTrace();

			j = Json.createObjectBuilder()
					.add("ficouLegal", "NO");			
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