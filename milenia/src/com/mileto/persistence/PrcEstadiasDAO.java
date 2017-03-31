package com.mileto.modules.persistence;

import java.math.BigInteger;
import java.util.List;

import org.exception.DAOException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.pattern.BaseDB;

import com.backinbean.RegistrarEstadiaBean;
import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.MovEstadia;
import com.mileto.domain.RekEspacoLocacao;

public class PrcEstadiasDAO extends BaseDB {


	/**
	 * Recupera uma lista de locais de locacao
	 * @throws DAOException
	 */
	public List<RekEspacoLocacao> getEspacos(AdmEmpresa empresa) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekEspacoLocacao WHERE empresa.id = :empresa ");				

			Query query = session.createQuery(strBuffer.toString());			
			query.setInteger("empresa", empresa.getId());
			//query.setString("codigo", "%" + bean.getBuscaCodigo().toLowerCase() + "%");								

			List<RekEspacoLocacao> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	/**
	 * Recupera uma lista de estadias
	 * @throws DAOException
	 */
	public List<MovEstadia> getEstadias(RegistrarEstadiaBean bean, AdmEmpresa empresa) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM MovEstadia WHERE empresa.id = :empresa ");	
			
			if ( bean.getBuscaData() != null ) {
				strBuffer.append(" AND DATE( :buscaData ) between DATE( dataInicio ) and DATE ( dataFim )  ");
			}			

			Query query = session.createQuery(strBuffer.toString());			
			query.setInteger("empresa", empresa.getId());
			
			if ( bean.getBuscaData() != null ) {
				query.setDate("buscaData", bean.getBuscaData());
			}
			 
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}	
	
	/**
	 * Atualiza o cadastro da estadia
	 * @throws DAOException
	 */
	public void saveEstadia(MovEstadia estadia) throws DAOException {
		try {
			session.merge(estadia);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
	
	public BigInteger getQuantasVagas(RekEspacoLocacao espaco) {
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(" select espaco.lotes - count(*) from MOV_ESTADIA estadia, REK_ESPACO_LOCACAO espaco ");
		strBuffer.append(" where estadia.empresa = :empresa  ");
		strBuffer.append(" and   espaco.empresa = :empresa  ");
		strBuffer.append(" and   espaco = :espaco  ");
		strBuffer.append(" and   espaco = espaco.id  ");
		
		SQLQuery query = session.createSQLQuery(strBuffer.toString());
		query.setInteger("empresa", espaco.getEmpresa().getId());
		query.setString("espaco", espaco.getId());
		
		List objetos = query.list();
		return (BigInteger)objetos.get(0);		
	}
}