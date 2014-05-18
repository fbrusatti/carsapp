package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.associations.NotAssociatedException;
public class Car extends Model {

	private int id_v;
	
	static {
		validatePresenceOf("capacity");
	}
	/*
	Si extiene de Vehicle:
	static {
		super.validate();
	}
	
	
	@Override
	public boolean saveIt() {
		this.set("type","Car");
		return super.saveIt();
	}
	*/
	public void setVehicleAttributes (String brand, String model, String year,String color) {
		Vehicle v = new Vehicle();
		v.set("brand", brand);
		v.set("model", model);
		v.set("year", year);
		v.set("color", color);
		v.set("type","car");
		v.saveIt();
		id_v=v.getInteger(getIdName());
	}

	@Override
	public boolean saveIt() {
		//boolean addCar = super.saveIt();
		Vehicle v = Vehicle.findById(id_v);
        this.set("vehicle_id",v.getId());
        //System.out.println(addCar);
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
