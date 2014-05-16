package com.unrc.app.models;

import org.javalite.activejdbc.Model;
public class Post extends Model {
	  static {
	      validatePresenceOf("id_user","id_vehicle","title","description","price");
	  }
	  
	  //get title
	  public String getTitle (){
		  return (this.getString("title"));
	  }
	  
	  //get description
	  public String getDescription(){
		  return (this.getString("description"));
	  }
	  
	  //get price
	  public int getPrice(){
		  return (this.getInt("price"));
	  }
	  
}