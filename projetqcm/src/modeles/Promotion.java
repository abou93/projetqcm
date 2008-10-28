package modeles;

import java.util.Vector;

public class Promotion {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String code;
	private String libelle;
	private Vector<Stagiaire> listeStagiaires;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Promotion(String code,String libelle){
		super();
		this.code = code;
		this.libelle = libelle;
		this.listeStagiaires = new Vector<Stagiaire>();
	}

	
	/*****************************************************
	*						Accesseurs					 *
	******************************************************/
	
	public String getCode() {
		return code;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public String toString(){
		return this.getCode();
	}
	
	public Vector<Stagiaire> getListeStagiaires(){
		return this.listeStagiaires;
	}
	
	public void setListeStagiaires(Vector<Stagiaire> stagiaires){
		this.listeStagiaires = stagiaires;
	}
	
}
