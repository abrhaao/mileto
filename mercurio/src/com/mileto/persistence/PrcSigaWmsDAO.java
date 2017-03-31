package com.mileto.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.mileto.pattern.BaseDB;
import com.mileto.pattern.Conexao;

public class PrcSigaWmsDAO extends BaseDB {

	/**
	 * Recupera da base de dados a programação de vendas
	 * @param dataProgramacao
	 * @return
	 * @throws Exception
	 */
	public static JsonArrayBuilder getProgramacaoVendas ( String dataProgramacao ) throws Exception {

		Connection db = Conexao.conexao( "BYYOU" );
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
	 * Recupera da base de dados a programação de vendas
	 * @param dataProgramacao
	 * @return
	 * @throws Exception
	 */
	public static JsonObjectBuilder getStatusCarregamento ( String filial, String pedido ) throws Exception {

		Connection db = Conexao.conexao( "BYYOU" );
		JsonObjectBuilder j = Json.createObjectBuilder();	
		
		String s = new String();

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( " SELECT C6_DESCRI, C6_NUM, C6_ITEM, A1_NREDUZ as CLIENTE, TRIM(A1_MUN) || '-' || A1_EST as CLIENTE_CIDADE,	");  
		sqlBuilder.append( "    C6_ENDPAD, C6_QTDEMP, C6_ENTREG, 	");
		sqlBuilder.append( "       A4_NOME, DA3_DESC, DA3_PLACA, TRIM(DA3_MUNPLA) || '-' || DA3_ESTPLA as VEICULO_CIDADE,	"); 
		sqlBuilder.append( "    	       DA4_NOME, 	");
		sqlBuilder.append( "        DECODE ( C6_TURNO, ' ', 'Aguardando Pesagem Inicial',  ");
		sqlBuilder.append("                            '0', 'Aguardando Pesagem Inicial',  ");
		sqlBuilder.append("                            '1', 'Dirigindo-se ao Carregamento',  ");
		sqlBuilder.append("                            '2', 'Carregando',  ");
		sqlBuilder.append("                            '3', 'Aguardando Pesagem Final', " ); 
		sqlBuilder.append("                            '4', 'Aguardando Nota Fiscal' ) as CARREGAMENTO_STATUS  ");
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
		sqlBuilder.append( "    	and   c6_num || '-' || c6_item	= '" + pedido + "' ");
		sqlBuilder.append( "    	and   c6_filial	= '" + filial + "' ");

		try {
						
			PreparedStatement pstmt = db.prepareStatement(sqlBuilder.toString());			
			java.sql.ResultSet rs = pstmt.executeQuery();			

			while(rs.next()){
								
				j.add("placa", rs.getString("DA3_PLACA").trim())
				.add("veiculo", rs.getString("DA3_DESC").trim())
				.add("veiculoCidade", rs.getString("VEICULO_CIDADE").trim())
				.add("transportadora", rs.getString("A4_NOME").trim())	
				.add("cliente", rs.getString("CLIENTE").trim())
				.add("clienteCidade", rs.getString("CLIENTE_CIDADE").trim())
				.add("doca", rs.getString("C6_ENDPAD").trim())
				.add("motorista", rs.getString("DA4_NOME").trim())
				.add("pedido", rs.getString("C6_NUM") + '-' + rs.getString("C6_ITEM") )
				.add("status", rs.getString("CARREGAMENTO_STATUS"))
				.add("produto", rs.getString("C6_DESCRI").trim());				

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

		return j;

	}
	
}


/**
 SELECT C6_DESCRI, C6_NUM, C6_ITEM, A1_NREDUZ as CLIENTE, TRIM(A1_MUN) || '-' || A1_EST as CLIENTE_CIDADE,    
        C6_ENDPAD, C6_QTDEMP, C6_ENTREG,   
           A4_NOME, DA3_DESC, DA3_PLACA, TRIM(DA3_MUNPLA) || '-' || DA3_ESTPLA as VEICULO_CIDADE,   
                 DA4_NOME  
          FROM siga.SC6020 C6, siga.SC5020 C5, siga.SA1020 A1, siga.SA4010 A4,   
               siga.DA3010 DA3, siga.DA4010 DA4, siga.SZP020 ZP  
          WHERE c5_tipo = 'N'   
          and   c6_cli = a1_cod and c6_loja = a1_loja  
          and   nvl ( trim (c6_xtransp), c5_transp ) = a4_cod  
          and   c5_num = c6_num and c5_filial = c6_filial  
          and   ZP.Zp_Veicul = da3_cod  and zp.zp_codmod = da4.da4_cod  
          and   zp_filial = c6_filial and zp_pedido = c6_num and zp_item = c6_item
          and   c5.d_e_l_e_t_  <> '*'  
          and   c6.d_e_l_e_t_  <> '*'  
          and   a4.d_e_l_e_t_  <> '*'  
          and   a1.d_e_l_e_t_  <> '*'  
          and   DA3.d_e_l_e_t_ <> '*'  
          and   zp.d_e_l_e_t_ <> '*'
          and   C6_ENTREG  = '20170310' 
		  
		  
		  
		  
		  
		  
		  SELECT C6_DESCRI, C6_NUM, C6_ITEM, A1_NREDUZ as CLIENTE, TRIM(A1_MUN) || '-' || A1_EST as CLIENTE_CIDADE,    
        C6_ENDPAD, C6_QTDEMP, C6_ENTREG,   
           A4_NOME, DA3_DESC, DA3_PLACA, TRIM(DA3_MUNPLA) || '-' || DA3_ESTPLA as VEICULO_CIDADE,   
                 DA4_NOME,   zp_ticket, 
                 /**

        case when zp.zp_notaf <> ' ' then  'Nota Fiscal Emitida' 
              else case when zp.zp_pescg > 0 then 'Realizada Pesagem Inicial'
                                             else  'Não PRevisto'
                   end
        end as CARREGAMENTO_STATUS  , 
                                zp.* 
          FROM siga.SC6020 C6, siga.SC5020 C5, siga.SA1020 A1, siga.SA4010 A4,   
               siga.DA3010 DA3, siga.DA4010 DA4  , siga.SZP020 ZP, siga.szq020 zq
          WHERE c5_tipo = 'N'   
          and   c6_cli = a1_cod and c6_loja = a1_loja  
          and   C6_XTRANSP = a4_cod  
          and   c5_num = c6_num and c5_filial = c6_filial  
          and   zp_veicul = da3_cod  and zp_codmod = da4.da4_cod  
          and   c5.d_e_l_e_t_  <> '*'  
          and   c6.d_e_l_e_t_  <> '*'  
          and   a4.d_e_l_e_t_  <> '*'  
          and   a1.d_e_l_e_t_  <> '*'  
          and   DA3.d_e_l_e_t_ <> '*'  
          and   c6_entreg = '20170312'
--          and   c6_num || '-' || c6_item  = '" + pedido + "' 
          and   c6_filial  = '02' 
          and   c6_filial = zp_filial and c6_num = zp_pedido and c6_item = zp_item 
          and   zp_ticket  = zq_codigo (+) and zp_filial  = zq_filial (+)
          and   zp.d_e_l_e_t_ <> '*' and zq.d_e_l_e_t_ (+) <> '*'
          and zp.zp_notaf <> ' '
		  
		  
		  
**/
