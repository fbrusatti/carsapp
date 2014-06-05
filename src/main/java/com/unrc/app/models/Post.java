package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
	static {
		validatePresenceOf("title", "description");
	}
	
	public String title() {
		return this.getString("title");
	}
	
	public String description() {
		return this.getString("description");
	}
	
}
