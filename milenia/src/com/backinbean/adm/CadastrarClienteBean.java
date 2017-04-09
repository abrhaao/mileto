package com.backinbean.adm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;
import com.backinbean.TabelarBean;
import com.mileto.domain.RekCliente;
import com.mileto.domain.RekContato;
import com.mileto.pattern.BusinessException;

/**
 * Caso de Uso: Cadastrar Clientes
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

@ManagedBean
@SessionScoped
public class CadastrarClienteBean extends BackingBean {


	private String buscaNome;
	private String buscaEmail;	
	private String buscaTelefone;
	private String buscaCnpj;
	private String buscaCpf;
	

	private RekCliente cliente;
	//private AdmPerfil perfil;

	private Collection<RekCliente> listaClientes;
	private Collection<RekContato> listaContatos = new ArrayList();


	

	public CadastrarClienteBean() {
		buscaNome = "";
		buscaEmail = "";
		buscaTelefone	= ""; 
		buscaCnpj = new String("");
		buscaCpf = new String("");	

	}

	public Collection<RekCliente> getListaClientes() throws BusinessException {
		
		AutenticarBean autBean = (AutenticarBean)super.getBean("autenticarBean");
		
		Set<RekCliente> treeSetClientes = new TreeSet();
		for (RekContato contato: getListaContatos()) {
			if ( contato.getCliente() != null) { 
				if ( contato.getCliente().getEmpresa().getId() ==  autBean.getEmpresa().getId()  ) {
					treeSetClientes.add(contato.getCliente());
				}				
			}			
		}
		
		
		this.listaClientes = new ArrayList();
		this.listaClientes.addAll(treeSetClientes);
				
		return this.listaClientes;
	}

	public Collection<RekContato> getListaContatos() throws BusinessException {
		this.listaContatos = RekContato.getContatos(this); 
		return this.listaContatos;
	}

	/**
	 * Realiza o cadastro de um cliente
	 * @param event
	 * @throws BusinessException
	 */
	public void cadastraCliente(ActionEvent event) throws BusinessException {
		
		NavegarBean navBean = (NavegarBean)super.getBean("navegarBean");
		TabelarBean tabBean = (TabelarBean)super.getBean("tabelarBean");	
		
		super.nextPage = "/jsf/modVendas/viewCadastroCliente.xhtml";

		try {
			this.cliente = (RekCliente)tabBean.getDataTableListaUsuarios().getRowData();
			altera = true;
		} catch (IllegalArgumentException  e) {
			this.cliente = new RekCliente();
			//this.usuario.setDataCriacao(new Date());
			//this.usuario.setAtivo(new Short((short)0));
			altera = false;
		}		

							
		navBean.redireciona(nextPage);		
		//bean.forward("/jsf/modAdminSys/viewCadastroUsuario.jsf");
	}


	/**
	 * Atualiza cadastro do cliente
	 * @return
	 */
	public String atualizaCliente() {
		super.nextPage = "/jsf/modVendas/viewConfirmaCliente.xhtml" ;			

		try {
			//this.usuario.setSenhaCrypt();
			//this.usuario.save();
			//navBean.setThatsOk(true);
		} catch (BusinessException e) {			
			//navBean.setThatsOk(false);
			e.printStackTrace();			
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

	public String getBuscaTelefone() {
		return buscaTelefone;
	}

	public void setBuscaTelefone(String buscaTelefone) {
		this.buscaTelefone = buscaTelefone;
	}

	public String getBuscaCnpj() {
		return buscaCnpj;
	}

	public void setBuscaCnpj(String buscaCnpj) {
		this.buscaCnpj = buscaCnpj;
	}

	public String getBuscaCpf() {
		return buscaCpf;
	}

	public void setBuscaCpf(String buscaCpf) {
		this.buscaCpf = buscaCpf;
	}

	public RekCliente getCliente() {
		return cliente;
	}

	public void setCliente(RekCliente cliente) {
		this.cliente = cliente;
	}

	public void setListaClientes(Collection<RekCliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public void setListaContatos(Collection<RekContato> listaContatos) {
		this.listaContatos = listaContatos;
	}

		
}