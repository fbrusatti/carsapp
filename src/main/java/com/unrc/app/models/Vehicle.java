package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {
      validatePresenceOf("id_user","model", "color","km","mark","year");
  }
  
  //Id get vehicle owner
  public Int getIdUser (){
	  return (this.getString("id_user")); 
  }
  
  //get model
  public String getModel(){
	  return (this.getString("model"));
  }
  
  //get color
  public String getColor(){
	  return (this.getString("color"));
  }
  
  //get km
  public int getKm(){
	  return (this.getInt("km"));
  }
  //get mark
  public String getMark(){
	  return (this.getInt("mark"));
  } 
  
  //get year
  public int getYear(){
	  return (this.getInt("year"));
  }
  

  
}
