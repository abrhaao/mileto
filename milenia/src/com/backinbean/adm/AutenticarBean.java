package com.backinbean.adm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.backinbean.BackingBean;
import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.AdmUsuario;
import com.mileto.services.ServiceLocator;

/**
 * Caso de Uso: Autenticar Usuario
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean 
@SessionScoped
public class AutenticarBean extends BackingBean {

	private String username;
	private String password;	
	private boolean flgLogin;
	private boolean flgLoginSistema;

	private String currentSistema;
	private int currentModulo;	
	private String currentMenu;

	private AdmUsuario usuario;
//	private AdmModulo modulo;
	private AdmEmpresa empresa;

	private List<SelectItem> listaSistemas;
	private List<SelectItem> listaModulos;
	
	//public static Map<String,String> business = new HashMap();
	
	private ServiceLocator locator = ServiceLocator.instance(); 

	public AutenticarBean() {
		this.flgLogin = false;
		this.currentSistema = "";
		this.currentModulo = 0;
		this.listaSistemas = new ArrayList();
		this.listaModulos = new ArrayList();
	}

	/**
	 * Realiza a autenticação do usuario no sistema
	 * @throws Exception
	 */
	/**
	public void realizaLoginUsuario(ActionEvent event) throws Exception {	 		
		try {
			this.usuario = AdmUsuario.getUsuarioLogin(this.username, this.password);		
			this.flgLogin = true;
			this.modulo = null;
			
			geraListaSistemas();
		} catch (JDBCConnectionException e) {			
			this.flgLogin = false;
			this.usuario = null;
			super.addMessage("Erro de comunicação com o banco de dados. Contate o administrador do sistema!");		
		} catch (ArrayIndexOutOfBoundsException e) {			
			this.flgLogin = false;
			this.usuario = null;
			super.addMessage("O grupo do usuário não possui <BR>nenhum acesso ao sistema!");		
		} catch (Exception e) {			
			this.flgLogin = false;
			this.usuario = null;
			super.addMessage("Usário ou senha inválida!");		
		}		
	}      **/
	
	/**
	 * Realiza a autenticacao do usuario no sistema
	 * @throws Exception
	 */
	/**
	public void realizaLogout(ActionEvent event) throws Exception {	 		
			this.flgLogin = false;
			this.usuario = null;	
			this.username = null;
			this.modulo = null;							
			this.business = new HashMap();
			this.listaSistemas = new ArrayList<SelectItem>();
			this.listaModulos = new ArrayList<SelectItem>();
	}    	**/
	
	/**
	 * Realiza a autenticacao do usuario no sistema como desenvolvedor, sem passar pela tela de login
	 * @throws Exception
	 */	
	public String realizaLoginDesenvolvedor() throws Exception {	 		
		try {
			this.usuario = AdmUsuario.getUsuarioLogin(locator.getProperty("debugUser"), locator.getProperty("debugPassword"));					
			//this.modulo = null;
			
			this.currentSistema = locator.getProperty("debugSystem");
			//this.currentModulo = 150;
			
			this.empresa = AdmEmpresa.getEmpresaDefault();
			
		} catch (Exception e) {
			this.flgLogin = false;
			//this.usuario = null;			
		}	
		this.flgLogin = isLoggedIn();
		return carregarSistema();
	}      

	/**
	 * Gera a combo com a listagem dos sistemas dispon�veis para o usu�rio autenticado
	 * @throws Exception
	 */
	/**
	public void geraListaSistemas() throws Exception {	 		
		if (flgLogin) {
			this.listaSistemas = new ArrayList<SelectItem>();
			Collection<AdmModulo> lista = this.usuario.getGrupo().getModulos();
			Collection<AdmSistema> listaCopia = new ArrayList();	
			for (AdmModulo modulo:lista) {
				AdmSistema sistema = modulo.getAdmSistema();
				if (!listaCopia.contains(sistema)) {
					this.listaSistemas.add(new SelectItem(sistema.getAdmCod(), sistema.getNome()));
					listaCopia.add(sistema);
				}
			}								
			this.currentSistema = ((AdmModulo)usuario.getGrupo().getModulos().toArray()[0]).getAdmSistema().getAdmCod();
			geraListaModulos(null);
		}
	} 
**/
	/**
	 * Gera a combo com a listagem dos sistemas dispon�veis para o usu�rio autenticado
	 * @throws Exception
	 */
	
	/**public void geraListaModulos(AjaxBehaviorEvent event) throws Exception {
		if (flgLogin) {
			this.listaModulos = new ArrayList<SelectItem>();
			Set<AdmModulo> lista = this.usuario.getGrupo().getModulos();
			for (AdmModulo modulo:lista) {
				AdmSistema sistema = modulo.getAdmSistema();
				if (sistema.getAdmCod().equals(this.currentSistema)) {
					this.listaModulos.add(new SelectItem(modulo.getAdmCod(), modulo.getNome()));
				}
			}
		}
	}**/

	/** 
	 * Carrega o modulo e empresa selecionadona na sessao do usuario
	 * @throws Exception
	 */
	
	public String carregarSistema() throws Exception {	 						
		
		super.nextPage = "/sample/entrance.jsf";									
		try {
			//SaxParser.loadModulo(this);	
			
			/** Garantir que aqui esteja setado o usuário, a empresa, a filial e o sistema **/
			
		} catch (Exception e) {							
			super.nextPage = "/sample/entranceError.jsf";		
		}
		//empresa = AdmEmpresa.getEmpresaDefault();
		return nextPage; 
	}
	
	public Locale locale() {
		return new java.util.Locale("pt", "BR");
	}
	



	public List<SelectItem> getListaSistemas() throws Exception {			
		return this.listaSistemas;
	}


	public List<SelectItem> getListaModulos() throws Exception {			
		return this.listaModulos;
	}


	public void setListaSistemas(List<SelectItem> listaSistemas) {
		this.listaSistemas = listaSistemas;
	}

	public void setListaModulos(List<SelectItem> listaModulos) {
		this.listaModulos = listaModulos;
	}

	
	/**
	public AdmModulo getModulo() {
		if ((this.modulo == null) || (this.modulo.getAdmCod() != this.currentModulo)) {
			Set<AdmModulo> listaModulos = this.getUsuario().getGrupo().getModulos();
			for (AdmModulo moduloEncontrado : listaModulos) {
				if (moduloEncontrado.getAdmCod() == this.getCurrentModulo()) {
					this.modulo = moduloEncontrado;
					break;
				}
			}
		} 
		return this.modulo;		
	}
	
	public static void addBusinessValue(String key, String value) {
		business.put(key, value);
	}

	@NotNull @Length(max=15)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id @NotNull @Length(max=15)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}   	

	public boolean isFlgLogin() {
		return flgLogin;
	}

	public void setFlgLogin(boolean flgLogin) {
		this.flgLogin = flgLogin;
	}

	public boolean isFlgLoginSistema() {
		return flgLoginSistema;
	}

	public void setFlgLoginSistema(boolean flgLoginSistema) {
		this.flgLoginSistema = flgLoginSistema;
	}

	public String getCurrentSistema() {
		return currentSistema;
	}

	public void setCurrentSistema(String currentSistema) {
		this.currentSistema = currentSistema;
	}

	public int getCurrentModulo() {
		return currentModulo;
	}

	public void setCurrentModulo(int currentModulo) {
		this.currentModulo = currentModulo;
	}
**/
	public AdmUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(AdmUsuario usuario) {
		this.usuario = usuario;
	}

/**

	public String getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(String currentMenu) {
		this.currentMenu = currentMenu;
	}

**/
	public AdmEmpresa getEmpresa() {
		if (empresa == null) {
			return AdmEmpresa.getEmpresaDefault();		//recurso para desenvolvedores, que não logam com usuário
		}
		return empresa;
	}

	public void setEmpresa(AdmEmpresa empresa) {
		this.empresa = empresa;
	}

	
	
	public void check() throws Exception {
	    //if (isLoggedIn()) {
	    
		if (!isLoggedIn()){
			realizaLoginDesenvolvedor();
		}
				
		
	    if ( 1 > 3)  {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        ec.redirect(ec.getRequestContextPath() + "/home.jsf");
	    }
	    
	}
	
	/**
	 * Verifica se o usuário está devidamente autenticado, atualmente, no sistema
	 * @return
	 */
	private boolean isLoggedIn() {
		if (this.usuario == null || this.empresa == null) {
			flgLogin = false;
			return false;
		}
		flgLogin = true;
		return true; 
	}
	
	
}