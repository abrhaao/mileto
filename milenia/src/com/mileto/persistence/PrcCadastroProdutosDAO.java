package com.mileto.modules.persistence;

import java.util.Collection;
import java.util.List;

import org.exception.BusinessException;
import org.exception.DAOException;
import org.hibernate.Query;
import org.pattern.BaseDB;

import com.backinbean.CadastrarProdutoBean;
import com.backinbean.EfetuarVendaBean;
import com.mileto.domain.RekProduto;
import com.mileto.domain.RekProdutoFamilia;
import com.mileto.domain.RekTabelaPrecoItem;

public class PrcCadastroProdutosDAO extends BaseDB {

	
	/**
	 * Recupera uma lista de produtos baseada nos par�metros do backing bean
	 * @throws DAOException
	 */
	public Collection<RekProduto> getProdutos(CadastrarProdutoBean bean) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekProduto");
			strBuffer.append(" WHERE (descricao like :descricao AND codigo like :codigo)");		

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("descricao", "%" + bean.getBuscaNome() + "%");
			query.setString("codigo", "%" + bean.getBuscaCodigo().toLowerCase() + "%");								
			
			List<RekProduto> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	/**
	 * Recupera uma lista de produtos de venda
	 * @throws DAOException
	 */
	public Collection<RekProduto> getProdutosDeVenda(EfetuarVendaBean bean, String pIni, String pFim) throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekProduto");
			strBuffer.append(" WHERE (descricao like :descricao)");		
			strBuffer.append(" AND (itemVenda like :itemVenda) ");
			strBuffer.append(" AND (codigo between :pIni and :pFim ) ");

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("itemVenda", "S");
			query.setString("descricao", "%" + bean.getBuscaNomeProduto().toUpperCase() + "%");								
			query.setString("pIni", pIni );
			query.setString("pFim", pFim );
			
			List<RekProduto> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	/**
	 * Recupera uma lista de fam�lias de produto
	 * @throws DAOException
	 */
	public Collection<RekProdutoFamilia> getProdutosFamilia() throws DAOException {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekProdutoFamilia");				

			Query query = session.createQuery(strBuffer.toString());			
			//query.setString("descricao", "%" + bean.getBuscaNome() + "%");
			//query.setString("codigo", "%" + bean.getBuscaCodigo().toLowerCase() + "%");								
			
			List<RekProdutoFamilia> objetos = query.list();
			return objetos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {
			super.close();			
		}
	}
	
	public RekProdutoFamilia getProdutoFamiliaByCodigo (String codigo) throws DAOException {			
		
		try{
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekProdutoFamilia subFamilia " );					
			strBuffer.append(" WHERE   subFamilia.codigo like :codigo ");

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("codigo", codigo);			
					
			List<RekProdutoFamilia> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (RekProdutoFamilia)objetos.get(0);
			} else {
				throw new BusinessException("Sub-Familia n�o encontrada : " + codigo);
			} 
		
		} finally {
			super.close();				/** As instru��es de consulta devem encerrar com super.close() **/
		}
	}		
			
	public RekTabelaPrecoItem getPrecoProduto (String tabela, String produto) throws DAOException {			
		
		try{
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" FROM RekTabelaPrecoItem " );					
			strBuffer.append(" WHERE   tabela like :tabela ");
			strBuffer.append(" AND     produto like :produto ");
			strBuffer.append(" AND     condEspecialDia is null "  );
			strBuffer.append(" AND     condEspecialHoraIni is null 	AND condEspecialHoraFim is null "  );

			Query query = session.createQuery(strBuffer.toString());			
			query.setString("tabela", tabela);			
			query.setString("produto", produto);
					
			List<RekTabelaPrecoItem> objetos = query.list();
			if (!objetos.isEmpty()) {						
				return (RekTabelaPrecoItem)objetos.get(0);
			} else {
				return new RekTabelaPrecoItem();											
			} 
		
		} finally {
			super.close();				/** As instru��es de consulta devem encerrar com super.close() **/
		}
	}		
	
	/**
	 * Atualiza o cadastro do produto
	 * @throws DAOException
	 */
	public void saveProduto(RekProduto produto) throws DAOException {
		try {
			session.saveOrUpdate(produto);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
	
	public void savePreco(RekTabelaPrecoItem item) throws DAOException {
		try {
			session.merge(item);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e, this);
		} finally {			
			super.commit();	/** N�o se preocupe! Se houve alguma exce��o anterior, nada ser� feito dentro deste m�todo, inclusive a conex�o j� foi fechada. **/
							/** As instru��es de atualiza��o no banco devem encerrar com super.commit(); **/
		}
	}
}