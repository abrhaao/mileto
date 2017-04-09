package com.mileto.domain.business;

import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.mileto.persistence.DataProviderSingleton;

public class BoardMessage implements Comparable {
	
	private Date momento;
	private String mensagem;
	private String assunto;	
	private String enterprise;
	private String appKey;
	
	
	public BoardMessage(String mensagem, String assunto, String enterprise, String appKey) {
		super();
		this.mensagem = mensagem;
		this.assunto = assunto;
		this.enterprise = enterprise;
		this.appKey = appKey;
	}
	
	public static void informa( String pEnterpriseKey, String pAssunto, String pAppKey ) {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		provider.putMessage(new BoardMessage( "Aviso direto da classe Message Manager", "PRONTUÁRIO", pEnterpriseKey, pAppKey));
	}

	
	@Override
	public int compareTo(Object myOtherObject) {
		Date d = ((BoardMessage)myOtherObject).getMomento();
		return this.momento.compareTo(d);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((momento == null) ? 0 : momento.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardMessage other = (BoardMessage) obj;
		if (momento == null) {
			if (other.momento != null)
				return false;
		} else if (!momento.equals(other.momento))
			return false;
		return true;
	}
	
	public Date getMomento() {
		return momento;
	}
	public void setMomento(Date momento) {
		this.momento = momento;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	
	

}
