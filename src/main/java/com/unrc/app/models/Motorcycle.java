package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.associations.NotAssociatedException;

public class Motorcycle extends Model{
	
	static {
		validatePresenceOf("cylinder_capacity");
	}
	
	/**
	 * String representation of each attribute.
	 */
	
	public String cylinderCapacity() {
		return this.getString("cylinder_capacity");
	}
}
