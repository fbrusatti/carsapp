package com.unrc.app.models;

import org.javalite.activejdbc.Model;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;

import static org.elasticsearch.node.NodeBuilder.*;

public class Post extends Model {
	
	static {
		validatePresenceOf("title", "description");
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
        json.put("title",this.title());
        
        client.prepareIndex("posts", "post")
                .setSource(json)
                .execute()
                .actionGet();
        
        node.close();
    }

	public String id() {
		return this.getString("id");
	}
	
	public String vehicle_id() {
		return this.getString("vehicle_id");
	}
	
	public String title() {
		return this.getString("title");
	}
	
	public String description() {
		return this.getString("description");
	}
	
	public String createdAt() {
		return this.getString("created_at");
	}

	public String ownerId() {
		return this.getString("user_id");
	}
	
	public String ownerName() {
		User u = User.findById(this.get("user_id"));
		return u.name();
	}
	
	
	
}
