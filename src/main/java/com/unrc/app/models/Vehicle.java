package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {

      validatePresenceOf("patent","model","mark");
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
    	Vehicle vehiculo=create("patent", patente, "model", modelo, "mark", marca,"id_user",usuario.getInteger("id"));

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


   
}
