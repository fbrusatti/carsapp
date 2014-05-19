package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Answer extends Model {
  static {
      validatePresenceOf("id_question","id_user", "text");
  }
  
  public String getText(){
	  return (this.getString("text"));
  }
}