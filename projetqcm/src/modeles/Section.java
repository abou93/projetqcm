package modeles;

import java.util.Vector;

public class Section {
	
	/*****************************************************
	*						Attributs					 *
	******************************************************/

	private int numero;
	private String nom;
	private int nbrQuestion;
	private Vector<Question> questions;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Section()
	{
		super();
		this.setNumero(0);
		this.setNom("Nouvelle Section");
		this.setNbrQuestion(1);
		this.setQuestions(new Vector<Question>());
	}
	
	public Section(String nom)
	{
		this();
		this.setNom(nom);
	}
	
	public Section(String nom,int num)
	{
		this(nom);
		this.setNumero(num);
	}

	public Section(String nom,int num, int nbrQuestion)
	{
		this(nom,num);
		this.setNbrQuestion(nbrQuestion);
	}

	
		
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public int getNbrQuestion() {
		return nbrQuestion;
	}


	public void setNbrQuestion(int nbrQuestion) {
		this.nbrQuestion = nbrQuestion;
	}


	public Vector<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}
	
	
}
