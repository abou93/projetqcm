package modeles;

public class Type {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private int numero ;
	private String libelle;
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Type()
	{
		super();
		this.setLibelle("Choix dans reponse");
		this.setNumero(1);
		
	}
	
	public Type(int numero, String libelle)
	{
		this();
		this.setLibelle(libelle);
		this.setNumero(numero);
	}

		
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero) 
	{
		this.numero = numero;
	}

	public String getLibelle()
	{
		return libelle;
	}

	public void setLibelle(String libelle)
	{
		this.libelle = libelle;
	}
	
	
}
