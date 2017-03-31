package com.mileto.persistence;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;

import com.backinbean.adm.CadastrarUsuarioBean;
import com.mileto.domain.AdmEmpresa;
import com.mileto.domain.AdmGrupo;
import com.mileto.domain.AdmPerfil;
import com.mileto.domain.AdmUsuario;
import com.mileto.pattern.BaseDB;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;

public class PrcAdminDAO extends BaseDB {


	/**
	 * Recupera um usuario atraves de seu login e senha
	 * @throws Exception
	 */
	public AdmUsuario getUsuarioLogin(String pLogin, String pSenhaCrypt) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmUsuario usuario WHERE usuario.login = :login    			");
			strBuffer.append("                         AND usuario.senhaCrypt = :senhaCrypt 	");

			Query query = session.createQuery(strBuffer.toString());
			query.setString("login", pLogin);
			query.setString("senhaCrypt", pSenhaCrypt);
					
			List<AdmUsuario> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmUsuario)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}
	
	/**
	 * Recupera um usuario atraves de seu e-mail
	 * @throws Exception
	 */
	public AdmUsuario getUsuarioByEmail(String email) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmUsuario usuario WHERE usuario.email = :email    			");			

			Query query = session.createQuery(strBuffer.toString());
			query.setString("email", email);			
					
			List<AdmUsuario> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmUsuario)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}	
	
	/**
	 * Recupera uma lista de usuarios baseada nos parametros do backing bean
	 * @throws DAOException
	 */
	public Collection<AdmUsuario> getUsuarios(CadastrarUsuarioBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmUsuario");
			strBuffer.append(" WHERE (nome like :nome AND login like :nome AND email like :email)");		

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("nome", "%" + bean.getBuscaNome() + "%");
			query.setString("nome", "%" + bean.getBuscaNome().toLowerCase() + "%");
			query.setString("email", "%" + bean.getBuscaEmail() + "%");						
			
			List<AdmUsuario> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	public AdmGrupo getGrupo(Integer idGrupo) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmGrupo grupo WHERE grupo.id = :id    			");			

			Query query = session.createQuery(strBuffer.toString());
			query.setInteger("id", idGrupo);			
					
			List<AdmGrupo> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmGrupo)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}
	
	/**
	 * Recupera uma lista de todos os grupos de usuarios do sistema
	 * @throws DAOException
	 */
	public Collection<AdmGrupo> getGrupos() throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmGrupo");				

			Query query = session.createQuery(strBuffer.toString());											
			
			List<AdmGrupo> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	/**
	 * Atualiza o cadastro do usuario
	 * @param usuario
	 * @throws DAOException
	 */
	public void saveUsuario(AdmUsuario usuario) throws DAOException {
		try {
			session.saveOrUpdate(usuario);			
		} catch (ConstraintViolationException e) {			
			rollback();	/** Como eu tive a 'brilhante' id�ia de querer tratar a exce��o no DAO, eu tenho que dar o rollback, pois n�o chamarei DAOException **/
			e.printStackTrace();					
			throw new BusinessException("Já existe usuário cadastrado com este login ou este e-mail");
		} catch (Exception e) {
			e.printStackTrace();			
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instrucoes de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
	
	/**
	 * Atualiza o perfil do usu�rio
	 * @param perfil
	 * @throws DAOException
	 */
	public void savePerfil(AdmPerfil perfil) throws DAOException {
		try {
			session.saveOrUpdate(perfil);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instrucoes de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}	
	
	/**
	 * Recupera a empresa default do sistema
	 * @param idGrupo
	 * @throws DAOException
	 */
	public AdmEmpresa getEmpresaDefault() throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmEmpresa empresa WHERE empresa.flgDefault = :flgDefault    			");			

			Query query = session.createQuery(strBuffer.toString());
			query.setBoolean("flgDefault", true);			
					
			List<AdmEmpresa> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmEmpresa)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}
	
	/**
	 * Recupera o perfil de um usu�rio
	 * @throws Exception
	 */
	public AdmPerfil getPerfil(AdmUsuario usuario) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmPerfil perfil WHERE perfil.usuario = :idUsuario    		");			

			Query query = session.createQuery(strBuffer.toString());
			query.setInteger("idUsuario", usuario.getId());			
					
			List<AdmPerfil> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmPerfil)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}	

	/**
	 * Recupera o cadastro de um usuario
	 * @throws Exception
	 */
	public AdmUsuario getUsuario(int id) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmUsuario usuario WHERE id = :idUsuario    		");			

			Query query = session.createQuery(strBuffer.toString());
			query.setInteger("idUsuario", id);			
					
			List<AdmUsuario> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (AdmUsuario)objetos.get(0);
			} else {
				throw new Exception();
			} 
		} catch (Exception e) {
			throw new DAOException(e, this);
		} finally {
			super.close();				/** As instrucoes de consulta devem encerrar com super.close() **/
		}
	}	
	
	
	public Collection<AdmEmpresa> getEmpresas() throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM AdmEmpresa");
			//strBuffer.append(" WHERE (nome like :nome AND login like :nome AND email like :email)");		

			Query query = session.createQuery(strBuffer.toString());			
			//query.setString("nome", "%" + bean.getBuscaNome() + "%");
			//query.setString("nome", "%" + bean.getBuscaNome().toLowerCase() + "%");
			//query.setString("email", "%" + bean.getBuscaEmail() + "%");						
			
			List<AdmEmpresa> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	

}

