package com.unrc.app.models;
import org.javalite.activejdbc.Model;

public class Answer extends Model {
	
	static {
		validatePresenceOf("description");
	}

	/**
	 * String representation of each attribute.
	 */
	
	public String description() {
		return this.getString("description");
	}

	public String createdAt() {
		return this.getString("created_at");
	}
        
    public String ownerName() {
		User u = User.findById(this.get("user_id"));
		return u.name();
	}
	
}
