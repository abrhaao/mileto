package com.backinbean.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;
import com.backinbean.TabelarBean;
import com.mileto.domain.AdmGrupo;
import com.mileto.domain.AdmPerfil;
import com.mileto.domain.AdmUsuario;
import com.mileto.pattern.BusinessException;

/**
 * Caso de Uso: Cadastrar Usuários
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class VotarBean extends BackingBean {


	private String buscaNome;
	private String buscaEmail;	
	

	private AdmUsuario usuario;
	private AdmPerfil perfil;

	private Collection<AdmUsuario> listaUsuarios = new ArrayList();
	private Collection<AdmGrupo> listaGrupos;

	

	public VotarBean() {
		buscaNome = "";
		buscaEmail = "";

		/** 
		 * Caso o cadastro venha de uma solicitacao externa, via portal, instancia-se o objeto usuario do backinBean para o usuario preencher as informa��es 
		 */
		try {
			if (getRequestParameter("byPortal").equals("true")) {
				this.usuario = new AdmUsuario();
				this.perfil = new AdmPerfil();
			}
		} catch (NullPointerException e) {}


	}

	public Collection<AdmUsuario> getListaUsuarios() throws BusinessException {
		this.listaUsuarios = AdmUsuario.getUsuarios(this); 
		return this.listaUsuarios;
	}

	public Collection<AdmGrupo> getListaGrupos() throws BusinessException {
		this.listaGrupos = AdmGrupo.getGrupos(); 
		return this.listaGrupos;
	}

	/**
	 * Realiza o cadastro de um usuário do sistema
	 * @param event
	 * @throws BusinessException
	 */
	public void cadastraUsuario(ActionEvent event) throws BusinessException {
		
		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		TabelarBean tabBean = (TabelarBean)super.getBean("tabelarBean");	
		
		super.nextPage = "/jsf/modAdminSys/viewCadastroUsuario.xhtml";

		try {
			this.usuario = (AdmUsuario)tabBean.getDataTableListaUsuarios().getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			this.usuario = new AdmUsuario();
			this.usuario.setDataCriacao(new Date());
			this.usuario.setAtivo(new Short((short)0));
			altera = false;
		}		

							
		navBean.redireciona(nextPage);		
		//bean.forward("/jsf/modAdminSys/viewCadastroUsuario.jsf");
	}


	/**
	 * Atualiza cadastro do usuário
	 * @return
	 */
	public String atualizaUsuario() {
		
		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		
		super.nextPage = "/jsf/modAdminSys/viewConfirmaUsuario.xhtml" ;			

		try {
			this.usuario.setSenhaCrypt();
			this.usuario.save();
			navBean.setThatsOk(true);
		} catch (BusinessException e) {			
			navBean.setThatsOk(false);
			e.printStackTrace();			
		}

		return nextPage;
	}
	
	public String atualizaUsuarioPortal() {
		super.nextPage = "/jsf/modPortal/viewConfirmaUsuario.xhtml" ;			

		try {					
			this.usuario.setSenhaCrypt();
			this.usuario.setGrupo(AdmGrupo.getGrupo(8)); /* Usu�rios Default */
			this.usuario.setAtivo((short)0);
			
			/** Ok, eu sei que os dois saves n�o est�o na mesma transa��o, mas eu assumo os riscos **/
			this.usuario.save();					
			
			/** O registro do perfil assume o mesmo ID que o usu�rio recebeu ap�s o save **/
			perfil.setUsuario(usuario.getId());			
			this.perfil.save();
			
			/** Em ocorrendo tudo certo, os objetos s�o inicializados, pro usu�rio n�o fazer besteira nas pr�ximas telas */
			this.usuario = new AdmUsuario();
			this.perfil = new AdmPerfil();
			
			setRequestAttribute("isGood", true);
		} catch (BusinessException e) {							
			
			setRequestAttribute("isGood", false);
			super.addMessage(e.getMessage());
			e.printStackTrace();			
			
		} finally {

		}

		return nextPage;
	}
	
	/**
	 * Ativa o perfil do usuário
	 * @return
	 */
	public String ativaUsuario( int stage) {
		
		/** Chamada da página de atualização **/
		if (stage == 0) {
			super.nextPage = "/jsf/modSeguranca/viewAtivaUsuario.xhtml" ;
			perfil = AdmPerfil.getPerfil(usuario);
		}
		
		/** Confirmação da atualização do perfil **/
		if (stage == 1) {
			try {
				this.perfil.save();
				setRequestAttribute("isGood", true);
			} catch (BusinessException e) {			
				setRequestAttribute("isGood", false);
				e.printStackTrace();			
			}
			super.nextPage = "/jsf/modSeguranca/viewConfirmaUsuario.xhtml" ;
		}
		
		/** Confirmação da atualização do perfil **/
		if (stage == 2) {
			super.nextPage = "/jsf/modSeguranca/viewAtivaUsuario.xhtml" ;
			this.perfil.setDataConfirmaAtivacao(new Date());
		}
					

		

		return nextPage;
	}

	public String getBuscaNome() {
		return buscaNome;
	}

	public void setBuscaNome(String buscaNome) {
		this.buscaNome = buscaNome;
	}

	public String getBuscaEmail() {
		return buscaEmail;
	}

	public void setBuscaEmail(String buscaEmail) {
		this.buscaEmail = buscaEmail;
	}

	public AdmUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(AdmUsuario usuario) {
		this.usuario = usuario;
	}

	//public UIDataTable getDataTableListaUsuarios() {
		//return dataTableListaUsuarios;
	//}

	//public void setDataTableListaUsuarios(UIDataTable dataTableListaUsuarios) {
		//this.dataTableListaUsuarios = dataTableListaUsuarios;
	//}

	public AdmPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(AdmPerfil perfil) {
		this.perfil = perfil;
	}

	
	
}