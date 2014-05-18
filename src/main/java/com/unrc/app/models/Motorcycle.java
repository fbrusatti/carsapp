package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorcycle extends Model{
private long id_v;
	
	static {
		validatePresenceOf("cylinder_capacity");
	}
	
	/*
	Si extiende de Vehicle:
	static {
		super.validate();
	}
	
	
	@Override
	public boolean saveIt() {
		this.set("type","Truck");
		return super.saveIt();
	}
	*/
	public void setVehicleAttributes (String brand, String model, String year) {
		Vehicle v = new Vehicle();
		v.set("brand", brand);
		v.set("model", model);
		v.set("year", year);
		v.saveIt();
		id_v=(Long)v.getId();
	}

	public long getVehicleId() {
		return id_v;
	}
}
