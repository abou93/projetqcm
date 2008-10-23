package dal;

import java.sql.*;
import java.util.Vector;

import modeles.Promotion;
import modeles.Stagiaire;

public class DalStagiaire {
	
	private static Connection cnx;
	
	/*****************************************************
	*						Méthodes					 *
	******************************************************/
	
	//Selectionner toutes les promotions
	public static Vector<String> selectAllPromotions(){
		cnx = AccesBase.getConnection();
		Vector<String> listePromo = new Vector<String>();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from PROMOTION");
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
	
	//Selectionner les stagiaire dont la promotion porte le libelle 'libelle'
	public static Vector<Stagiaire> selectStagiaires(String libelle){
		cnx = AccesBase.getConnection();
		Vector<Stagiaire> listeStagiaires = new Vector<Stagiaire>();
		
		PreparedStatement stm;
		try {
			stm = cnx.prepareStatement("select * from PROMOTION p inner join STAGIAIRE s on p.CODE = s.CODE_PROMOTION where p.LIBELLE = ?");
			stm.setString(1, libelle);	
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				Promotion p = new Promotion(rs.getString("CODE"),rs.getString("LIBELLE"));
				Stagiaire s = new Stagiaire(rs.getString("NOM"),rs.getString("PRENOM"),p);
				listeStagiaires.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeStagiaires;
	}
	
}
