package com.mileto.services;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.omg.CORBA.SystemException;

/** 
 * Singleton que tem como responsabilidade pesquisar e localizar os 
 * servicos solicitados, retornando-os atraves de interfaces.
 * @author Mayworm, ajustado para ProtheusWeb por Abrhaão Ribeiro
 * @date   17/12/2002
 **/
public class ServiceLocator {
	
    //private static final String RESOURCE_BUNDLE_CONFIG 		 = "com.application.config"; 
    //private static final String RESOURCE_BUNDLE_CONSTANTE    = "com.application.constante";
    //private static final String RESOURCE_BUNDLE_UMENU		=  "com.application.umenu";
	private static final String RESOURCE_BUNDLE_DEVELOPMENT 		 = "com.application.development";

	/**
	 * Representa a instancia do singleton
	 */
	private static ServiceLocator instance;
	
	/**
	 * Map para armazenar os objetos em cache
	 */
	private Map cache;
	
	/**
	 * Armazena o context.
	 */
	private InitialContext ctx = null;
	
	/**
	 * Objeto para auxiliar no sincronismo
	 */
	private static Integer serviceLock = new Integer(1);
	
	
	/**
	 * Construtor privado para garantir a 
	 * utilizacao do Singleton.
	 */
	private ServiceLocator(){}
	
	/**
	 * Metodo responsavel por verificar se a 
	 * classe ja foi instanciada alguma vez, 
	 * retornando a referencia estatica existente.
	 * Representa o Singleton.
	 * 
	 * @return ServiceLocator
	 * 
 	 * @date   17/12/2002
 	 **/
	public static ServiceLocator instance() {
		if(instance == null){
			instance = new ServiceLocator();
			instance.initializeCache();

			instance.initializeCache();
			instance.loadFileApplication(RESOURCE_BUNDLE_DEVELOPMENT);
			//instance.loadFileApplication(RESOURCE_BUNDLE_CHEMIN);			
		}

		return instance;	
	}
	
     /**
	  * Esse metodo retorna um objeto no contexto padrao do JNDI com o 
	  * JNDI name armazenado.
	  * 
	  * @param jndiName - JNDI name
	  * @return Object O objeto existente na arvore JNDI tree para o name informado.
	  * @throws SystemException Exception para esse metodo  
	  **/
	public Object getService( String jndiName ) throws Exception {
		
		try {
			
			// verifica se o servico ainda nao esta no cache
			if( !cache.containsKey( jndiName ) ) {
				//obtem o objeto solicitado no JNDI e armazena no cache
				cache.put( jndiName, ctx.lookup( jndiName ) );
				//logger.debug(" @@@ InitialContext:"+ctx.lookup(jndiName)+"@@@");
			}
			
		} catch( NamingException ex ) {
			//logger.debug("@@@ NamingException... Erro ocorrido no metodo getService em ServiceLocator @@@");
			throw new Exception(ex.getMessage() );
			
		} catch( SecurityException ex ) {
			//logger.error("@@@ SecurityException Erro ocorrido no metodo getService em ServiceLocator @@@");

			////logger.fatal("Erro ocorrido no metodo getService em ServiceLocator", ex);
			throw new Exception(ex.getMessage() );
		} catch( NullPointerException ex ) {
			//logger.error(" @@@ NullPointerException Erro ocorrido no metodo getService em ServiceLocator @@@");
		}	
		// retorna o objeto do cache
		Object returnObject = cache.get(jndiName);
		////logger.debug("getService(String) - end");
		return returnObject;
	}
	

	/**
	  * Esse metodo o objeto com o 
	  * JNDI name armazenado.
	  * 
	  * @param jndiName - JNDI name
	  * @throws SystemException Exception para esse metodo  
	  **/
	public void removeService( String jndiName ) throws SecurityException {
		try {
			// verifica se o servico  esta no cache
			if( cache.containsKey( jndiName ) ) {
				//remove o objeto solicitado do cache
				cache.remove( jndiName);
			}
			
		} catch( SecurityException ex ) {
			throw ex;
			
		}
	}
	


	
	/**
	 * Metodo de auxilio usado para os varios 
	 * "facades" existente.
	 * 
	 * @param String - nome da propriedade a ser acessada
	 * @return String - o valor da propriedade acessada
	 */
	public String getProperty(String sPropertyName) throws Exception {
		String sPropertyValue = null;
		sPropertyValue = (String) cache.get(sPropertyName);
		if (sPropertyValue == null){
			throw new Exception("Propriedade " + sPropertyName + " n\343o especificada");
		}else{
			return sPropertyValue;
		}
	}
	
	
	
	/**
	 * Inicializa a tabela que armazenara as views definidas no 
	 * uriRegistry.
	 */
	protected void initializeCache() {
		if (cache == null){
			cache = Collections.synchronizedMap(new HashMap());
		}
		else{
			cache.clear();
		}
	}	

	
	/**
	 * Carrega para a instância do Service Locator um arquivo de recursos
	 * @param sFileAppGeral
	 */
	private void loadFileApplication(String sFileAppGeral) {
		ResourceBundle  bundle = ResourceBundle.getBundle(sFileAppGeral,Locale.ENGLISH);
		Enumeration     bundleKeys = bundle.getKeys();
		
		while (bundleKeys.hasMoreElements()) {
			String key = (String)bundleKeys.nextElement();
			String value  = bundle.getString(key);
			cache.put(key, value);
		}		
	}

}