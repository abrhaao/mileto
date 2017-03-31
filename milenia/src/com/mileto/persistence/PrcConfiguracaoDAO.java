package com.mileto.persistence;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SQLQuery;

import com.backinbean.BackingBean;
import com.backinbean.adm.AutenticarBean;
import com.mileto.domain.AdmParametro;
import com.mileto.pattern.BaseDB;
import com.mileto.pattern.DAOException;

public class PrcConfiguracaoDAO extends BaseDB {



	public List<AdmParametro> getParametros() throws DAOException {

		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append(" SELECT empresa, param, valor, descricao from ADM_PARAMETRO ");
		strBuffer.append(" WHERE  empresa = :empresa  OR  empresa is null ");

		AutenticarBean bean = (AutenticarBean)BackingBean.getBean("autenticarBean");
		try {
			SQLQuery query = session.createSQLQuery(strBuffer.toString());
			query.setInteger("empresa",  bean.getEmpresa().getId());		

			int ij = 0;
			List<AdmParametro> listaDeParametros = new ArrayList();
			List lista = query.list();
			for (Object o: lista) {
				AdmParametro param = new AdmParametro();
				String[] properties = {"empresa", "param", "valor", "descricao"};
				if ( o instanceof Object[]) {
					provideBO ( param , properties, (Object[])o );	
				}

				ij++;
				listaDeParametros.add(param);
			}

			return listaDeParametros;		
		} catch (Exception e) {
			throw new DAOException(e, this);
		}
	}

	public void saveParametro(AdmParametro parametro) throws DAOException {

		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append(" INSERT INTO ADM_PARAMETRO (empresa, param, valor, descricao) ");
		strBuffer.append(" VALUES ( :empresa, :param, :valor:, :descricao) ");

		try {
			SQLQuery query = session.createSQLQuery(strBuffer.toString());
			query.setInteger("empresa",  parametro.getEmpresa());
			query.setString("param",  parametro.getParam());	
			query.setString("valor",  parametro.getValor());	
			query.setString("descricao",  parametro.getDescricao());	
			query.executeUpdate();						
		} catch (Exception e) {
			throw new DAOException(e, this);
		}
	}


	public static void provideBO ( Serializable businessObject , String[] properties, Object[] values ) {
		int ik = 0;		
		for (String property: properties) {
			try {
				PropertyUtils.setProperty(businessObject, property, values[ik]);   
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ik++;
		}

	}
}