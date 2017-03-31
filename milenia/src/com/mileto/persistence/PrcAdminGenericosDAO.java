package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.BusinessException;
import org.exception.DAOException;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.pattern.BaseDB;

import com.mileto.domain.AdmGenerico;
import com.mileto.domain.AdmGenericoItem;

public class PrcAdminGenericosDAO extends BaseDB {


	/**
	 * Recupera a lista de tabelas genéricas do sistema 
	 * @throws DAOException
	 */
	public Collection<AdmGenerico> getListaTabelasGenerica() throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmGenerico 	");			

			Query query = session.createQuery(strBuffer.toString());								

			List<AdmGenerico> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}

	/**
	 * Recupera uma lista de itens da tabela genérica 
	 * @throws DAOException
	 */
	public Collection<AdmGenericoItem> getListaItensTabelaGenerica(String tabela) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmGenericoItem 	");
			strBuffer.append(" WHERE tabela = :tabelaKey 	");
			strBuffer.append(" ORDER BY chave 	");		

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("tabelaKey", tabela);				

			List<AdmGenericoItem> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}


	/**
	 * Atualiza o cadastro de uma tabela genérica
	 * @param usuario
	 * @throws DAOException
	 */
	public void saveTabelaGenerica(AdmGenerico generico) throws DAOException {
		try {
			session.saveOrUpdate(generico);			
		} catch (ConstraintViolationException e) {			
			rollback();	/** Como eu tive a 'brilhante' id�ia de querer tratar a exce��o no DAO, eu tenho que dar o rollback, pois n�o chamarei DAOException **/
			e.printStackTrace();					
			throw new BusinessException("Já existe esta tabela genérica cadastrada com esta chave");
		} catch (Exception e) {
			e.printStackTrace();			
			throw new DAOException(e, this);
		} 
	}

	/**
	 * Atualiza o perfil do usu�rio
	 * @param perfil
	 * @throws DAOException
	 */
	public void saveItemTabelaGenerica(AdmGenericoItem item) throws DAOException {
		try {
			session.merge(item);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			//super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
			/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}	

	/**
	 * Dele os itens existente de uma tabela genérica
	 * @param generico
	 * @throws DAOException
	 */
	public void deleteItensTabelaGenerica(AdmGenerico generico) throws DAOException {
		try {
			String hql = "delete from AdmGenericoItem where tabela = :tabela";
			session.createQuery(hql).setString("tabela", generico.getTabela()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		}
	}


}