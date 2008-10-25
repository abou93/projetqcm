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
	*					M�thodes 						 *
	******************************************************/
	
	/**
	 * Cette m�thode stocke le fichier Properties � l'emplacement sp�cifi�
	 * 
	 * @param fichier Le fichier � stocker
	 * @param fileLocation L'emplacement o� le fichier doit �tre stock�
	 * @param comments Commentaires � ins�rer en t�te du fichier
	 * @throws FileNotFoundException si le fichier n'est pas trouv�
	 * @throws IOException si une erreur est survenue lors de l'�criture du fichier
	 */
	public void saveProperties(Properties fichier, String fileLocation, String comments) throws FileNotFoundException,
			IOException {
		
		OutputStream out = new FileOutputStream(fileLocation);
		fichier.store(out, comments);
		out.flush();
		out.close();
	}

	
	/**
	 * Cette m�thode lit un fichier Properties � l'emplacement sp�cifi�
	 * 
	 * @param propertiesFileLocation L'emplacemnt du fichier
	 * @return Le fichier Properties charg�
	 * @throws FileNotFoundException si le fichier n'a pas �t� trouv�
	 * @throws IOException si une erreur est survenue durant la lecture
	 */
	public Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException
	{
		Properties fichier = new Properties();
		fichier.load(new FileInputStream(propertiesFileLocation));
		return fichier;
	}// Fin loadProperties

	
	/**
	 * Cette m�thode affiche cahque paire [cl�,valuer] d'un fichier Properties
	 * 
	 * @param props Le fichier � afficher
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
