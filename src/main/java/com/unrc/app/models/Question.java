package com.unrc.app.models;
import org.javalite.activejdbc.Model;
public class Question extends Model {
	
	static {
		validatePresenceOf("description");
	}
        public String id() {
		return this.getString("id");
	}

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
