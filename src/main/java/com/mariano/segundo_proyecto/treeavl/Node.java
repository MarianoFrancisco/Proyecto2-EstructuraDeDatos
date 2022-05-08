package com.mariano.segundo_proyecto.treeavl;

public class Node {

	 private int data;
	 private int balance;
	 private String card;
	 private String cardType;
	 private Node childRight;
	 private Node childLeft;
	 

	public Node() {
	}
	//creamos constructor
	public Node(String card, String cardType, int data) {
		this.data = data;
		this.card = card;
		this.cardType = cardType;
		this.balance = 0;
		this.childRight = null;
		this.childLeft = null;
	}
	//Creamos nuestros metodos get y set
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Node getChildRight() {
		return childRight;
	}
	public void setChildRight(Node childRight) {
		this.childRight = childRight;
	}
	public Node getChildLeft() {
		return childLeft;
	}
	public void setChildLeft(Node childLeft) {
		this.childLeft = childLeft;
	}
	public String createUserGraphic(){
        //establecemos un cambio de tipo en caso contengan cierto caracter para que lo reconozca graphviz
		String changeCardType=" ";
		if (cardType.equals("♥")) {
			changeCardType="&#9829;\"";
        }else if (cardType.equals("♠")) {
        	changeCardType="&#9824;\"";
        }else if (cardType.equals("♦")) {
        	changeCardType="&#9830;\"";
        }else if (cardType.equals("♣")) {
        	changeCardType="&#9827;\"";
        }
        if (childLeft==null&&childRight==null) {
            return String.valueOf("\""+card+changeCardType);
        }
        else{
            String data= "";            
            if (childLeft!=null) {
                data="\""+card+changeCardType+"->"+childLeft.createUserGraphic()+"\n";
            }
            if (childRight!=null) {
                data+="\""+card+changeCardType+"->"+childRight.createUserGraphic()+"\n";
            }
            return data;
        }
    }
}
