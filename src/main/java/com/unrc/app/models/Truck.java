package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.associations.NotAssociatedException;

public class Truck extends Model {
	
	private long id_v;
	
	static {
		validatePresenceOf("length","height");
	}
	
	/**
	 * String representation of each attribute.
	 */
	
	public String length() {
		return this.getString("length");
	}
	
	public String height() {
		return this.getString("height");
	}

}
