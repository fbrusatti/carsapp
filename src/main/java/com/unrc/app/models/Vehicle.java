
package com.unrc.app.models;
import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.get.GetResponse;
import org.javalite.activejdbc.Model;
import org.elasticsearch.client.Client;

public class Vehicle extends Model{
      static {
      validatePresenceOf("mark", "model","patent","id_user", "color","tipo","cc","isCoupe","capacity");
  }
//           @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("mark", this.get("mark"));
//      json.put("model", this.get("model"));
//      json.put("patent", this.get("patent"));
//      json.put("id_user", this.get("id_user"));
//      json.put("color", this.get("color"));
//      json.put("tipo", this.get("tipo"));
//      json.put("cc", this.get("cc"));
//      json.put("isCoupe", this.get("isCoupe"));
//      json.put("capacity", this.get("capacity"));
//      
//      ElasticSearch.client().prepareIndex("vehicles", "vehicle",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
//    public void  getVehicleElasticsearch(){
//              System.out.println("---------------------------antesconsulta---------------------------------------------");  
//             ElasticSearch.client().close();
//              GetResponse response = ElasticSearch.client().prepareGet("vehicles", "vehicle", "2")
//                  .execute()
//                  .actionGet();
//            System.out.println("---------------------------consulta---------------------------------------------");
//            
//            System.out.println("source---"+response.getSource());
//            System.out.println("source as string  --- "+response.getSourceAsString());
//        }
      
    public static Vehicle findByPatent(String patente){
	return (findFirst("patent = ?", patente));
    }
	
    public static Boolean existVehicle(String patente){
          return (Vehicle.first("patent = ? ", patente ) == null);
    }
   
    public static Vehicle createVehicle(String patente, String marca, String modelo,int usuario,String color,String tipo,int cc,String isCoupe,int capacity){
        Vehicle vehiculo =create("patent", patente, "model", modelo, "mark", marca,"id_user",usuario,"color",color,"tipo",tipo,"cc",cc,"isCoupe",isCoupe,"Capacity",capacity);

        vehiculo.saveIt();
        
        return findByPatent(patente);
    }

    public static void deleteVehicle(String patente){
        if(existVehicle(patente)){
            Vehicle.delete("patent = ?", patente );
        }
    }        
    
}
