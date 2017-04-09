package com.mileto.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mileto.pattern.Icon;

public class RekProduto implements Serializable {

	private String id;
	private String descricao;
	private String unimed;
	private String tipo;
	private String origem;

	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Empresa Default", Icon.VERMELHO));
		//hashIconStatus.put("2", new Icon("Usuários Premium", Icon.LARANJA));
		//hashIconStatus.put("9", new Icon("Desenvolvedores",   Icon.ROSA));	
	}

	public String getIconStatus() {
		setIconStatus(Icon.getGraphicIcon(this.hashIconStatus, new Integer(1).toString()));
		return iconStatus;
	}	

	public void setIconStatus(String iconStatus) {
		this.iconStatus = iconStatus;
	}
	
	public static List<Icon> getListaIcones() {
		return new ArrayList<Icon>(hashIconStatus.values());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnimed() {
		return unimed;
	}

	public void setUnimed(String unimed) {
		this.unimed = unimed;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	
	
	



}