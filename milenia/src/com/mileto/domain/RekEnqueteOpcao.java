package com.mileto.domain;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mileto.pattern.Icon;

@Entity
@Table(name="REK_ENQUETE_OPCAO")
public class RekEnqueteOpcao implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ID_OPCAO")
	private String idOpcao;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "PROPOSTA")
	private String proposta;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ENQUETE", referencedColumnName="ID")
	private RekEnquete enquete;

	
	@Transient
	private String iconStatus;



	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estado
	 */
	
	public String getIdOpcao() {
		return idOpcao;
	}
	
	

	public RekEnqueteOpcao() {
		super();
	}

	public void setIdOpcao(String idOpcao) {
		this.idOpcao = idOpcao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public RekEnquete getEnquete() {
		return enquete;
	}

	public void setEnquete(RekEnquete enquete) {
		this.enquete = enquete;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}
	
	
	


	

}