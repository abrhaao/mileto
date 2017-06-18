package com.mileto.persistence;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.mileto.pattern.Conexao;

public class BaseDB {
	
	protected Conexao db;
	

	/********************************************************************************************************************/
	public void executa(String pProcedure, List<Object> pParameters, Conexao bd) throws IOException, Exception  {	  	  		  											

		int cIntErro = 0;
		db	=	bd;
		
		db.prepareCall(pProcedure);

		setParametersProc(pParameters);

		db.executeProc();					
		db.commit();
		db.closeConnection();		
	}
	

	private void setParametersProc(List pParameters) throws SQLException {
		for (int index = 0; index < pParameters.size(); index++) {
			if (pParameters.get(index) instanceof String) {
				db.setStringProc((pParameters.get(index)).toString()); 
			}
			if ( (pParameters.get(index) instanceof Double) || (pParameters.get(index) instanceof Float)) {
				db.setBigDecimalProc(new BigDecimal(((Float)pParameters.get(index)).floatValue())); 
			}		
			if (pParameters.get(index) instanceof Integer) {
				db.setIntProc(((Integer)pParameters.get(index))); 
			}		
			if (pParameters.get(index) instanceof Short) {
				db.setIntProc(((Short)pParameters.get(index))); 
			}		
		}
	}


}
