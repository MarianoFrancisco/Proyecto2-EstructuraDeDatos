package com.mariano.segundo_proyecto.actionsTree;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mariano.segundo_proyecto.treeavl.TreeAvl;

public class Route {
	
	public Route() {

	}
	//creamos un map para obtener la ruta en preOrden
	public Map<String, String> routePreOrder(TreeAvl arbol) {
		//obtenemos la ruta en preOrden del arbol
		String route = "";
		Map<String, String> routePreOrder = new LinkedHashMap<String, String>();
		route = arbol.routePreOrden(arbol.getRoot());
		//Separamos nuestras rutas
		String[] split = route.split(" ");
		for (int i = 0; i < split.length; i++) {
			routePreOrder.put(""+i+"", split[i]);
		}
		return routePreOrder;
	}
	//creamos un map para obtener la ruta en inOrden
	public Map<String, String> routeInOrder(TreeAvl arbol) {
		//obtenemos la ruta en ineOrden del arbol
		String route = "";
		Map<String, String> routeInOrder = new LinkedHashMap<String, String>();
		route = arbol.routeInOrder(arbol.getRoot());
		//Separamos nuestras rutas
		String[] split = route.split(" ");
		for (int i = 0; i < split.length; i++) {
			routeInOrder.put(""+i+"", split[i]);
		}
		return routeInOrder;
	}
	//creamos un map para obtener la ruta en postOrden
	public Map<String, String> routePostOrder(TreeAvl arbol) {
		//obtenemos la ruta en ineOrden del arbol
		String route = "";
		Map<String, String> routePostOrder = new LinkedHashMap<String, String>();
		route = arbol.routePostOrder(arbol.getRoot());
		//Separamos nuestras rutas
		String[] split = route.split(" ");
		for (int i = 0; i < split.length; i++) {
			routePostOrder.put(""+i+"", split[i]);
		}
		return routePostOrder;
	}
}
