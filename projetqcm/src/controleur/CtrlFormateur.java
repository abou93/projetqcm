package controleur;

import java.util.Enumeration;
import java.util.Vector;

import modeles.Inscription;
import modeles.Promotion;
import modeles.Stagiaire;
import modeles.Test;
import dal.DalInscription;
import dal.DalStagiaire;
import dal.DalTest;

public class CtrlFormateur {
	
	static CtrlFormateur instance;
	Vector<Test> listeTests;
	Vector<Promotion> listePromotions;
	
	
	/***
	 * Constructeur du controleur CtrlFormateur
	 */
	private CtrlFormateur(){
		super();
		listeTests=new Vector<Test>();
		listePromotions = new Vector<Promotion>();
	}
	

	/***
	 * Accesseur permettant d'acc�der � la liste des tests.
	 * @return Vector(Test)
	 */
	public Vector<Test> getListeTests() {
		return listeTests;
	}


	/***
	 * M�thode singleton permettant de r�cup�rer l'instance du controleur. Si cette instance existe la m�thode la renvoie sinon la m�thode la cr�er.
	 * @return instance du controleur CtrlFormateur
	 */
	public static CtrlFormateur getCtrlFormateur(){
		if(instance == null){
			instance=new CtrlFormateur();
		}
		return instance;
	}
	
	
	/***
	 * Charger la liste des tests depuis la base.
	 * @return Vector(Test)
	 */
	public void chargerListeTests(){
		listeTests = DalTest.selectAllTest();
	}
	
	
	/***
	 * Ajoute un nouveau test � la liste des tests.
	 */
	public void nouveauTest(){
		listeTests.add(new Test("Nouveau test",60,50));
	}
	
	
	/***
	 * Enregistrer le test pass� en param�tre.
	 * @param Test : test
	 * @return Boolean : True si l'enregistrement est ok, false dans l'autre cas.
	 */
	public boolean enregistrerTest(Test test){
		if(DalTest.selectTestByNom(test.getNom())==null){
			return DalTest.insertTest(test);
		}else{
			return DalTest.updateTest(test);
		}
	}
	
	
	/***
	 * Supprimer le test pass� en param�tre.
	 * @param Test : test
	 * @return Boolean : True si le test � bien �t� supprim�, false dans l'autre cas.
	 */
	public boolean supprimerTest(Test test){
		if(DalTest.deleteTest(test)){
			listeTests.remove(test);
			return true;
		}
		return false;
	}

	
	/***
	 * Charge la liste des promotions depuis la base.
	 */
	public void chargerListePromotions(){
		listePromotions = DalStagiaire.selectAllPromotions();
	}
	
	
	/***
	 * Accesseur permettant d'acc�der � la liste des promotions.
	 * @return Vector(Test)
	 */
	public Vector<Promotion> getListePromotions() {
		return listePromotions;
	}
	
	
	/***
	 * Liste les stagiaires pour la promotion d�sir�e.
	 * @param Promotion : promotion
	 * @return Vector(Stagiaire)
	 */
	public Vector<Stagiaire> getStagiairesPromo(Promotion promotion){
		return DalStagiaire.selectStagiairesPromotion(promotion.getCode());
	}
	
	
	/***
	 * Liste les stagiaires pour le test d�sir� (inscrit au test). 
	 * @param Test : test
	 * @return Vector(Stagiaire)
	 */
	public Vector<Stagiaire> getStagiairesTest(Test test){
		return DalStagiaire.selectStagiaireTest(test);
	}
	
	
}
