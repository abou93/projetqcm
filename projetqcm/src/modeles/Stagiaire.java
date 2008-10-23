package modeles;

import java.util.UUID;
import java.util.Vector;

public class Stagiaire {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private UUID id;
	private String nom;
	private String prenom;
	private Promotion promotion;
	private String motDePasse;
	private Vector<Inscription> inscriptions;
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Stagiaire(){
		super();
		this.inscriptions = new Vector<Inscription>();
	}
	
	public Stagiaire(String nom,String prenom,Promotion promotion){
		this();
		this.nom = nom;
		this.prenom = prenom;
		this.promotion = promotion;
	}
	
	public Stagiaire(UUID id,String nom,String prenom,Promotion promotion,String motDePasse){
		this();
		this.setId(id);
		this.nom = nom;
		this.prenom = prenom;
		this.promotion = promotion;
		this.setMotDePasse(motDePasse);
	}
	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public UUID getId() {
		return id;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Promotion getPromotion() {
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
	
	/*****************************************************
	*						Methodes					 *
	******************************************************/
	
	public void changerMotDePasse(String nouveauMotDePasse){
		this.setMotDePasse(nouveauMotDePasse);
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
