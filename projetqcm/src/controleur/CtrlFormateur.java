package controleur;

import java.sql.Connection;
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
import dal.AccesBase;
import dal.DalInscription;
import dal.DalQuestion;
import dal.DalStagiaire;
import dal.DalTest;

public class CtrlFormateur {
	
	/*****************************************************
	*					Attributs priv�					 *
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
			//this.listeSectionsParTest.add(section);
			this.affecterSectionTest(section, this.getTestEnCour());
			}
	}
	
	public void supSectionListeSectionsParTest(Section section) {
		
		this.listeSectionsParTest.remove(section);
		DalTest.deleteTest_Section(this.getTestEnCour().getNom(), section.getNumero());
	}

	
	/***
	 * Accesseur permettant d'acc�der � la liste des promotions.
	 * @return Vector(Test)
	 */
	public Vector<Promotion> getListePromotions() {
		return listePromotions;
	}
	
	/***
	 * Accesseur permettant d'acc�der � la liste des tests.
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
	
		
	}
	

	/*****************************************************
	*					M�thodes						 *
	******************************************************/
	


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
	
	
	public boolean testConnexion(){
		Connection ctn = AccesBase.getConnection();
		if (ctn==null)	return false;
		else
		{
			AccesBase.deconnexionBase(ctn);
			return true;
		}
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
	public void chargerListePromotions(){
		Vector<Promotion> v = DalStagiaire.selectAllPromotions();
		if (v != null) listePromotions=v; 
	}
	
	
	/***
	 * Charge la liste des types depuis la base.
	 */
	public void chargerListeType(){
		Vector<Type> v = DalQuestion.selectAllType();
		if (v != null) listeTypes=v; 
	}
	
	
	/***
	 * Liste les stagiaires pour la promotion d�sir�e.
	 * @param Promotion : promotion
	 * @return Vector(Stagiaire)
	 */
	public Vector<Stagiaire> getStagiairesPromo(Promotion promotion){
		Vector<Stagiaire> v = DalStagiaire.selectStagiairesPromotion(promotion.getCode());
		if (v !=null) promotion.setListeStagiaires(v);
		return v;
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
		
		if(DalInscription.deleteTirage(stagiaire.getId(), test.getNom())){
			if(DalInscription.deleteInscription(stagiaire.getId(), test.getNom())){
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
	 * Charge la liste de section du test d�sir�.
	 * @param Test : test
	 */
	public void chargerListeSectionsParTest(Test test){
		
		listeSectionsParTest = DalTest.selectSectionByTest(test.getNom());
		for(int j= 0 ; j<listeSectionsParTest.size() ; j++){
			int i = listeSections.indexOf(listeSectionsParTest.elementAt(j));
			if (i!=-1) listeSectionsParTest.setElementAt(listeSections.elementAt(i),j);
		}
		
		test.setSections(listeSectionsParTest);
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
	 * @return Boolean : True si la section et ses question ont bien �t� supprim�es, false dans l'autre cas.
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
			for(Question q : s.getQuestions()){
				q.setListesReponses(DalQuestion.selectReponse(q));
			}
		}
	}
	
	
	/***
	 * Accesseur permettant d'acceder � la liste de toutes les sections
	 * @return Vector(Section)
	 */
	public Vector<Section> getListeSection(){
		return listeSections;
	}
	
	
	/***
	 * Affecte une section � un test.
	 * @param Section : section
	 * @param Test : test
	 * @return Boolean : True si la section � bien �t� affect�e au test, false dans l'autre cas.
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
	 * @return Boolean : True si la section � bien �t� enlev�e du test, false dans l'autre cas.
	 */
	public boolean enleverSectionTest(Section section,Test test){
		if(DalTest.deleteTest_Section(test.getNom(), section.getNumero())){
			test.supprSection(section);
			return true;
		}
		return false;
	}
	
	
	/***
	 * Renvoi la liste de question pour la section d�sir�e.
	 * @param Section : section
	 * @return Vector(Section)
	 */
	public Vector<Question> questionParSection(Section section){
		Vector<Question> questionsSection =  DalQuestion.selectQuestions(section);
		for(Question q : questionsSection){
			q.setSection(section);
		}
		return questionsSection;
	}
	
	
	/***
	 * Supprimer une question.
	 * @param Question : question
	 * @return Boolean : True si la question est bien supprim�e, false dans l'autre cas.
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
	 * Enregistre une question avec ses r�ponses.
	 * @param Question : question
	 * @return Boolean : True si la question et toutes ses r�ponses ont bien �t� enregistr�es, false dans l'autre cas.
	 */
	public boolean enregistrerQuestion(Question question){
		boolean b;
		question.setSection(this.getSectionEnCour());
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
	
	
	public void changerMotPasseStagiaire(Stagiaire stagiaire,String nouveauMotDePasse){
		if(nouveauMotDePasse!=null){
			stagiaire.changerMotDePasse(nouveauMotDePasse);
			DalStagiaire.updateStagiaire(stagiaire);
		}
	}
	
	
	/***
	 * R�cup�re les r�ponse d'une question.
	 * @param Question : question
	 * @return Vector(Reponse)
	 */
	public Vector<Reponse> getReponses(Question question){
		question.setListesReponses(DalQuestion.selectReponse(question));
		return question.getListeReponses();
		
	}
	
	

	public void deleteReponse(int index)
	{
		if(DalQuestion.deleteReponse(this.questionEnCour.getReponseAt(index))){
			this.questionEnCour.getListeReponses().remove(index);
			if ((index>0)&&(DalQuestion.updateNumeroReponse(this.getQuestionEnCour().getId(), index)))
				{
					for(int i = index ; i<this.getQuestionEnCour().getListeReponses().size();i++)
					{
						Reponse r = this.getQuestionEnCour().getReponseAt(i);
						r.setNumero(r.getNumero() -1);
					}
				}
		
		}
			
	}

	
	/***
	 * Change une question de section
	 * @param Question : question
	 * @param Section : ancienneSection
	 */
	public void changerQuestionSection(Question question,Section ancienneSection,Section nouvelleSection){
		nouvelleSection.addQuestion(question);
		ancienneSection.supprQuestion(question);
		question.setSection(nouvelleSection);
		DalQuestion.updateQuestion(question);
	}

	/***
	 * Verifie si la section est dans un test qui poss�de une (ou plusieurs) inscription
	 * @param Section : section
	 * @return Boolean : True si au moins un des tests dans lequel est la section poss�de au moins une inscription, false si tout les tests dans lesquels est la section ne poss�dent aucune inscription
	 */
	public boolean isInscriptionSection(Section section){
		return DalInscription.selectInscriptionSection(section);
	}
	
	public boolean isInscriptionTest(Test test){
		return DalInscription.selectInscriptionTest(test);
	}
	
}


