package com.unrc.app.models;
import com.unrc.app.App;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;

import static org.elasticsearch.node.NodeBuilder.*;
import org.javalite.activejdbc.Model;

public class User extends Model {

	static {
      validatePresenceOf("first_name", "last_name","email","pass");
  	}

	@Override
	public void afterCreate() {
            Map<String, Object> json = new HashMap<>();
            json.put("name", this.getFirstName() +" "+ this.getLastName());
            json.put("email", this.getEmail());

            App.client().prepareIndex("users", "user")
                        .setSource(json)
                        .execute()
                        .actionGet();

       //     node.close();
      }
        
        
        public String id() {
		return this.getString("id");
	}
	
	//get First Name
	public String getFirstName(){
		return (this.getString("first_name"));
	}
	
	//get LastName
	public String getLastName (){
		return (this.getString("last_name"));
	}
	
	//get email
	public String getEmail(){
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
	
