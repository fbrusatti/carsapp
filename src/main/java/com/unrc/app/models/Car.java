package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Car extends Model{
  static {

      validatePresenceOf("is_coupe");
  }

  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static Car findByCar(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
    public static Car createCar(boolean iscoupe,Vehicle v){
        String p = v.getString("patent");
        Car car=create("id_vehicle",p,"is_coupe", iscoupe);
        if(!existCar(p)){
            car.saveIt();
        }
        return findByCar(p);
    }


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existCar(String i){
    	Boolean status=true;
    	if( Car.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina un usuario de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteCar(String iden){
        if(existCar(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}