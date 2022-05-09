package com.mariano.segundo_proyecto.actionsTree;

import com.mariano.segundo_proyecto.treeavl.TreeAvl;
import java.util.Map;
import com.google.gson.Gson;

public class Delete {
	//caso de error
	int getErrorCardDelete = 0,status406=0;
	public Delete() {
	}
	//obtener valor normal
	public int getCardValueSimple(String card) {
		int dataCard = 0;
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
				getErrorCardDelete = 1;
			}
		}
		return dataCard;
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
					getErrorCardDelete = 1;
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
		
		//agregamos un dato a la carta con un json y un arbol,esto luego de haber iniciado
		public TreeAvl deleteCard(String dataJson, TreeAvl treeUser) {
			//creamos json y nuestro map, asi mismo la carta
			status406=0;
			Gson objectJson = new Gson();
			Map<?, ?> card = objectJson.fromJson(dataJson, Map.class);
			int dataBefore13=0,dataBefore13Second=0,total=0;
			String typeCard13="",typeCard13Second="",letterCard13="",letterCard13Second="";
			int dataCard13=0,dataCard13Second=0;
			for (int i = 0; i < 2; i++) {
				String cardTree = (String) card.get("delete_"+(i+1));
				if(cardTree!=null) {
					
					if (cardTree.length() == 2) {//establecemos si tuviera numero como tambien letra
						//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
						String typeCard = cardTree.substring(cardTree.length() - 1);
						String letterCard = cardTree.substring(cardTree.length() - 2, cardTree.length() - 1);
						int dataCard = getCardValue(typeCard, letterCard);
						treeUser.searchInTree(dataCard, treeUser.getRoot());
						if(treeUser.getStatusCode404()==0) {
							treeUser.deleteData(letterCard, typeCard, dataCard);
						}
						if(i==0) {
							dataBefore13=getCardValueSimple(letterCard);
							typeCard13=typeCard;
							dataCard13=dataCard;
							letterCard13=letterCard;
						}else if(i==1) {
							dataBefore13Second=getCardValueSimple(letterCard);
							typeCard13Second=typeCard;
							dataCard13Second=dataCard;
							letterCard13Second=letterCard;
						}
					} else if (cardTree.length() == 3) {//si la carta tuviera 3 datos
						//obtenemos el simbolo de la carta,letra de la carta y a cuanto equivale
						String typeCard = cardTree.substring(cardTree.length() - 1);
						String letterCard = cardTree.substring(0, cardTree.length() - 1);
						int dataCard = 0;
						dataCard = getCardValue(typeCard, letterCard);
						treeUser.searchInTree(dataCard, treeUser.getRoot());
						if(treeUser.getStatusCode404()==0) {
							treeUser.deleteData(letterCard, typeCard, dataCard);
						}
						if (treeUser.getErrores() == 1) {
							getErrorCardDelete = 1;//System.out.println("error 2");
						}
						if(i==0) {
							dataBefore13=getCardValueSimple(letterCard);
							typeCard13=typeCard;
							dataCard13=dataCard;
							letterCard13=letterCard;
						}else if(i==1) {
							dataBefore13Second=getCardValueSimple(letterCard);
							typeCard13Second=typeCard;
							dataCard13Second=dataCard;
							letterCard13Second=letterCard;
						}
					} else {
						getErrorCardDelete = 2;//si obtuvieramos algun tipo de error por medida
					}
					if(i==1) {
						
						if(dataBefore13!=13) {
							if(dataBefore13Second!=13) {
								total=dataBefore13+dataBefore13Second;
								if(total!=13) {status406=1;
									if(treeUser.getStatusCode404()==0) {
										treeUser.insertData(letterCard13, typeCard13, dataCard13);//insertamos dato
										treeUser.insertData(letterCard13Second, typeCard13Second, dataCard13Second);//insertamos dato
									}
								}
									
							}
							if(dataBefore13Second==13) {status406=1;
								if(treeUser.getStatusCode404()==0) {
									treeUser.insertData(letterCard13, typeCard13, dataCard13);//insertamos dato
								}
							}
						}
						if(dataBefore13==13) {
							if(dataBefore13Second!=13) {//System.out.println("No pude eliminar el segundo, es distinto a 13");
							if(treeUser.getStatusCode404()==0) {
								treeUser.insertData(letterCard13Second, typeCard13Second, dataCard13Second);//insertamos dato
							}
							}
						}
						if(dataBefore13Second==13) {
							if(dataBefore13!=13) {//System.out.println("No pude eliminar el primero, es distinto a 13");
							if(treeUser.getStatusCode404()==0) {
								treeUser.insertData(letterCard13, typeCard13, dataCard13);//insertamos dato
							}
							
						}
						}
					}
				}else {
					if(i==0) {
						break;
					}else if(i==1) {
						if(dataBefore13==0) {
							status406=0;
						}else if(dataBefore13>0&&dataBefore13<13) {//System.out.println("No pude eliminar, primer dato no contiene 13 y segundo es un nulo");
						if(treeUser.getStatusCode404()==0) {
							treeUser.insertData(letterCard13, typeCard13, dataCard13);//insertamos dato
						}
						}
					}
				}
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
  			getErrorCardDelete = 1;//si tuvieramos un error
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
  			getErrorCardDelete = 1;//si tuvieramos un error
  			return null;
  		}
  	}
  //metodos get y set
  	public int getErrorCardDelete() {
  		return getErrorCardDelete;
  	}
  	public void setErrorCardDelete() {
  		getErrorCardDelete = 0;
  	}
  	public int getStatus406() {
  		return status406;
  	}
  	public void setStatus406(int status406) {
  		this.status406=status406;
  	}
}
