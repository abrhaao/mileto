package com.mileto.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.backinbean.adm.CadastrarUsuarioBean;
import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.pattern.Icon;
import com.mileto.persistence.PrcAdminDAO;
import com.mileto.services.Security;


@Entity
@Table(name="ADM_USUARIO")
public class AdmUsuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "LOGIN", nullable = false)
	private String login;	
	
	@Column(name = "SENHA_CRYPT", nullable = false)
	private String senhaCrypt;
	
	@Column(name = "CARGO")
	private String cargo;
	
	@Column(name = "DEPARTAMENTO")
	private String departamento;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ATIVO")
	private Short ativo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_GRUPO", referencedColumnName="ID")
	private AdmGrupo grupo;
	
	@Column(name = "DATA_ATIVACAO")
	@Type(type="timestamp")
	private Date dataAtivacao;
	
	@Column(name = "DATA_CRIACAO")
	@Type(type="timestamp")
	private Date dataCriacao;
	
	@Column(name = "DATA_ULTIMO_LOGIN")
	@Type(type="timestamp")
	private Date dataUltimoLogin;
	
	
	private String senha;
	
	//@Transient
	//private Collection<AdmModulo> listaModulosLogin;
	
	@Transient
	private String iconStatus;
	
	private static Map<String, Icon> hashIconStatus;
	
	/** 
	 * Diagrama de Estados
	 */
	static {		
		hashIconStatus = new HashMap<String,Icon>();
		hashIconStatus.put("1", new Icon("Administradores", Icon.VERMELHO));
		hashIconStatus.put("2", new Icon("Usuários Premium", Icon.LARANJA));
		hashIconStatus.put("9", new Icon("Desenvolvedores",   Icon.ROSA));	
	}
	
	public String getIconStatus() {
		setIconStatus(Icon.getGraphicIcon(AdmUsuario.hashIconStatus, new Integer(this.grupo.getId()).toString()));
		return iconStatus;
	}	
		
	public void setIconStatus(String iconStatus) {
		this.iconStatus = iconStatus;
	}
	
	public Map<String, Icon> getHashIconStatus() {
		return AdmUsuario.hashIconStatus;
	}	

	/**
	 * Salva o cadastro do usuário
	 * @throws BusinessException
	 */
	public void save() throws BusinessException {		
		try {			
			PrcAdminDAO dao = new PrcAdminDAO();
			dao.saveUsuario(this);			
		} catch (DAOException e) {
			throw new BusinessException("Erro ao exceutar módulo save, da classe AdmUsuario");
		} 
	}
	
	/**
	 * Recupera uma listagem de usuários
	 * @return
	 * @throws BusinessException
	 */
	public static Collection<AdmUsuario> getUsuarios(CadastrarUsuarioBean bean) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getUsuarios(bean);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getUsuarios, da classe AdmUsuario");
		}
	}
	
	/**
	 * 
	 * @param pLogin
	 * @param pPasswd
	 * @return
	 * @throws BusinessException
	 */
	public static AdmUsuario getUsuarioLogin(String pLogin, String pPasswd) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();
			System.out.println(encryptPasswd(pLogin, pPasswd));
			AdmUsuario usuario = dao.getUsuarioLogin(pLogin, encryptPasswd(pLogin, pPasswd));
			if (usuario != null) {
				usuario.setDataUltimoLogin(new Date());
				usuario.save();
			}
			return usuario;
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getUsuarioLogin, da classe AdmUsuario");
		}
	}
	
	/**
	 * Busca um usuário a partir do seu e-mail
	 * @param pEmail
	 * @return
	 * @throws BusinessException
	 */
	public static AdmUsuario getUsuarioByEmail(String pEmail) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getUsuarioByEmail(pEmail);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getUsuarioByEmail, da classe AdmUsuario");
		}
	}
	
	/**
	 * Busca um usuário a partir do seu ID
	 * @param idUsuario
	 * @return
	 * @throws BusinessException
	 */
	public static AdmUsuario getUsuarioById(int idUsuario) throws BusinessException {	
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getUsuario(idUsuario);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar método getUsuarioById, da classe AdmUsuario");
		}
	}
	
	
	/**
	 * Criptografa senha, primeiro com algoritmo MD5 e em seguida com algoritmo SHA.
	 * Utiliza-se tanto da String Login quanto da String Senha para formar uma senha criptografada
	 * @param pPassword
	 * @return String contendo a senha do usu�rio criptografada
	 */
	private static String encryptPasswd(String pLogin, String pPassword) {
		String lSenha = Security.encrypt(pLogin.trim()+pPassword.trim(), "MD5");
		return Security.encrypt(lSenha, "SHA").toUpperCase();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaCrypt() {
		return senhaCrypt;
	}

	public void setSenhaCrypt() {
		if (this.senha != null) {			
			this.senhaCrypt = encryptPasswd(this.login, this.senha);
		}
		
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public AdmGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(AdmGrupo grupo) {
		this.grupo = grupo;
	}

	public Short getAtivo() {
		return ativo;
	}

	public void setAtivo(Short ativo) {
		this.ativo = ativo;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataUltimoLogin() {
		return dataUltimoLogin;
	}

	public void setDataUltimoLogin(Date dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}
	
	
	public static List<Icon> getListaIcones() {
		return new ArrayList<Icon>(hashIconStatus.values());
	}

		
}
