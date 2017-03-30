package com.backinbean;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.AdmParametro;
import com.mileto.domain.AdmUsuario;
import com.mileto.domain.RekCliente;
import com.mileto.pattern.Icon;


/**
 * Caso de Uso: Informar ao usuário dados do contexto de aplicacao
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

/**
 * @author kubuntu
 *
 */
@ManagedBean
@ApplicationScoped
public class InformarBean extends BackingBean {
	

	private List<Icon> listaIconesUsuarios = AdmUsuario.getListaIcones();
	private List<Icon> listaIconesEmpresas = AdmEmpresa.getListaIcones();
	private List<Icon> listaIconesParametros = AdmParametro.getListaIcones();
	private List<Icon> listaIconesClientes = RekCliente.getListaIcones();
	
	private String variavelTeste;
	
	public List<Icon> getListaIconesUsuarios() {
		return listaIconesUsuarios;
	}
	public void setListaIconesUsuarios(List<Icon> listaIconesUsuarios) {
		this.listaIconesUsuarios = listaIconesUsuarios;
	}
	public List<Icon> getListaIconesEmpresas() {
		return listaIconesEmpresas;
	}
	public void setListaIconesEmpresas(List<Icon> listaIconesEmpresa) {
		this.listaIconesEmpresas = listaIconesEmpresa;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

	public List<Icon> getListaIconesClientes() {
		return listaIconesClientes;
	}
	public void setListaIconesClientes(List<Icon> listaIconesClientes) {
		this.listaIconesClientes = listaIconesClientes;
	}
	public List<Icon> getListaIconesParametros() {
		return listaIconesParametros;
	}
	public void setListaIconesParametros(List<Icon> listaIconesParametros) {
		this.listaIconesParametros = listaIconesParametros;
	}
	public String getVariavelTeste() {
		return variavelTeste;
	}

	public void setVariavelTeste(String variavelTeste) {
		this.variavelTeste = variavelTeste;
	}
	
	
	
	


}