package dal;

import java.sql.*;
import java.util.ResourceBundle;

public class AccesBase {

	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public AccesBase() 
	{
		super();
	}
	
	/*****************************************************
	*						Methodes					 
	 * @throws ClassNotFoundException 
	 * @throws SQLException *
	******************************************************/
	
	public static Connection getConnection()
	{
		Connection ctn=null; 
		
		try {
			Class.forName(ResourceBundle.getBundle("parametres").getString("nomPiloteSQLServer"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		// ouverture de la connexion
		try {
			ctn= DriverManager.getConnection(ResourceBundle.getBundle("parametres").getString("chaineConnectionSQLServer"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ctn;
	}
	
	public static void deconnexionBase(Connection cnx){
		
		try {
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	
	}
	
}
