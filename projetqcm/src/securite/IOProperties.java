package securite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

public class IOProperties {
	
	public IOProperties() {
		super();
	}

	
	/**
	 * Cette méthode stocke le fichier Properties à l'emplacement spécifié
	 * 
	 * @param props Le fichier à stocker
	 * @param fileLocation L'emplacement où le fichier doit être stocké
	 * @param comments Commentaires à insérer en tête du fichier
	 * @throws FileNotFoundException si le fichier n'est pas trouvé
	 * @throws IOException si une erreur est survenue lors de l'écriture du fichier
	 */
	public void saveProperties(Properties props, String fileLocation, String comments) throws FileNotFoundException,
			IOException {
		OutputStream out = new FileOutputStream(fileLocation);
		props.store(out, comments);
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
	public Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFileLocation));
		return props;
	}

	
	/**
	 * 
	 * Cette méthode affiche cahque paire [clé,valuer] d'un fichier Properties
	 * 
	 * @param props Le fichier à afficher
	 */
	public void displayProperties(Properties fichier) {
		Iterator<Object> it = fichier.keySet().iterator();
		while (it.hasNext()) {
			String propertyName = (String) it.next();
			String propertyValue = fichier.getProperty(propertyName);
			System.out.println("Name : " + propertyName + " = " + propertyValue);
		}
	}

}
