package com.mileto.modules.persistence;

import org.exception.DAOException;
import org.pattern.BaseDB;

import com.mileto.domain.MovMegaSena;

public class PrcMegaSenaDAO extends BaseDB {
	
    /**
     * Atualiza o cadastro de um sorteio da mega-sena
     * @param MovMegaSena
     * @throws DAOException
     */
    public void saveMegaSena(MovMegaSena sorteioBean) throws DAOException {
    	try {    		    		
    		session.saveOrUpdate(sorteioBean);
    	} catch (Exception e) {
    		throw new DAOException(e, this);
    	} finally {
    		super.commit();
    	}
    }    
	
	
}