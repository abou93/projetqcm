package dal;

import java.sql.*;
import java.util.UUID;
import java.util.Vector;

import modeles.Promotion;
import modeles.Stagiaire;
import modeles.Test;

/***
 * Permet l'acc�s aux tables <b>Stagiaires</b> et <b>Promotions</b> de la base de donn�es
 * @author Ma�l Genevrais
 * 
 */

public class DalStagiaire {
	
	/***
	 * Connection permettant l'acc�s � la base de donn�e.
	 */
	private static Connection cnx;
	
	
	/***
	 *  Va chercher toutes les promotions dans la base.
	 *  Retourne un vecteur de String
	 *  @param none
	 *  @return Vecteur de String (Noms des promotions) 
	 */
	
	//Selectionner toutes les promotions
	public static Vector<String> selectAllPromotions(){
		cnx = AccesBase.getConnection();
		Vector<String> listePromo = new Vector<String>();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS");
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				listePromo.add(rs.getString("LIBELLE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccesBase.deconnexionBase(cnx);
		return listePromo;
	}
	
	
	/***
	 *  Va chercher les stagiaires d'une promotion dans la base. 
	 *  Re�oit un code promotion en param�tre.
	 *  Retourne un vecteur de Stagaire
	 *  @param String codePromotion
	 *  @return Vector(Stagiaire)
	 */
	
	//Selectionner les stagiaires dont la promotion porte le libelle 'libelle'
	public static Vector<Stagiaire> selectStagiaires(String codePromotion){
		cnx = AccesBase.getConnection();
		Vector<Stagiaire> listeStagiaires = new Vector<Stagiaire>();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTIONS p inner join STAGIAIRES s on p.CODE = s.CODE_PROMOTION where p.CODE = ?");
			stm.setString(1, codePromotion);	
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Promotion p = new Promotion(rs.getString("CODE"),rs.getString("LIBELLE"));
				Stagiaire s = new Stagiaire(UUID.fromString(rs.getString("ID")),rs.getString("NOM"),rs.getString("PRENOM"),p,rs.getString("MOT_DE_PASSE"));
				listeStagiaires.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccesBase.deconnexionBase(cnx);
		return listeStagiaires;
	}
	
	
	/***
	 *  Va chercher un stagiaire dans la base. 
	 *  Re�oit l'identifiant du stagiaire d�sir� en param�tre.
	 *  Retourne un Stagaire
	 *  @param UUID idStagaire
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
			Promotion p = new Promotion(rs.getString("CODE"),rs.getString("LIBELLE"));
			s = new Stagiaire(UUID.fromString(rs.getString("ID")),rs.getString("NOM"),rs.getString("PRENOM"),p,rs.getString("MOT_DE_PASSE"));
		} catch (SQLException e) {
			e.printStackTrace();
			s=null;
		}
		
		AccesBase.deconnexionBase(cnx);
		return s;
	}
	
	
	/***
	 *  Va chercher les stagiaires inscrit � un test dans la base. 
	 *  Re�oit le test d�sir� en param�tre.
	 *  Retourne un Vecteur de Stagiaire
	 *  @param Test test
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
				Promotion p = new Promotion(rs.getString("CODE"),rs.getString("LIBELLE"));
				listeStagiaires.add(new Stagiaire((UUID)rs.getObject("ID"),rs.getString("NOM"),rs.getString("PRENOM"),p,rs.getString("MOT_DE_PASSE")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccesBase.deconnexionBase(cnx);
		return listeStagiaires;
	}
	
}
