package net.leadware.commons.io.env;

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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Classe représentant l'utilitaire de resolution de variables d'environnement 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 30 mai 2015 - 09:30:07
 */
public class ENVHelper {

	/**
	 * Delimiteur gauche Simple
	 */
	public static String SIMPLE_LEFT_DELIMITER = "${";
	
	/**
	 * Delimiteur droit simple
	 */
	public static String SIMPLE_RIGHT_DELIMITER = "}";
	
	/**
	 * Delimiteurs de variables d'environnement
	 */
	public static String ENV_LEFT_DELIMITER = "\\$\\{";
	public static String ENV_OPEN = "\\{";
	public static String ENV_CLOSE = "\\}";
	public static String ENV = "[\\w||\\.]+";
	public static String ENV_CHAIN_PATTERN = ENV_LEFT_DELIMITER + ENV + ENV_CLOSE;
	public static String SPLITTER_CHAIN = " |=|,|;|:|<|>|!|\\?|\\*|\\+|/|-|%|\\)|\\(";
    
	/**
	 * Methode permettant de verifier si un chemin contient des variables d'environnement
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainsENV(String expression) {
		
		// Si la chaine est vide : false
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne false
			return false;
		}
		
		// On split
		return isExpressionContainPattern(expression, ENV_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant d'obtenir la liste des sous-chaines representant des ENV
	 * @param expression Chaine a scruter
	 * @return	Liste des ENVs
	 */
	public static String[] getENVTokens(String expression) {
		
		// On retourne le tableau
		return extractToken(expression, ENV_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant de resoudre les variables d'environnement dans une chemin
	 * @param expression	Expression du chemin
	 * @return	Expression resolue
	 */
	public static String resolveEnvironmentsParameters(String expression) {

		// Si l'expression est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne null
			return null;
		}
		
		// Tant que la chaene traitee contient des ENVs
		while(isExpressionContainPattern(expression, ENV_CHAIN_PATTERN)) {

			// Obtention de la liste des ENVs
			String[] envs = extractToken(expression, ENV_CHAIN_PATTERN);

			// Parcours
			for (String env : envs) {
				
				String cleanEnv = env.replace(SIMPLE_LEFT_DELIMITER, "");
				cleanEnv = cleanEnv.replace(SIMPLE_RIGHT_DELIMITER, "");
								
				// Obtention de la valeur de la propriete
				String resolvedEnv = System.getProperty(cleanEnv, cleanEnv);
				
				// On remplace l'occurence courante par le nom de la variable
				expression = expression.replace(env, resolvedEnv);
			}
		}
		
		// On retourne l'expression
		return expression;
	}

	/**
	 * Methode permettant de verifier si un chemin contient des Fonctions
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainPattern(String expression, String pattern) {
		
		try {
			
			// Si la chaine est vide : false
			if(expression == null || expression.trim().length() == 0) {
				
				// On retourne false
				return false;
			}
			
			// Construction d'un Pattern
			Pattern regex = Pattern.compile(".*" + pattern + ".*");
			
			// On retourne le resultat
			return regex.matcher(expression).matches();
			
		} catch (PatternSyntaxException e) {
			
			// On leve l'exception relative
			throw new RuntimeException(pattern, e);
		}
	}

	/**
	 * Methode d'extraction de toutes les sous-chaines respectant le pattern donne
	 * @param expression	Expression mere
	 * @param pattern	Pattern a rechercher
	 * @return	Liste des sous-chaines respectant ce pattern
	 */
	public static String[] extractToken(String expression, String pattern) {
		
		// Si la chaine est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne null;
			return null;
		}
		
		// Si le pattern est null
		if(pattern == null) {
			
			// On retourne null;
			return null;
		}
		
		// On splitte par l'espace
		String[] spacePlitted = expression.split(SPLITTER_CHAIN);
		
		// Array des Tokens
		StringBuffer aTokens = new StringBuffer();
		
		// Un Index
		int index = 0;
		
		// On parcours le tableau
		for (String spaceToken : spacePlitted) {
			
			// Si le token ne respecte pas le pattern
			if(isExpressionContainPattern(spaceToken, pattern)) {

				// Si on est pas au premier
				if(index++ > 0) aTokens.append("@");
				
				// On ajoute
				aTokens.append(spaceToken);
			}
		}
		
		// On split la chaine originale avec ce pattern
		return aTokens.toString().split("@");
	}

}
