package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Truck extends Model{
  static {

      validatePresenceOf("count_belt");
  }

  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static Truck findByTruck(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
    public static Truck createTruck(int cantCinturon,Vehicle v){
        String p = v.getString("patent");
        Truck truck=create("id_vehicle",p,"count_belt", cantCinturon);
        if(!existTruck(p)){
            truck.saveIt();
        }
        return findByTruck(p);
    }


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existTruck(String i){
    	Boolean status=true;
    	if( Truck.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina un usuario de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteTruck(String iden){
        if(existTruck(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}