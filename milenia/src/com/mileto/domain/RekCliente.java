package com.mileto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@Table(name="REK_CLIENTE")
public class RekCliente implements Serializable, Comparable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "RAZAO_SOCIAL", nullable = false)
	private String razaoSocial;

	@Column(name = "NOME_FANTASIA", nullable = false)
	private String nomeFantasia;	

	@Column(name = "CHAVE1", nullable = false)
	private String chave1;

	@Column(name = "CHAVE2", nullable = false)
	private String chave2;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="EMPRESA", referencedColumnName="ID")
	private AdmEmpresa empresa;

	@Transient
	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Empresa Default", Icon.ROSA));
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RekCliente other = (RekCliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof RekCliente) {
			if ( this.getId() >  ((RekCliente)arg0).getId() )  {
				return 1;
			}
			if ( this.getId() <   ((RekCliente)arg0).getId() )  {
				return 1;
			}			
		}
		return 0;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getChave1() {
		return chave1;
	}

	public void setChave1(String chave1) {
		this.chave1 = chave1;
	}

	public String getChave2() {
		return chave2;
	}

	public void setChave2(String chave2) {
		this.chave2 = chave2;
	}

	public AdmEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(AdmEmpresa empresa) {
		this.empresa = empresa;
	}




}
