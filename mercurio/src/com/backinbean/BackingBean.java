package com.backinbean;

import java.util.Iterator;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;


/**
 * Classe para utilizar todos os backing beans do projeto
 * @author USUARIO
 */
public class BackingBean  {

	
	protected String nextPage;
	
	/**
	 * Atributo utilizado para as paginas que necessitam de controle de indice em seus rich:tables
	 */
	protected int currentIndex;
	
	/**
	 * Atributo que informa ao Faces se a rotina é de inclusão, alteração ou exclusão
	 */
	protected Boolean altera;
	protected Boolean exclui; 
	protected Boolean inclui;
	
	/**
	 * Recupera um bean de um tipo qualquer. Disponivel para todas as classes do aplicativo.
	 * @param idBean
	 * @return
	 */
	public static BackingBean getBean(String idBean){
		FacesContext facesContext = FacesContext.getCurrentInstance();				
		ExpressionFactory eFactory = facesContext.getApplication().getExpressionFactory();
		ValueExpression expression = eFactory.createValueExpression(facesContext.getELContext(), String.format("#{%s}", idBean), Object.class);				
		return (BackingBean)expression.getValue(facesContext.getELContext());
	}	

	protected Object getRequestParameter(String name) {  
		FacesContext facesContext = FacesContext.getCurrentInstance();		
		return facesContext.getExternalContext().getRequestParameterMap().get(name);
	}

	protected void setRequestAttribute(String name, Object value) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletRequest myRequest = (ServletRequest) facesContext.getExternalContext().getRequest();
		myRequest.setAttribute(name, value);			
	}
	
	/**
	 * Retorna o caminho completo do servidor
	 * @return
	 */
	public String getServerPath() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		ServletContext sc = (ServletContext) facesContext.getExternalContext().getContext();			
		return sc.getRealPath("");
	}
	
	public String getServerUrl() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		ExternalContext ex = facesContext.getExternalContext();
		return ex.getRequestScheme() + ":" + "//" + ex.getRequestServerName() + ":" + ex.getRequestServerPort() + ex.getRequestContextPath();   				
	}
	
	protected UIComponent getComponent(String id) {
		  FacesContext context = FacesContext.getCurrentInstance();		  		   		  
		  return findComponent(context.getViewRoot(), id);
	}
	
	private UIComponent findComponent(UIComponent c, String id) {
	    if (id.equals(c.getId())) {
	      return c;
	    }
	    Iterator<UIComponent> kids = c.getFacetsAndChildren();
	    while (kids.hasNext()) {
	      UIComponent found = findComponent(kids.next(), id);
	      if (found != null) {
	        return found;
	      }
	    }
	    return null;
	  }
	
	

	/**
	 * Registra uma mensagem aos context do faces
	 * @param message
	 */
	public void addMessage(String message) {
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
		//ResourceBundle bundle = Messages.getMessage(name, param);
	}



	/**
	 * Recupera a mensagem do Resource Bundle
	 * @param key
	 * @param params
	 * @return
	 */
	/*
	private FacesMessage getMessageResourceString(String key, Object[] params){

		String text = null;		
		try{
			text = this.bundle.getString(key);
		} catch(MissingResourceException e){
			text = "?? chave " + key + " n�o encontrada ??";
		}

		if(params != null){
			MessageFormat mf = new MessageFormat(text, FacesContext.getCurrentInstance().getViewRoot().getLocale());
			text = mf.format(params, new StringBuffer(), null).toString();
		}		
		return new FacesMessage(text);

	}	
	 */

	protected static Integer qtdDeMesesNoIntervalo(String anoMesInicial, String anoMesFinal){
		String anoInicial = anoMesInicial.substring(0, 4);
		String mesInicial = anoMesInicial.substring(4, 6);
		String anoFinal = anoMesFinal.substring(0, 4);
		String mesFinal = anoMesFinal.substring(4, 6);
		int diferencaEntreAnos = Integer.parseInt(anoFinal) - Integer.parseInt(anoInicial);
		int diferencaEntreMeses = Integer.parseInt(mesFinal) - Integer.parseInt(mesInicial);
		return new Integer(diferencaEntreAnos * 12 + diferencaEntreMeses + 1);
	}  
	
	public String getManagedBeanName() {
		return null;
	}

	public String getManagedBeanClassName() {
		return null;
	}

	public Class getManagedBeanClass() {
		return null;
	}

	public String getManagedBeanScope() {
		return null;
	}

	public int getInitMode() {
		return 0;
	}

	public Iterator getManagedProperties() {
		return null;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Boolean getAltera() {
		return altera;
	}

	public void setAltera(Boolean altera) {
		this.altera = altera;
	}

	public Boolean getExclui() {
		return exclui;
	}

	public void setExclui(Boolean exclui) {
		this.exclui = exclui;
	}

	public Boolean getInclui() {
		return inclui;
	}

	public void setInclui(Boolean inclui) {
		this.inclui = inclui;
	}
	
	
	
	
	
}