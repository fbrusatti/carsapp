package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {
      validatePresenceOf("model", "color","km","mark","year");
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
  public Integer getKm(){
	  return (this.getInteger("km"));
  }
  //get mark
  public String getMark(){
	  return (this.getString("mark"));
  } 
  
  //get year
  public Integer getYear(){
	  return (this.getInteger("year"));
  }
}
