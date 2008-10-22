package modeles;

import java.util.UUID;
import java.util.Vector;

public class Stagiaire {
	//Attributs
	private UUID id;
	private String nom;
	private String prenom;
	private String promotion;
	private String motDePasse;
	private Vector<Inscription> inscriptions;
	
	//Constructeurs
	public Stagiaire(){
		super();
		this.inscriptions = new Vector<Inscription>();
	}
	
	public Stagiaire(UUID id,String nom,String prenom,String promotion){
		this();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.promotion = promotion;
	}
	
	//Propri�t�s
	public UUID getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getPromotion() {
		return promotion;
	}

	public Vector<Inscription> getInscriptions() {
		return inscriptions;
	}
	
	public Inscription getInscriptions(Test test) {
		for (Inscription insc : this.getInscriptions()){
			if (insc.getTest() == test){
				return insc;
			}
		}
		return null;
	}
	
	//M�thodes
	public void changerMotDePasse(String nouveauMotDePasse){
		this.motDePasse = nouveauMotDePasse;
	}
	
	public void addInscription(Inscription insc){
		this.getInscriptions().add(insc);
	}
	
	public void supprInscription(Inscription insc){
		this.getInscriptions().remove(insc);
	}
	
	public String toString(){
		return (this.getNom() + " " + this.getPrenom());
	}
	
}
