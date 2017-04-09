package com.backinbean;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.richfaces.component.UIDataTable;


/**
 * Caso de Uso: Listar datatables do sistema
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@RequestScoped
public class TabelarBean extends BackingBean {
	
	private UIDataTable dataTableListaProdutos;
	//private UIDataTable dataTableListaEmpresas;
	//private UIDataTable dataTableListaParametros;
	private UIDataTable dataTableListaFciProducoes;
	private UIDataTable dataTableListaFciCompras;
	private UIDataTable dataTableListaFciVendas;
	
	private UIDataTable dataTablePopUp;

	public UIDataTable getDataTableListaProdutos() {
		return dataTableListaProdutos;
	}

	public void setDataTableListaProdutos(UIDataTable dataTableListaProdutos) {
		this.dataTableListaProdutos = dataTableListaProdutos;
	}

	public UIDataTable getDataTableListaFciCompras() {
		return dataTableListaFciCompras;
	}

	public void setDataTableListaFciCompras(UIDataTable dataTableListaFciCompras) {
		this.dataTableListaFciCompras = dataTableListaFciCompras;
	}

	public UIDataTable getDataTableListaFciVendas() {
		return dataTableListaFciVendas;
	}

	public void setDataTableListaFciVendas(UIDataTable dataTableListaFciVendas) {
		this.dataTableListaFciVendas = dataTableListaFciVendas;
	}

	public UIDataTable getDataTableListaFciProducoes() {
		return dataTableListaFciProducoes;
	}

	public void setDataTableListaFciProducoes(UIDataTable dataTableListaProducoes) {
		this.dataTableListaFciProducoes = dataTableListaProducoes;
	}

	public UIDataTable getDataTablePopUp() {
		return dataTablePopUp;
	}

	public void setDataTablePopUp(UIDataTable dataTablePopUp) {
		this.dataTablePopUp = dataTablePopUp;
	}
	
	
	

	
	
}
