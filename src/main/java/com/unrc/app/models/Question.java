package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {
  static {
      validatePresenceOf("text");
  }
  
  public String getText(){
	  return (this.getString("text"));
  }
}