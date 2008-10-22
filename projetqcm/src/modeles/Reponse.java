package modeles;

public class Reponse {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String texte;
	private boolean etat;
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Reponse()
	{
		super();
		this.setTexte("Nouvelle reponse");
		this.setEtat(false);
		
	}
	
	public Reponse(String untexte, boolean unetat)
	{
		this();
		this.setTexte(untexte);
		this.setEtat(unetat);
	}
	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public String getTexte() 
	{
		return texte;
	}
	
	public void setTexte(String texte) 
	{
		this.texte = texte;
	}
	
	public boolean isEtat() 
	{
		return etat;
	}
	
	public void setEtat(boolean etat) 
	{
		this.etat = etat;
	}
	
	
}
