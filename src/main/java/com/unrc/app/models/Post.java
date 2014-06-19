package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {
	validatePresenceOf("price");
  }

  public Integer idp() {
    return this.getInteger("id");
  }

  public Integer price() {
    return this.getInteger("price");
  }

  public String description() {
    return this.getString("description");
  }

  public User user() {
    User u = User.findById(getInteger("user_id"));
    return u;
  }

  public Vehicle vehicle() {
    Vehicle v = Vehicle.findById(getInteger("vehicle_id"));
    return v;
  }
}