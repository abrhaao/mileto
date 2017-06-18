package com.mileto.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.RekEnquete;
import com.mileto.domain.RekEnqueteOpcao;
import com.mileto.pattern.BaseDB;
import com.mileto.pattern.DAOException;

public class PrcEleicaoDAO extends BaseDB {


	/**
	 * Recupera uma lista de eleições/enquetes
	 * @throws DAOException
	 */
	public Collection<RekEnquete> getEnquetes(AdmEmpresa empresa) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekEnquete WHERE empresa.id = :empresa ");				

			Query query = session.createQuery(strBuffer.toString());			
			query.setInteger("empresa", empresa.getId());				
			
			Collection<RekEnquete> colecao = new ArrayList();
			for ( RekEnquete enquete: (List<RekEnquete>)query.list() ) {
				StringBuilder sqlBuilder = new StringBuilder();
				sqlBuilder.append(" FROM RekEnqueteOpcao WHERE enquete.id = :enquete ");	
				
				query = session.createQuery(sqlBuilder.toString());			
				query.setInteger("enquete", enquete.getId());	
				enquete.setOpcoes( query.list() );	
				
				colecao.add(enquete);
			}
			
			return colecao;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	
	/**
	 * Atualiza o cadastro da enquete
	 * @throws DAOException
	 */
	public void saveEnquete(RekEnquete enquete) throws DAOException {
		try {
			if ( enquete.getId() == null ) {
				session.persist(enquete);
			} else {
				session.merge(enquete);
			}
			for ( RekEnqueteOpcao opcao: enquete.getOpcoes() ) {
				opcao.setEnquete(enquete);
				session.merge(opcao);
			}						
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conexao ja foi fechada. **/
							/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
}