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
	 * Cette m�thode stocke le fichier Properties � l'emplacement sp�cifi�
	 * 
	 * @param props Le fichier � stocker
	 * @param fileLocation L'emplacement o� le fichier doit �tre stock�
	 * @param comments Commentaires � ins�rer en t�te du fichier
	 * @throws FileNotFoundException si le fichier n'est pas trouv�
	 * @throws IOException si une erreur est survenue lors de l'�criture du fichier
	 */
	public void saveProperties(Properties props, String fileLocation, String comments) throws FileNotFoundException,
			IOException {
		OutputStream out = new FileOutputStream(fileLocation);
		props.store(out, comments);
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
	public Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFileLocation));
		return props;
	}

	
	/**
	 * 
	 * Cette m�thode affiche cahque paire [cl�,valuer] d'un fichier Properties
	 * 
	 * @param props Le fichier � afficher
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
