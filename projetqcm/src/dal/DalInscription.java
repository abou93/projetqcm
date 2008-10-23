package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.Vector;

import modeles.*;

/***
 * Permet l'accès aux tables <b>Inscriptions</b> et <b>Tirage</b> de la base de données
 * @author Maël Genevrais
 */

public class DalInscription {

	/***
	 * Connection permettant l'accès à la base de donnée.
	 */
	private static Connection cnx;
	
	
	/***
	 *  Insert une inscription dans la base.
	 *  @param Inscription : inscription
	 *  @return Boolean <li> True si la requète à bien été exécutée <li> False dans l'autre cas
	 */
	
	public static boolean insertInscription(Inscription inscription){
		cnx = AccesBase.getConnection();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("insert into INSCRIPTIONS (ID_STAGIAIRE,NOM_TEST,DUREE,MAIL_FORMATEUR) values (?,?,?,?)");
			stm.setString(1, inscription.getStagiaire().getId().toString());
			stm.setString(2, inscription.getTest().getNom());
			stm.setString(3, inscription.getDuree());
			stm.setString(4, inscription.getMailformateur());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return false;
		}
		return true;
	}
	
	
	/***
	 *  Insert un tirage dans la base.
	 *  @param Inscription : inscription
	 *  @return Boolean <li> True si la requète à bien été exécutée <li> False dans l'autre cas
	 */
	
	public static boolean insertTirage(Inscription inscription){
		cnx = AccesBase.getConnection();
		Enumeration<UUID> enumTirage = inscription.getTirage().elements();
		while(enumTirage.hasMoreElements()){
			try {
				PreparedStatement stm = cnx.prepareStatement("insert into TIRAGE (ID_QUESTION,NOM_TEST,ID_STAGIAIRE) values (?,?,?)");
				stm.setString(1, enumTirage.nextElement().toString());
				stm.setString(2, inscription.getTest().getNom());
				stm.setString(3, inscription.getStagiaire().getId().toString());
				stm.execute();
				AccesBase.deconnexionBase(cnx);
			} catch (SQLException e) {
				e.printStackTrace();
				AccesBase.deconnexionBase(cnx);
				return false;
			}
		}
		return true;
	}
	
	
	/***
	 *  Efface une inscription de la base.
	 *  @param UUID : idStagiaire
	 *  @param String : nomTest
	 *  @return Boolean <li> True si la requète à bien été exécutée <li> False dans l'autre cas
	 */
	
	public static boolean deleteInscription(UUID idStagiaire,String nomTest){
		cnx = AccesBase.getConnection();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("delete from INSCRIPTIONS where ID_STAGIAIRE=? and NOM_TEST=?");
			stm.setString(1, idStagiaire.toString());
			stm.setString(2, nomTest);
			stm.execute();
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return false;
		}
		return true;
	}
	
	
	/***
	 *  Efface un tirage de la base.
	 *  @param UUID : idStagiaire
	 *  @param String : nomTest
	 *  @return Boolean <li> True si la requète à bien été exécutée <li> False dans l'autre cas
	 */
	
	public static boolean deleteTirage(UUID idStagiaire,String nomTest){
		cnx = AccesBase.getConnection();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("delete from TIRAGE where NOM_TEST=? and ID_STAGIAIRE=?");
			stm.setString(1, nomTest);
			stm.setString(2, idStagiaire.toString());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return false;
		}
		return true;
	}
	
	
	/***
	 *  Met à jour une inscription dans la base.
	 *  @param Inscription : inscription
	 *  @return Boolean <li> True si la requète à bien été exécutée <li> False dans l'autre cas
	 */
	
	public static boolean updateInscription(Inscription inscription){
		cnx = AccesBase.getConnection();
		
		try {
			PreparedStatement stm = cnx.prepareStatement("update INSCRIPTIONS set DUREE=?,MAIL_FORMATEUR=? where ID_STAGIAIRE=? and NOM_TEST=?");
			stm.setString(1, inscription.getDuree());
			stm.setString(2, inscription.getMailformateur());
			stm.setString(3,inscription.getStagiaire().getId().toString());
			stm.setString(4,inscription.getTest().getNom());
			stm.execute();
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return false;
		}
		return true;
	}
	
	
	/***
	 *  Selectionne une inscription dans la base. Retourne l'inscription.
	 *  @param UUID : idStagiaire
	 *  @param String : nomTest
	 *  @return Inscription
	 */
	
	public static Inscription selectInscription(UUID idStagiaire,String nomTest){
		cnx = AccesBase.getConnection();
		Inscription insc;
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from INSCRIPTIONS where ID_STAGIAIRE=? and NOM_TEST=?");
			stm.setString(1, idStagiaire.toString());
			stm.setString(2, nomTest);
			ResultSet rs = stm.executeQuery();
			rs.next();
			insc=new Inscription(DalTest.selectTest(nomTest),rs.getString("DUREE"),rs.getString("MAIL_FORMATEUR"),DalStagiaire.selectStagiaire(idStagiaire));
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return insc;
	}
	
	
	/***
	 *  Selectionne un tirage dans la base. Retourne les id de toutes les questions du tirage.
	 *  @param UUID : idStagiaire
	 *  @param String : nomTest
	 *  @return Vector(UUID)
	 */
	
	public static Vector<UUID> selectTirage(UUID idStagiaire,String nomTest){
		cnx = AccesBase.getConnection();
		Vector<UUID> tirage = new Vector<UUID>();
		try {
			PreparedStatement stm = cnx.prepareStatement("select * from TIRAGE where NOM_TEST=? and ID_STAGIAIRE=?");
			stm.setString(1, nomTest);
			stm.setString(2, idStagiaire.toString());
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				tirage.add(UUID.fromString(rs.getString("")));
			}
			AccesBase.deconnexionBase(cnx);
		} catch (SQLException e) {
			e.printStackTrace();
			AccesBase.deconnexionBase(cnx);
			return null;
		}
		return tirage;
	}
}
