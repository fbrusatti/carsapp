package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.List;
import java.util.LinkedList;

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
  
	public String id() {
		return this.getString("id");
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
