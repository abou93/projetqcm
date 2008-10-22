package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import modeles.Question;

public class DalQuestion {
	

	
	private static boolean insertQuestion(Question question,int numeroSection){
		
		Connection cnx;
		PreparedStatement stm;
		cnx=AccesBase.getConnection();
		
		try {
			stm = cnx.prepareStatement("Insert into ARTICLE (REFERENCE,PRIXUNITAIRE,QTESTOCK,MARQUE,TYPE,COULEUR) values ( ?, ? , ? , ? , 'STYLO', ?)");
			stm.setObject(1, question.getId());
			stm.setString(2,question.getEnonce());
			stm.setInt(3, question.getType().getNumero());
			stm.setString(4, question.getCheminImage());
			stm.setInt(5,numeroSection);
			stm.execute();
			AccesBase.deconnexionBase(cnx);
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}					
		
		AccesBase.deconnexionBase(cnx);
		return false;
	}
	
	
}
