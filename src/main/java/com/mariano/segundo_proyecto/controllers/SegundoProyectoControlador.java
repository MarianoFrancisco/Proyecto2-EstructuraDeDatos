package com.mariano.segundo_proyecto.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mariano.segundo_proyecto.actionsTree.Delete;
import com.mariano.segundo_proyecto.actionsTree.Enter;
import com.mariano.segundo_proyecto.actionsTree.Level;
import com.mariano.segundo_proyecto.actionsTree.Route;
import com.mariano.segundo_proyecto.treeavl.*;

//Definimos su uso en localhost 8080
//Manejo de errores: https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
@RestController
@RequestMapping("/")

public class SegundoProyectoControlador {
	//creamos un arbol, metodo insertar, metodo eliminar
    TreeAvl treeAvlUser;
    Enter addElementTree = new Enter();
    Delete deleteElementTree = new Delete();
    /*Metodo POST
     * Recibimiento de un arbol inicial
     * */
    @RequestMapping(value = "/Game/start", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> insertarStart(@RequestBody String node) {
        //llamamos al arbol y definimos su estado correcto
    	try {
    		treeAvlUser = addElementTree.addCardStart(node);
            if(treeAvlUser.getDuplicateCard()==0) {
    			return new ResponseEntity<>("", HttpStatus.OK);//dato correcto
    		}else {
    			return new ResponseEntity<>("Status Code 406", HttpStatus.NOT_ACCEPTABLE);
    		}
    	}catch(Exception ex) {
    		return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
    	}
    }
    /*Metodo POST
     * Recibimiento para adicion de carta
     * */
    @RequestMapping(value = "/Game/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> insertar(@RequestBody String node) {
        if (treeAvlUser == null) {
            return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        } else {
        	try {
        		treeAvlUser.setDuplicateCard(0);
        		treeAvlUser = addElementTree.addCardAfterStart(node, treeAvlUser);
        		if(treeAvlUser.getDuplicateCard()==0) {
        			return new ResponseEntity<>("", HttpStatus.OK);//dato correcto
        		}else {
        			return new ResponseEntity<>("Status Code 406", HttpStatus.NOT_ACCEPTABLE);
        		}
        	}catch(Exception ex) {
        		return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        	}
        }
    }
    /*Metodo GET
     * Enviado a usuario los datos en preOrden,postOrden e inOrden
     * */
    @GetMapping(value = "/Game/avltree", produces = "application/json")
    public ResponseEntity<String> recorrido(@RequestParam(name = "transversal") String tipo) {
    	//creamos variables
        Route routeTree = new Route();
        Gson objectGson = new Gson();
        //condicional para verificar que no sea nulo
        if (tipo == null || treeAvlUser == null) {
            return new ResponseEntity<>("Status Code 400 ", HttpStatus.BAD_REQUEST);
        } else {//establecemos que si contiene ya sea preOrden,postOrden e inOrden, jale recorrido, contrario a esto error
        	//Recorrido en preOrden
            if (tipo.equals("preOrder")) {
            	/*Realizamos un map para el recorrido y devolvemos su conversion en string
            	 * */
                Map<String, String> preOrder = routeTree.routePreOrder(treeAvlUser);
                treeAvlUser.cleanAll();
                String convertPreOrder = objectGson.toJson(preOrder);
                return new ResponseEntity<>(convertPreOrder, HttpStatus.OK);
            }if (tipo.equals("inOrder")) {
            	/*Realizamos un map para el recorrido y devolvemos su conversion en string
            	 * */
                Map<String, String> inOrder = routeTree.routeInOrder(treeAvlUser);
                treeAvlUser.cleanAll();
                String convertInOrder = objectGson.toJson(inOrder);
                return new ResponseEntity<>(convertInOrder, HttpStatus.OK);
            }if (tipo.equals("postOrder")) {
            	/*Realizamos un map para el recorrido y devolvemos su conversion en string
            	 * */
                Map<String, String> postOrder = routeTree.routePostOrder(treeAvlUser);
                treeAvlUser.cleanAll();
                String convertPostOrder = objectGson.toJson(postOrder);
                return new ResponseEntity<>(convertPostOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
            }
        }
    }
    /*Metodo GET
     * Procesamiento de imagen y enviado de datos al usuario respecto a donde observar dicha imagen
     * */
    @GetMapping(value = "/Game/status-avltree", produces = "application/json")
    public ResponseEntity<String> graficar(HttpServletRequest solicitudServidor) {
    	try {
    		Gson objectGson = new Gson();
        	treeAvlUser.createImageJpg();
            treeAvlUser.realizeGraphicGraphviz();
            Map<String, String> getUrl = new LinkedHashMap<String, String>();
            String url = solicitudServidor.getRequestURL().toString()+"/state-tree";
            getUrl.put("path", url);
            url=objectGson.toJson(getUrl);
            return new ResponseEntity<>(url, HttpStatus.OK);
    	}catch(Exception ex) {
    		return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
    	}
    	
    }
    /*Metodo GET
     * Devolucion de imagen
     * */
    @GetMapping(value = "/Game/status-avltree/state-tree", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> stateTree() throws IOException {
        final ByteArrayResource getDataStream = new ByteArrayResource(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"\\treeAvlImage.jpg")));
        return ResponseEntity.status(HttpStatus.OK).contentLength(getDataStream.contentLength()).body(getDataStream);
   }
    /*Metodo GET
     * Enviado a usuario los datos de los nodos que se encuentran en un determinado nivel
     * */
    @GetMapping(value = "/Game/get-level", produces = "application/json")
    public ResponseEntity<String> getLevel(@RequestParam(name = "level") int levelUser) {
        Level level = new Level();
        Gson objectGson = new Gson();
      //condicional para verificar que no sea nulo o menor a 0
        if (levelUser< 0 || treeAvlUser == null) {
            return new ResponseEntity<>("Status Code 400 ", HttpStatus.BAD_REQUEST);
        } else {
        	try {//verificamos que el dato ingresado sea un numero y que se cuente con el nivel determinado
        		if(levelUser<=treeAvlUser.getHeight(treeAvlUser.getRoot())) {
        			/*Realizamos un map para el recorrido y devolvemos su conversion en string
                	 * */
                    Map<String, String> getLevel = level.getLevel(treeAvlUser, levelUser);
                    treeAvlUser.cleanLevel();
                    String convertGetLevel = objectGson.toJson(getLevel);
                    return new ResponseEntity<>(convertGetLevel, HttpStatus.OK);
        		}else {
                    return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        		}	
        	}catch(Exception ex){
                return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        	}
        }
    }
    
    /*Metodo DELETE
     * Recibimos una peticion para poder eliminar un nodo
     * */
    @DeleteMapping(value = "/Game/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteCard(@RequestBody String nodes) {
    	if (treeAvlUser == null) {
            return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        } else {//deleteElementTree
        	try {
        		treeAvlUser.setStatusCode404(0);
            	treeAvlUser.setStatusCode409(0);
            	treeAvlUser = deleteElementTree.deleteCard(nodes, treeAvlUser);
            	if(deleteElementTree.getStatus406()==0&&treeAvlUser.getStatusCode404()==0&&treeAvlUser.getStatusCode409()==0) {
            		return new ResponseEntity<>("", HttpStatus.OK);
            	}else{
            		if(treeAvlUser.getStatusCode404()==1) {
            			return new ResponseEntity<>("Status Code 404", HttpStatus.NOT_FOUND);
            		}else if(deleteElementTree.getStatus406()==1) {
            			return new ResponseEntity<>("Status Code 406", HttpStatus.NOT_ACCEPTABLE);
            		}else if(treeAvlUser.getStatusCode409()==1) {
            			return new ResponseEntity<>("Status Code 409", HttpStatus.CONFLICT);
            		}else {
            			return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
            		}
            	}
        	}catch(Exception ex) {
        		return new ResponseEntity<>("Status Code 400", HttpStatus.BAD_REQUEST);
        	}
        }
    }
}
