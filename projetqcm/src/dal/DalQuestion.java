package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import classArticle.Stylo;



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
			updateQuestionReponse(question);
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
	private static boolean updateReponse(Question question){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			
			stm = cnx.prepareStatement("Insert into QUESTIONS_REPONSES (ID_ENONCE,NUMERO,TEXTE,REPONSE) values (?, ?, ?, ?)");
			stm.setObject(1, question.getId());
			stm.setInt(2,question.getListeReponses().size());
			stm.setInt(3,question.getReponseAt(index)));
			stm.setString(4,question.getCheminImage());
			stm.setInt(5,numeroSection);
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}					
		try {
			cnx.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
}
