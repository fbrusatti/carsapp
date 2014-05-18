package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorclicle extends Model {
  static {
	  validatePresenceOf("id_vehicle","id_user","type","type_motor","boot_sistem","displacement");	  
  }
  
	
  //Id get vehicle owner
  public String getIdVehicle (){
	  return (this.getString("id_vehicle")); 
  }
  
  //get user
  public String getIdUser(){
	  return (this.getString("id_user"));
  }
  
  //get type
  public String getType(){
	  return (this.getString("type"));
  }
  
  //get type_motor
  public String getTypeMotor(){
	  return (this.getString("type_motor"));
  }
  //get boot_sistem
  public String getBootSitem(){
	  return (this.getString("boot_sistem"));
  } 
  
  //get displacement
  public String getDisplacement(){
	  return (this.getString("displacement"));
  }

}
