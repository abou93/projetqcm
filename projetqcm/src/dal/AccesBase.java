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
	*						Methodes					 *
	******************************************************/
	
	public static Connection getConnection()
	{
		Connection ctn=null; 
		try
		{
		Class.forName(ResourceBundle.getBundle("parametres").getString("nomPiloteSQLServer"));
		    
		// ouverture de la connexion
		ctn= DriverManager.getConnection(ResourceBundle.getBundle("parametres").getString("chaineConnectionSQLServer"));
		}
		catch (Exception ex)
		{
			return null;
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
