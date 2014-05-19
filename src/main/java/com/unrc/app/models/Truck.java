package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Truck extends Model {
  static {
	  validatePresenceOf("brake_System","direction","capacity");	  
  }
 
  //get brake_system
  public String getbrakeSystem(){
	  return (this.getString("brake_system"));
  }
  
  //get direction
  public String getTypeMotor(){
	  return (this.getString("direction"));
  }
  //get capacity
  public String getBootSitem(){
	  return (this.getString("capacity"));
  } 
}
