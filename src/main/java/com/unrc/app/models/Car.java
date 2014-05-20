package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Car extends Model{
  static {

      validatePresenceOf("is_coupe");
  }

  //retorna el modelo Car a partir de la busqueda en la bd a partir de la patente de un auto
	public static Car findByCar(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir de la patente y el estado que indica si el auto es coupe o no
    //crea un auto nuevo siempre y cuando este no exista en la bd
    public static Car createCar(boolean iscoupe,Vehicle v){
        String p = v.getString("patent");
        Car car=create("id_vehicle",p,"is_coupe", iscoupe);
        if(!existCar(p)){
            car.saveIt();
        }
        return findByCar(p);
    }


	//retorna un booleano verificando a partir de la patente si un auto existe o no en bd
	public static Boolean existCar(String i){
    	Boolean status=true;
    	if( Car.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina un auto de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    //true = operacion exitosa
    //false = operacion fallida
    public static Boolean deleteCar(String iden){
        if(existCar(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}
