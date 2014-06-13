package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {
  static {

      validatePresenceOf("description");
   

      
  }
  //retorna el modelo Question a partir de la busqueda en la bd a partir del  identificador de la pregunta 
	public static Question findByQuestion(int ident){
		return (findFirst("id = ?", ident));
	}

    public static Question findByDescription(String preg){
        return (findFirst("description = ?", preg));
    }

	//retorna un booleano verificando a partir del identificador de la pregunta si la pregunta existe
	public static Boolean existQuestion(int ident){
    	Boolean status=true;
    	if( Question.first("id = ? ", ident )==null){
    		return false;
    	}
    	return status;
    }
   
    //a partir del descripcion y del usuario que realiza la pregunta crea una pegunta nueva siempre y cuando ésta no exista en la bd
	public static Question createQuestion(String descripcion, User usuario, Post post){
    	Question pregunta=create("description", descripcion, "id_users", usuario.getInteger("id"), "id_posts", post.getInteger("id"));
        pregunta.saveIt();
        return findByQuestion(pregunta.getInteger("id"));
    }


    //elimina una pregunta de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteQuestion(int ident){
        
    	if(existQuestion(ident)){
            Question a=Question.findByQuestion(ident);
            a.delete("id = ? ", a.getInteger("id"));
            return true;
        }
        else{return false;}
    }

    public String description(){
        return this.getString("description");
    }
     
    public int id(){
        return this.getInteger("id");
    }          
}
