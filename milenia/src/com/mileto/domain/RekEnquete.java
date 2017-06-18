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

import com.backinbean.adm.CadastrarEmpresaBean;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.PrcAdminDAO;
import com.mileto.persistence.PrcEleicaoDAO;

@Entity
@Table(name="REK_ENQUETE")
public class RekEnquete implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "STATUS")
	private String status;

	@Transient
	private List<RekEnqueteOpcao> opcoes;
	
	@ManyToOne
	@JoinColumn(name="EMPRESA", referencedColumnName="ID")
	private AdmEmpresa empresa;
	
	@Transient
	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Empresa Default", Icon.VERMELHO));
	}
	
	
	
	public RekEnquete() {
		super();
	}

	public RekEnquete(int quantasOpcoes) {
		super();
		List<RekEnqueteOpcao> listaOpcoesPraEssaNovaEnquete = new ArrayList<RekEnqueteOpcao>();
		
		listaOpcoesPraEssaNovaEnquete.add(new RekEnqueteOpcao());

		setOpcoes(listaOpcoesPraEssaNovaEnquete);
		setStatus("D");
	}
	
	/**
	 * Salva o cadastro da enquete
	 * @throws BusinessException
	 */
	public void save() throws BusinessException {		
		try {			
			PrcEleicaoDAO dao = new PrcEleicaoDAO();
			dao.saveEnquete(this);			
		} catch (DAOException e) {
			throw new BusinessException("Erro ao exceutar método save, da classe RekEnquete");
		} 
	}
	
	public static Collection<RekEnquete> getEnquetes(AdmEmpresa empresa) throws BusinessException {		
		try {
			PrcEleicaoDAO dao = new PrcEleicaoDAO();			
			return dao.getEnquetes(empresa);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar metodo getEmpresaDefault, da classe AdmEmpresa");
		}
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<RekEnqueteOpcao> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<RekEnqueteOpcao> opcoes) {
		this.opcoes = opcoes;
	}

	public AdmEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(AdmEmpresa empresa) {
		this.empresa = empresa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	


	

}