package com.unrc.app.models;

import org.javalite.activejdbc.Model;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.settings.*;
import static org.elasticsearch.node.NodeBuilder.*;

public class Post extends Model {
	
	static {
		validatePresenceOf("title", "description");
	}
	
	public void afterCreate() {
        
        Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("title",this.title());
        
        client.prepareIndex("posts", "post")
                .setSource(json)
                .execute()
                .actionGet();
        
        client.close();
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
