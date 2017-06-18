package com.backinbean.portal;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;
import com.backinbean.TabelarBean;
import com.backinbean.adm.AutenticarBean;
import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.RekEnquete;
import com.mileto.domain.RekEnqueteOpcao;
import com.mileto.pattern.BusinessException;

/**
 * Caso de Uso: Cadastrar Enquetes e Eleições
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class CadastrarEnqueteBean extends BackingBean {


	private String buscaNome;
	//private String buscaEmail;	
	
	//private AdmUsuario usuario;
	//private AdmPerfil perfil;
	private RekEnquete enquete;
	
	private Collection<RekEnquete> listaEnquetes = new ArrayList();
	//private Collection<AdmGrupo> listaGrupos;

	

	public CadastrarEnqueteBean() {
		buscaNome = "";
		//buscaEmail = "";

		/** 
		 * Caso o cadastro venha de uma solicitacao externa, via portal, instancia-se o objeto usuario do backinBean para o usuario preencher as informa��es 
		 */
		try {
			if (getRequestParameter("byPortal").equals("true")) {
				//this.usuario = new AdmUsuario();
				//this.perfil = new AdmPerfil();
			}
		} catch (NullPointerException e) {}


	}

	/**
	 * Realiza o cadastro de uma enquete no sistema
	 * @param event
	 * @throws BusinessException
	 */
	public void cadastraEnquete(ActionEvent event) throws BusinessException {
		
		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		TabelarBean tabBean = (TabelarBean)super.getBean("tabelarBean");	
		AutenticarBean autenticarBean = (AutenticarBean)super.getBean("autenticarBean");
		
		super.nextPage = "/jsf/developing/viewCadastroEnquete.xhtml";

		try {
			this.enquete = (RekEnquete)tabBean.getDataTableListaEnquetes().getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			this.enquete = new RekEnquete(3);
			this.enquete.setEmpresa ( autenticarBean.getEmpresa() );
			//this.enquete.setDataCriacao(new Date());
			//this.enquete.setAtivo(new Short((short)0));
			altera = false;
		}		

							
		navBean.redireciona(nextPage);		
	}


	/**
	 * Atualiza cadastro da enquete
	 * @return
	 */
	public String atualizaEnquete() {
		
		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		
		super.nextPage = "/jsf/developing/viewListaEnquetes.xhtml";			

		try {			
			this.enquete.save();
			navBean.setThatsOk(true);
		} catch (BusinessException e) {			
			navBean.setThatsOk(false);
			e.printStackTrace();			
		}

		return nextPage;
	}
	
	
	public void adicionaOpcao() {
		this.enquete.getOpcoes().add(new RekEnqueteOpcao());
	}
	
	
	
	public String getBuscaNome() {
		return buscaNome;
	}

	public void setBuscaNome(String buscaNome) {
		this.buscaNome = buscaNome;
	}

	public RekEnquete getEnquete() {
		return enquete;
	}

	public void setEnquete(RekEnquete enquete) {
		this.enquete = enquete;
	}

	
	
	public Collection<RekEnquete> getListaEnquetes() throws BusinessException {
		AutenticarBean autenticarBean = (AutenticarBean)super.getBean("autenticarBean");
		this.listaEnquetes = RekEnquete.getEnquetes( autenticarBean.getEmpresa() ); 		
		return this.listaEnquetes;
	}

	public void setListaEnquetes(Collection<RekEnquete> listaEnquetes) {
		this.listaEnquetes = listaEnquetes;
	}

	
	
	
	
	
}