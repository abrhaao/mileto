package com.mileto.pattern;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BaseDB {

	protected Session session;
	protected Transaction tx;
	
	protected boolean isOK;

	public BaseDB() {
		try {
			session = HibernateUtil.currentSession();
			
			/** Teste **/
			session.clear();
			
			/** Teste **/
			session.setCacheMode(CacheMode.IGNORE);
			
			tx = session.beginTransaction();

			/** Marca a transa��o aberta como OK **/
			this.isOK = true;
		} catch (NoSuchMethodError e) {
			System.out.println("Erro biblioteca CGI ... ");
		} 
	}
	
	public void commit() {
		if (isOK) {
			tx.commit();
			this.close();
		}				        
	}
		
	public void close(){
    	HibernateUtil.closeSession();    	
	}
	
	public void rollback(){		
		
		/** Para qualquer problema que houver, marca a transa��o aberta como n�o OK **/		
		this.isOK = false;		
		tx.rollback();
		this.close();
	}
	
	

	
}

