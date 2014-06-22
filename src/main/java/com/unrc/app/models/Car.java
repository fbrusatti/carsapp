package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Car extends Model {
  static {
	  validatePresenceOf("doors","version","transmission","direction");	  
  }

  //get doors
  public String getDoors(){
	  return (this.getString("doors"));
  }
  
  public Integer getIdVehicle(){
      return (this.getInteger("vehicle_id"));
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
