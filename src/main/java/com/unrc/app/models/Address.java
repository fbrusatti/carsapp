package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {

      validatePresenceOf("street","num");
  }

  //A partir de la direccion crea un usuario nuevo siempre y cuando este no exista en la bd
  public static Address createAddress(String dir, int num){
    Address a = create("street", dir,"num", num);
    if(!existAddress(dir,num)){
          a.saveIt();
        }
  	return findByAddress(dir,num);
  }

  //Retorna el modelo Address en la bd a partir de la dirección de un usuario
  public static Address findByAddress(String dir, int num){
  return (Address.findFirst("street = ? and num = ?", dir, num));
  }

  //Retorna True si encuentra la dirección en la bd
  public static Boolean existAddress(String dir, int num){
    Boolean status=true;
     if( Address.first("street = ? and num = ?",dir, num)==null){
        status = false;
     }
    return status;
  }

  public static Boolean deleteAddress(String dir, int num){
    if(existAddress(dir,num)){
      Address a=Address.findByAddress(dir,num);
      a.delete();
      return true;
    }
    else{return false;}
  }
}