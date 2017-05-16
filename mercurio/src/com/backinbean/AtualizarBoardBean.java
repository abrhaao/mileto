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
import javax.json.JsonArray;

import org.richfaces.json.JSONException;
import org.richfaces.json.JSONObject;

import com.mileto.domain.entity.MovCarregamento;
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

	private Queue fila;

	private String msgError;

	private int currentIxCompraProduto;
	private int currentIxPopUpCompraProduto;
	private MovCarregamento currentCarregamento;

	/** Lista de icones para legenda, chamada de diversas paginas da aplicacao **/
	private List<Icon> listaIcones = new ArrayList<Icon>();

	public AtualizarBoardBean() {		


		currentCarregamento = new MovCarregamento(null, null, null, null, null, null);
	}

	@PostConstruct
	public void init() {


		DataProviderSingleton provider = DataProviderSingleton.getInstance();

		try {
			JsonArray array = DemoDAO.getWMSListaStatusCarregamento();
			for (Object j: array.toArray()) {
				System.out.println(j);
				JSONObject jobject = new JSONObject(j.toString());

				MovCarregamento cgto = new MovCarregamento( jobject.get("pedido").toString(), 
						jobject.get("placa").toString(), 
						jobject.get("motorista").toString(),
						jobject.get("status").toString(),
						jobject.get("instrucao").toString(),
						jobject.get("produto").toString()
						);
				provider.putCarregamento(cgto);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}





		fila = provider.getFilaCarregamento();



	}



	public void exibeFila() throws Exception {

		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		//provider.putMessage(new BoardMessage(this.msg, this.assunto, this.enterprise, this.appKey));


		fila = provider.getFilaCarregamento();
		this.redireciona("/jsf/developer/viewBoard.xhtml");

	}


	public void inclui () {
		DataProviderSingleton provider = DataProviderSingleton.getInstance();
		MovCarregamento cgto = new MovCarregamento( this.currentCarregamento.getPedido(),
				this.currentCarregamento.getPlaca(),
				this.currentCarregamento.getMotorista(),
				this.currentCarregamento.getStatus(),
				this.currentCarregamento.getInstrucao(),
				this.currentCarregamento.getProduto()
				);
		provider.putCarregamento(cgto);

		currentCarregamento = new MovCarregamento(null, null, null, null, null, null);
		
		fila = provider.getFilaCarregamento();
		this.redireciona("/jsf/developer/viewBoard.xhtml");
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