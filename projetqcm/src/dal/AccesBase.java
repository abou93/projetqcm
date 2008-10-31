package dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import securite.IOProperties;

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
		IOProperties IoPropertie = new IOProperties();
		
			
		// recupere le pilote
		try {
			
			Class.forName(ResourceBundle.getBundle("parametres").getString("nomPiloteSQLServer"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		// ouverture de la connexion
		try {
			Properties prop = IoPropertie.loadProperties("Props/connexion.properties");
			StringBuilder connexion= new StringBuilder();
			connexion.append("jdbc:sqlserver://"+prop.getProperty("adresse"));
			connexion.append(":1433;user="+prop.getProperty("user"));
			connexion.append(";password="+prop.getProperty("password"));
			connexion.append(";databasename="+prop.getProperty("databasename"));
			ctn= DriverManager.getConnection(connexion.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return ctn;
	}// Fin getConnection
	
	
	public static void deconnexionBase(Connection cnx){
		
		try {
			cnx.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	
	}// Fin deconnexionBase
	
}
