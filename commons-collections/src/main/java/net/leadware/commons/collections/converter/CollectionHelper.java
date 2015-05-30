package net.leadware.commons.collections.converter;

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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe utilitaire pour la convertion des collections 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 30 mai 2015 - 09:29:30
 */
public class CollectionHelper {
	
	/**
	 * Methode de split d'une liste en un nombre donne de sous liste
	 * @param list	Liste de base
	 * @param subListNumber	Nombre de sous-listes
	 * @return	Liste splittee
	 */
	public static <T> List<List<T>> splitList(List<T> list, int subListNumber) {
		
		// Liste a retourner
		List<List<T>> splitted = new ArrayList<List<T>>();
		
		// Si la liste est vide
		if(list == null || list.size() == 0) return splitted;
		
		// Cas specifique du nombre de sous liste inferieur a 1
		if(subListNumber <= 1) {
			
			// Ajout de la liste courante a la liste des sous liste
			splitted.add(list);
			
			//retour de la liste des sous liste
			return splitted;
		}
		
		// Taille de la liste
		int listSize = list.size();
		
		// Taille de la sous liste
		int subListSize = Math.max(listSize / (subListNumber - 1), 1);
		
		// Parcours de la liste a splitter
		for(int i = 0; i < listSize; i += subListSize){
			
			// Ajout de la sous-liste
			splitted.add(new ArrayList<T>(list.subList(i, Math.min(listSize, i + subListSize))));
		}
		
		// On retourne la liste des sous-liste
		return splitted;
	}
	

	/**
	 * Methode de conversion d'une collection en Ensemble
	 * @param <T>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Ensemble converti
	 */
	public static <T> Set<T> convertCollectionToSet(Collection<T> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<T>(collection);
	}

	/**
	 * Methode de conversion d'une collection en Liste
	 * @param <T>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Liste converti
	 */
	public static <T> List<T> convertCollectionToList(Collection<T> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne la Liste
		return new ArrayList<T>(collection);
	}
	
	/**
	 * Methode de conversion d'une MAP en Collection
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Collection convertie
	 */
	public static <K, T> Collection<T> convertMapToCollection(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return map.values();
	}
	
	/**
	 * Methode de conversion d'une MAP en Liste
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Liste convertie
	 */
	public static <K, T> List<T> convertMapToList(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new ArrayList<T>(map.values());
	}

	/**
	 * Methode de conversion d'une MAP en Set
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Ensemble convertie
	 */
	public static <K, T> Set<T> convertMapToSet(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<T>(map.values());
	}
	
	/**
	 * Méthode de conversion d'un tableau de T en Set
	 * @param <T>	Parametre de type de contenu
	 * @param objects	Tableau a convertir
	 */
	public static <T> Set<T> convertArrayToSet(T...objects) {
		
		// Si le tableau est null
		if(objects == null) return null;
		
		// Le Set
		Set<T> set = new HashSet<T>();
		
		// Parcours du tableau
		for(T object : objects) set.add(object);
		
		// On retourne le set
		return set;
	}

	/**
	 * Méthode de conversion d'un tableau de T en List
	 * @param <T>	Parametre de type de contenu
	 * @param objects	Tableau a convertir
	 */
	public static <T> List<T> convertArrayToList(T...objects) {
		
		// Si le tableau est null
		if(objects == null) return null;
		
		// Le Set
		List<T> list = new ArrayList<T>();
		
		// Parcours du tableau
		for(T object : objects) list.add(object);
		
		// On retourne le List
		return list;
	}
}
