package com.backinbean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.mileto.domain.DocContent;


/**
 * Caso de Uso: Consultar e redigir a documentação do sistema
 * @author Abrhaao Ribeiro
 * @jsf.bean
 */

/**
 * @author Abrhaão Ribeiro
 *
 */
@ManagedBean
@ViewScoped
public class DocumentarBean extends BackingBean {
	

	private Boolean modoEdicao	=	false;
	private Boolean modoDesenvolvimento	=	true;
	
	private DocContent content;
	
	
	//public String giveMeDoc ( Integer idConteudo ) {
		//System.out.println("Passou dentro da classe Informar Bean. Pegando um content para exibir no help");
		//return "oque";
	//}
	
		
	public String exibeDocumentacao () {
		System.out.println("Parâmetro do Ajax:  " + super.getRequestParameter("contentId") );
		
		Integer id = Integer.parseInt ( super.getRequestParameter("contentId").toString() );
		
		try {
			this.content = DocContent.getDocumentacao( id );
		} catch (Exception e) {
			e.printStackTrace();
			this.content = new DocContent();
			this.content.setContentId( id );
			this.content.setContentHtml("<div></div>");
			salva();
		}
		
		//this.content.setDataModificacao ( new Date() );		
		
		return "nobody";
	}
	
	public String edita () {
		setModoEdicao(true);		
		return "nobody";
	}
	
	public String salva () {
		setModoEdicao(false);
		this.content.setDataModificacao(new Date());
		this.content.save();
		return "nobody";
	}


	public DocContent getContent() {
		return content;
	}


	public void setContent(DocContent content) {
		this.content = content;
	}


	public Boolean getModoEdicao() {
		return modoEdicao;
	}


	public void setModoEdicao(Boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}


	public Boolean getModoDesenvolvimento() {
		return modoDesenvolvimento;
	}


	public void setModoDesenvolvimento(Boolean modoDesenvolvimento) {
		this.modoDesenvolvimento = modoDesenvolvimento;
	}		
	
	
	
	
	
}
