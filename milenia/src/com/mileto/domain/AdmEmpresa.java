package com.mileto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.backinbean.adm.CadastrarEmpresaBean;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.PrcAdminDAO;

@Entity
@Table(name="ADM_EMPRESA")
public class AdmEmpresa implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "RAZAO_SOCIAL", nullable = false)
	private String razaoSocial;

	@Column(name = "NOME_FANTASIA", nullable = false)
	private String nomeFantasia;	

	@Column(name = "CNPJ", nullable = false)
	private String cnpj;

	@Column(name = "ENDERECO")
	private String endereco;

	@Column(name = "COMPLEMENTO")
	private String complemento;

	@Column(name = "BAIRRO")
	private String bairro;

	@Column(name = "COD_MUNICIPIO")
	private String codMunicipio;

	@Column(name = "MUNICIPIO")
	private String municipio;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "CEP")
	private String cep;

	@Column(name = "FLG_DEFAULT", nullable = false)
	private Boolean flgDefault;


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
		setIconStatus(Icon.getGraphicIcon(AdmEmpresa.hashIconStatus, new Integer(1).toString()));
		return iconStatus;
	}	

	public void setIconStatus(String iconStatus) {
		this.iconStatus = iconStatus;
	}
	
	public static List<Icon> getListaIcones() {
		return new ArrayList<Icon>(hashIconStatus.values());
	}

	//public Map<String, Icon> getHashIconStatus() {
		//return AdmEmpresa.hashIconStatus;
	//}	


	/**
	 * Recupera a empresa default do sistema	
	 * @throws BusinessException
	 */
	public static AdmEmpresa getEmpresaDefault() throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getEmpresaDefault();
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar metodo getEmpresaDefault, da classe AdmEmpresa");
		}
	}

	public static Collection<AdmEmpresa> getEmpresas(CadastrarEmpresaBean bean) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getEmpresas();
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar metodo getEmpresaDefault, da classe AdmEmpresa");
		}
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Boolean getFlgDefault() {
		return flgDefault;
	}

	public void setFlgDefault(Boolean flgDefault) {
		this.flgDefault = flgDefault;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}	


}
