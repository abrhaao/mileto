package com.backinbean;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.richfaces.component.UIDataTable;

import com.mileto.domain.AdmParametro;


/**
 * Caso de Uso: Listar datatables do sistema
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@RequestScoped
public class TabelarBean extends BackingBean {

	
	private UIDataTable dataTableListaUsuarios;
	private UIDataTable dataTableListaEmpresas;
	private UIDataTable dataTableListaParametros;
	private UIDataTable dataTableListaClientes;
	
	private UIDataTable dataTableListaEnquetes;
	private UIDataTable dataTableListaOpcoesEnquete;

	
	public UIDataTable getDataTableListaUsuarios() {
		return dataTableListaUsuarios;
	}

	public void setDataTableListaUsuarios(UIDataTable dataTableListaUsuarios) {
		this.dataTableListaUsuarios = dataTableListaUsuarios;
	}	
	
	public void setDataTableListaParametros(UIDataTable dataTableListaParametros) {
		this.dataTableListaParametros = dataTableListaParametros;
	}

	public UIDataTable getDataTableListaEmpresas() {
		return dataTableListaEmpresas;
	}

	public void setDataTableListaEmpresas(UIDataTable dataTableListaEmpresas) {
		this.dataTableListaEmpresas = dataTableListaEmpresas;
	}

	public UIDataTable getDataTableListaClientes() {
		return dataTableListaClientes;
	}

	public void setDataTableListaClientes(UIDataTable dataTableListaClientes) {
		this.dataTableListaClientes = dataTableListaClientes;
	}

	public UIDataTable getDataTableListaParametros() {
		return dataTableListaParametros;
	}

	public UIDataTable getDataTableListaEnquetes() {
		return dataTableListaEnquetes;
	}

	public void setDataTableListaEnquetes(UIDataTable dataTableListaEnquetes) {
		this.dataTableListaEnquetes = dataTableListaEnquetes;
	}

	public UIDataTable getDataTableListaOpcoesEnquete() {
		return dataTableListaOpcoesEnquete;
	}

	public void setDataTableListaOpcoesEnquete(UIDataTable dataTableListaOpcoesEnquete) {
		this.dataTableListaOpcoesEnquete = dataTableListaOpcoesEnquete;
	}
	
	

	
	
}
