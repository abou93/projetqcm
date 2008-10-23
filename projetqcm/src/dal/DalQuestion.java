package dal;

import java.sql.*;

import modeles.Question;


public class DalQuestion {
	

	
	public static boolean insertQuestion(Question question,int numeroSection){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into QUESTIONS_ENONCES (ID,ENONCE,TYPE,IMAGE,NUMERO_SECTION) values (?, ?, ?, ?, ?)");
			stm.setObject(1, question.getId());
			stm.setString(2,question.getEnonce());
			stm.setInt(3, question.getType().getNumero());
			stm.setString(4, question.getCheminImage());
			stm.setInt(5,numeroSection);
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
	
	
	private static boolean insertReponse(Question question){
		
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
	
	
}
