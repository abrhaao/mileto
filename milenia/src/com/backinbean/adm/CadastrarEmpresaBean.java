package com.backinbean.adm;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UIDataTable;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;
import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.AdmParametro;
import com.mileto.pattern.BusinessException;

/**
 * Caso de Uso: Cadastrar Empresas
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@RequestScoped
public class CadastrarEmpresaBean extends BackingBean {


	private String buscaNome;
	//private String buscaEmail;	
	//private List<Icon>	iconesUsuarios;

	private AdmEmpresa empresa;
	//private AdmPerfil perfil;

	private Collection<AdmEmpresa> listaEmpresas = new ArrayList();
	

	private UIDataTable dataTableListaEmpresas;		

	public CadastrarEmpresaBean() {
		buscaNome = "";
		//buscaEmail = "";

	}
	
	

	public Collection<AdmEmpresa> getListaEmpresas() throws BusinessException {
		this.listaEmpresas = AdmEmpresa.getEmpresas(this); 		
		return this.listaEmpresas;
	}


	/**
	 * Realiza o cadastro de uma empresa do sistema
	 * @param event
	 * @throws BusinessException
	 */
	public void cadastraEmpresa(ActionEvent event) throws BusinessException {
		super.nextPage = "/jsf/modSeguranca/viewCadastroUsuario.xhtml";

		/**
		try {
			this.usuario = (AdmUsuario)dataTableListaUsuarios.getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			this.usuario = new AdmUsuario();
			this.usuario.setDataCriacao(new Date());
			this.usuario.setAtivo(new Short((short)0));
			altera = false;
		}	
		**/
		
		Collection l = AdmParametro.getParametros();

		NavegarBean bean = (NavegarBean)super.getBean("navegarBean");		
		bean.setNomePrograma("Cadastra Usuario");				
		bean.redireciona(nextPage);		
	}
	

	public String getBuscaNome() {
		return buscaNome;
	}

	public void setBuscaNome(String buscaNome) {
		this.buscaNome = buscaNome;
	}

	public void setListaEmpresas(Collection<AdmEmpresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}



	public UIDataTable getDataTableListaEmpresas() {
		return dataTableListaEmpresas;
	}



	public void setDataTableListaEmpresas(UIDataTable dataTableListaEmpresas) {
		this.dataTableListaEmpresas = dataTableListaEmpresas;
	}
	
	

	
	
}

