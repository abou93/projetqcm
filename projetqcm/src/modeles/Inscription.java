package modeles;

import java.util.UUID;
import java.util.Vector;

public class Inscription {
	
	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private Test test;
	private String duree;
	private String mailformateur;
	private Stagiaire stagiaire;
	private Vector<UUID> tirage;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Inscription(){
		super();
		tirage = new Vector<UUID>();
	}
	
	public Inscription(Test test,String duree,String mailFormateur,Stagiaire stagiaire){
		this();
		this.test = test;
		this.duree = duree;
		this.mailformateur = mailFormateur;
		this.stagiaire = stagiaire;
	}
	
	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getMailformateur() {
		return mailformateur;
	}

	public void setMailformateur(String mailformateur) {
		this.mailformateur = mailformateur;
	}
	
	public Stagiaire getStagiaire(){
		return this.stagiaire;
	}
	
	public Vector<UUID> getTirage(){
		return this.tirage;
	}
	
	
	/*****************************************************
	*						Méthodes					 *
	******************************************************/
	
	public void addIdQuestion(Question question){
		this.tirage.add(question.getId());
	}
	
	public void supprIdQuestion(Question question){
		this.tirage.removeElement(question.getId());
	}
	
}
