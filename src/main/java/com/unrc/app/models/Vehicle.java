package com.unrc.app.models;

import org.javalite.activejdbc.Model;


public class Vehicle extends Model {
  static {
    validatePresenceOf("name", "model","km");
  }

  public String name() {
    return this.getString("name");
  }

  public String model() {
    return this.getString("model");
  }

  public String km() {
    return this.getString("km");
  }

  public User user() {
    User u = User.findById(getInteger("user_id"));
    return u;
  }
}