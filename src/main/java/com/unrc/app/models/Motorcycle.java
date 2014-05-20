package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motorcycle extends Model{
  static {

      validatePresenceOf("wheel_size" , "engine_size");
  }

  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static Motorcycle findByMotorcycle(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
    public static Motorcycle createMotorcycle(int rodado,int cilindrada,Vehicle v){
        String p = v.getString("patent");
        Motorcycle moto=create("id_vehicle",p,"wheel_size", rodado,"engine_size",cilindrada);
        if(!existMotorcycle(p)){
            moto.saveIt();
        }
        return findByMotorcycle(p);
    }


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existMotorcycle(String i){
    	Boolean status=true;
    	if( Motorcycle.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina un usuario de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteMotorcycle(String iden){
        if(existMotorcycle(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}