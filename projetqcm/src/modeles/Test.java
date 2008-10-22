package modeles;

import java.util.Vector;


public class Test {
	
	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String nom;
	private String temps;
	private int seuil;
	private Vector<Section> sections;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Test(){
		super();
		this.sections = new Vector<Section>();
	}
	
	public Test(String nom,String temps,int seuil){
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

	public String getTemps() {
		return temps;
	}

	public void setTemps(String temps) {
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
	
	public Section getSection(int numero){
		for (Section sec : this.getSections()){
			if(sec.getNumero() == numero){
				return sec;
			}
		}
		return null;
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
		return ("Test " + this.getNom() + "d'une durée de " + this.getTemps() + " minute(s). Seuil de réussite : " + this.getSeuil() + "%");
	}
	
}
