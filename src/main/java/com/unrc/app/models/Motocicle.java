package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Motocicle extends Model{
  static {

      validatePresenceOf("roll" , "cylinder");
  }

  //retorna el modelo Motocicle a partir de la busqueda en la bd a partir de la patente 
	public static Motocicle findByMotocicle(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir del rodado,cilindrada y patente crea una moto nueva siempre y cuando esta no exista en la bd
    public static Motocicle createMotocicle(int rodado,int cilindrada,Vehicle v){
        String p = v.getString("patent");
        Motocicle moto=create("id_vehicle",p,"roll", rodado,"cylinder",cilindrada);
        if(!existMotocicle(p)){
            moto.saveIt();
        }
        return findByMotocicle(p);
    }


	//retorna un booleano verificando a partir de la patente si una moto existe en la bd o no
	public static Boolean existMotocicle(String i){
    	Boolean status=true;
    	if( Motocicle.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   

   

    //elimina una moto de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    //true= operacion exitosa
    //false= operacion fallida
    public static Boolean deleteMotocicle(String iden){
        if(existMotocicle(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  


   
}
