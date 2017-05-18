package com.mileto.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.richfaces.json.JSONObject;

public class DemoDAO //extends BaseDB 
{


	/**
	 * Busca programação de vendas
	 * @param dataProgramacao
	 * @see WMS
	 * @return
	 */
	public static JsonArray getWMSProgramacaoVendas(String dataProgramacao ) {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();

		if (provider.get("listaDemoProgramacaoVendas") == null) { 

			try {

				/** Faz a leitura do banco de dados inicial, que é o arquivo JSON **/
				JsonReader reader = Json.createReader(new FileReader("/var/spool/mercurio/wmsprogramacao.json"));
				JsonArray jsonArray	= reader.readArray();

				/** Salva a lista no contexto da aplicação **/
				provider.save("listaDemoProgramacaoVendas", jsonArray);

				/** Refaz o método para recuperar a lista salva **/
				return getWMSProgramacaoVendas(dataProgramacao);

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 

		} else { 

			return (JsonArray)provider.get("listaDemoProgramacaoVendas");
		}

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

}