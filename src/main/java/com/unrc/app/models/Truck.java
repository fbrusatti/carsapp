package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Truck extends Model {
  static {
	  validatePresenceOf("id_vehicle","id_user","brake_System","direction","capacity");	  
  }
 
	
  //Id get vehicle owner
  public String getIdVehicle (){
	  return (this.getString("id_vehicle")); 
  }
  
  //get id_user
  public String getIdUser(){
	  return (this.getString("id_user"));
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
