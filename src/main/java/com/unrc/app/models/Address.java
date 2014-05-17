package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {

      validatePresenceOf("direction");
  }

  //A partir de la direccion crea un usuario nuevo siempre y cuando este no exista en la bd
  public static Address createAddress(String dir){
  	Address a = create("direction",dir);
    a.saveIt();
  	return findByAddress(dir);
  }

  //Retorna el modelo Address en la bd a partir de la dirección de un usuario
  public static Address findByAddress(String dir){
  return (Address.findFirst("direction = ?" ,dir));
  }

  //Retorna True si encuentra la dirección en la bd
  public static Boolean existAddress(String dir){
    Boolean ret=true;
     if( Address.first("direction = ?" ,dir)==null){
        return false;
     }
    return ret;
  }

}  