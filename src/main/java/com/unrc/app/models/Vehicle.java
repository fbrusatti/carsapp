package com.unrc.app.models;

import java.util.List;
import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
	
	static {     
		validatePresenceOf("brand", "model", "year","color","type");
	}

	/**
	 * String representation of each attribute.
	 */

	public String id() {
		return this.getString("id");
	}	
	public String generalBrand() {
		return this.getString("brand")+" "+this.getString("model")+" "+this.getString("year");
	}
	
	public String color() {
		return this.getString("color");
	}
	
	public String type() {
		return this.getString("type");
	}
	
	/**
	 * Return true if this vehicle has not been posted
	 */
	public Boolean notPosted(){
		List<Post> posts = Post.where("vehicle_id = ?", this.getString("id"));
		return posts.isEmpty();
	}
        
}
