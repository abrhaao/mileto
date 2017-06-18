package com.mileto.pattern;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class Conexao {	

	private String sURL;
	private String sLogin;	
	private String sPassword;
	private String sJDBC;
	
	private Connection cConec;
	
	private PreparedStatement cPstStatement;
	private CallableStatement cCallableStmt;
	private ResultSet cRstResultado;
	private int cIntIndStat;
	private int cIntIndRetorno = 0;
	private String[][] cStrResultado = null;
	private Vector cVctColunas = new Vector();
	private int[] cIntTamColunas;
	private int cIntTotLinhas;
	private int cIntTotColunas;
	protected int cIntErro;


	public Conexao( String sAmbiente) {
		

		try{


			/**
			 * Acessar o XML para buscar as propriedades de conex�o com o banco de dados
			 */
			//Map<String, String> dataSource = SaxHandler.getDataSource(sAmbiente);
			//this.sURL 		= dataSource.get("IBASE_DB_CONNECTION");
			//this.sLogin 	= dataSource.get("IBASE_DB_USUARIO");
			//this.sPassword 	= dataSource.get("IBASE_DB_SENHA");			
			//this.sJDBC 		= dataSource.get("IBASE_DB_DRIVER");
			
			if ( sAmbiente.equals("PAN")) {
				this.sURL 		= "jdbc:oracle:thin:@10.8.0.25:1521:totvs";
				this.sLogin 	= "siga";
				this.sPassword 	= "msiga";			
				this.sJDBC 		= "oracle.jdbc.OracleDriver";
			} else {
				this.sURL 		= "jdbc:mysql://10.80.80.8:3306/milenia";
				this.sLogin 	= "mileto";
				this.sPassword 	= "millenaire";			
				this.sJDBC 		= "com.mysql.jdbc.Driver";
			}
			
			this.cConec		= getConnection();
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//throw new ValidateException(e.getLocalizedMessage(), e);			
		} catch (Exception e) {
			e.printStackTrace();
			//throw new ValidateException("As propriedades do banco de dados não foram corretamente definidas. Contate o administrador.", e);			
		}

		/** MYSQL **/
		//if (pStringDB.equals("com.mysql.jdbc.Driver")) {
		//this.sURL = "jdbc:mysql://localhost/pim";
		//this.sLogin = "root";
		//this.sPassword = "root";				
		//this.sJDBC = "com.mysql.jdbc.Driver";
		//}
	}

	public Connection getConnection() throws Exception {			
		
		try {
			
			if (cConec != null && ! cConec.isClosed()) {
				return cConec;
				
			} else {
				
				Class.forName(sJDBC);			
			}

		} catch (Exception e) {
			System.out.println("Não foi possível conectar ao banco de dados.");
			e.printStackTrace();
			//throw new ValidateException("As propriedades de conexão não foram corretamente definidas. Contate o administrador.", e);
			throw new RuntimeException();
		} 
		
		/**
		 * A determinacao de usuario e senha é realizada a partir da biblioteca de seguranca
		 * Security, no pacote Mileto, que utiliza algoritmo proprio
		 */
		try {
			//return DriverManager.getConnection(sURL, Security.revealSecurityCode(sLogin), Security.revealSecurityCode(sPassword));
			return DriverManager.getConnection(sURL, sLogin, sPassword);
		} catch (SQLException e) {			
			e.printStackTrace();			
			//throw new ValidateException("Não foi possível estabelecer comunicação com o banco de dados. Contate o administrador.", e);
			throw new RuntimeException();
		} catch (Exception e) {			
			e.printStackTrace();			
			//throw new ValidateException("As propriedades do driver de conexão não foram corretamente definidas. Contate o administrador.", e);
			throw new RuntimeException();
		}		
	}
	
	
	/**
	 * Define a conexao e origem do banco de dados, para montar a conexao para o iReport.
	 * Se houver qualquer problema na busca de uma conexao válida, retorna um objeto nulo 
	 * que será devidamente tratado nos programas clientes. 
	 * @return
	 */
	public static Connection conexao( String sAmbiente) throws Exception {
		try {
			Conexao conexao = (new Conexao( sAmbiente ));
			return conexao.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			//throw new ValidateException("A conexão com o banco de dados não foi bem sucedida. Contate o administrador.", e);
			throw new RuntimeException();
		} catch (IOException e) {			
			e.printStackTrace();
			//throw new ValidateException("A conexão com o banco de dados não foi bem sucedida. Contate o administrador.", e);
			throw new RuntimeException();
		}		
	}	
	
	
	/** Inclui um par�metro do tipo String no statement a ser executado.
	 *  @param pStrDado String Par�metro a ser inserido
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setStringProc(String pStrDado) throws SQLException {
		if (pStrDado != null){
			cCallableStmt.setString(cIntIndStat++, pStrDado);
			cIntErro = 0;
		}else{
			cCallableStmt.setNull(cIntIndStat++, Types.VARCHAR);
			cIntErro = 0;
		}
	}

	/** Inclui um par�metro do tipo BigDecimal no statement a ser executado.
	 *  @param pBgdDado BigDecimal Par�metro a ser inserido
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setBigDecimalProc(BigDecimal pBgdDado) throws SQLException {
		if (pBgdDado != null)
			cCallableStmt.setBigDecimal(cIntIndStat++, pBgdDado);
		else
			cCallableStmt.setNull(cIntIndStat++, Types.NUMERIC);
	}

	/** Inclui um par�metro do tipo int no statement a ser executado.
	 *  @param pStrDado Par�metro a ser inserido 
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setBigDecimalProc(String pStrDado) throws SQLException {
		if ((pStrDado != null) && (!pStrDado.equals(""))){
			try {
				BigDecimal number = new BigDecimal(pStrDado);
				cCallableStmt.setBigDecimal(cIntIndStat++, number);
				cIntErro = 0;
			}
			catch (Exception exc) {
				cIntErro = -1;
			}
		} 
		else{
			cCallableStmt.setNull(cIntIndStat++, Types.NUMERIC);
			cIntErro = 0;
		}
	}

	/** Inclui um par�metro do tipo float no statement a ser executado.
	 *  @param pFltDado float Par�metro a ser inserido
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setFloatProc(float pFltDado) throws SQLException {
		cCallableStmt.setFloat(cIntIndStat++, pFltDado);
		cIntErro = 0;
	}

	/** Inclui um par�metro do tipo int no statement a ser executado.
	 *  @param pStrDado String Par�metro a ser inserido
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setIntProc(int pIntDado) throws SQLException {
		cCallableStmt.setInt(cIntIndStat++, pIntDado);
		cIntErro = 0;
	}
	

	/** Inclui um parametro do tipo int no statement a ser executado.
	 *  @param pStrDado Parametro a ser inserido
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void setIntProc(String pStrDado) throws SQLException {
		if ((pStrDado != null) && (! pStrDado.equals(""))) {
			//if (Funcao.aclValidaInteiro(pStrDado)){
				cCallableStmt.setInt(cIntIndStat++, Integer.parseInt(pStrDado));
				cIntErro = 0;
			//}else{
			//	cIntErro = -1;
			//}
		}else{
			cCallableStmt.setNull(cIntIndStat++, Types.NUMERIC);
			cIntErro = 0;
		}
	}
	
	public void prepareCall(String pStrStatement) throws SQLException {
		try {
			cIntIndStat = 1;
			cCallableStmt = this.getConnection().prepareCall(pStrStatement);
			cIntErro = 0;
		} catch (Exception e) {
			cIntErro = -1;
			e.printStackTrace();
		}
	}
	
	/** Executa inserts, updates e deletes com o banco.
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  @return Numero de linhas afetadas pelo statement
	 *  */
	public void executeProc() throws SQLException {
		try {
			cCallableStmt.execute();
			cCallableStmt.close();		//Nova instrução em 24/09/2016
			cIntErro = 0;
		} catch (NullPointerException e) {
			cIntErro = -1;
			e.printStackTrace();
		}
	}
	
	

	/** Comita as transa��es executadas com o banco.
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void commit(){
		try{
			try{
				cConec.commit();
			}catch (SQLException e){
				cIntErro = -1;
				System.err.println("Erro commit: "+e.getMessage());
			}
		}catch (NullPointerException e){
			cIntErro = -2;
			System.err.println("Erro commit: "+e.getMessage());
		}
	}

	/** Desfaz as transa��es executadas com o banco.
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void rollback() {
		try{
			try{
				cConec.rollback();
			}catch (SQLException e){
				cIntErro = -1;
				System.err.println("Erro rollback: "+e.getMessage());
			}
		}catch (NullPointerException e){
			cIntErro = -2;
			System.err.println("Erro rollback: "+e.getMessage());
		}
	}

	/** Fecha a conexao com o banco.
	 *  @throws SQLException Se ocorrer um erro de acesso ao banco de dados
	 *  */
	public void closeConnection() {
		try{
			try{

				cConec.close();
			}catch (SQLException e){
				cIntErro = -1;
				System.err.println("Erro closeConnection: "+e.getMessage());
			}
		}catch (NullPointerException e){
			cIntErro = -2;
			System.err.println("Erro closeConnection: "+e.getMessage());
		}
	}

		
}
