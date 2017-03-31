package com.mileto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.backinbean.adm.CadastrarClienteBean;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.PrcPessoaDAO;

@Entity
@Table(name="REK_CONTATO")
public class RekContato implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private String id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "TELEFONE", nullable = false)
	private String telefone;
	
	@Column(name = "RADIO", nullable = false)
	private String radio;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CLIENTE", referencedColumnName="ID")
	private RekCliente cliente;

	

	@Transient
	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Empresa Default", Icon.VERMELHO));
		//hashIconStatus.put("2", new Icon("Usuários Premium", Icon.LARANJA));
		//hashIconStatus.put("9", new Icon("Desenvolvedores",   Icon.ROSA));	
	}

	public String getIconStatus() {
		setIconStatus(Icon.getGraphicIcon(this.hashIconStatus, new Integer(1).toString()));
		return iconStatus;
	}	

	public void setIconStatus(String iconStatus) {
		this.iconStatus = iconStatus;
	}
	
	public static List<Icon> getListaIcones() {
		return new ArrayList<Icon>(hashIconStatus.values());
	}


	public static Collection<RekContato> getContatos(CadastrarClienteBean cadastrarClienteBean) throws BusinessException {		
		try {
			PrcPessoaDAO dao = new PrcPessoaDAO();			
			return dao.getContatos ( cadastrarClienteBean ) ;
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar metodo getContatos classe RekContato");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public RekCliente getCliente() {
		return cliente;
	}

	public void setCliente(RekCliente cliente) {
		this.cliente = cliente;
	}
	
	

}