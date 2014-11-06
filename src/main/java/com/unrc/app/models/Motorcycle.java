package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
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
    public static Motorcycle createMotorcycle(int rodado,int cilindrada,Vehicle vehicle){
        String p = vehicle.getString("patent");
        //Motorcycle motorcycle=create("id_vehicle",p,"wheel_size", rodado,"engine_size",cilindrada);
        Motorcycle motorcycle = new Motorcycle();
        //vehicle.add(motorcycle);
        motorcycle.set("id_vehicle",p,"wheel_size", rodado,"engine_size",cilindrada);
        if(!existMotorcycle(p)){
            motorcycle.saveIt();
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

    public String licensePlate() {
        return this.getString("id_vehicle");
    }
  
    public int wheelSize(){
        return this.getInteger("wheel_size");
    }   

    public int engineSize(){
        return this.getInteger("engine_size");
    }


    public void afterCreate() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("licencePlate", this.licensePlate());
        json.put("engineSize", this.engineSize());
        json.put("wheelSize", this.wheelSize());
        App.client().prepareIndex("motorcycles", "motorcycle")
                  .setSource(json)
                  .execute()
                  .actionGet();
    }
}
