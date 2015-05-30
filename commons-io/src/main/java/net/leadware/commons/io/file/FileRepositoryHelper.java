package net.leadware.commons.io.file;

/*
 * #%L
 * Commons Tools
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 - 2015 Leadware
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Classe representant l'outil d'aide a l'acces aux fichier de Configuration 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 30 mai 2015 - 09:30:17
 */
public class FileRepositoryHelper {
	
	/**
	 * Separateur de chemin de fichier
	 */
	protected static String PATH_SEPARATOR = File.separator;
	
	/**
	 * Methode de chargement d'un fichier de proprietes
	 * @param filePath Chemin du fichier tenant compte de son emplacement 	
	 * @return Proprietes chargees
	 */
	public static Properties loadPropertiesFileFromClasspath(String filePath) {
		
		// Si le chemin est vide
		if(filePath == null || filePath.trim().isEmpty())
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes: Le chemin du fichier n'est pas renseigne");
		
		// Chemin complet
		String completeFilePath = filePath.trim();
		
		// Stream sur le fichier
		InputStream stream = FileRepositoryHelper.class.getClassLoader().getResourceAsStream(completeFilePath);
		
		// Si le stream est null
		if(stream == null) 
			
			// On leve une exception
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes: Le fichier n'existe pas dans le classpath");
		
		// On retourne les proprietes
		return loadPropertiesFileFromStream(stream);
	}
	
	/**
	 * Methode de chargement d'un fichier de proprietes
	 * @param filePath Chemin du fichier tenant compte de son emplacement 	
	 * @return Proprietes chargees
	 */
	public static Properties loadPropertiesFile(String filePath) {
		
		// Si le chemin est vide
		if(filePath == null || filePath.trim().isEmpty())
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes: Le chemin du fichier n'est pas renseigne");
		
		// Chemin complet
		String completeFilePath = filePath.trim();
		
		// Stream sur le fichier
		InputStream stream = null;
		
		try {
			
			// Chargement du Stream sur le fichier
			stream = new FileInputStream(completeFilePath);
			
		} catch (FileNotFoundException e) {
			
			// On relance
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes: Le chemin du fichier n'existe pas", e);
		}
		
		// On retourne les proprietes
		return loadPropertiesFileFromStream(stream);
	}
	
	/**
	 * Methode d'enregistrement des proprietes dans un fichier
	 * @param properties	Proprietes a enregistrer
	 * @param filePath	Chemin complet du fichier
	 */
	public static void saveProperties(Properties properties, String filePath) {
		
		// Si les proprietes sont nulle
		if(properties == null) 
			throw new RuntimeException("Erreur lors de l'ecriture du fichier de proprietes: La liste des proprietes est nulle");
		
		
		// Si le chemin est vide
		if(filePath == null || filePath.trim().isEmpty())
			throw new RuntimeException("Erreur lors de l'ecriture du fichier de proprietes: Le chemin du fichier n'est pas renseigne");
		
		// Chemin complet
		String completeFilePath = filePath.trim();
		
		// Objet File sur le fichier
		File file = new File(completeFilePath);
		
		// Chemin parent
		File parent = file.getParentFile();
		
		// Si le chemin parent n'existe pas
		if(!parent.exists()) parent.mkdirs();
		
		// Stream de sortie
		FileOutputStream outputStream = null;
		
		try {

			// Stream de sortie
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			
			// On relance
			throw new RuntimeException("Erreur lors de l'ecriture du fichier de proprietes: Le chemin du fichier est incorrect", e);
		}
		
		try {

			// Enregistrement
			properties.store(outputStream, "IP Configurations");
			
		} catch (IOException e) {
			
			// On relance
			throw new RuntimeException("Erreur lors de l'ecriture du fichier de proprietes", e);
		}
	}
	
	/**
	 * Methode permettant de verifier qu'un fichier existe dans le classpath
	 * @param path	Chemin du fichier
	 * @return	Etat d'existence
	 */
	public static boolean isFileExist(String path) {
		
		// Si le chemin est vide
		if(path == null || path.trim().isEmpty()) return false;

		// Resultat
		boolean exist = false;
		
		// Stream de sortie
		InputStream stream = null;
		
		try {

			// Tentative de chargement de la ressource
			stream = FileRepositoryHelper.class.getClassLoader().getResourceAsStream(path.trim());
			
			// On positionne l'etat d'existence
			exist = true;
			
		} catch (Exception e) {
			
		} finally {
			
			// On referme le stream
			closeStream(stream);
		}
		
		// On retourne le resultat
		return exist;
	}
	
	/**
	 * Methode de chargement d'un fichier de proprietes a partir d'un stream
	 * @param stream Stream a charger	
	 * @return Proprietes chargees
	 */
	public static Properties loadPropertiesFileFromStream(InputStream stream) {
		
		// Si le chemin est vide
		if(stream == null)
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes: Le stream est null");
		
		// Proprietes
		Properties properties = new Properties();
		
		try {
			
			// Chargement du fichier de propriete
			properties.load(new InputStreamReader(stream));
			
		} catch (IOException e) {
			
			// Fermeture
			closeStream(stream);
			
			// On relance
			throw new RuntimeException("Erreur lors du chargement du fichier de proprietes", e);
		}

		// Fermeture
		closeStream(stream);
		
		// On retourne les proprietes
		return properties;
	}
	
	/**
	 * Methode de fermeture d'un stream
	 * @param stream	Stream a fermer
	 */
	protected static void closeStream(InputStream stream) {
		
		// Si le stream est null
		if(stream == null) return;
		
		try {
			
			// Tentative de fermeture
			stream.close();
			
		} catch (IOException e) {}
	}
}
