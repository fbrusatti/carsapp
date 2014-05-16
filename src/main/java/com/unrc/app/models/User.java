package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {

	static {
      validatePresenceOf("first_name", "last_name","email");
  	}
	
	//get First Name
	public string getFirstName(){
		return (this.getString("first_name"));
	}
	
	//get LastName
	public string getLastName (){
		return (this.getString("last_name"));
	}
	
	//get email
	public string getEmail(){
		return (this.getString("email"));
	}
	
	//find user
	public User findUser (String email){
		return User.findFirst("email = ?",email);
	}
	
	//delete user javalite.io/batch_operations
	public boolean deleteUser(String email){
		return User.findFirst("email = ?").delete();
	}
	
	
	}
	
	
	
}
