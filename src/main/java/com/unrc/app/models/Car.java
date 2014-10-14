package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.associations.NotAssociatedException;

public class Car extends Model {
	
	static {
		validatePresenceOf("capacity");
	}
	
	/**
	 * String representation of each attribute.
	 */

	public String capacity() {
		return this.getString("capacity");
	}
}
