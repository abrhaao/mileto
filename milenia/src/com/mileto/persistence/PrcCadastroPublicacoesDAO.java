package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.DAOException;
import org.hibernate.Query;
import org.pattern.BaseDB;

import com.backinbean.CadastrarPublicacaoBean;
import com.mileto.domain.RekAuthor;

public class PrcCadastroPublicacoesDAO extends BaseDB {


	/**
	 * Recupera a lista de autores / artistas
	 */
	public Collection<RekAuthor> getAuthores(CadastrarPublicacaoBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekAuthor ");
			strBuffer.append(" ORDER BY nome  ");

			Query query = session.createQuery(strBuffer.toString());			
						
			List<RekAuthor> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		}
	}	
	
    /**
     * Atualiza o cadastro de um author
     * @param usuarioBean
     * @throws DAOException
     */
    public void saveAuthor(RekAuthor authorBean) throws DAOException {
    	try {    		    		
    		session.saveOrUpdate(authorBean);
    	} catch (Exception e) {
    		throw new DAOException(e, this);
    	} finally {
    		super.commit();
    	}
    }    
	
	
}