package com.mileto.persistence;

<<<<<<< HEAD
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.mileto.util.StringAcol;

import com.mileto.pattern.Conexao;


public class PrcEleicaoDAO extends BaseDB  {



	/**
	 * Recupera da base de dados os candidatos elegiveis de uma eleição
	 * @param pEleicao
	 * @return
	 * @throws Exception
	 */
	public static JsonArrayBuilder getCandidatosElegiveis (  List<Object> arrayParameters, Conexao cx ) throws Exception {

		Connection db = cx.getConnection();
		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	

		String urlImagem = new String();

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( " SELECT ID_OPCAO, DESCRICAO, PROPOSTA, ID, FIGURA	");  
		sqlBuilder.append( "    	from  REK_ENQUETE_OPCAO");
		sqlBuilder.append( "    	where ENQUETE = ? 	");
		if ( arrayParameters.size() > 1 ) {  
			sqlBuilder.append( "        and   ID_OPCAO = ?  ");
		}

		try {

			PreparedStatement pstmt = db.prepareStatement(sqlBuilder.toString());
			pstmt.setInt(1, (Integer)arrayParameters.get(0) );
			if ( arrayParameters.size() > 1 ) {
				pstmt.setString(2, (String)arrayParameters.get(1) );
			}
			java.sql.ResultSet rs = pstmt.executeQuery();			

			while(rs.next()){
				Blob blob = rs.getBlob("FIGURA");
				byte[] bdata = blob.getBytes(1, (int) blob.length());	

				if ( arrayParameters.size() > 1 ) {
					DataProviderSingleton provider = DataProviderSingleton.getInstance();
					urlImagem = provider.getImagem("ENQUETE" + arrayParameters.get(0).toString() + "_OPCAO" + arrayParameters.get(1) );
					if ( urlImagem == null ) {
						urlImagem = provider.putImagem ( "enquetes", "ENQUETE" + arrayParameters.get(0).toString() + "_OPCAO" + arrayParameters.get(1), bdata );
					}
				}
				JsonObjectBuilder j = Json.createObjectBuilder()
						.add("idOpcao", rs.getString("ID_OPCAO"))
						.add("descricao", rs.getString("DESCRICAO"))
						.add("proposta", StringAcol.nvl(rs.getString("PROPOSTA"), ""))
						.add("id", rs.getString("ID"))
						.add("figura", urlImagem);
				rs.getBlob("FIGURA");
				jsonArray.add(j);

			}
			rs.close();
			pstmt.close();					

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}

		return jsonArray;

	}

	/**
	 * Grava o voto do companheiro
	 * @throws Exception
	 */
	public static void vota ( List<Object> arrayParameters, Conexao cx ) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO MOV_VOTO ( ID, DATA_VOTO, ENQUETE, OPCAO_VOTO, IDENTIFICACAO, TERMINAL ) ");
		sql.append(" VALUES ( current_timestamp(), ?, ?, ?, ? ) ");

		//List<Object> arrayParameters = new ArrayList<Object>();
		//arrayParameters.add(enquete);//ANO	


		//TODO Tratamento de erros
		PrcEleicaoDAO dao = new PrcEleicaoDAO();
		dao.executa( sql.toString(), arrayParameters, cx);
	}

	/**
	 * Grava a imagem do candidato num BLOB
	 * @throws Exception
	 */
	public static void gravaFigura ( List<Object> arrayParameters, Conexao cx ) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE REK_ENQUETE_OPCAO SET FIGURA = ? ");
		sql.append(" WHERE ID = ? ");

		//TODO Tratamento de erros
		PrcEleicaoDAO dao = new PrcEleicaoDAO();
		dao.executa( sql.toString(), arrayParameters, cx);
	}
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.mileto.pattern.Conexao;

public class PrcEleicaoDAO extends BaseDB  {

	

	/**
	 * Recupera da base de dados os candidatos elegiveis de uma eleição
	 * @param pEleicao
	 * @return
	 * @throws Exception
	 */
	public static JsonArrayBuilder getCandidatosElegiveis ( String eleicao, Conexao cx ) throws Exception {

		JsonArrayBuilder jsonArray  = Json.createArrayBuilder();	
		
		String s = new String();

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( " SELECT C6_DESCRI, C6_NUM, C6_ITEM, A1_NREDUZ as CLIENTE, TRIM(A1_MUN) || '-' || A1_EST as CLIENTE_CIDADE,	");  
		sqlBuilder.append( "    C6_ENDPAD, C6_QTDEMP, C6_ENTREG, 	");
		sqlBuilder.append( "       A4_NOME, DA3_DESC, DA3_PLACA, TRIM(DA3_MUNPLA) || '-' || DA3_ESTPLA as VEICULO_CIDADE,	"); 
		sqlBuilder.append( "    	       DA4_NOME	");
		sqlBuilder.append( "    	FROM byyou.SC6990 C6, byyou.SC5990 C5, byyou.SA1990 A1, byyou.SA4990 A4,	"); 
		sqlBuilder.append( "    	     byyou.DA3990 DA3, byyou.DA4990 DA4	");
		sqlBuilder.append( "    	WHERE c5_tipo = 'N' 	");
		sqlBuilder.append( "    	and   c6_cli = a1_cod and c6_loja = a1_loja	");
		sqlBuilder.append( "    	and   c5_transp = a4_cod	");
		sqlBuilder.append( "    	and   c5_num = c6_num and c5_filial = c6_filial	");
		sqlBuilder.append( "    	and   c5_veiculo = da3_cod  and da3_motori = da4.da4_cod	");
		sqlBuilder.append( "    	and   c5.d_e_l_e_t_  <> '*'	");
		sqlBuilder.append( "    	and   c6.d_e_l_e_t_  <> '*'	");
		sqlBuilder.append( "    	and   a4.d_e_l_e_t_  <> '*'	");
		sqlBuilder.append( "    	and   a1.d_e_l_e_t_  <> '*'	");
		sqlBuilder.append( "    	and   DA3.d_e_l_e_t_ <> '*'	");
		sqlBuilder.append( "    	and   C6_ENTREG	= '" + dataProgramacao + "' ");

		try {
						
			PreparedStatement pstmt = db.prepareStatement(sqlBuilder.toString());			
			java.sql.ResultSet rs = pstmt.executeQuery();			

			while(rs.next()){
								
				JsonObjectBuilder j = Json.createObjectBuilder()
				.add("placa", rs.getString("DA3_PLACA"))
				.add("veiculo", rs.getString("DA3_DESC"))
				.add("veiculoCidade", rs.getString("VEICULO_CIDADE"))
				.add("transportadora", rs.getString("A4_NOME"))	
				.add("cliente", rs.getString("CLIENTE"))
				.add("clienteCidade", rs.getString("CLIENTE_CIDADE"))
				.add("doca", rs.getString("C6_ENDPAD"))
				.add("motorista", rs.getString("DA4_NOME"))
				.add("pedido", rs.getString("C6_NUM") + '-' + rs.getString("C6_ITEM") )
				.add("produto", rs.getString("C6_DESCRI"));
				jsonArray.add(j);

			}
			rs.close();
			pstmt.close();					

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}

		return jsonArray;

	}
	
	/**
	 * Grava o voto do companheiro
	 * @throws Exception
	 */
	public static void vota ( List<Object> arrayParameters, Conexao cx ) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO MOV_VOTO ( ID, DATA_VOTO, ENQUETE, OPCAO_VOTO, IDENTIFICACAO, TERMINAL ) ");
		sql.append(" VALUES ( current_timestamp(), ?, ?, ?, ? ) ");

		//List<Object> arrayParameters = new ArrayList<Object>();
		//arrayParameters.add(enquete);//ANO	
		
		
		//TODO Tratamento de erros
		PrcEleicaoDAO dao = new PrcEleicaoDAO();
		dao.executa( sql.toString(), arrayParameters, cx);


	}

>>>>>>> refs/remotes/origin/develop



}