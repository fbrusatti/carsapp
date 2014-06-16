package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.Map;
import java.util.HashMap;
import org.elasticsearch.node.Node;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.index.IndexResponse;
import static org.elasticsearch.node.NodeBuilder.*;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name", "email");
  }

/* Before the invocation of the method, should check if is the admin who create the user*/
  public void createUser (String fn, String ln, String email){
  	if (getBoolean("is_admin")){
  		User.createIt("first_name", fn, "last_name", ln,"email",email,"is_admin",false);
  	}
  }
  /*-------------------------------MOUSTACHE STUFF---------------------------------------*/
  public String name() {
    return this.getString("first_name") +" "+ this.getString("last_name");
  }
  
  public String email() {
    return this.getString("email");
  }

  public String id(){
    return this.getString("id");
  }

  /*------------------------------ELASTIC SEARCH STUFF-----------------------------------*/

  public void afterSave() {
    //Starts the elastic search cluster
    Node node = nodeBuilder().local(true).clusterName("carsapp").node();
    Client client = node.client();

    //Index the just created user
    Map<String, Object> json = new HashMap<String, Object>();
    json.put("id",this.id());
    json.put("name",this.name());
    json.put("email",this.email());
    /**
    *TODO: add is_admin field
    **/

    IndexResponse response = client.prepareIndex("users","user")
                                    .setSource(json)
                                    .execute()
                                    .actionGet();


    node.close();
  }

}
