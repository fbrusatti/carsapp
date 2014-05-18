package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Car extends Model {
  static {
	  validatePresenceOf("id_vehicle","id_user","doors","version","transmission","direction");	  
  }
  
  //Id get vehicle owner
  public String getIdVehicle (){
	  return (this.getString("id_vehicle")); 
  }
  
  //get user
  public String getIdUser(){
	  return (this.getString("id_user"));
  }
  
  //get doors
  public String getDoors(){
	  return (this.getString("doors"));
  }
  
  //get version
  public String getVersion(){
	  return (this.getString("version"));
  }
  //get transmission
  public String getTransmission(){
	  return (this.getString("transmission"));
  } 
  
  //get direction
  public String getDirection(){
	  return (this.getString("direction"));
  }

}
