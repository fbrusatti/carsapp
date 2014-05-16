package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {

      validatePresenceOf("first_name", "last_name");
      validatePresenceOf("email");

      
  }
  //Return model of an user that find in basedata for the email
	public static User findByEmail(String email){
		return (findFirst("email = ?", email));
	}

	public static Boolean existUser(String email){
    	Boolean status=true;
    	if( User.first("email = ? ", email )==null){
    		return false;
    	}
    	return status;
    }
   
    // create an user after verify if exist the email , if not exist save user.
    //after return model
	public static User createUser(String name, String lastname, String email){
    	User user=create("first_name", name, "last_name", lastname, "email", email);

        if(!existUser(email)){
        	user.saveIt();
        }
        return findByEmail(email);
    }

    public static Boolean deleteUser(String email){
    	if(existUser(email)){
        	User.delete("email ?", email);
        	return true;
        }
        else{return false;}
        
        
    }
              
}
