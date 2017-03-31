package com.mileto.persistence;

import java.util.List;

import org.hibernate.Query;

import com.mileto.domain.DocContent;
import com.mileto.pattern.BaseDB;
import com.mileto.pattern.DAOException;


public class PrcDocumentacaoDAO extends BaseDB {


	/**
	 * Recupera uma documentação 
	 * @throws DAOException
	 */
	public DocContent getDocumentacao( Integer contentId ) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM DocContent WHERE contentId = :id ");				

			Query query = session.createQuery(strBuffer.toString());			
			query.setInteger("id", contentId);
			//query.setString("codigo", "%" + bean.getBuscaCodigo().toLowerCase() + "%");								

			List<DocContent> objetos = query.list();
			return objetos.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	

	
	/**
	 * Atualiza o cadastro da documentação
	 * @throws DAOException
	 */
	public void saveDocumentacao(DocContent doc) throws DAOException {
		try {
			session.merge(doc);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** Nao se preocupe! Se houve alguma excecao anterior, nada sera feito dentro deste metodo, inclusive a conexao ja foi fechada. **/
							/** As instrucoes de atualizacao no banco devem encerrar com super.commit(); **/
		}
	}

}
