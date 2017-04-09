package com.mileto.domain;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mileto.pattern.BusinessException;
import com.mileto.pattern.DAOException;
import com.mileto.persistence.PrcAdminDAO;



@Entity
@Table(name="ADM_GRUPO")
public class AdmGrupo {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;
	
	//@ManyToMany(targetEntity=AdmModulo.class, cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)  
	//@JoinTable(name = "ADM_PERMIS", joinColumns = @JoinColumn(name = "ADM_GRUPO"), inverseJoinColumns = @JoinColumn(name = "ADM_MODULO")) 
	//private Set<AdmModulo> modulos; 
	
	/**
	 * Recupera uma listagem de grupos
	 * @return
	 * @throws BusinessException
	 */
	public static Collection<AdmGrupo> getGrupos() throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getGrupos();
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar m�todo getUsuarios, da classe AdmUsuario");
		}
	}
	
	public static AdmGrupo getGrupo(Integer idGrupo) throws BusinessException {		
		try {
			PrcAdminDAO dao = new PrcAdminDAO();			
			return dao.getGrupo(idGrupo);
		} catch (DAOException e) {
			throw new BusinessException("Erro ao executar m�todo getUsuarios, da classe AdmUsuario");
		}
	}
	
	
	

	@Override
	public String toString() {
		return new Integer(this.getDescricao()).toString();
	}
		

	/**
	 * Se voc� vai utilizar este objeto em combos, obrigatoriamente voc� deve implementar os m�todos equals() e hashCodes()
	 * sempre a partir das chaves prim�rias. Al�m disso, voc� dever� implementar uma classe Converter.
	 * Acredite, se n�o for assim, n�o vai funcionar!
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdmGrupo other = (AdmGrupo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	//public Set getModulos() {
		//return modulos;
	//}
	//public void setModulos(Set modulos) {
//		this.modulos = modulos;
//	}	
}
