package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.List;
import java.util.LinkedList;

public class User extends Model {
  static {     
		validatePresenceOf("first_name", "last_name");
  }
  
  @Override
  public void add(Model m) {
	  if (m.getClass()==Car.class) {
		  Car c = (Car)m;
		  Vehicle v = Vehicle.findById(c.get("vehicle_id"));
		  this.add(v);
	  } else {
		  super.add(m);
	  }
  }
  
}
