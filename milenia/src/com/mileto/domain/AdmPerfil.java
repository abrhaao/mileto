package com.mileto.domain;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.persistence.PrcAdminDAO;



@Entity
@Table(name="ADM_PERFIL")
public class AdmPerfil {
	
	@Id
	@Column(name = "USUARIO")
	private int usuario;
	
	@Column(name = "AVATAR")
	private Blob avatar;
	
	@Column(name = "SEXO")
	private String sexo;		
	
	@Column(name = "DATA_ENVIO_ATIVACAO")
	private Date dataEnvioAtivacao;
	
	@Column(name = "DATA_CONFIRMA_ATIVACAO")
	private Date dataConfirmaAtivacao;		

	/**
	 * Salva o cadastro do perfil
	 * @throws BusinessException
	 */
	public void save() throws BusinessException {		
		try {			
			PrcAdminDAO dao = new PrcAdminDAO();
			dao.savePerfil(this);			
		} catch (DAOException e) {
			throw new BusinessException("Erro ao exceutar método save, da classe AdmPerfil");
		} 
	}
	
	
	/**
	 * 
	 * 
	 * @throws BusinessException
	 */
	public static AdmPerfil getPerfil(AdmUsuario usuario) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getPerfil(usuario);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getUsuarioLogin, da classe AdmPerfil");
		}
	}
	

	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}


	public Blob getAvatar() {
		return avatar;
	}


	public void setAvatar(Blob avatar) {
		this.avatar = avatar;
	}


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public Date getDataEnvioAtivacao() {
		return dataEnvioAtivacao;
	}


	public void setDataEnvioAtivacao(Date dataEnvioAtivacao) {
		this.dataEnvioAtivacao = dataEnvioAtivacao;
	}


	public Date getDataConfirmaAtivacao() {
		return dataConfirmaAtivacao;
	}


	public void setDataConfirmaAtivacao(Date dataConfirmaAtivacao) {
		this.dataConfirmaAtivacao = dataConfirmaAtivacao;
	}
	
	
	
	
}
