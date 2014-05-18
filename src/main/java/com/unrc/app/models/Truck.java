package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Truck extends Model {
  static {
	  validatePresenceOf("id_vehicle","id_user","brake_System","direction","capacity");	  
  }
 
	
  //Id get vehicle owner
  public int getIdVehicle (){
	  return (this.getInt("id_vehicle")); 
  }
  
  //get id_user
  public int getIdUser(){
	  return (this.getInt("id_user"));
  }
  
  //get brake_system
  public string getbrakeSystem(){
	  return (this.getString("brake_system"));
  }
  
  //get direction
  public String getTypeMotor(){
	  return (this.getString("direction"));
  }
  //get capacity
  public int getBootSitem(){
	  return (this.getString("capacity"));
  } 
}
