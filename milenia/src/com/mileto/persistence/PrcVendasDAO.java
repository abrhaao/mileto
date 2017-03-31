package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.DAOException;
import org.hibernate.Query;
import org.pattern.BaseDB;

import com.mileto.domain.RekApostaLoteria;
import com.mileto.domain.RekTabelaPreco;

public class PrcVendasDAO extends BaseDB {


	/**
	 * Recupera uma lista de 
	 * @param bean
	 * @return
	 * @throws DAOException
	 */
	public Collection<RekTabelaPreco> getTabelasPreco(boolean isVigente) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekTabelaPreco");
			//strBuffer.append(" WHERE (nome like :nome )");		

			Query query = session.createQuery(strBuffer.toString());			
			//query.setString("nome", "%" + bean.getBuscaNome() + "%");								
			
			List<RekTabelaPreco> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	
}