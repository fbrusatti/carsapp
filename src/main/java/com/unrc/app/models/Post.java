package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
	static {
		validatePresenceOf("title", "description");
	}
	
	public String id() {
		return this.getString("id");
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
