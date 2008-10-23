package dal;

import java.sql.*;
import java.util.Vector;

import modeles.*;

/***
 * @author Lefort Steve 
 * @category Acces base de donn�e
 * :  <i> Description de la classe </i> <br> <br>
 * Regroupe les diff�rentes m�thodes d'acc�s � la base de donn�e
 * pour les classes d'objet : <br> <br>
 * <li> Test </li>
 * <li>	Section </li> 
 *  
 *   
 */
public class DalTest {

	/*****************************************************
	*					Methodes Insert 				 *
	******************************************************/
	
	
	/***
	 *  Re�oit un test pour l'ins�r� dans la base de donn�e
	 *  Retourne true si ok, false si nok
	 *  @param Test test
	 *  @return Boolean      
	 */
	public static boolean insertTest(Test test){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into TESTS (NOM,TEMPS,SEUIL) values (?, ?, ?)");
			stm.setString(1,test.getNom());
			stm.setInt(2,test.getTemps());
			stm.setInt(3, test.getSeuil());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertTest");
			
			AccesBase.deconnexionBase(cnx);
			return false;
		}					
			
	}
	
	/***
	 *  Re�oit une section pour l'ins�r�e dans la base de donn�e
	 *  Retourne true si ok, false si nok
	 *  @param Section section
	 *  @return Boolean      
	 */
	public static boolean insertSection(Section section){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into Sections (NUMERO,NOM,NBR_QUESTION) values (?, ?, ?)");
			stm.setInt(1,section.getNumero());
			stm.setString(2,section.getNom());
			stm.setInt(3, section.getNbrQuestion());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertSection");
			
			AccesBase.deconnexionBase(cnx);
			return false;
		}					
			
	}
	
		
	/*****************************************************
	*					Methodes Update 				 *
	******************************************************/
	
	/***
	 *  Re�oit un test � mettre � jour dans la base de donn�e
	 *  Retourne true si ok, false si nok 
	 *  @param Test test
	 *  @return Boolean
	 */
	public static boolean updateTest(Test test){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update TEST set TEMPS = ?,SEUIL = ? where NOM = ? ");
			stm.setInt(1,test.getTemps());
			stm.setInt(2, test.getSeuil());
			stm.setString(3, test.getNom().trim());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateTest");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 *  Re�oit une section � mettre � jour dans la base de donn�e
	 *  Retourne true si ok, false si nok 
	 *  @param Section section
	 *  @return Boolean
	 */
	public static boolean updateSection(Section section){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update SECTIONS set NOM = ?,NBR_QUESTION = ? where NUMERO = ? ");
			stm.setString(1,section.getNom());
			stm.setInt(2, section.getNbrQuestion());
			stm.setInt(3, section.getNumero());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateSection");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
		
	/*****************************************************
	*					Methodes Delete 				 *
	******************************************************/
	
	
	/***
	 * Re�oit un test � supprimer de la base de donn�e <br>
	 * Retourne true si ok , false si nok
	 * @param Test test
	 * @return Boolean
	 */
	public static boolean deleteTest(Test test)
	{
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("delete TESTS where NOM = ? ");
			stm.setString(1, test.getNom().trim());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur deleteTest");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 * Re�oit une section � supprimer de la base de donn�e <br>
	 * Retourne true si ok , false si nok
	 * @param Test test
	 * @return Boolean
	 */
	public static boolean deleteSection(Section section)
	{
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("delete SECTIONS where NUMERO = ? ");
			stm.setInt(1, section.getNumero());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur deleteSection");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
	/*****************************************************
	*					Methodes Select 				 *
	******************************************************/
	

	/***
	 * R�cup�re le test dans la base pour la question pass�e en param�tre <br>
	 * Retourne un test
	 * @param String nomTest
	 * @return Test
	 */
	public static Test selectTestByNom(String nomTest)
	{
		Test test = new Test();
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement("select TESTS where NOM = ? ");
			stm.setString(1, nomTest.trim());
			rs=stm.executeQuery();
			
			while(rs.next())
			{
				test.setNom(nomTest);
				test.setSeuil(rs.getInt("SEUIL"));
				test.setTemps(rs.getInt("TEMPS"));
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectTest");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return test;
	}
	
	
}
