package com.mileto.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.persistence.PrcDocumentacaoDAO;

@Entity
@Table(name="DOC_CONTENT")
public class DocContent {
	
	@Id
	@Column(name = "CONTENT_ID")
	private int contentId;
	
	@Column(name = "CONTENT_HTML")
	@Type(type="text")
	private String contentHtml;
		
	@Column(name = "DATA_MODIFICACAO")
	private Date dataModificacao;
		

	/**
	 * Salva o cadastro do conteúdo de documentação
	 * @throws BusinessException
	 */
	public void save() throws BusinessException {		
		try {			
			PrcDocumentacaoDAO dao = new PrcDocumentacaoDAO();
			dao.saveDocumentacao(this);			
		} catch (DAOException e) {
			throw new BusinessException("Erro ao exceutar método save, da classe DocContent");
		} 
	}
	
	
	/**
	 * 
	 * Recupera um conteúdo de documentação
	 * @throws BusinessException
	 */
	public static DocContent getDocumentacao(Integer contentId) throws BusinessException {		
		try {
			PrcDocumentacaoDAO dao = new PrcDocumentacaoDAO();			
			return dao.getDocumentacao( contentId );
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getDocumentacao, da classe DocContent");
		}
	}


	public int getContentId() {
		return contentId;
	}


	public void setContentId(int contentId) {
		this.contentId = contentId;
	}


	public String getContentHtml() {
		return contentHtml;
	}


	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}


	public Date getDataModificacao() {
		return dataModificacao;
	}


	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	
	

	
	
}
