package com.backinbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIDataTable;

import com.mileto.domain.BoardMessage;
import com.mileto.pattern.Icon;
import com.mileto.persistence.DataProviderSingleton;
import com.mileto.services.Email;
import com.mileto.services.Security;


/**
 * Caso de Uso: Navegar pelo sistema. Todas as chamadas são com escopo Request
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@RequestScoped
public class TestarBean extends BackingBean {

	//private HtmlInputHidden inputHiddenHashIcons;

	private UIDataTable dataTableListaIcones;
	private UIDataTable dataTableListaDiversa;

	private String msg, assunto, enterprise, appKey;	

	private String dispatch;	
	private Boolean thatsOk = false;

	private Queue fila;

	private String msgError;

	/** Lista de icones para legenda, chamada de diversas paginas da aplicacao **/
	private List<Icon> listaIcones = new ArrayList<Icon>();

	///** Display Dimmer Element **/
	//private boolean flgDisplayDimmer;

	public TestarBean() {		
		//flgDisplayDimmer = false; /** Inicializa sempre o dimmer como falso **/		
	}

	@PostConstruct
	public void init() {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		fila = provider.getFilaMensagens();
	}



	public void exibeFilaMensagens() throws Exception {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		provider.putMessage(new BoardMessage(this.msg, this.assunto, this.enterprise, this.appKey));


		fila = provider.getFilaMensagens();

		this.redireciona("/jsf/developer/viewFila.xhtml");

	}

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





	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public String getAssunto() {
		return assunto;
	}



	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}



	public String getEnterprise() {
		return enterprise;
	}



	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}



	public String getAppKey() {
		return appKey;
	}



	public void setAppKey(String appKey) {
		this.appKey = appKey;
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



	public Queue getFila() {
		return fila;
	}



	public void setFila(Queue fila) {
		this.fila = fila;
	}



	public UIDataTable getDataTableListaDiversa() {
		return dataTableListaDiversa;
	}



	public void setDataTableListaDiversa(UIDataTable dataTableListaDiversa) {
		this.dataTableListaDiversa = dataTableListaDiversa;
	}













}