package com.backinbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.mileto.domain.business.FciCompraVenda;
import com.mileto.domain.business.FciProducao;
import com.mileto.domain.entity.RekProduto;
import com.mileto.pattern.Icon;
import com.mileto.persistence.DataProviderSingleton;


/**
 * Caso de Uso: Navegar pelo sistema. Todas as chamadas são com escopo Request
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class SimularFciBean extends BackingBean {

	//private UIDataTable dataTableListaIcones;
	//private UIDataTable dataTableListaDiversa;

	//private String msg, assunto, enterprise, appKey;	

	//private String dispatch;	
	//private Boolean thatsOk = false;
	
	private List<RekProduto> listaProdutos;
	private List<FciCompraVenda> listaEntradas;
	private List<FciCompraVenda> listaSaidas;
	private List<FciProducao> listaProducoes;

	private Queue fila;

	private String msgError;
	
	private int currentIxCompraProduto;
	private int currentIxPopUpCompraProduto;
	private FciCompraVenda currentCompra;

	/** Lista de icones para legenda, chamada de diversas paginas da aplicacao **/
	private List<Icon> listaIcones = new ArrayList<Icon>();

	public SimularFciBean() {		
		//flgDisplayDimmer = false; /** Inicializa sempre o dimmer como falso **/	
		listaProdutos = new ArrayList<RekProduto>();
		listaProdutos.add(new RekProduto());
		
		listaEntradas = new ArrayList<>();
		addCompra();
		
		listaSaidas = new ArrayList<>();
		addVenda();
	}

	@PostConstruct
	public void init() {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		fila = provider.getFilaMensagens();
	}

	public void addProduto() {
		listaProdutos.add(new RekProduto());
	}
	
	public void addCompra() {
		listaEntradas.add(new FciCompraVenda("E", new RekProduto()));
	}
	
	public void addVenda() {
		listaSaidas.add(new FciCompraVenda("S", new RekProduto()));
	}
	
	public void addProducao() {
		listaProducoes.add(new FciProducao(new RekProduto()));
	}


	public void exibeCadastros() throws Exception {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		//provider.putMessage(new BoardMessage(this.msg, this.assunto, this.enterprise, this.appKey));

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
/**]
	public final void forward(String path) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try{
			ec.dispatch( path );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	**/


	public void validate(FacesContext context, UIComponent component, Object value) {

		/** Para quando o componente estiver inv�lido **/
		((UIInput) component).setValid(false);					
		super.addMessage("Explodindo valida��o " + value);

	}




	public List<Object> getNovoConjuntoUnitario() {
		List<Object> lo = new ArrayList<Object>();
		lo.add(new Object());
		return lo;
	}














	public List<RekProduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<RekProduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
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



	public Queue getFila() {
		return fila;
	}



	public void setFila(Queue fila) {
		this.fila = fila;
	}

	public List<FciCompraVenda> getListaEntradas() {
		return listaEntradas;
	}

	public void setListaEntradas(List<FciCompraVenda> listaEntradas) {
		this.listaEntradas = listaEntradas;
	}

	public List<FciCompraVenda> getListaSaidas() {
		return listaSaidas;
	}

	public void setListaSaidas(List<FciCompraVenda> listaSaidas) {
		this.listaSaidas = listaSaidas;
	}

	public List<FciProducao> getListaProducoes() {
		return listaProducoes;
	}

	public void setListaProducoes(List<FciProducao> listaProducoes) {
		this.listaProducoes = listaProducoes;
	}
	
	
	
	
	public Object[] getListaProdutosDispCompra(){
		return listaProdutos.stream().filter(produto -> produto.getId() != null &&  !produto.getId().isEmpty()).toArray();
	}
	
	public void selecionaPopUpCompra() { 
		this.currentCompra = listaEntradas.get(currentIxCompraProduto);
	
	}
	
	public void selecionaPopUpCompraConfirmacao() { 
		System.out.println("Chamou method selecionaPopUpCompraProduto seleciona dentro do Bean");
		RekProduto produtoSelecionado = (RekProduto)getListaProdutosDispCompra()[currentIxPopUpCompraProduto];
		this.currentCompra.setProduto(produtoSelecionado);
	}
	
	public int getCurrentIxCompraProduto() {
		return currentIxCompraProduto;
	}

	public void setCurrentIxCompraProduto(int currentIxCompraProduto) {
		this.currentIxCompraProduto = currentIxCompraProduto;
	}

	public int getCurrentIxPopUpCompraProduto() {
		return currentIxPopUpCompraProduto;
	}

	public void setCurrentIxPopUpCompraProduto(int currentIxPopUpCompraProduto) {
		this.currentIxPopUpCompraProduto = currentIxPopUpCompraProduto;
	}
	
	
	
	
	


}