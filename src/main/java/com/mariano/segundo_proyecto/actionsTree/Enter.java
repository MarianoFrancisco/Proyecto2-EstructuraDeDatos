package com.mariano.segundo_proyecto.actionsTree;

import java.util.Map;
import com.google.gson.Gson;
import com.mariano.segundo_proyecto.treeavl.TreeAvl;

public class Enter {
	//caso de error
	int getErrorCardEnter = 0;
	public Enter() {

	}
	//obtenemos los valores que tendría una carta
	public int getCardValue(String cardType, String card) {
		int dataCard = 0;
		//Establecemos el corrimiento
		int slippingClover = 0;
		int slippingDiamond = 20;
		int slippingHeart = 40;
		int slippingPica = 60;
		//Establecemos valores numericos a letras
		if (card.equals("As")) {
			dataCard = 1;
		} else if (card.equals("K")) {
			dataCard = 13;
		}else if (card.equals("Q")) {
			dataCard = 12;
		} else if (card.equals("J")) {
			dataCard = 11;
		} else {
			try {
				dataCard = Integer.parseInt(card);
			} catch (Exception e) {
				getErrorCardEnter = 1;
			}
		}
		//verificamos que contenga cierto caracter para darle corrimiento
		switch (cardType) {
		case "♥":
			dataCard += slippingHeart;
			break;
		case "♠":
			dataCard += slippingPica;
			break;
		case "♦":
			dataCard += slippingDiamond;
			break;
		case "♣":
			dataCard += slippingClover;
			break;
		}
		return dataCard;
	}
	//agregamos carta con un json, esto al inicio
	public TreeAvl addCardStart(String dataJson) {
		//creamos variables, mapa y arbol
		int tmp = 0;
		Gson objectJson = new Gson();
		Map<?, ?> card = objectJson.fromJson(dataJson, Map.class);
		TreeAvl treeAddUser = new TreeAvl();
		while (card.containsKey("" + tmp + "")) {
			String cardTree = (String) card.get("" + tmp + "");
			//establecemos si tuviera numero como tambien letra
			if (cardTree.length() == 2) {
				//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
				String type = getCardType(cardTree);
				String letterCard = getPrincipalCard(cardTree);
				int dataCard = getCardValue(type, letterCard);
				treeAddUser.insertData(letterCard, type, dataCard);//insertamos dato
				if (treeAddUser.getErrores() == 1) {
					getErrorCardEnter = 1;
				}
			} else if (cardTree.length() == 3) {//si la carta tuviera 3 datos
				//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
				String type = getCardType(cardTree);
				String letterCard = cardTree.substring(0, cardTree.length() - 1);
				int dataCard = 0;
				dataCard = getCardValue(type, letterCard);
				treeAddUser.insertData(letterCard, type, dataCard);
				if (treeAddUser.getErrores() == 1) {
					getErrorCardEnter = 1;
				}
			} else {//si obtuvieramos algun tipo de error por medida
				getErrorCardEnter = 2;
			}
			tmp++;
		}
		return treeAddUser;
	}
	//agregamos un dato a la carta con un json y un arbol,esto luego de haber iniciado
	public TreeAvl addCardAfterStart(String dataJson, TreeAvl treeUser) {
		//creamos json y nuestro map, asi mismo la carta
		Gson objectJson = new Gson();
		Map<?, ?> card = objectJson.fromJson(dataJson, Map.class);
		String cardTree = (String) card.get("insert");
		if (cardTree.length() == 2) {//establecemos si tuviera numero como tambien letra
			//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
			String typeCard = cardTree.substring(cardTree.length() - 1);
			String letterCard = cardTree.substring(cardTree.length() - 2, cardTree.length() - 1);
			int dataCard = getCardValue(typeCard, letterCard);
			treeUser.insertData(letterCard, typeCard, dataCard);
			if (treeUser.getErrores() == 1) {
				getErrorCardEnter = 1;
			}
		} else if (cardTree.length() == 3) {//si la carta tuviera 3 datos
			//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
			String typeCard = cardTree.substring(cardTree.length() - 1);
			String letterCard = cardTree.substring(0, cardTree.length() - 1);
			int dataCard = 0;
			dataCard = getCardValue(typeCard, letterCard);
			treeUser.insertData(letterCard, typeCard, dataCard);
			if (treeUser.getErrores() == 1) {
				getErrorCardEnter = 1;
			}
		} else {
			getErrorCardEnter = 2;//si obtuvieramos algun tipo de error por medida
		}
		return treeUser;
	}
	//obtenemos el tipo de carta
	public String getCardType(String treeCard) {
		//establecemos el tipo de carta que se tendria retornando ese valor
		if (treeCard.length() == 2) {
			return treeCard.substring(treeCard.length() - 1);
		} else if (treeCard.length() == 3) {
			return treeCard.substring(treeCard.length() - 1);
		} else {
			getErrorCardEnter = 1;//si tuvieramos un error
			return null;
		}
	}
	//establecemos el dato principal de la cara, la letra
	public String getPrincipalCard(String treeCard) {
		//establecemos el tipo de carta que se tendria retornando ese valor
		if (treeCard.length() == 2) {//medida igual a 2
			return treeCard.substring(treeCard.length() - 2, treeCard.length() - 1);
		} else if (treeCard.length() == 3) {
			return treeCard.substring(0, treeCard.length() - 1);
		} else {
			getErrorCardEnter = 1;//si tuvieramos un error
			return null;
		}
	}
	//metodos get y set
	public int getErrorCardEnter() {
		return getErrorCardEnter;
	}
	public void setErrorCardEnter() {
		getErrorCardEnter = 0;
	}
}
