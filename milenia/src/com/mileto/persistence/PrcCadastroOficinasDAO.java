package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.DAOException;
import org.hibernate.Query;
import org.pattern.BaseDB;

import com.backinbean.CadastrarOficinaBean;
import com.mileto.domain.RekLoja;

public class PrcCadastroOficinasDAO extends BaseDB {


	/**
	 * 
	 */
	public Collection<RekLoja> getOficinas(CadastrarOficinaBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekLoja");
			strBuffer.append(" WHERE (nome like :nomeLoja OR endereco like :endereco )");
			strBuffer.append(" AND (localidade like :localidade )");

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("nomeLoja", "%" + bean.getBuscaNome() + "%");
			query.setString("endereco", "%" + bean.getBuscaNome() + "%");
			query.setString("localidade", "%" + bean.getBuscaLocalidade() + "%");			
			
			List<RekLoja> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		}
	}
	
	/**
	public Collection<AdmModulo> getModulosLogin(AdmGrupo pGrupo) throws DAOException {
		try {		
			   //Session sessao = sessionFactory.openSession();  
			   pGrupo = (AdmGrupo)session.get(AdmGrupo.class, Integer.valueOf(pGrupo.getId()));  
			   Iterator<AdmModulo> ite = pGrupo.getModulos().iterator();  
			   while(ite.hasNext()){  
			       AdmModulo mod  = ite.next();  
			       System.out.println(mod.getAdmCod());  
			   }  
			
			//Query query = session.createQuery(strBuffer.toString());
			//query.setInt("login", pGrupo.getId());
			//query.setString("senhaCrypt", pSenhaCrypt);

			
			   return pGrupo.getModulos();
		} catch (Exception e) {
			throw new DAOException(e, this);
		}
	}	
	**/

}