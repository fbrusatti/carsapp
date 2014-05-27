package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.associations.NotAssociatedException;

public class Truck extends Model {
	
	private long id_v;
	
	static {
		validatePresenceOf("length","height");
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
	public void setVehicleAttributes (String brand, String model, String year, String color) {
		Vehicle v = new Vehicle();
		v.set("brand", brand);
		v.set("model", model);
		v.set("year", year);
		v.set("color", color);
		v.set("type","truck");
		v.saveIt();
		id_v=(Long)v.getId();
	}

	@Override
	public boolean saveIt() {
		Vehicle v = Vehicle.findById(id_v);
        this.set("vehicle_id",v.getId());
		return super.saveIt();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void add(Model m) {
		if (m.getClass()==Post.class) {
			Vehicle v = Vehicle.findById(id_v);
			v.add(m);
		} else {
			throw new NotAssociatedException("vehicles",m.getTableName());
		}
	}
	
	public long getVehicleId() {
		return id_v;
	}
}
