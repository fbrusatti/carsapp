package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {

      validatePresenceOf("patent","model","brand");
  }

  //retorna el modelo Vehicle a partir de la busqueda en la bd a partir de la patente de un vehiculo
	public static Vehicle findByPatent(String patente){
		return (findFirst("patent = ?", patente));
	}


	//retorna un booleano verificando a partir de la patente si un vehiculo existe con esa patente
	public static Boolean existVehicle(String patente){
    	Boolean status=true;
    	if( Vehicle.first("patent = ? ", patente )==null){
    		return false;
    	}
    	return status;
    }
   
    //a partir de la patente,modelo y marca crea un vehiculo nuevo siempre y cuando este no exista en la bd
	public static Vehicle createVehicle(String patente, String modelo, String marca,User usuario){
        Vehicle vehiculo = new Vehicle();
        vehiculo.set("patent", patente, "model", modelo, "brand", marca,"id_user",usuario.getInteger("id"));
        if(!existVehicle(patente)){
        	vehiculo.saveIt();
        }
        return findByPatent(patente);
    }

    //elimina un vehiculo de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    //true=operacion exitosa
    //false=operacion fallida
    public static Boolean deleteVehicle(String patente){
        if(existVehicle(patente)){
            Vehicle u=Vehicle.findByPatent(patente);
            Vehicle.delete("patent = ?", patente );
            return true;
        }
        else{return false;}
    }  
   
    public String licensePlate() {
        return this.getString("patent");
    }
  
    public String model() {
        return this.getString("model");
    }  

    public String brand() {
        return this.getString("brand");
    }


    public void afterCreate() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("licencePlate", this.licensePlate());
        json.put("model", this.model());
        json.put("brand", this.brand());
        App.client().prepareIndex("vehicles", "vehicle")
                  .setSource(json)
                  .execute()
                  .actionGet();
    }    
}
