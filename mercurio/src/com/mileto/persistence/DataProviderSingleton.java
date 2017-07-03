package com.mileto.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import com.mileto.domain.business.BoardMessage;
import com.mileto.services.Security;

public class DataProviderSingleton {

	private static DataProviderSingleton uniqueInstance = new DataProviderSingleton();

	private Queue<BoardMessage> filaMensagens;
	private Map<String, String> repositorioImagens;
	private Map<String, BoardMessage> repositorioEventos;


	/** Ainda será usado? **/
	private Map<String, JsonStructure> dadosJson;

	private DataProviderSingleton() {
		
		/** Fila de mensagens para as aplicações **/		
		filaMensagens = new ConcurrentLinkedQueue<BoardMessage>();
		
		/** Repositório de imagens para as aplicações **/
		repositorioImagens = new HashMap<String, String>();
		
		/** Repositório de eventos para as aplicações **/
		repositorioEventos = new HashMap<String, BoardMessage>();
		

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


	/** API de Mensagens *******************************************************************************************************************/
	
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

	/**
	 * Pega a primeira mensagem da fila
	 * @return
	 */
	public JsonObject getFirstMessage ( String enterprise, String appKey ) {

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
	 * Retorna a fila de mensages pro cliente. 
	 * Utilizado inicialmente para testes.
	 * @return
	 */
	public Queue<BoardMessage> getFilaMensagens() {
		return filaMensagens;
	}
	
	
	/** API de Eventos *******************************************************************************************************************/
	
	/**
	 * Coloca a criança no pool de eventos. Associa a data atual para o evento.
	 * @param message
	 */
	public void putEvento(String key, BoardMessage message) {
		if ( ! ( repositorioEventos == null ) ) {
			message.setMomento(new GregorianCalendar().getTime());
			repositorioEventos.put(key, message);
		}
	}

	/**
	 * Pega os dados do evento
	 * @return
	 */
	public BoardMessage getEvento ( String key ) {

		Calendar cal = Calendar.getInstance();
		cal.setTime( new Date() );
		cal.add(Calendar.MINUTE, -1);
		
		for (Map.Entry<String, BoardMessage> entry: repositorioEventos.entrySet())	{
			BoardMessage evento = repositorioEventos.get( entry.getKey() );
			
			/**
			 * Caso a mensagem esteja há muito tempo na lista, não considera
			 */
			if ( evento.getMomento().getTime() > cal.getTime().getTime() ) {								
				if ( entry.getKey().equals(key) ) {
					return evento;
				} 				
			} else {
				repositorioEventos.remove( entry.getKey() );
			}	
		}
		return null;
	}

	
	/** API de Imagens *******************************************************************************************************************/

	public String getImagem(String key) {
		String urlImagem = repositorioImagens.get(key);
		return urlImagem;
	}

	public String putImagem ( String folder, String resourceKey, byte[] imagem ) {
		File fileSpool 	= 	 new File ("" + System.getProperty("catalina.base") 	+ "//" + "webapps" + "//" + "mercurio" + "//" + "spool" ) ;
		fileSpool.mkdir();
		File fileEnquetes =  new File ( fileSpool.getAbsolutePath() + "//" +  folder + "//");
		fileEnquetes.mkdir();		
		
		String pathEnquetes	= 	 fileEnquetes.getAbsolutePath() + "//";
		
		try { 
			File arquivoImagem	= new File ( pathEnquetes + Security.getUniqueKey ( 4, 1 ) + ".jpg" );
			FileOutputStream arquivoImagemStream = new FileOutputStream ( arquivoImagem );
			arquivoImagemStream.write(imagem);
			arquivoImagemStream.close();

			repositorioImagens.put(resourceKey, "http://10.80.80.4:8080/mercurio/spool/" + folder + "//" + arquivoImagem.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getImagem(resourceKey);



	}



}