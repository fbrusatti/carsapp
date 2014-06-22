package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Answer extends Model {
  static {
      validatePresenceOf("textA");
  }
  
  public String getText(){
	  return (this.getString("textA"));
  }
}