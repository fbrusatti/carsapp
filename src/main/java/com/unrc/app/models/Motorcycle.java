package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorcycle extends Model {
  static {
	  validatePresenceOf("type","type_motor","boot_system","displacement");	  
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
	  return (this.getString("boot_system"));
  } 
  
  //get displacement
  public String getDisplacement(){
	  return (this.getString("displacement"));
  }

  
}
