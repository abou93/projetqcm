package modeles;

public class Promotion {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String code;
	private String libelle;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Promotion(String code,String libelle){
		super();
		this.code = code;
		this.libelle = libelle;
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
	
	
}
