package com.backinbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIDataTable;

import com.mileto.pattern.Icon;
import com.mileto.services.Email;
import com.mileto.services.Security;


/**
 * Caso de Uso: Navegar pelo sistema. Todas as chamadas são com escopo Request
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@RequestScoped
public class NavegarBean extends BackingBean {

	private HtmlInputHidden inputHiddenHashIcons;
	
	private UIDataTable dataTableListaIcones;

	private String dispatch;	
	private Boolean thatsOk = false;

	private String msgError;

	/** Lista de icones para legenda, chamada de diversas paginas da aplicacao **/
	private List<Icon> listaIcones = new ArrayList<Icon>();

	///** Display Dimmer Element **/
	//private boolean flgDisplayDimmer;

	public NavegarBean() {		
		//flgDisplayDimmer = false; /** Inicializa sempre o dimmer como falso **/		
	}


	//public String redirecionaHome() {		
		//return redireciona("/jsf/startup.xhtml");
	//}
	
	//public String redirecionaModulo() {		
		//return redireciona("/jsf/menu/viewModulo.xhtml");
	//}

	public final String redireciona(String path) {
		FacesContext context = FacesContext.getCurrentInstance();  
		NavigationHandler handler = context.getApplication().getNavigationHandler();  
		handler.handleNavigation(context, null, path);  
		context.renderResponse();
		return null;
	}
	
	public final void forward(String path) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    try{
	    	ec.dispatch( path );
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	



	public void validate(FacesContext context, UIComponent component, Object value) {

		/** Para quando o componente estiver inv�lido **/
		((UIInput) component).setValid(false);					
		super.addMessage("Explodindo valida��o " + value);

	}
	
	public void goHome() throws Exception {
	   	   
	    //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    //ec.dispatch(ec.getRequestContextPath() + "/jsf/modAdminSys/viewListaUsuarios.xhtml");
		this.redireciona("/jsf/modAdminSys/viewListaUsuarios.xhtml");
	   	  
	}


	/**
	 * Retorna um Id único para um componente de página, de modo a evitar erra de Id já encontrado na visualização
	 * @param keyPage
	 */
	public String getUniqueId(String keyPage) {		
		return keyPage + Security.getUniqueKey( 4 , 1 ); 	//Level 4, opção 1, comente letras maiusculas
	}


	//public boolean isFlgDisplayDimmer() {
	//	return flgDisplayDimmer;
	//}

/**
	public void setFlgDisplayDimmer(boolean flgDisplayDimmer) {
		this.flgDisplayDimmer = flgDisplayDimmer;
	}

	public void showDimmer(Map<String, Icon> map) {

		List<Icon> listaObjetos = (List<Icon>)(List<?>)Arrays.asList(map.values().toArray());		
		this.listaIcones = listaObjetos;
		setFlgDisplayDimmer(true);

	}
	**/

	public void sendMail() {
		Email.enviaEmailExemplo();
	}
	
	public List<Object> getNovoConjuntoUnitario() {
		List<Object> lo = new ArrayList<Object>();
		lo.add(new Object());
		return lo;
	}




















	//public String getNomePrograma() {		
		//return this.nomePrograma;
	//}

	//public void setNomePrograma(String nomePrograma) {
		//this.nomePrograma = nomePrograma;
	//}


	public String getDispatch() {
		return dispatch;
	}



	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}


	public HtmlInputHidden getInputHiddenHashIcons() {
		return inputHiddenHashIcons;
	}


	public void setInputHiddenHashIcons(HtmlInputHidden inputHiddenHashIcons) {
		this.inputHiddenHashIcons = inputHiddenHashIcons;
	}


	public List<Icon> getListaIcones() {
		return listaIcones;
	}


	public void setListaIcones(List<Icon> listaIcones) {
		this.listaIcones = listaIcones;
	}


	public String getMsgError() {
		return msgError;
	}


	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}


	public UIDataTable getDataTableListaIcones() {
		return dataTableListaIcones;
	}


	public void setDataTableListaIcones(UIDataTable dataTableListaIcones) {
		this.dataTableListaIcones = dataTableListaIcones;
	}


	public Boolean getThatsOk() {
		return thatsOk;
	}


	public void setThatsOk(Boolean thatsOk) {
		this.thatsOk = thatsOk;
	}

	










}