package dal;

import java.sql.*;
import java.util.Vector;

import modeles.*;

/***
 * @author Lefort Steve 
 * @category Acces base de donnée
 * :  <i> Description de la classe </i> <br> <br>
 * Regroupe les différentes méthodes d'accès à la base de donnée
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
	 *  Reçoit un test pour l'inséré dans la base de donnée
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
	 *  Reçoit une section pour l'insérée dans la base de donnée
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
	
	
	/***
	 *  Reçoit un numero de section et un nom de test pour l'inséré dans la base de donnée
	 *  Retourne true si ok, false si nok <br>
	 *  Permet de lié un test a différentes sections
	 *  @param String nomTest <br>
	 *  	   int numeroSection 
	 *  @return Boolean      
	 */
	public static boolean insertTests_Sections(String nomTest, int numeroSection){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into TESTS_SECTIONS (NOM_TEST,NUMERO_SECTION) values (?, ?)");
			stm.setString(1,nomTest);
			stm.setInt(2,numeroSection);
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertTestSection");
			AccesBase.deconnexionBase(cnx);
			return false;
		}					
			
	}
	
		
	/*****************************************************
	*					Methodes Update 				 *
	******************************************************/
	
	/***
	 *  Reçoit un test à mettre à jour dans la base de donnée
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
	 *  Reçoit une section à mettre à jour dans la base de donnée
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
	 * Reçoit un test à supprimer de la base de donnée <br>
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
	 * Reçoit une section à supprimer de la base de donnée <br>
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
	 * Récupère le test dans la base pour le nom de test passé en paramètre <br>
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
			
			stm = cnx.prepareStatement("select * from TESTS where NOM = ? ");
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
	
	/***
	 * Récupère une liste de section dans la base pour le test passé en paramètre <br>
	 * Retourne une liste de Section
	 * @param String nomTest
	 * @return Vector
	 */
	public static Vector<Section> selectSectionByTest(String nomTest)
	{
		Vector<Section> listeSectionsDuTest = new Vector<Section>();
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement(	"select *from SECTIONS inner join TESTS_SECTIONS on SECTIONS.NUMERO = TESTS_SECTIONS.NUMERO_SECTION " + 
										"where NOM_TEST = ? ");
			stm.setString(1, nomTest);
			rs=stm.executeQuery();
			
			while(rs.next())
			{
				Section section = new Section();
				section.setNumero(rs.getInt("NUMERO"));
				section.setNom(rs.getString("NOM"));
				section.setNbrQuestion(rs.getInt("NBR_QUESTION"));
				listeSectionsDuTest.add(section);
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectSection");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeSectionsDuTest;
	}
	
	
	/***
	 * Récupère la liste de tout les tests la base<br>
	 * Retourne une liste de test
	 * @param none
	 * @return Vector
	 */
	public static Vector<Test> selectAllTest()
	{
		Vector<Test> listeTest = new Vector<Test>();
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement(	"select * from TESTS");
			rs=stm.executeQuery();
			
			while(rs.next())
			{
				Test test = new Test(rs.getString("NOM"),rs.getInt("TEMPS"),rs.getInt("SEUIL"));
				listeTest.add(test);
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectSection");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeTest;
	}
	
}
