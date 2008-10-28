package modeles;

import java.util.UUID;
import java.util.Vector;

public class Question {

	/*****************************************************
	*						Attributs					 *
	******************************************************/

	private UUID id;
	private String enonce;
	private Type type;
	private String cheminImage;
	private Section section;
	private Vector<Reponse> reponses ;
	
	
	

	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	public Question()
	{
		super();
		this.setId(UUID.randomUUID());
		this.setEnonce("Nouvel enoncé");
		this.setType(new Type());
		this.setListesReponses( new Vector<Reponse>());
	}
	
	public Question(String enonce,Type type)
	{
		this();
		this.setEnonce(enonce);
		this.setType(type);
	}
	
	public Question(UUID id,String enonce,Type type)
	{
		this();
		this.setId(id);
		this.setEnonce(enonce);
		this.setType(type);
	}
	
	public Question(UUID id,String enonce,Type type,String chemin)
	{
		this();
		this.setId(id);
		this.setEnonce(enonce);
		this.setType(type);
		this.setCheminImage(chemin);
	}
		
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public UUID getId() 
	{
		return id;
	}
	
	public void setId(UUID id)
	{
		this.id = id;
	}
	
	public String getEnonce() 
	{
		return enonce;
	}
	
	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
	}
	
	public Type getType() 
	{
		return type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public String getCheminImage()
	{
		return cheminImage;
	}
	
	public void setCheminImage(String cheminImage)
	{
		this.cheminImage = cheminImage;
	}
	
	public Vector<Reponse> getListeReponses()
	{
		return reponses;
	}
	
	public void setListesReponses(Vector<Reponse> reponses)
	{
		this.reponses = reponses;
	} 
	
	public Section getSection() {
		return section;
	}

	
	public void setSection(Section section) {
		this.section = section;
	}
	
	/*****************************************************
	*						Methodes					 *
	******************************************************/
	
	
	public Reponse getReponseAt(int index)
	{
		return this.reponses.elementAt(index);
	}
	
	public int getNombreReponse()
	{
		return this.getListeReponses().size();
	}
	
	public void addReponse(Reponse reponse)
	{
		this.getListeReponses().add(reponse);
	}
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question : " + this.getEnonce()+ "\n");
		return builder.toString();
	}
		
	
}
