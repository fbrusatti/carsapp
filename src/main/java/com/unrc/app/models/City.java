package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class City extends Model {
	static {
		validatePresenceOf("name");
	}
	
	public String id() {
		return this.getString("id");
	}
	
	public String name() {
		return this.getString("name");
	}
}
