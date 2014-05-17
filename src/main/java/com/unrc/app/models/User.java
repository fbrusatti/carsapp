package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {

      validatePresenceOf("first_name", "last_name");
      validatePresenceOf("email");

      
  }
  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static User findByEmail(String email){
		return (findFirst("email = ?", email));
	}


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existUser(String email){
    	Boolean status=true;
    	if( User.first("email = ? ", email )==null){
    		return false;
    	}
    	return status;
    }
   
    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
	public static User createUser(String name, String lastname, String email){
    	User user=create("first_name", name, "last_name", lastname, "email", email);

        if(!existUser(email)){
        	user.saveIt();
        }
        return findByEmail(email);
    }


    //elimina un usuario de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteUser(String email){
    	if(existUser(email)){
        	User u=User.findByEmail(email);
        	u.delete();
        	return true;
        }
        else{return false;}
    }
              
}
