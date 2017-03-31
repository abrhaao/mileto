package com.mileto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADM_SISTEMA")
public class AdmSistema {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ADM_COD")
	private String admCod;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "NOME_COMPLETO", nullable = false)
	private String nomeCompleto;	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admCod == null) ? 0 : admCod.hashCode());
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
		final AdmSistema other = (AdmSistema) obj;
		if (admCod == null) {
			if (other.admCod != null)
				return false;
		} else if (!admCod.equals(other.admCod))
			return false;
		return true;
	}

	public String getAdmCod() {
		return admCod;
	}

	public void setAdmCod(String admCod) {
		this.admCod = admCod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}			
}
