package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {
  static {
      validatePresenceOf("textQ");
  }
  
  public Integer getId(){
      return (this.getInteger("id"));
  }
  
  public String getText(){
	  return (this.getString("textQ"));
  }
}