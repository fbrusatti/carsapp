package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name", "email");
  }
}
