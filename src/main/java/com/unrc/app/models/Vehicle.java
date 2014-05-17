package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {

      validatePresenceOf("patent","model","mark");
  }

  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static Vehicle findByPatent(String patente){
		return (findFirst("patent = ?", patente));
	}


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existVehiculo(String patente){
    	Boolean status=true;
    	if( Vehicle.first("patent = ? ", patente )==null){
    		return false;
    	}
    	return status;
    }
   
    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
	public static Vehicle createUser(String patente, String modelo, String marca){
    	Vehicle vehiculo=create("patent", patente, "model", modelo, "mark", marca);

        if(!existVehiculo(patente)){
        	vehiculo.saveIt();
        }
        return findByPatent(patente);
    }

              
}
