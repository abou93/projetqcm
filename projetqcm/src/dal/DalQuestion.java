package dal;

import java.sql.*;
import java.util.UUID;
import java.util.Vector;

import modeles.*;


/***
 * @author Lefort Steve 
 * @category Acces base de donn�e
 * :  <i> Description de la classe </i> <br> <br>
 * Regroupe les diff�rentes m�thodes d'acc�s � la base de donn�e
 * pour les classes d'objet : <br> <br>
 * <li> Question </li>
 * <li>	Reponse </li> 
 * <li>	Type </li> 
 *   
 */
public class DalQuestion {
	
	/*****************************************************
	*					Methodes Insert 				 *
	******************************************************/
	
	
	/***
	 *  Re�oit une question pour l'ins�r�e dans la base de donn�e
	 *  Retourne true si ok, false si nok
	 *  @param Question question
	 *  @return Boolean      
	 */
	public static boolean insertQuestion(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into QUESTIONS_ENONCES (ID,ENONCE,TYPE_QUESTION,IMAGE_QUESTION,NUMERO_SECTION) values (?, ?, ?, ?, ?)");
			stm.setString(1, question.getId().toString());
			stm.setString(2,question.getEnonce());
			stm.setInt(3, question.getType().getNumero());
			stm.setString(4, question.getCheminImage());
			stm.setInt(5,question.getSection().getNumero());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertQuestion");
			
			AccesBase.deconnexionBase(cnx);
			return false;
		}					
			
	}
	
	/***
	 *  Re�oit une Reponse pour l'ins�r�e dans la base de donn�e
	 *  Retourne true si ok, false si nok
	 *  @param Reponse reponse
	 *  @return Boolean 
	 */
	public static boolean insertReponse(Reponse reponse){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
				stm = cnx.prepareStatement("Insert into QUESTIONS_REPONSES (ID_ENONCE,NUMERO,TEXTE,REPONSE) values (?, ?, ?, ?)");
				stm.setString(1, reponse.getQuestion().getId().toString());
				stm.setInt(2,reponse.getNumero());
				stm.setString(3,reponse.getTexte());
				stm.setBoolean(4,reponse.isEtat());
				stm.execute();
				AccesBase.deconnexionBase(cnx);
				return true;
			
			
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertReponse");
			
		}					
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/*****************************************************
	*					Methodes Update					 *
	******************************************************/
	
	
	/***
	 *  Re�oit une question � mettre � jour dans la base de donn�e
	 *  Retourne true si ok, false si nok 
	 *  @param Question question
	 *  @return Boolean
	 */
	public static boolean updateQuestion(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update QUESTIONS_ENONCES set ENONCE = ?,TYPE_QUESTION = ? ,IMAGE_QUESTION =?,NUMERO_SECTION=? where ID = ?");
			stm.setString(1,question.getEnonce());
			stm.setInt(2, question.getType().getNumero());
			stm.setString(3, question.getCheminImage());
			stm.setInt(4,question.getSection().getNumero());
			stm.setString(5, question.getId().toString());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateQuestion");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 *  Re�oit une question � mettre � jour dans la base de donn�e
	 *  Retourne true si ok, false si nok 
	 *  @param Reponse reponse
	 *  @return Boolean
	 */
	public static boolean updateReponse(Reponse reponse){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update QUESTIONS_REPONSES set TEXTE =?,REPONSE =? where ID_ENONCE = ? and NUMERO = ? ");
			stm.setString(1,reponse.getTexte());
			stm.setBoolean(2, reponse.isEtat());
			stm.setString(3, reponse.getQuestion().getId().toString());
			stm.setInt(4,reponse.getNumero());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateReponse");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/*****************************************************
	*					Methodes Delete					 *
	******************************************************/
	
	
	/***
	 * Re�oit une question � supprimer de la base de donn�e <br>
	 * Retourne true si ok , false si nok
	 * @param Question question
	 * @return Boolean
	 */
	public static boolean deleteQuestion(Question question)
	{
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("delete QUESTIONS_ENONCES where ID = ? ");
			stm.setString(1, question.getId().toString());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur deleteReponse");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 * Re�oit une reponse � supprimer de la base de donn�e <br>
	 * Retourne true si ok , false si nok
	 * @param Reponse reponse
	 * @return Boolean
	 */
	public static boolean deleteReponse(Reponse reponse)
	{
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("delete QUESTIONS_REPONSES where ID_ENONCE = ? and NUMERO = ? ");
			stm.setString(1, reponse.getQuestion().getId().toString());
			stm.setInt(2, reponse.getNumero());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur deleteReponse");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/*****************************************************
	*					Methodes Select					 *
	******************************************************/
	
	/***
	 * Recupere les questions presentes dans la base pour la section pass�e en param�tre <br>
	 * Retourne une liste de Question
	 * @param Section section
	 * @return Vector
	 */
	public static Vector<Question> selectQuestions(Section section)
	{
		
		Vector<Question> listeQuestions= new Vector<Question>(); 
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement("select QUESTIONS_ENONCES where NUMERO_SECTION = ? ");
			stm.setInt(1, section.getNumero());
			rs=stm.executeQuery();
			
			while(rs.next())
			{
				Question question = new Question();
				question.setId(UUID.fromString(rs.getString("ID")));
				question.setEnonce(rs.getString("ENONCE"));
				question.setType(selectType(rs.getInt("TYPE_QUESTION")));
				question.setCheminImage(rs.getString("IMAGE_QUESTION"));
				listeQuestions.add(question);
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectQuestions");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeQuestions;
	}
	
	/***
	 * R�cup�re les reponses dans la base pour la question pass�e en param�tre <br>
	 * Retourne une liste de Reponse
	 * @param Question question
	 * @return Vector
	 */
	public static Vector<Reponse> selectReponse(Question question)
	{
		Vector<Reponse> listeReponses= new Vector<Reponse>(); 
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement("select QUESTIONS_REPONSES where ID_ENONCE = ? ");
			stm.setString(1, question.getId().toString());
			rs=stm.executeQuery();
			
			while(rs.next())
			{
				Reponse reponse = new Reponse();
				reponse.setQuestion(question);
				reponse.setNumero(rs.getInt("NUMERO"));
				reponse.setTexte(rs.getString("TEXTE"));
				reponse.setEtat(rs.getBoolean("REPONSE"));
				listeReponses.add(reponse);
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectQuestions");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeReponses;
	}
	
	/***
	 * Recupere les questions presentes dans la base pour la section pass�e en param�tre <br>
	 * Retourne une liste de Question
	 * @param Section section
	 * @return Vector
	 */
	public static Type selectType(int numero)
	{
		
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		Type type = new Type();;
		
		try {
			
			stm = cnx.prepareStatement("select TYPE_QUESTION where NUMERO = ? ");
			stm.setInt(1, numero);
			rs=stm.executeQuery();
			while(rs.next())
			{
				type.setNumero(rs.getInt("NUMERO"));
				type.setLibelle(rs.getString("LIBELLE"));
			}
			
							
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectType");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return type;
	}
	
	
}
