package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name", "email");
  }

/* Before the invocation of the method, should check if is the admin who create the user*/
  public void createUser (String fn, String ln, String email){
  	if (getInteger("is_admin")==1){
  		User.createIt("first_name", fn, "last_name", ln,"email",email,"is_admin","0");
  	}
  }

  public String name() {
    return this.getString("first_name") +" "+ this.getString("last_name");
  }
  
  public String email() {
    return this.getString("email");
  }

}
