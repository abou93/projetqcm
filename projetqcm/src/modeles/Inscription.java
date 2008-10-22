package modeles;

public class Inscription {
	
	/*****************************************************
	*						Attributs					 *
	******************************************************/
	
	private Test test;
	private String duree;
	private String mailformateur;
	
	
	/*****************************************************
	*						Constructeurs				 *
	******************************************************/
	
	public Inscription(){
		super();
	}
	
	public Inscription(Test test,String duree,String mailFormateur){
		this();
		this.test = test;
		this.duree = duree;
		this.mailformateur = mailFormateur;
	}
	
	
	/*****************************************************
	*						Accesseur					 *
	******************************************************/
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getMailformateur() {
		return mailformateur;
	}

	public void setMailformateur(String mailformateur) {
		this.mailformateur = mailformateur;
	}
	
	
}
