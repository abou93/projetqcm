package securite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

public class IOProperties 
{
	
	/*****************************************************
	*				Constructeur						 *
	******************************************************/
	
	public IOProperties() 
	{
		super();
	}

	
	/*****************************************************
	*					Méthodes 						 *
	******************************************************/
	
	/**
	 * Cette méthode stocke le fichier Properties à l'emplacement spécifié
	 * 
	 * @param fichier Le fichier à stocker
	 * @param fileLocation L'emplacement où le fichier doit être stocké
	 * @param comments Commentaires à insérer en tête du fichier
	 * @throws FileNotFoundException si le fichier n'est pas trouvé
	 * @throws IOException si une erreur est survenue lors de l'écriture du fichier
	 */
	public void saveProperties(Properties fichier, String fileLocation, String comments) throws FileNotFoundException,
			IOException {
		
		OutputStream out = new FileOutputStream(fileLocation);
		fichier.store(out, comments);
		out.flush();
		out.close();
	}

	
	/**
	 * Cette méthode lit un fichier Properties à l'emplacement spécifié
	 * 
	 * @param propertiesFileLocation L'emplacemnt du fichier
	 * @return Le fichier Properties chargé
	 * @throws FileNotFoundException si le fichier n'a pas été trouvé
	 * @throws IOException si une erreur est survenue durant la lecture
	 */
	public Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException
	{
		Properties fichier = new Properties();
		fichier.load(new FileInputStream(propertiesFileLocation));
		return fichier;
	}// Fin loadProperties

	
	/**
	 * Cette méthode affiche cahque paire [clé,valuer] d'un fichier Properties
	 * 
	 * @param props Le fichier à afficher
	 */
	public void displayProperties(Properties fichier) 
	{
		for(Object o : fichier.keySet())
		{
			String propertyValue = fichier.getProperty((String)o);
			System.out.println("Name : " + (String)o + " = " + propertyValue);
		}
	}// Fin displayProperties

}
