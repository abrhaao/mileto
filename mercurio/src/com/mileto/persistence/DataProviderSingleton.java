package com.mileto.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import com.mileto.domain.business.BoardMessage;
import com.mileto.domain.entity.MovCarregamento;

/**
 * Esta classe provê os dados JSON e filas da aplicação
 * @author abrhaao
 */
public class DataProviderSingleton {

	private static DataProviderSingleton uniqueInstance = new DataProviderSingleton();

	private Queue<BoardMessage> filaMensagens;
	private Queue<MovCarregamento> filaCarregamento;
	private Map<String, JsonStructure> dadosJson;

	private DataProviderSingleton() {
		filaMensagens = new ConcurrentLinkedQueue<BoardMessage>();
		filaCarregamento = new ConcurrentLinkedQueue<MovCarregamento>();
		dadosJson = new HashMap<String, JsonStructure>();
	}

	public static DataProviderSingleton getInstance() {
		return uniqueInstance;
	}

	public void save(String key, JsonStructure value) {
		dadosJson.put(key, value);
	}

	public JsonStructure get(String key) {
		JsonStructure jsonData = dadosJson.get(key);
		return jsonData;
	}

	/**
	 * Coloca a criança no pool de mensagens. Associa a data atual para a mensagem.
	 * @param message
	 */
	public void putMessage(BoardMessage message) {
		if ( ! ( filaMensagens == null ) ) {
			message.setMomento(new GregorianCalendar().getTime());
			filaMensagens.add(message);
		}
	}

	public void persiste() {

		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	

		for ( MovCarregamento carregamento: filaCarregamento) {					
			JsonObjectBuilder j = Json.createObjectBuilder()
					.add("placa", 			carregamento.getPlaca())
					.add("veiculo", 		carregamento.getVeiculo() == null ? "" : carregamento.getVeiculo())
					.add("veiculoCidade", 	carregamento.getVeiculoCidade() == null ? "" : carregamento.getVeiculoCidade())
					.add("transportadora", 	carregamento.getTransportadora().getRazaoSocial())
					.add("icone", 			carregamento.getTransportadora().getLogotipo())
					.add("cliente", 		"CLIENTE")
					.add("clienteCidade", 	"MARACANDU")
					.add("doca", 			carregamento.getDoca())
					.add("motorista", 		carregamento.getMotorista())
					.add("pedido", 			carregamento.getPedido())
					.add("produto", 		carregamento.getProduto())
					.add("status", 			carregamento.getStatus())
					.add("instrucao", 		carregamento.getInstrucao());
			jsonArray.add(j);
		}

		/** Salva a lista no contexto da aplicação **/
		this.save("listaDemoProgramacaoVendas", jsonArray.build());
	}

	/**
	 * Coloca a criança no pool de carregamentos. Associa a data atual para a mensagem.
	 * @param message
	 */
	public void putCarregamento(MovCarregamento mov) {
		if ( ! ( filaCarregamento == null ) ) {			
			filaCarregamento.add(mov);
		}
	}

	public void withdrawOlder() {
		if ( ! ( filaMensagens == null ) ) {
			filaMensagens.forEach(message->{
				System.out.println(message.getMomento().toString());
			});
			//filaMensagens.remove(o);
		}
	}

	/**
	 * Pega a primeira mensagem da fila
	 * @return
	 */
	public JsonObject getFirstMessage ( String enterprise, String assunto ) {

		JsonObjectBuilder j = Json.createObjectBuilder();

		for (BoardMessage b: filaMensagens) {
			if ( (b.getEnterprise().equals(enterprise)) && (b.getAssunto().equals(assunto)) ) {		

				Calendar cal = Calendar.getInstance();
				cal.setTime( new Date() );
				cal.add(Calendar.MINUTE, -1);			

				/**
				 * Caso a mensagem esteja há muito tempo na lista, não considera
				 */
				if ( b.getMomento().getTime() > cal.getTime().getTime() ) {
					j = Json.createObjectBuilder()
							.add("assunto", b.getAssunto())
							.add("mensagem", b.getMensagem());
					return j.build();
				} else {
					filaMensagens.remove(b);
				}
			}

		}

		return j.build();

	}

	/**
	 * Retorna a fila de mensages pro cliente. A princípio a fila não deve ficar fisponível.
	 * Utilizado inicialmente para testes.
	 * @return
	 */
	public Queue<BoardMessage> getFilaMensagens() {		
		return filaMensagens;
	}

	public Queue<MovCarregamento> getFilaCarregamento() {
		if ( filaCarregamento.size() == 0) {

			try { 
				JsonArray array = (JsonArray)get("listaDemoProgramacaoVendas");			
				for (Object j: array.toArray()) {
					System.out.println(j);
					JSONObject jobject = new JSONObject(j.toString());

					MovCarregamento cgto = new MovCarregamento( jobject.get("pedido").toString(), 
							jobject.get("placa").toString(),
							jobject.get("veiculo").toString(),
							jobject.get("veiculoCidade").toString(), 
							jobject.get("motorista").toString(),
							jobject.get("status").toString(),
							jobject.get("instrucao").toString(),
							jobject.get("produto").toString(),
							jobject.get("transportadora").toString(),
							jobject.get("icone").toString(), 
							jobject.get("doca").toString()
							);
					putCarregamento(cgto);
				}
			} catch (JSONException joe) {
				joe.printStackTrace();
			}
		}
		return filaCarregamento;
	}

	public void setFilaCarregamento(Queue<MovCarregamento> filaCarregamento) {
		this.filaCarregamento = filaCarregamento;
	}





}