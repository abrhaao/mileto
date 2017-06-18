package com.mileto.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.mileto.pattern.Icon;


public class RekVoto implements Serializable {

	//@Id @GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name = "ID")
	private Integer id;

	//@Column(name = "NOME", nullable = false)
	private RekEnquete enquete;

	//@Column(name = "DESCRICAO", nullable = false)
	private Timestamp date;

	//@Column(name = "RADIO", nullable = false)
	private String voto

	//@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="CLIENTE", referencedColumnName="ID")
	//private OrderedList<String> opcoes;

	

	@Transient
	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Empresa Default", Icon.VERMELHO));
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


	

}