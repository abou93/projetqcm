package controleur;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import modeles.Inscription;
import modeles.Promotion;
import modeles.Question;
import modeles.Reponse;
import modeles.Section;
import modeles.Stagiaire;
import modeles.Test;
import dal.DalInscription;
import dal.DalQuestion;
import dal.DalStagiaire;
import dal.DalTest;

public class CtrlFormateur {
	
	static CtrlFormateur instance;
	Vector<Test> listeTests;
	Vector<Promotion> listePromotions;
	Vector<Section> listeSectionsParTest;
	Vector<Section> listeSections;
	//Vector<Stagiaire> listeStagiaires;
	
	
	/***
	 * Constructeur du controleur CtrlFormateur
	 */
	private CtrlFormateur(){
		super();
		listeTests=new Vector<Test>();
		listePromotions = new Vector<Promotion>();
		listeSectionsParTest = new Vector<Section>();
		listeSections = new Vector<Section>();
		//listeStagiaires = new Vector<Stagiaire>();
		chargerListePromotions();
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
	 * Ajoute une nouvelle section � la liste des sections.
	 */
	public void nouvelleSection(){
		listeSections.add(new Section("Nouvelle section"));
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
	private void chargerListePromotions(){
		Vector<Promotion> v = DalStagiaire.selectAllPromotions();
		if (v != null) listePromotions=v; 
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
	
	
	/***
	 * R�alise l'inscription d'un stagiaire � un test. Tire au sort les questions d'une section si besoin. Enregistre l'inscription et le tirage dans la base.
	 * @param Stagiaire : stagiaire
	 * @param Test : test
	 * @param String : duree
	 * @param String : mailFormateur
	 * @return Boolean : True si l'inscription est ok, false dans l'autre cas.
	 */
	public boolean inscrirStagiaireTest(Stagiaire stagiaire,Test test,String duree,String mailFormateur){
		Inscription inscription = new Inscription(test,duree,mailFormateur,stagiaire);
		
		if(test.getSections().size()>0){
			Enumeration<Section> enumSection = test.getSections().elements();
			
			while(enumSection.hasMoreElements()){
				Section s = enumSection.nextElement();
				if(s.getQuestions().size()>s.getNbrQuestion()){
					tirage(test,inscription);
				}
				
			}
		}
		
		if(DalInscription.insertInscription(inscription)){
			if(inscription.getTirage()==null){
				stagiaire.addInscription(inscription);
				return true;
			}else{
				if(DalInscription.insertTirage(inscription)){
					stagiaire.addInscription(inscription);
					return true;
				}else{
					DalInscription.deleteInscription(stagiaire.getId(), test.getNom());
					stagiaire.supprInscription(inscription);
					return false;
				}
			}
			
		}else{
			return false;
		}
	}
	
	
	/***
	 * R�alise le tirage au sort des questions d'un section.
	 * @param Test : test
	 * @param Inscription : inscription
	 */
	private void tirage(Test test,Inscription inscription){
		Enumeration<Section> enumSection = test.getSections().elements();
		while(enumSection.hasMoreElements()){
			Section section = enumSection.nextElement();
			int i = section.getQuestions().size();
			boolean[] dejaTire = new boolean[i];
			for(int j= 0;j<i;j++){
				dejaTire[j]=false;
			}
			int num = 0;
			while(num<section.getNbrQuestion()){
				Random rdm = new Random();
				int nbrRdm = rdm.nextInt(i);
				if(dejaTire[nbrRdm]==false){
					inscription.addIdQuestion(section.getQuestionAt(nbrRdm));
					dejaTire[nbrRdm]=true;
					num++;
				}
			}
		}
	}
	
	
	/***
	 * Supprime une inscription pour un stagiaire et pour un test donn�.
	 * @param Stagiaire : stagiaire
	 * @param Test : test
	 */
	public void supprInscription(Stagiaire stagiaire,Test test){
		if(DalInscription.deleteInscription(stagiaire.getId(), test.getNom())){
			if(DalInscription.deleteTirage(stagiaire.getId(), test.getNom())){
				for(Inscription insc : stagiaire.getInscriptions()){
					if(insc.getTest().getNom()==test.getNom()){
						stagiaire.supprInscription(insc);
					}
				}
			}
		}
		
	}
	
	
	/***
	 * Charge la liste de section du test d�sir�.
	 * @param Test : test
	 */
	public void chargerListeSectionsParTest(Test test){
		listeSectionsParTest = DalTest.selectSectionByTest(test.getNom());
	}
	

	/***
	 * Accesseur permettant d'acceder � la liste des sections pour un test.
	 * @return
	 */
	public Vector<Section> getSectionParTest(){
		return listeSectionsParTest;
	}
	
	/***
	 * Enregistre la section pass�e en param�tre.
	 * @param Section : section
	 * @return Boolean : True si l'enregistrement est OK, false dans l'autre cas.
	 */
	public boolean enregistrerSection(Section section){
		if(DalTest.selectSection(section.getNumero())==null){
			return DalTest.insertSection(section);
		}else{
			return DalTest.updateSection(section);
		}
	}
	
	/***
	 * Supprime une section d�finitivement.
	 * @param Section : section
	 * @return Boolean
	 */
	public boolean supprimerSection(Test test,Section section){
		if(DalTest.deleteTest_Section(test.getNom(),section.getNumero())){
			for(Question q:section.getQuestions()){
				for(Reponse r:q.getListeReponses()){
					DalQuestion.deleteReponse(r);
				}
			DalQuestion.deleteQuestion(q);
			}
			if(DalTest.deleteSection(section)){
				listeSectionsParTest.remove(section);
				listeSections.remove(section);
				return true;
			}
		}
		return false;
	}
	
	
	/***
	 * Charge la liste de toutes les sections
	 */
	public void chargerListeSection(){
		listeSections = DalTest.selectAllSection();
	}
	
	
	/***
	 * Accesseur permettant d'acceder � la liste de toutes les sections
	 * @return Vector(Section)
	 */
	public Vector<Section> getListeSection(){
		return listeSections;
	}
}
