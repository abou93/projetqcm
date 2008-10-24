package dal;

import java.sql.*;
import java.util.UUID;
import java.util.Vector;

import modeles.Promotion;
import modeles.Stagiaire;
import modeles.Test;

/***
 * Permet l'accès aux tables <b>Stagiaires</b> et <b>Promotions</b> de la base de données
 * @author Maël Genevrais
 */

public class DalStagiaire {
	
	/***
	 * Connection permettant l'accès à la base de donnée.
	 */
	private static Connection cnx;
	
	
	/***
	 *  Va chercher toutes les promotions dans la base.
	 *  Retourne un vecteur de Promotion contenant 
	 *  @param none
	 *  @return Vector(Promotion)
	 */
	
	//Selectionner toutes les promotions
	public static Vector<Promotion> selectAllPromotions(){
		cnx = AccesBase.getConnection();
		Vector<Promotion> listePromo = new Vector<Promotion>();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS");
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				listePromo.add(new Promotion(rs.getString("CODE").trim(),rs.getString("LIBELLE").trim()));
			}
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return listePromo;
	}
	
	
	/***
	 *  Va chercher les stagiaires d'une promotion dans la base. 
	 *  Reçoit un code promotion en paramètre.
	 *  Retourne un vecteur de Stagaire
	 *  @param String : codePromotion
	 *  @return Vector(Stagiaire)
	 */
	
	//Selectionner les stagiaires dont la promotion porte le libelle 'libelle'
	public static Vector<Stagiaire> selectStagiairesPromotion(String codePromotion){
		cnx = AccesBase.getConnection();
		Vector<Stagiaire> listeStagiaires = new Vector<Stagiaire>();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS p inner join STAGIAIRES s on p.CODE = s.CODE_PROMOTION where p.CODE = ?");
			stm.setString(1, codePromotion);	
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Promotion p = new Promotion(rs.getString("CODE").trim(),rs.getString("LIBELLE").trim());
				Stagiaire s = new Stagiaire(UUID.fromString(rs.getString("ID")),rs.getString("NOM").trim(),rs.getString("PRENOM").trim(),p,rs.getString("MOT_DE_PASSE").trim());
				listeStagiaires.add(s);
			}
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return listeStagiaires;
	}
	
	
	/***
	 *  Va chercher un stagiaire dans la base. 
	 *  Reçoit l'identifiant du stagiaire désiré en paramètre.
	 *  Retourne un Stagaire
	 *  @param UUID : idStagaire
	 *  @return Stagiaire
	 */
	
	//Selectionner le stagiaire dont l'identifiant est 'id'
	public static Stagiaire selectStagiaire(UUID idStagiaire){
		cnx = AccesBase.getConnection();
		Stagiaire s;
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS p inner join STAGIAIRES s on p.CODE = s.CODE_PROMOTION where ID = ?");
			stm.setString(1, idStagiaire.toString());	
			ResultSet rs = stm.executeQuery();
			rs.next();
			Promotion p = new Promotion(rs.getString("CODE").trim(),rs.getString("LIBELLE").trim());
			s = new Stagiaire(UUID.fromString(rs.getString("ID")),rs.getString("NOM").trim(),rs.getString("PRENOM").trim(),p,rs.getString("MOT_DE_PASSE").trim());
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return s;
	}
	
	
	/***
	 *  Va chercher les stagiaires inscrit à un test dans la base. 
	 *  Reçoit le test désiré en paramètre.
	 *  Retourne un Vecteur de Stagiaire
	 *  @param Test : test
	 *  @return Vector(Stagiaire)
	 */
	
	//Selectionner les stagiaires inscrit au test 'test'
	public static Vector<Stagiaire> selectStagiaireTest(Test test){
		cnx = AccesBase.getConnection();
		Vector<Stagiaire> listeStagiaires = new Vector<Stagiaire>();
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS p inner join STAGIAIRES s on p.CODE = s.CODE_PROMOTION inner join INSCRIPTIONS i on s.ID = i.ID_STAGIAIRE where NOM_TEST = ?");
			stm.setObject(1, test.getNom());	
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Promotion p = new Promotion(rs.getString("CODE").trim(),rs.getString("LIBELLE").trim());
				listeStagiaires.add(new Stagiaire((UUID)rs.getObject("ID"),rs.getString("NOM").trim(),rs.getString("PRENOM").trim(),p,rs.getString("MOT_DE_PASSE").trim()));
			}
		AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return listeStagiaires;
	}
	
}
