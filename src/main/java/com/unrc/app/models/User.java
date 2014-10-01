package com.unrc.app.models;

import org.javalite.activejdbc.Model;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.settings.*;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;

import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;

import static org.elasticsearch.node.NodeBuilder.*;

public class User extends Model {
	
	static {     
		validatePresenceOf("first_name", "last_name");
	}
  
	@Override
	public void add(Model m) {
		if (m.getClass()==Car.class) {
			Car c = (Car)m;
			Vehicle v = Vehicle.findById(c.get("vehicle_id"));
			this.add(v);
		} else {
			super.add(m);
		}
	}
  
	/**
	 * Indexing a user
	 */
	protected void afterCreate() {
        
        Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        Map<String, Object> json = new HashMap<String, Object>();
        json.put("name",this.name());
        json.put("email",this.email());

        client.prepareIndex("users", "user",this.id())
                .setSource(json)
                .execute()
                .actionGet();
        
        client.close();
    }
	
	/**
	 * Editing a user indexed
	 */
	protected void afterSave() {
		
		Client client = new TransportClient()
							.addTransportAddress(new InetSocketTransportAddress("localhost",9300));

		Map<String, Object> json = new HashMap<String, Object>();
        json.put("name",this.name());
        json.put("email",this.email());

    	UpdateResponse response = client.prepareUpdate("users","user",this.id())
    							.setDoc(json)
    							.setRefresh(true)
                				.execute()
                				.actionGet();

        client.close();
	}
	
	/**
	 * Deleting a user indexed
	 */
	protected void beforeDelete() {
		
		Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        DeleteResponse response = client.prepareDelete("users","user",this.id())
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

	public String firstName() {
		return this.getString("first_name");
	}
       
    public String lastName() {
		return this.getString("last_name");
	}

	public String name() {
		return this.get("first_name")+" "+this.get("last_name");
	}
	
	public String email() {
		return this.getString("email");
	}
	
	public String mobile() {
		return this.getString("mobile");
	}
	
	public String telephone() {
		return this.getString("telephone");
	}
	
	public String address() {
		return this.getString("address");
	}
	
	public String city() {
		return this.parent(City.class).name();
	}
	
}
