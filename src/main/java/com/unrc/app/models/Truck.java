package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Truck extends Model{
  static {

      validatePresenceOf("count_belt");
  }

  //retorna el modelo Truck a partir de la busqueda en la bd a partir de la patente de una camioneta
	public static Truck findByTruck(String i){
		return (findFirst("id_vehicle = ?", i));
	}

    //a partir de la cantidad de cinturones y la patente  crea una camioneta nueva siempre y cuando esta no exista en la bd
    public static Truck createTruck(int cantCinturon,Vehicle vehicle){
        String p = vehicle.getString("patent");
        //Truck truck=create("id_vehicle",p,"count_belt", cantCinturon);
        Truck truck = new Truck();
        //vehicle.add(truck);
        truck.set("id_vehicle",p,"count_belt", cantCinturon);
        if(!existTruck(p)){
            truck.saveIt();
        }
        return findByTruck(p);
    }


	//retorna un booleano verificando a partir de la patente si una camioneta existe o no en la bd
	public static Boolean existTruck(String i){
    	Boolean status=true;
    	if( Truck.first("id_vehicle = ? ", i )==null){
    		return false;
    	}
    	return status;
    }
   
    //elimina una camioneta de la bd indicando con un valor booleano el estado de finalizacion de la operacion
    //true=operacion exitosa
    //false=operacion fallida
    public static Boolean deleteTruck(String iden){
        if(existTruck(iden)){
            Vehicle u=Vehicle.findByPatent(iden);
            Vehicle.delete("patent = ?", iden );
            return true;
        }
        else{return false;}
    }  

    public String licensePlate() {
        return this.getString("id_vehicle");
    }
  
    public String belt() {
        return this.getString("count_belt");
    }

    public void afterCreate() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("licencePlate", this.licensePlate());
        json.put("belst", this.belt());
        App.client().prepareIndex("trucks", "truck")
                  .setSource(json)
                  .execute()
                  .actionGet();
    }
}
