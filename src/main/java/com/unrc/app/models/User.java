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

	//return first name the an user
	public String getFirstName(){
		
		return (getString("first_name"));
	}

	//return last name the an user
	public String getlastName(){
		
		return (getString("last_name"));
	}

	//return email the an user
	public String getEmail(){
		
		return (getString("email"));
	}

	//change first name the an user
	public void setFirstName(String fn){
		set("first_name", fn);
		saveIt();
	}

	//change last name the an user
	public void setLastName(String ln){
		set("last_name", ln);
		saveIt();
	}

	//change email the an user
	public void setEmail(String e){
		set("email", e);
		saveIt();
	}

	//return count operation an user
	public int getCountOperation(){
		
		return (getInteger("count_operation"));
	}

	// set count operation where countOperation=countOPeration+1
	public void setCountOPeration(){
        int cant=this.getCountOperation()+1;
		set("count_operation", cant).saveIt();
	}

	public static Boolean existUser(String e){
    	Boolean status=true;
    	if( User.first("email = ? ", e )==null){
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

	//||--------------------------------------------------------------------||//
	//||--------------------------------------------------------------------||//
	//||  preguntarles a los chicos como calcular el promedio de los puntos ||//
	//||--------------------------------------------------------------------||//
	//||--------------------------------------------------------------------||//
              
}
