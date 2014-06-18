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
  public void createUser (String fn, String ln, String email, String street, String address_number){
  	if (getBoolean("is_admin")){
  		User newuser = User.createIt("first_name", fn, "last_name", ln,"email",email,"is_admin",false);
      newuser.addAddress(street,address_number);
  	}
  }

  public void addAddress (String s, String n){
    Address a= Address.create("street",s,"address_number",n);
    this.add(a);
    this.saveIt();
  }

  public void addVehicle (String n, String m, String k){ //not finished, add truck, car, moto, or other
    Vehicle v= Vehicle.create("name", n,"model", m,"km",k );
    this.add(v);
    this.saveIt();
  }

  public void addPost (String price, String descr, Vehicle vehicle){
    Post p = Post.create("price",price,"description",descr);
    p.saveIt();
    vehicle.add(p);
    this.add(p);
    this.saveIt();
  }

  /*-------------------------------MOUSTACHE STUFF---------------------------------------*/
  public String name() {
    return this.getString("first_name") +" "+ this.getString("last_name");
  }
  
  public String email() {
    return this.getString("email");
  }

  public Integer id(){
    return this.getInteger("id");
  }

  public String address() {
    Address address = Address.findFirst("user_id = ?", this.id());
    return address.getString("street")+" "+address.getString("address_number");
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
