package com.mileto.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.PrcConfiguracaoDAO;


public class AdmParametro implements Serializable {
		
	private Integer empresa;		
	private String param;
	private String valor;
	private String descricao;
	
	@Transient
	private String iconStatus;

	private static Map<String, Icon> hashIconStatus;

	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("GLOBAL", 	new Icon("Parametro Global", Icon.VERDE));
		hashIconStatus.put("EXCLUSIVO", new Icon("Parametro Exclusivo da Empresa", Icon.LARANJA));
		//hashIconStatus.put("9", new Icon("Desenvolvedores",   Icon.ROSA));	
	}
		
	
	public String getIconStatus() {		
		setIconStatus(Icon.getGraphicIcon(this.hashIconStatus, ( empresa == null ? "GLOBAL" : "EXCLUSIVO") ));
		return iconStatus;
	}	

	public void setIconStatus(String iconStatus) {
		this.iconStatus = iconStatus;
	}
	
	public static List<Icon> getListaIcones() {
		return new ArrayList<Icon>(hashIconStatus.values());
	}
	
	/**
	 * Salva o cadastro do parametro de sistema
	 * @throws BusinessException
	 */
	public void save() throws BusinessException {		
		try {			
			PrcConfiguracaoDAO dao = new PrcConfiguracaoDAO();
			dao.saveParametro(this);			
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método save, da classe AdmParametro");
		} 
	}
	
	
	/**
	 * 
	 * Recupera um parametro do sistema
	 * @throws BusinessException
	 */
	public static AdmParametro getParametro(String parametro) throws BusinessException {		
		try {
			PrcConfiguracaoDAO dao = new PrcConfiguracaoDAO();			
			return dao.getParametro( parametro);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getParametro, da classe AdmParametro");
		}
	}
	
	/**
	 * 
	 * Recupera um conteúdo de documentação
	 * @throws BusinessException
	 */
	public static Collection<AdmParametro> getParametros() throws BusinessException {		
		try {
			PrcConfiguracaoDAO dao = new PrcConfiguracaoDAO();			
			return dao.getParametros();
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getParametros, da classe AdmParametro");
		}
	}
	
	public Integer getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	


}
