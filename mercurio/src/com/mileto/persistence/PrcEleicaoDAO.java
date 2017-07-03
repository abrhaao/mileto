package com.mileto.persistence;

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



}