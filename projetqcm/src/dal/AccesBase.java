package dal;

import java.sql.*;
import java.util.ResourceBundle;

public class AccesBase {

	public AccesBase() 
	{
		super();
	}
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
}
