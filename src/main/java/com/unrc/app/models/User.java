package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class User extends Model {
  static {

      validatePresenceOf("first_name", "last_name");
      validatePresenceOf("email");
      validatePresenceOf("passs");

      
  }
  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static User findByEmail(String email){
		return (findFirst("email = ?", email));
	}

 
    
    public static Boolean findUserByPass(String pass){
    Boolean status=true;
    if( User.first("passs = ?", pass)==null){
        status = false;
    }
    return status;
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
	public static User createUser(String name, String lastname, String email,String pass){
    	User user=create("first_name", name, "last_name", lastname, "email", email,"passs", pass);
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

    public static Boolean deleteUserAddress(String dir, int num, String ciudad, String email){
        User user = User.findByEmail(email);
        Address address = Address.findByAddress(dir,num,ciudad);
        user.remove(address);
        return true;
    }

    public int id(){
        return this.getInteger("id");
    }

    public String name() {
        return this.getString("first_name")+" "+this.getString("last_name");
    }
  
    public String email() {
        return this.getString("email");
    }

    public void afterCreate() {
    Map<String, Object> json = new HashMap<String, Object>();
    json.put("name", this.name());
    json.put("email", this.email());
    App.client().prepareIndex("users", "user")
                .setSource(json)
                .execute()
                .actionGet();
    }
}
