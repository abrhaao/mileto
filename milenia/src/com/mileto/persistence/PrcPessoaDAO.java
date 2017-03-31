package com.mileto.persistence;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

import com.backinbean.adm.CadastrarClienteBean;
import com.mileto.domain.RekContato;
import com.mileto.pattern.BaseDB;
import com.mileto.pattern.DAOException;

public class PrcPessoaDAO extends BaseDB {



	public Collection<RekContato> getContatos(CadastrarClienteBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekContato");
			strBuffer.append(" WHERE telefone like :telefone");			

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("telefone", "%" + bean.getBuscaTelefone() + "%");					
			
			List<RekContato> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		}
	}
		

}