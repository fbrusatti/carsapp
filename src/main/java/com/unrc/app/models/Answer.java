package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Answer extends Model {
  static {

      validatePresenceOf("description");
   

      
  }
  //retorna el modelo Answer a partir de la busqueda en la bd a partir del contenido de la respuesta 
	public static Answer findByAnswer(int ident){
		return (findFirst("id = ?", ident));
	}


	//retorna un booleano verificando a partir del contenido de la pregunta si la pregunta existe
	public static Boolean existAnswer(int ident){
    	Boolean status=true;
    	if( Answer.first("id = ? ", ident )==null){
    		return false;
    	}
    	return status;
    }
   
    //a partir de la descripcion,del usuario y de la pregunta crea una respuesta nueva siempre y cuando ésta no exista en la bd
	public static Answer createAnswer(String descripcion, User usuario, Question pregunta){
    	Answer respuesta=create("description", descripcion, "id_users", usuario.getInteger("id"), "id_question", pregunta.getInteger("id"));
        respuesta.saveIt();
        
        return findByAnswer(respuesta.getInteger("id"));
    }


    //elimina una respuesta de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteAnswer(int ident){
    	if(existAnswer(ident)){
        	Answer a=Answer.findByAnswer(ident);
        	a.delete("id = ? ", a.getInteger("id"));
        	return true;
        }
        else{return false;}
    }

    public int id(){
        return this.getInteger("id");
    }

    public String description(){
        return this.getString("description");
    }

    public void afterCreate() {
    Map<String, Object> json = new HashMap<String, Object>();
    json.put("id", this.id());
    json.put("description", this.description());
    App.client().prepareIndex("answers", "answer")
              .setSource(json)
              .execute()
              .actionGet();
    }        
}
