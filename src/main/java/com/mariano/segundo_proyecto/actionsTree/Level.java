package com.mariano.segundo_proyecto.actionsTree;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mariano.segundo_proyecto.treeavl.TreeAvl;

public class Level {
	public Level() {

	}
	//creamos un map para obtener los nodos de un nivel determinado
	public Map<String, String> getLevel(TreeAvl arbol,int levelUser) {
		//obtenemos el nivel del arbol
		String level = "";
		Map<String, String> getLevel = new LinkedHashMap<String, String>();
		level = arbol.getLevel(arbol.getRoot(),levelUser-1);
		//Separamos nuestros datos del nivel solicitado
		String[] split = level.split(" ");
		for (int i = 0; i < split.length; i++) {
			getLevel.put(""+i+"", split[i]);
		}
		return getLevel;
	}
}
