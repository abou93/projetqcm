package dal;

import java.sql.*;

import modeles.Question;


public class DalQuestion {
	
	
	/***
	 *  Insert Question Reponse	
	 *  reçoit une question à insérée dans la Base
	 *  Retourne true si ok, false si nok 
	 */
	
	public static boolean insertQuestion(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into QUESTIONS_ENONCES (ID,ENONCE,TYPE,IMAGE,NUMERO_SECTION) values (?, ?, ?, ?, ?)");
			stm.setObject(1, question.getId());
			stm.setString(2,question.getEnonce());
			stm.setInt(3, question.getType().getNumero());
			stm.setString(4, question.getCheminImage());
			stm.setInt(5,question.getSection().getNumero());
			stm.execute();
			insertReponse(question);
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertQuestion");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 *  InsertReponse	
	 *  reçoit une question à mettre à jour dans la Base
	 *  Retourne true si ok, false si nok 
	 */
	
	
	public static boolean insertReponse(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			for (int i = 0 ; i<question.getNombreReponse();i++)
			{
				stm = cnx.prepareStatement("Insert into QUESTIONS_REPONSES (ID_ENONCE,NUMERO,TEXTE,REPONSE) values (?, ?, ?, ?)");
				stm.setObject(1, question.getId());
				stm.setInt(2,i);
				stm.setString(3,question.getReponseAt(i).getTexte());
				stm.setBoolean(4, question.getReponseAt(i).isEtat());
				stm.execute();
				AccesBase.deconnexionBase(cnx);
				return true;
			}
			
				
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur insertReponse");
			
		}					
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	/***
	 *  UpdateQuestion	
	 *  reçoit une question à mettre à jour dans la Base
	 *  Retourne true si ok, false si nok 
	 */
	
	public static boolean updateQuestion(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("update QUESTIONS_ENONCES set ENONCE = ?,TYPE = ? ,IMAGE =?,NUMERO_SECTION=? where ID = ?");
			stm.setString(1,question.getEnonce());
			stm.setInt(2, question.getType().getNumero());
			stm.setString(3, question.getCheminImage());
			stm.setInt(4,question.getSection().getNumero());
			stm.setObject(5, question.getId());
			stm.execute();
			insertReponse(question);
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("erreur updateQuestion");
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
	
}
