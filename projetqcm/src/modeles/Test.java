package modeles;

import java.util.Vector;


public class Test {
	
	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String nom;
	private int temps;
	private int seuil;
	private Vector<Section> sections;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Test(){
		super();
		this.sections = new Vector<Section>();
	}
	
	public Test(String nom,int temps,int seuil){
		this();
		this.setNom(nom);
		this.setTemps(temps);
		this.setSeuil(seuil);
	}

	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public int getSeuil() {
		return seuil;
	}

	public void setSeuil(int seuil) {
		this.seuil = seuil;
	}

	public Vector<Section> getSections() {
		return sections;
	}
	
	public Section getSection(int index){
			return this.getSections().elementAt(index);
	}

	public void setSections(Vector<Section> sec){
		this.sections=sec;
	}
		
	
	
	/*****************************************************
	*						Methodes					 *
	******************************************************/
	
	public void addSection(Section sec){
		this.getSections().add(sec);
	}
	
	public void supprSection(Section sec){
		this.getSections().remove(sec);
	}
	
	public String toString(){
		return (this.getNom());
	}
	
}
