package modeles;

public class Reponse {

	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private String texte;
	private boolean etat;
	private int numero;
	private Question question;
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Reponse()
	{
		super();
		this.setTexte("Nouvelle reponse");
		this.setEtat(false);
		this.setNumero(0);
		this.setQuestion(null);
		
	}
	
	public Reponse(String untexte, boolean unetat)
	{
		this();
		this.setTexte(untexte);
		this.setEtat(unetat);
	}
	
	public Reponse(String untexte,int numero, boolean unetat)
	{
		this(untexte,unetat);
		this.setNumero(numero);
	
	}
	
	public Reponse(String untexte, int numero, boolean unetat, Question question)
	{
		this(untexte,numero,unetat);
		this.setQuestion(question);
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
	
	public Question getQuestion() 
	{
		return question;
	}

	public void setQuestion(Question question) 
	{
		this.question = question;
	}
	
	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero) 
	{
		this.numero = numero;
	}

	
	/*****************************************************
	*						Méthodes					 *
	******************************************************/
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Reponse " + this.getNumero() + "\n");
		builder.append(this.getTexte()+ "\n");
		builder.append(this.isEtat());
		return builder.toString();
	}
	
	
}
