package securite;


 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
/**
 * 
 * @author Lefort steve
 * Liste des algorithmes de hashage java "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384","SHA-512"
 *
 */
 public class hashPassword
 {
	 	/*****************************************************
		*				Méthode de calcul du hash			 *
		******************************************************/
	 
	 /**
	  * Calcul du hash d'un mot de passe passé en parametre <br>
	  * Retourne le Hash en string, Algorithme par defaut : SHA-256 
	  * @category Securite
	  * @param String
	  * @return String
	  * @since 25/10/2008
	  * @throws Exception
	  * 
	  */
	 public String getHash(String password) throws Exception
	 {
		 try{
		  	String hex="";
		  	int h=-1;
		  	
		  	this.setMsgDigest(MessageDigest.getInstance(getAlgorithme()));
		  	this.getMsgDigest().update(password.getBytes());
		  	
		  	hash=this.getMsgDigest().digest();

		  	for(int i=0;i<hash.length;i++)
		  	{
		  		h=hash[i] & 0xFF;
		  		if (h<16) { hex += "0";} // fin si 
		  		hex+=Integer.toString(h,16).toUpperCase()+"";
		  		hex =hex + Byte.toString(hash[i]);
		  	}

		  	hex=hex.substring(0,14);
		  	return hex;
	  	 }
		 catch(NoSuchAlgorithmException x)
		 {
		  throw new Exception("Erreur du calcul de hashage");
		 }

	 }//fin getHash

  	/*****************************************************
	*					Attributs de classe				 *
	******************************************************/
	
  	private MessageDigest msgDigest;
 	private String algorithme="SHA-256";
 	private byte[] hash;

 	/*****************************************************
	*					Accesseurs						 *
	******************************************************/
 	public String getAlgorithme()
 	{
 		return algorithme;
 	}

 	private MessageDigest getMsgDigest()
 	{
 		return msgDigest;
 	}
 	
 	private void setMsgDigest(MessageDigest msg)
 	{
 		this.msgDigest=msg;
 	}

 	public void setAlgorithme(String string)
 	{
 		algorithme = string;
 	}

 }//fin de la classe 