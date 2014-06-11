package com.unrc.app.models;
import org.javalite.activejdbc.Model;
public class Question extends Model {
	
	static {
		validatePresenceOf("description");
	}

	public String description() {
		return this.getString("description");
	}

	public String createdAt() {
		return this.getString("created_at");
	}
	
}
