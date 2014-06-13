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
  
	
	public void afterCreate() {
        
        Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        Map<String, Object> json = new HashMap<String, Object>();
        json.put("name",this.name());
        json.put("email",this.email());

        client.prepareIndex("users", "user")
                .setSource(json)
                .execute()
                .actionGet();
        
        client.close();
    }
	
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
