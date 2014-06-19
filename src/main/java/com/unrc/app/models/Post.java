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

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;

public class Post extends Model {
	
	static {
		validatePresenceOf("title", "description");
	}
	
	/**
	 * Indexing a post
	 */
	public void afterCreate() {
        
        Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("title",this.title());
        json.put("ownerName",this.ownerName());
        json.put("ownerId",this.ownerId());

        client.prepareIndex("posts", "post",this.id())
                .setSource(json)
                .execute()
                .actionGet();
        
        client.close();
    }

    /**
	 * Deleting a post indexed
	 */
    public void beforeDelete() {
		
		Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        DeleteResponse response = client.prepareDelete("posts","post",this.id())
        							.execute()
        							.actionGet();
        client.close();

	}

	/**
	 * String representation of each attribute.
	 */
	
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
