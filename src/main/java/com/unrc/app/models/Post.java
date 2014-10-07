package com.unrc.app.models;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;

import static org.elasticsearch.node.NodeBuilder.*;

import org.javalite.activejdbc.Model;
public class Post extends Model {
	  static {
	      validatePresenceOf("title","description","price");
	  }

	  
	  public void afterSave() {
                Node node = org.elasticsearch.node
                                          .NodeBuilder
                                          .nodeBuilder()
                                          .clusterName("carsapp")
                                          .local(true)
                                          .node();

                Client client = node.client();

                Map<String, Object> json = new HashMap<String, Object>();
                json.put("title", this.getTitle());

                client.prepareIndex("posts", "post")
                            .setSource(json)
                            .execute()
                            .actionGet();

                node.close();
        }
          
        public String id() {
            return this.getString("id");
	}
	  
	  //get title
	  public String getTitle (){
		  return (this.getString("title"));
	  }
	  
	  //get description
	  public String getDescription(){
		  return (this.getString("description"));
	  }
	  
	  //get price
	  public String getPrice(){
		  return (this.getString("price"));
	  }
          
          public String creatorPost (){
              return (this.getString("user_id"));
          }
          
          public Integer getVehicle(){
              return (this.getInteger("vehicle_id"));
          }
	  
}
