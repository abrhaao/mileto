package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.DAOException;
import org.hibernate.Query;
import org.pattern.BaseDB;

import com.backinbean.CadastrarPessoaBean;
import com.mileto.domain.RekLoja;

public class PrcCadastroPessoasDAO extends BaseDB {

	
	/**
	 * Recupera uma lista de clientes baseada nos parametros do backing bean
	 * @throws DAOException
	 */
	public Collection<RekLoja> getClientes(CadastrarPessoaBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekLoja");
			strBuffer.append(" WHERE (nome like :nome )");		

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("nome", "%" + bean.getBuscaNome() + "%");										
			
			List<RekLoja> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
			
	/**
	 * Atualiza o cadastro do cliente
	 * @throws DAOException
	 */
	public void saveCliente(RekLoja loja) throws DAOException {
		try {
			session.saveOrUpdate(loja);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
}