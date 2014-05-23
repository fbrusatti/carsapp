/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unrc.app.models;
import org.javalite.activejdbc.Model;
/**
 *
 * @author castagneris
 */
public class City extends Model {
    static {
      validatePresenceOf("country","state","name");
  	}
	
	//get Name
	public String getName(){
		return (this.getString("name"));
	}
        
        public String getState(){
		return (this.getString("state"));
	}
        
        public String getCountry(){
		return (this.getString("country"));
	}
    
}
