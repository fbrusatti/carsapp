package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorclicle extends Model {
  static {
	  validatePresenceOf("id_vehicle","id_user","type","type_motor","boot_sistem","displacement");	  
  }
  
	
  //Id get vehicle owner
  public int getIdVehicle (){
	  return (this.getInt("id_vehicle")); 
  }
  
  //get user
  public int getIdUser(){
	  return (this.getInt("id_user"));
  }
  
  //get type
  public string getType(){
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
