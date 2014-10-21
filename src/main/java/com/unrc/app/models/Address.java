package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {

      validatePresenceOf("street","num");
  }

  //A partir de la direccion crea un usuario nuevo si y solo si este no existe en la bd
  public static Address createAddress(String dir, int num, String ciudad, User usuario){
    Address address = create("street", dir,"num", num,"city",ciudad);
    if(!existAddress(dir,num,ciudad)){
          address.saveIt();
          usuario.add(address);
        }
    return findByAddress(dir,num,ciudad);
  }

  //Retorna el modelo Address en la bd a partir de la dirección de un usuario
  public static Address findByAddress(String dir, int num, String ciudad){
  return (Address.findFirst("street = ? and num = ? and city = ?", dir, num, ciudad));
  }

  //Retorna True si encuentra la dirección en la bd
  public static Boolean existAddress(String dir, int num, String ciudad){
    Boolean status=true;
     if( Address.first("street = ? and num = ? and city = ?",dir, num, ciudad)==null){
        status = false;
     }
    return status;
  }
  
  //Elimina una direccion si y solo si no tiene ningun usuario asociado a la misma
  public static Boolean deleteAddress(String dir, int num, String ciudad){
  List<User> users = new ArrayList<User>(); //creo lista de Users
  Address address = Address.findByAddress(dir,num, ciudad);
  users = address.getAll(User.class); //contiene todos los users que poseen esa direccion
    if(existAddress(dir,num, ciudad) && users.size() == 0){//si la direccion existe y no tiene ningun usuario asociado, la elimino
      address.delete();
      return true;
    }
    else{return false;
    }
  }

    public String dir(){
        return this.getString("street");
    }

    public int num(){
        return this.getInteger("num");
    }  

    public String city(){
        return this.getString("city");
    }

  public void afterCreate() {
  Map<String, Object> json = new HashMap<String, Object>();
  json.put("street", this.getString("street"));
  json.put("number", this.getInteger("num"));
  json.put("city", this.getString("city"));
  App.client().prepareIndex("addresses", "address")
              .setSource(json)
              .execute()
              .actionGet();
  }
}
