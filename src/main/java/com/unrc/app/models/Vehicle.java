package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {     
	 validatePresenceOf("brand", "model", "year","color","type");
  }

}
