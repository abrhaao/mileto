package com.backinbean;

import java.util.ArrayList;
import java.util.Date;
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
import javax.json.JsonArray;

import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import com.mileto.domain.entity.MovCarregamento;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.DataProviderSingleton;
import com.mileto.persistence.DemoDAO;


/**
 * Caso de Uso: Navegar pelo sistema. Todas as chamadas são com escopo Request
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class AtualizarBoardBean extends BackingBean {

	//private List<RekProduto> listaProdutos;
	//private List<FciCompraVenda> listaEntradas;
	//private List<FciCompraVenda> listaSaidas;
	//private List<FciProducao> listaProducoes;

	private Queue<MovCarregamento> fila;

	private String msgError;

	private int currentIxCompraProduto;
	private int currentIxPopUpCompraProduto;
	private MovCarregamento currentCarregamento;

	/** Lista de icones para legenda, chamada de diversas paginas da aplicacao **/
	private List<Icon> listaIcones = new ArrayList<Icon>();

	public AtualizarBoardBean() {		
		currentCarregamento = new MovCarregamento(null, null, null, null, null, null, null, null, null, "blank.png", "");
	}

	@PostConstruct
	public void init() {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();

		/**
		try {
			JsonArray array = DemoDAO.getWMSProgramacaoVendas("20170516");
			for (Object j: array.toArray()) {
				System.out.println(j);
				JSONObject jobject = new JSONObject(j.toString());

				MovCarregamento cgto = new MovCarregamento( jobject.get("pedido").toString(), 
						jobject.get("placa").toString(),
						jobject.get("veiculo").toString(),
						jobject.get("veiculoCidade").toString(), 
						jobject.get("motorista").toString(),
						jobject.get("status").toString(),
						jobject.get("instrucao").toString(),
						jobject.get("produto").toString(), 
						jobject.get("transportadora").toString(),
						jobject.get("icone").toString()
						);
				//provider.putCarregamento(cgto);

			}**/
		 
			altera = false;

		fila = provider.getFilaCarregamento();
	}



	public void inclui () {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		MovCarregamento cgto = new MovCarregamento( this.currentCarregamento.getPedido(),
				this.currentCarregamento.getPlaca(),
				this.currentCarregamento.getVeiculo(),
				this.currentCarregamento.getVeiculoCidade(),
				this.currentCarregamento.getMotorista(),
				this.currentCarregamento.getStatus(),
				this.currentCarregamento.getInstrucao(),
				this.currentCarregamento.getProduto(),
				this.currentCarregamento.getTransportadora().getRazaoSocial(), 
				this.currentCarregamento.getTransportadora().getLogotipo(), 
				this.currentCarregamento.getDoca()
				);
		provider.putCarregamento(cgto);
		provider.persiste();
		
		currentCarregamento = new MovCarregamento(null, null, null, null, null, null, null, null, null, "blank.png", "");
		altera = false;

		fila = provider.getFilaCarregamento();
		this.redireciona("/jsf/developer/viewBoard.xhtml");
	}


	/**
	 * Confirma alteração
	 */
	public void altera() {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		/** Precisa???? 
		fila = provider.getFilaCarregamento();
		for ( MovCarregamento carregamento: fila) {					
			System.out.println(carregamento.getMotorista());
			if (carregamento.getPedido().equals(currentCarregamento.getPedido())) {
				carregamento = currentCarregamento;
			} 

		}
		**/
		
		provider.persiste();

		this.redireciona("/jsf/developer/viewBoard.xhtml");

		altera = false;
	}


	/**
	 * Realiza o cadastro de um usuário do sistema
	 * @param event
	 * @throws BusinessException
	 */
	public void escolhe (ActionEvent event) throws BusinessException {

		TestarBean tabBean = (TestarBean)super.getBean("testarBean");	

		try {
			this.currentCarregamento = (MovCarregamento)tabBean.getDataTableListaDiversa().getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			//this.usuario = new AdmUsuario();
			//this.usuario.setDataCriacao(new Date());
			//this.usuario.setAtivo(new Short((short)0));
			altera = false;
		}		

		this.redireciona("/jsf/developer/viewBoard.xhtml");
	}




	public final String redireciona(String path) {
		FacesContext context = FacesContext.getCurrentInstance();  
		NavigationHandler handler = context.getApplication().getNavigationHandler();  
		handler.handleNavigation(context, null, path);  
		context.renderResponse();
		return null;
	}


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

	public MovCarregamento getCurrentCarregamento() {
		return currentCarregamento;
	}

	public void setCurrentCarregamento(MovCarregamento currentCarregamento) {
		this.currentCarregamento = currentCarregamento;
	}






}