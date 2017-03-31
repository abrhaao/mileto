package com.backinbean.adm;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.beanutils.PropertyUtils;
import org.richfaces.component.UIDataTable;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;
import com.backinbean.TabelarBean;
import com.mileto.domain.AdmParametro;
import com.mileto.pattern.BusinessException;

/**
 * Caso de Uso: Cadastrar Parametros do Sistema e demais configurações sistêmicas
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class ConfigurarSistemaBean extends BackingBean {


	private String buscaParametro;

	private AdmParametro parametro;
	//private AdmPerfil perfil;

	private Collection<AdmParametro> listaParametros = new ArrayList();




	public ConfigurarSistemaBean() {
		buscaParametro = "";

	}



	public Collection<AdmParametro> getListaParametros() throws BusinessException {
		System.out.println("Está chamando o bean de parametros");
		this.listaParametros = AdmParametro.getParametros(); 		
		return this.listaParametros;
	}


	/**
	 * Realiza o cadastro de um parametro no sistema
	 * @param event
	 * @throws BusinessException
	 */
	public void cadastraParametro(ActionEvent event) throws BusinessException {		

		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		TabelarBean tabBean = (TabelarBean)super.getBean("tabelarBean");

		super.nextPage = "/jsf/modAdminSys/viewCadastroParametro.xhtml";


		try {
			this.parametro = (AdmParametro)tabBean.getDataTableListaParametros().getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			this.parametro = new AdmParametro();			
			altera = false;
		}		

		navBean.redireciona(nextPage);		
	}

	/**
	 * Replica o cadastro de um parametro do sistema para a empresa selecionada
	 * @throws BusinessException
	 */
	public String replicaParametro() throws BusinessException {		

		//NavegarBean    navBean = (NavegarBean)super.getBean("navegarBean");
		AutenticarBean autBean = (AutenticarBean)super.getBean("autenticarBean");		

		//super.nextPage = "/jsf/modAdminSys/viewReplicaParametro.xhtml";

		try {
			AdmParametro parametroEmpresa = new AdmParametro();	
			PropertyUtils.copyProperties(parametroEmpresa, this.parametro);

			this.parametro = parametroEmpresa;
			this.parametro.setEmpresa( autBean.getEmpresa().getId() ) ;
			altera = true;
		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
		}catch (Exception  e) {
			e.printStackTrace();		
		}
		return atualizaParametro();
	}


	/**
	 * Atualiza cadastro do parametro
	 * @return
	 */
	public String atualizaParametro() {

		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");

		super.nextPage = "/jsf/modAdminSys/viewConfirmaParametro.xhtml" ;			

		try {			
			this.parametro.save();
			navBean.setThatsOk(true);
		} catch (BusinessException e) {			
			navBean.setThatsOk(false);
			e.printStackTrace();			
		}

		return nextPage;
	}

	public String getBuscaParametro() {
		return buscaParametro;
	}

	public void setBuscaParametro(String buscaNome) {
		this.buscaParametro = buscaNome;
	}



	public AdmParametro getParametro() {
		return parametro;
	}



	public void setParametro(AdmParametro parametro) {
		this.parametro = parametro;
	}



	public void setListaParametros(Collection<AdmParametro> listaParametros) {
		this.listaParametros = listaParametros;
	}










}

