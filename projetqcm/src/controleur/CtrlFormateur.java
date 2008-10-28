package controleur;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import pluriel.ListeSections;

import modeles.Inscription;
import modeles.Promotion;
import modeles.Question;
import modeles.Reponse;
import modeles.Section;
import modeles.Stagiaire;
import modeles.Test;
import modeles.Type;
import dal.DalInscription;
import dal.DalQuestion;
import dal.DalStagiaire;
import dal.DalTest;

public class CtrlFormateur {
	
	/*****************************************************
	*					Attributs privé					 *
	******************************************************/
	
	static CtrlFormateur instance;
	Vector<Test> listeTests;
	Vector<Promotion> listePromotions;
	ListeSections listeSectionsParTest;
	Vector<Type> listeTypes;

	ListeSections listeSections;
	//Vector<Stagiaire> listeStagiaires;
	
	Test testEnCour = null;
	Section sectionEnCour = null;
	Question questionEnCour = null;
	
	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public Test getTestEnCour() {
		return testEnCour;
	}


	public Question getQuestionEnCour() {
		return questionEnCour;
	}


	public void setQuestionEnCour(Question questionEnCour) {
		this.questionEnCour = questionEnCour;
	}


	public void setTestEnCour(Test testEnCour) {
		this.testEnCour = testEnCour;
	}


	public Section getSectionEnCour() {
		return sectionEnCour;
	}


	public void setSectionEnCour(Section sectionEnCour) {
		this.sectionEnCour = sectionEnCour;
	}
	
	public Vector<Section> getListeSectionsParTest() {
		return listeSectionsParTest;
	}


	public void addSectionListeSectionsParTest(Section section) {
		if (!(this.listeSectionsParTest.contains(section)))
			{ 
			this.listeSectionsParTest.add(section);
			this.affecterSectionTest(section, this.getTestEnCour());
			}
	}
	
	public void supSectionListeSectionsParTest(Section section) {
		
		this.listeSectionsParTest.remove(section);
		DalTest.deleteTest_Section(this.getTestEnCour().getNom(), section.getNumero());
	}

	
	/***
	 * Accesseur permettant d'accéder à la liste des promotions.
	 * @return Vector(Test)
	 */
	public Vector<Promotion> getListePromotions() {
		return listePromotions;
	}
	
	/***
	 * Accesseur permettant d'accéder à la liste des tests.
	 * @return Vector(Test)
	 */
	public Vector<Test> getListeTests() {
		return listeTests;
	}
	
	public Vector<Type> getListeTypes() {
		return listeTypes;
	}


	public void setListeTypes(Vector<Type> listeTypes) {
		this.listeTypes = listeTypes;
	}


	/*****************************************************
	*					Constructeur					 *
	******************************************************/
	
	private CtrlFormateur(){
		super();
		listeTests=new Vector<Test>();
		listePromotions = new Vector<Promotion>();
		listeSectionsParTest = new ListeSections();
		listeSections = new ListeSections();
		listeTypes = new Vector<Type>();
	
		chargerListePromotions();
		chargerListeType();
	}
	

	/*****************************************************
	*					Méthodes						 *
	******************************************************/
	


	/***
	 * Méthode singleton permettant de récupérer l'instance du controleur. Si cette instance existe la méthode la renvoie sinon la méthode la créer.
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
	 * Ajoute un nouveau test à la liste des tests.
	 */
	public void nouveauTest(){
		listeTests.add(new Test("Nouveau test",60,50));
	}
	
	
	/***
	 * Ajoute une nouvelle section à la liste des sections.
	 */
	public void nouvelleSection(){
		listeSections.add(new Section("Nouvelle section"));
	}
	
	
	/***
	 * Enregistrer le test passé en paramètre.
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
	 * Supprimer le test passé en paramètre.
	 * @param Test : test
	 * @return Boolean : True si le test à bien été supprimé, false dans l'autre cas.
	 */
	public boolean supprimerTest(Test test){
		if(DalTest.selectTestByNom(test.getNom())==null){
			listeTests.remove(test);
			return true;
		}else{
			if(DalTest.deleteTest(test)){
				listeTests.remove(test);
				return true;
			}
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
	 * Charge la liste des types depuis la base.
	 */
	private void chargerListeType(){
		Vector<Type> v = DalQuestion.selectAllType();
		if (v != null) listeTypes=v; 
	}
	
	
	/***
	 * Liste les stagiaires pour la promotion désirée.
	 * @param Promotion : promotion
	 * @return Vector(Stagiaire)
	 */
	public Vector<Stagiaire> getStagiairesPromo(Promotion promotion){
		Vector<Stagiaire> v = DalStagiaire.selectStagiairesPromotion(promotion.getCode());
		if (v !=null) promotion.setListeStagiaires(v);
		return v;
	}
	
	
	/***
	 * Liste les stagiaires pour le test désiré (inscrit au test). 
	 * @param Test : test
	 * @return Vector(Stagiaire)
	 */
	public Vector<Stagiaire> getStagiairesTest(Test test){
		return DalStagiaire.selectStagiaireTest(test);
	}
	
	
	/***
	 * Réalise l'inscription d'un stagiaire à un test. Tire au sort les questions d'une section si besoin. Enregistre l'inscription et le tirage dans la base.
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
	 * Réalise le tirage au sort des questions d'un section.
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
	 * Supprime une inscription pour un stagiaire et pour un test donné.
	 * @param Stagiaire : stagiaire
	 * @param Test : test
	 */
	public void supprInscription(Stagiaire stagiaire,Test test){
		if(DalInscription.deleteInscription(stagiaire.getId(), test.getNom())){
			if(DalInscription.deleteTirage(stagiaire.getId(), test.getNom())){
				Vector<Inscription> listeInsc = stagiaire.getInscriptions();
				for(int i=0;i<listeInsc.size();i++){
					if(listeInsc.elementAt(i).getTest().getNom()==test.getNom()){
						stagiaire.supprInscription(listeInsc.elementAt(i));
					}
				}
				
					
				
			}
		}
		
	}
	
	
	/***
	 * Charge la liste de section du test désiré.
	 * @param Test : test
	 */
	public void chargerListeSectionsParTest(Test test){
		listeSectionsParTest = DalTest.selectSectionByTest(test.getNom());
	}
	

	/***
	 * Accesseur permettant d'acceder à la liste des sections pour un test.
	 * @return
	 */
	public Vector<Section> getSectionParTest(){
		return listeSectionsParTest;
	}
	
	/***
	 * Enregistre la section passée en paramètre.
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
	 * Supprime une section définitivement.
	 * @param Section : section
	 * @return Boolean : True si la section et ses question ont bien été supprimées, false dans l'autre cas.
	 */
	public boolean supprimerSection(Test test,Section section){
		boolean b;
		if(DalTest.deleteTest_Section(test.getNom(),section.getNumero())){
			for(Question q:section.getQuestions()){
				for(Reponse r:q.getListeReponses()){
					b = DalQuestion.deleteReponse(r);
				}
			b = DalQuestion.deleteQuestion(q);
			}
			if(DalTest.deleteSection(section)){
				listeSectionsParTest.remove(section);
				listeSections.remove(section);
				b = true;
			}else{
				b = false;
			}
		}else{
			b = false;
		}
		return b;
	}
	
	
	/***
	 * Charge la liste de toutes les sections
	 */
	public void chargerListeSection(){
		listeSections = DalTest.selectAllSection();
		for(Section s : listeSections){
			s.setQuestions(DalQuestion.selectQuestions(s));
		}
	}
	
	
	/***
	 * Accesseur permettant d'acceder à la liste de toutes les sections
	 * @return Vector(Section)
	 */
	public Vector<Section> getListeSection(){
		return listeSections;
	}
	
	
	/***
	 * Affecte une section à un test.
	 * @param Section : section
	 * @param Test : test
	 * @return Boolean : True si la section à bien été affectée au test, false dans l'autre cas.
	 */
	public boolean affecterSectionTest(Section section,Test test){
		if(DalTest.insertTests_Sections(test.getNom(), section.getNumero())){
			test.addSection(section);
			return true;
		}
		return false;
	}
	
	
	/***
	 * Enlever une section d'un test.
	 * @param Section : section
	 * @param Test : test
	 * @return Boolean : True si la section à bien été enlevée du test, false dans l'autre cas.
	 */
	public boolean enleverSectionTest(Section section,Test test){
		if(DalTest.deleteTest_Section(test.getNom(), section.getNumero())){
			test.supprSection(section);
			return true;
		}
		return false;
	}
	
	
	/***
	 * Renvoi la liste de question pour la section désirée.
	 * @param Section : section
	 * @return Vector(Section)
	 */
	public Vector<Question> questionParSection(Section section){
		return DalQuestion.selectQuestions(section);
	}
	
	
	/***
	 * Supprimer une question.
	 * @param Question : question
	 * @return Boolean : True si la question est bien supprimée, false dans l'autre cas.
	 */
	public boolean supprQuestion(Question question){
		for(Reponse r:question.getListeReponses()){
			DalQuestion.deleteReponse(r);
		}
		if(DalQuestion.deleteQuestion(question)){
			return true;
		}
		return false;
	}
	
	
	/***
	 * Enregistre une question avec ses réponses.
	 * @param Question : question
	 * @return Boolean : True si la question et toutes ses réponses ont bien été enregistrées, false dans l'autre cas.
	 */
	public boolean enregistrerQuestion(Question question){
		boolean b;
		if(DalQuestion.selectQuestion(question.getId())==null){
			b = DalQuestion.insertQuestion(question);
		}else{
			b = DalQuestion.updateQuestion(question);
		}
		
		for(Reponse r:question.getListeReponses()){
			if(DalQuestion.selectReponseQuestion(question.getId(), r.getNumero())==null){
				b = DalQuestion.insertReponse(r);
			}else{
				b = DalQuestion.updateReponse(r);
			}
		}
		return b;
	}
	
	
	
	
}


