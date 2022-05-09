package com.mariano.segundo_proyecto.treeavl;

import java.io.FileWriter;
import java.io.PrintWriter;

public class TreeAvl {
	//Establecemos variables iniciales
	private Node root;
	private String level="";
	private int statusCode404=0;
	private int statusCode409=0;
	private String preOrder = "";
	private String inOrder="";
	private String postOrder="";
	private 
	//Si se logra eliminar navl
	int errores = 0;
	int duplicateCard=0;
	//creamos arbol y raiz
	public TreeAvl() {
		this.root = null;
	}
	public Node getRoot() {
		return root;
	}
	//Metodo para buscar si un nodo se encuentra ya establecido
	public Boolean searchInTree(int data,Node root) {
		if (root == null) {
			statusCode404=1;
			return false;
		} else if (root.getData() ==data ) {
			return true;
		} else if (root.getData() < data) {
			return searchInTree(data, root.getChildRight());
		} else {
			return searchInTree(data, root.getChildLeft());
		}
	}
	//Realizamos un metodo para lograr obtener el fe(Balance)
	private int getBalanceData(Node node) {
		if (node == null) {
			return -1;
		} else {
			return node.getBalance();
		}
	}
	//realizar una rotacion para la izquierda
	private Node rotationLeft(Node nodeRotation) {
		Node tmp = nodeRotation.getChildLeft();
		nodeRotation.setChildLeft(tmp.getChildRight());
		tmp.setChildRight(nodeRotation) ;
		//obtenemos el valor maximo
		nodeRotation.setBalance(Math.max(getBalanceData(nodeRotation.getChildLeft()), getBalanceData(nodeRotation.getChildRight())) + 1);
		tmp.setBalance(Math.max(getBalanceData(tmp.getChildLeft()), getBalanceData(tmp.getChildRight())) + 1);
		return tmp;
	}
	//realizar una rotacion para la derecha
	private Node rotacionDerecha(Node nodeRotation) {
		Node tmp = nodeRotation.getChildRight();
		nodeRotation.setChildRight(tmp.getChildLeft());
		tmp.setChildLeft(nodeRotation);
		//obtenemos el valor maximo
		nodeRotation.setBalance(Math.max(getBalanceData(nodeRotation.getChildLeft()), getBalanceData(nodeRotation.getChildRight())) + 1);
		tmp.setBalance(Math.max(getBalanceData(tmp.getChildLeft()), getBalanceData(tmp.getChildRight())) + 1);
		return tmp;
	}
	//realizar una rotacion doble para la izquierda
	private Node rotationDoubleLeft(Node nodeRotation) {
		Node tmp;
		nodeRotation.setChildLeft(rotacionDerecha(nodeRotation.getChildLeft()));
		tmp = rotationLeft(nodeRotation);
		return tmp;
	}
	//realizar una rotacion doble para la derecha
		private Node rotationDoubleRight(Node nodeRotation) {
			Node tmp;
			nodeRotation.setChildRight(rotationLeft(nodeRotation.getChildRight()));
			tmp = rotacionDerecha(nodeRotation);
			return tmp;
	}
	
	//Agregamos datos a nuestro arbol avl
	private Node insertTreeAvl(Node avlNewNode, Node avlSubTree) {
		Node newFather = avlSubTree;
		if (avlNewNode.getData() < avlSubTree.getData()) {
			if (avlSubTree.getChildLeft() == null) {
				avlSubTree.setChildLeft(avlNewNode);
			} else {
				avlSubTree.setChildLeft(insertTreeAvl(avlNewNode, avlSubTree.getChildLeft()));
				if ((getBalanceData(avlSubTree.getChildLeft()) - getBalanceData(avlSubTree.getChildRight()) == 2)) {
					if (avlNewNode.getData() < avlSubTree.getChildLeft().getData()) {
						newFather = rotationLeft(avlSubTree);
					} else {
						newFather = rotationDoubleLeft(avlSubTree);
					}
				}
			}
		} else if (avlNewNode.getData() > avlSubTree.getData()) {
			if (avlSubTree.getChildRight() == null) {
				avlSubTree.setChildRight(avlNewNode);
			} else {
				avlSubTree.setChildRight(insertTreeAvl(avlNewNode, avlSubTree.getChildRight()));
				if ((getBalanceData(avlSubTree.getChildRight()) - getBalanceData(avlSubTree.getChildLeft()) == 2)) {
					if (avlNewNode.getData() > avlSubTree.getChildRight().getData()) {
						newFather = rotacionDerecha(avlSubTree);
					} else {
						newFather = rotationDoubleRight(avlSubTree);
					}
				}
			}
		} else {
			duplicateCard=1;//si encontramos dato duplicado
		}
		//Refrescamos la altura
		if ((avlSubTree.getChildLeft() == null) && (avlSubTree.getChildRight() != null)) {
			avlSubTree.setBalance(avlSubTree.getChildRight().getBalance() + 1);
		} else if ((avlSubTree.getChildRight() == null) && (avlSubTree.getChildLeft() != null)) {
			avlSubTree.setBalance(avlSubTree.getChildLeft().getBalance() + 1);
		} else {
			avlSubTree.setBalance(Math.max(getBalanceData(avlSubTree.getChildLeft()), getBalanceData(avlSubTree.getChildRight())) + 1);
		}
		return newFather;
	}
	//agregar dato a arbol
	public void insertData(String card, String cardType, int data) {
		Node newNode = new Node(card, cardType, data);
		if (root == null) {
			root = newNode;
		} else {
			root = insertTreeAvl(newNode, root);
		}
	}
	/*realizar recorridos
    recorrido pre orden*/
	public String routePreOrden(Node root) {

		if (root != null) {
			// System.out.println(rootArbol.getData());
			preOrder += root.getCard() + root.getCardType() + " ";
			routePreOrden(root.getChildLeft());
			routePreOrden(root.getChildRight());
		}
		return preOrder;
	}
	//recorrido in orden
	public String routeInOrder(Node root) {
        if (root != null) {
            routeInOrder(root.getChildLeft());
            inOrder += root.getCard() + root.getCardType() + " ";
            routeInOrder(root.getChildRight());
        }
        return inOrder;
    }
	//recorrido post orden
    public String routePostOrder(Node root) {
        if (root != null) {
            routePostOrder(root.getChildLeft());
            routePostOrder(root.getChildRight());
            postOrder += root.getCard() + root.getCardType() + " ";
        }
        return postOrder;
    }
    //obtenemos datos de nivel solicitado
    public String getLevel(Node root, int levelUser) {
        if (root != null) {
            if (levelUser == 0) {
                level += root.getCard() + root.getCardType() + " ";
            }
            getLevel(root.getChildLeft(), levelUser - 1);
            getLevel(root.getChildRight(), levelUser - 1);
        }
        return level;
    }
    //obtenemos altura
    public int getHeight(Node root) {
        if (root != null) {
            int left = getHeight(root.getChildLeft()) + 1;
            int right = getHeight(root.getChildRight()) + 1;
            if (left > right) {
                return left;
            }
            return right;
        }
        return -1;
    }
    //limpeamos nuestros string de recorridos
	public void cleanAll() {
		preOrder = "";
		inOrder = "";
		postOrder = "";
	}
	//limpeamos el string de nivel
	public void cleanLevel() {
		level="";
	}
	//regresamos errores
	public int getErrores(){
		return errores;
	}
	//creamos un metodo para la eliminacion dentro del nodo
    private Node deleteTreeAvl(Node avlNewNode, Node avlSubTree) {
    	Node newFather = avlSubTree;
		if (avlNewNode.getData() < avlSubTree.getData()) {
			if (avlSubTree.getChildLeft() == null) {
				avlSubTree.setChildLeft(avlNewNode);
			} else {
				avlSubTree.setChildLeft(deleteTreeAvl(avlNewNode, avlSubTree.getChildLeft()));
			}
		} else if (avlNewNode.getData() > avlSubTree.getData()) {
			if (avlSubTree.getChildRight() == null) {
				avlSubTree.setChildRight(avlNewNode);
			} else {
				avlSubTree.setChildRight(deleteTreeAvl(avlNewNode, avlSubTree.getChildRight()));
			}
		} else {
			/*Empezamos eliminacion
			 * Caso de tener unicamente un hijo
			 * */
			if(avlSubTree.getChildLeft()==null||avlSubTree.getChildRight()==null) {
				Node nodeTmp=null;
				if(nodeTmp==avlSubTree.getChildLeft()) {//si el nodo nulo es el hijo izquierdo
					nodeTmp=avlSubTree.getChildRight();
				}else {//el nodo nulo es el hijo derecho
					nodeTmp=avlSubTree.getChildLeft();
				}
				//En caso de que no tuviera hijo, que es lo que buscamos
				if(nodeTmp==null) {
					newFather=null;
				}else {//En caso de que tuvieramos un hijo, Status Code 409
					//error por tener un hijo
					statusCode409=1;
				}
			}else {//Si tiene dos hijos
				//Status Code 409, error por tener dos hijos
				statusCode409=1;
			}
		}
		return newFather;
    }
    //Metodo para elminar dentro del arbol creado
  	public void deleteData(String card, String cardType, int data) {
  		Node newNode = new Node(card, cardType, data);
  		root = deleteTreeAvl(newNode, root);//eliminamos
      }
	//escritura de archivo Graphviz
    public String realizeGraphicGraphviz() {
        String stringGraphviz = "digraph G\n"
                + "{\n"
                + "    node[shape = circle]\n"
                + "    node[style = filled]\n"
                + "    node[fillcolor = \"#40CFFF\"]\n"
                + "    node[color = \"#0000FF\"]\n"
                + "    edge[color = \"#000000\"]\n";
        if (root != null) {
            stringGraphviz += root.createUserGraphic();
        }
        stringGraphviz += "\n}";
        return stringGraphviz;
    }
    //creamos archivo Dot
    private void getFileDot(String getRouteFile, String data) {
        FileWriter archive = null;
        PrintWriter printFile = null;
        try {
            archive = new FileWriter(getRouteFile);
            printFile = new PrintWriter(archive);
            printFile.write(data);
            printFile.close();
            archive.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (printFile != null) {
                printFile.close();
            }
        }
    }
    //Creamos imagen jpg
    public void createImageJpg() {
        try {//ejecutamos comando interno para poder realizar la conversion de dot a jpg
            getFileDot("treeAvlDot.dot", realizeGraphicGraphviz());
            ProcessBuilder processBuilder;
            processBuilder = new ProcessBuilder("dot", "-Tpng", "treeAvlDot.dot", "-o", "treeAvlImage.jpg");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }   
    //metodos get y set
    public int getDuplicateCard() {
    	return duplicateCard;
    }
    public void setDuplicateCard(int duplicateCard) {
    	this.duplicateCard = duplicateCard;
    }
    public int getStatusCode404() {
    	return statusCode404;
    }
    public void setStatusCode404(int statusCode404) {
    	this.statusCode404 = statusCode404;
    }
    public int getStatusCode409() {
    	return statusCode409;
    }
    public void setStatusCode409(int statusCode409) {
    	this.statusCode409 = statusCode409;
    }
}
