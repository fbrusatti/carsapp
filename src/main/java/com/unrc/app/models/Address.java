package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.*;

public class Address extends Model {
  static {

      validatePresenceOf("street","num");
  }

  //A partir de la direccion crea un usuario nuevo si y solo si este no existe en la bd
  public static Address createAddress(String dir, int num, User usuario){
    Address address = create("street", dir,"num", num);
    usuario.add(address);
    if(!existAddress(dir,num)){
          address.saveIt();
        }
    return findByAddress(dir,num);
  }

  //Retorna el modelo Address en la bd a partir de la dirección de un usuario
  public static Address findByAddress(String dir, int num){
  return (Address.findFirst("street = ? and num = ? ", dir, num));
  }

  //Retorna True si encuentra la dirección en la bd
  public static Boolean existAddress(String dir, int num){
    Boolean status=true;
     if( Address.first("street = ? and num = ?",dir, num)==null){
        status = false;
     }
    return status;
  }
  
  //Elimina una direccion si y solo si no tiene ningun usuario asociado a la misma
  public static Boolean deleteAddress(String dir, int num){
  List<User> users = new ArrayList<User>(); //creo lista de Users
  Address address = Address.findByAddress(dir,num);
  users = address.getAll(User.class); //contiene todos los users que poseen esa direccion
    if(existAddress(dir,num) && users.size() == 0){//si la direccion existe y no tiene ningun usuario asociado, la elimino
      address.delete();
      return true;
    }
    else{return false;
    }
  }
}