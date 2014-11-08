package com.unrc.app.models;

import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Model;

public class Address extends Model {
  static {

      validatePresenceOf("street","city","province","postal_code","num");
  }
//  @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("street", this.get("street"));
//      json.put("city", this.get("descrcityiption"));
//      json.put("province", this.get("province"));
//      json.put("postal_code", this.get("postal_code"));
//      json.put("num", this.get("num"));
//      ElasticSearch.client().prepareIndex("addresses", "address",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }

  public static Address findByAddress(String direc , int num, String ciudad, String provincia, int codigoPostal){
  return (Address.findFirst("street = ? and num = ? and city = ? and province = ? and postal_code =?", direc , num, ciudad,provincia,codigoPostal));
  }

  public static Address createAddress(String direc , int num, String ciudad, String provincia, int codigoPostal){
    Address address = create("street", direc,"num", num,"city",ciudad,"province",provincia,"postal_code",codigoPostal);
    address.saveIt();
    return findByAddress(direc,num,ciudad,provincia,codigoPostal);
  }

  public static Boolean direcExistente(String direc, int num,String ciudad,String provincia, int codigoPostal){
      return (Address.first("street = ? and num = ? and city = ? and province = ? and postal_code =?",direc, num, ciudad,provincia,codigoPostal) != null);
  }

  public static void deleteAddress(String dir, int num, String ciudad,String provincia, int codigoPostal){
    Address address = Address.findByAddress(dir,num,ciudad,provincia,codigoPostal);
    if(direcExistente(dir,num, ciudad,provincia,codigoPostal))
        address.delete();
  }
}