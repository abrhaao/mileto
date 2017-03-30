package com.mileto.pattern;

import org.hibernate.TransactionException;

public class DAOException extends Throwable{
	
	private String msg;
	
	public DAOException(Exception e, BaseDB baseDB) {
		try {			
			baseDB.rollback();
			e.printStackTrace();			
			this.msg = e.getCause().getLocalizedMessage();
		} catch (TransactionException te) {
			te.printStackTrace();
			HibernateUtil.closeSession();
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
