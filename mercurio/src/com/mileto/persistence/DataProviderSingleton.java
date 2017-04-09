package com.mileto.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import com.mileto.domain.business.BoardMessage;

public class DataProviderSingleton {

	private static DataProviderSingleton uniqueInstance = new DataProviderSingleton();

	private Queue<BoardMessage> filaMensagens;
	private Map<String, JsonStructure> dadosJson;

	private DataProviderSingleton() {
		filaMensagens = new ConcurrentLinkedQueue<BoardMessage>();
		dadosJson = new HashMap<String, JsonStructure>();
	}

	public static DataProviderSingleton getInstance() {
		return uniqueInstance;
	}

	public void save(String key, JsonStructure value) {
		//dados.put(key, value);
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
	public JsonObject getFirstMessage ( String enterprise, String appKey ) {

		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	
		JsonObjectBuilder j = Json.createObjectBuilder();

		for (BoardMessage b: filaMensagens) {
				if ( (b.getEnterprise().equals(enterprise)) && (b.getAppKey().equals(appKey)) ) {		

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





}