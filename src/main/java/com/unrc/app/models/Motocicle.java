package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motocicle extends Model{
  static {

      validatePresenceOf("roll" , "cylinder");
  }

  //retorna el modelo User a partir de la en la bd a partir del email de un usuario
	public static Motocicle findByMotocicle(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir del nombre-apellido-email crea un usuario nuevo siempre y cuando este no exista en la bd
    public static Motocicle createMotocicle(int rodado,int cilindrada,Vehicle v){
        String p = v.getString("patent");
        Motocicle moto=create("id_vehicle",p,"roll", rodado,"cylinder",cilindrada);
        if(!existMotocicle(p)){
            moto.saveIt();
        }
        return findByMotocicle(p);
    }


	//retorna un booleano verificando a partir del email si un usuario existe
	public static Boolean existMotocicle(String i){
    	Boolean status=true;
    	if( Motocicle.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina un usuario de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    public static Boolean deleteMotocicle(String iden){
        if(existMotocicle(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}