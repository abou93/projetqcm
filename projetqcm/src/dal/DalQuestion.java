package dal;

import java.sql.*;
import java.util.UUID;
import java.util.Vector;

import pluriel.ListeTypes;

import modeles.*;


/***
 * @author Lefort Steve 
 * @category Acces base de donnée
 * :  <i> Description de la classe </i> <br> <br>
 * Regroupe les différentes méthodes d'accès à la base de donnée
 * pour les classes d'objet : <br> <br>
 * <li> Question </li>
 * <li>	Reponse </li> 
 * <li>	Type </li> 
 *   
 */
public class DalQuestion {
	
	private static ListeTypes listeType = new ListeTypes();
	
	
	/*****************************************************
	*					Methodes Insert 				 *
	******************************************************/
	
	
	/***
	 *  Reçoit une question pour l'insérée dans la base de donnée
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
	 *  Reçoit une Reponse pour l'insérée dans la base de donnée
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
	 *  Reçoit une question à mettre à jour dans la base de donnée
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
	 *  Reçoit une reponse à mettre à jour dans la base de donnée
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
	
	
	/***
	 *  Reçoit une reponse à mettre à jour dans la base de donnée
	 *  Retourne true si ok, false si nok 
	 *  @param Reponse reponse
	 *  @return Boolean
	 */
	public static boolean updateNumeroReponse(UUID idQuestion, int index){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update QUESTIONS_REPONSES set NUMERO = NUMERO -1 where ID_ENONCE = ? and NUMERO > ? ");
			stm.setString(1,idQuestion.toString());
			stm.setInt(4,index);
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateNumeroReponse");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
	/*****************************************************
	*					Methodes Delete					 *
	******************************************************/
	
	
	/***
	 * Reçoit une question à supprimer de la base de donnée <br>
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
	 * Reçoit une reponse à supprimer de la base de donnée <br>
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
	 * Recupere les questions presentes dans la base pour la section passée en paramètre <br>
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
			
			stm = cnx.prepareStatement("select * from QUESTIONS_ENONCES where NUMERO_SECTION = ? ");
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
				
				int i = listeType.indexOf(question.getType());
				question.setType(listeType.elementAt(i));
				
			}
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectQuestions");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeQuestions;
	}
	
	/***
	 * Récupère les reponses dans la base pour la question passée en paramètre <br>
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
			
			stm = cnx.prepareStatement("select * from QUESTIONS_REPONSES where ID_ENONCE = ? ");
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
	 * Recupere les questions presentes dans la base pour la section passée en paramètre <br>
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
			
			stm = cnx.prepareStatement("select * from TYPE_QUESTION where NUMERO = ? ");
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
	
	
	/***
	 * Recupere les questions presentes dans la base pour la section passée en paramètre <br>
	 * Retourne une liste de Question
	 * @param Section section
	 * @return Vector
	 */
	public static Vector<Type> selectAllType()
	{
		
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		
		
		try {
			
			stm = cnx.prepareStatement("select * from TYPE_QUESTION ");
			rs=stm.executeQuery();
			while(rs.next())
			{
				Type type = new Type();
				type.setNumero(rs.getInt("NUMERO"));
				type.setLibelle(rs.getString("LIBELLE"));
				listeType.add(type);
			}
			
							
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectType");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return listeType;
	}
	
	
	/***
	 * Récupère la question dont l'ID correspond à l'ID passé en paramètre.
	 * @param UUID : idQuestion
	 * @return Question : question
	 */
	public static Question selectQuestion(UUID idQuestion)
	{
		
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		Question question;
		
		try {
			
			stm = cnx.prepareStatement("select * from QUESTIONS_ENONCES where ID = ? ");
			stm.setString(1, idQuestion.toString());
			rs=stm.executeQuery();
			
			rs.next();
			question = new Question();
			question.setId(UUID.fromString(rs.getString("ID")));
			question.setEnonce(rs.getString("ENONCE"));
			question.setType(selectType(rs.getInt("TYPE_QUESTION")));
			question.setCheminImage(rs.getString("IMAGE_QUESTION"));
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectQuestions");
			question=null;
		}					
		
		AccesBase.deconnexionBase(cnx);
		return question;
	}
	
	
	/***
	 * Récupère une réponse d'une question donnée.
	 * @param UUID : idQuestion
	 * @param Integer : numeroReponse
	 * @return Reponse : reponse
	 */
	public static Reponse selectReponseQuestion(UUID idQuestion,int numeroReponse)
	{
		
		Connection cnx;
		PreparedStatement stm;
		ResultSet rs;
		cnx=AccesBase.getConnection();
		Reponse reponse;
		
		try {
			
			stm = cnx.prepareStatement("select * from QUESTIONS_REPONSES where ID_ENONCE = ? and NUMERO = ?");
			stm.setString(1, idQuestion.toString());
			stm.setInt(2, numeroReponse);
			rs=stm.executeQuery();
			
			rs.next();
			reponse = new Reponse();
			reponse.setEtat(rs.getBoolean("REPONSE"));
			reponse.setTexte(rs.getString("TEXTE"));
			reponse.setNumero(numeroReponse);
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur selectReponseQuestion");
			reponse=null;
		}					
		
		AccesBase.deconnexionBase(cnx);
		return reponse;
	}
	
	
}
