package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {
      validatePresenceOf("street", "address_number");
  }

  public Integer id(){
    return this.getInteger("id");
  }

  public String street(){
    return this.getString("street") +" "+ this.getString("address_number");
  }

  public User user() {
    User u = User.findById(getInteger("user_id"));
    return u;
  }
}