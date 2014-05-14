package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {
      validatePresenceOf("street", "address_number");
  }
}