package com.unrc.app;

import com.unrc.app.models.User;

public class Html {

    public String getAllUsers(String users) {
        String s = "<br><br><div align=\"center\">USUARIOS<br><br><form action=\"/getusers\" method=\"post\"><br><br>";
        s = s + "<select name=\"Usuarios\" size=\"20\">";
        String[] tmp = users.split(",");
        for (int i = 0; i < tmp.length ; i++) {
            s = s + "<option value=" + "\"" + i + "\"" + ">" + tmp[i];

        }
        s+="<br><br></select><br><br>";
        s+="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /> ";
        s+="<br><br>Eliminar usuario (id) : <input type=\"text\" name=\"id_user\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"delete\"><br><br> ";
        s = s + "<br><br><div align=\"center\"><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/admin'\" value=\"Volver\"></form></body></html>";

        return s;
    }
    
        public String getAllPost(String post) {
        String s = "<br><br><div align=\"center\">Post<br><br><form action=\"/post\" method=\"post\"><br><br>";
        s+= "<table border=\"1\"style=\"border-collapse: separate; border: blue 2px solid;\">";
        s = s + "<tr> <td>ID</td><td>DESCRIPCION</td><td>DUE&#209O</td> <td>PATENTE</td> </tr>";
        String[] tmp = post.split(",");

        for (int i = 0; i < tmp.length; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
        
        }
        s+="</table>";
        s+="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /> ";
        s = s + " <br><br> Ver Post: <input type=\"text\" name=\"id_post\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"ok\"><br><br> ";
        s+="<br><br>Eliminar Post (id) : <input type=\"text\" name=\"postEliminar\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"Eliminar\"><br><br> ";
        s = s + "<br><br><div align=\"center\"><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/admin'\" value=\"Volver\"></form></body></html>";

        return s;
    }
        
        
     public String AdminInbox(String message,String messageGuest) {
        String s = "<br><br><div align=\"center\">Mensajes Usuarios<br><br><form action=\"/inbox\" method=\"post\">";
        s+= "<table border=\"1\"style=\"border-collapse: separate; border: blue 2px solid;\">";
        s = s + "<tr> <td>ID Mensaje</td><td>Usuario</td> </tr>";
        String[] tmp = message.split(",");

        for (int i = 0; i < tmp.length; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
        
        }
        
       s+="</table>";
       s = s + " <br><br> Ver Mensaje de usuario (id): <input type=\"text\" name=\"idmu\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"ok\"><br><br> ";
       s+="Eliminar Mensaje de usuario (id) : <input type=\"text\" name=\"eliminarmu\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"Eliminar\"><br><br> ";

       s+= "<br><br><div align=\"center\">Mensajes De Invitados<br><br><form action=\"/inbox\" method=\"post\">";
       s+= "<table border=\"1\"style=\"border-collapse: separate; border: blue 2px solid;\">";
       s = s + "<tr> <td>ID Mensaje</td><td>Usuario</td> </tr>"; 
       String[] tmp2 = messageGuest.split(",");

       for (int i = 0; i < tmp2.length; i++) {
            s = s + "<tr>";
            s = s + "<td>" + tmp2[i] + "</td>";
            s = s + "<td>" + "Invitado" + "</td>";
            s = s + "</tr>";
        
        }
         s+="</table>";
       s = s + " <br><br> Ver Mensaje de invitado (id): <input type=\"text\" name=\"idmi\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"ok\"><br><br> ";
       s+="Eliminar Mensaje de invitado (id) : <input type=\"text\" name=\"eliminarmi\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"Eliminar\"><br><br> ";
       s+="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /> ";
        
        s = s + "<div align=\"center\"><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/admin'\" value=\"Volver\"></form></body></html>";

        return s;
    }

    public String getFailLogin() {
       String s ="<br><font size=\"7\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /><div align=\"center\"> Acceso denegado <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Ingresar\"></form></body></html>";
    return s;
    }
    
    public String getMessagePag(String a,String b) {
       String s ="<br><font size=\"4\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /><div align=\"center\">"+a+" <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567"+b+"'\" value=\"Volver\"></form></body></html>";
    return s;
    }
    
    public String getUserBy(String user) {
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
        s = s + "<div align=\"center\">Usuario<br><br>";
        s = s + "<tr> <td>Nombre</td><td>Apellido</td> <td>Email</td> </tr>";
        String[] tmp = user.split(",");
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<td>" + tmp[i] + "</td>";

        }
        s = s + " <br><br><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }
        public String getPostBySearch(String desc,String post,String v,String ans) {
            String s = "<br><div align=\"center\">POST <br><br><form action=\"/getpostbysearch\" method=\"post\">";  
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DESCRIPCION</td> </tr>";
            s = s + "<td>" + desc + "</td>";
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DUE&#209O</td><td>PROVINCIA</td><td>CIUDAD</td><td>CODIGO POSTAL</td><td>DIRECCION</td><td>NUMERO</td> </tr>";
            String [] tmp = post.split("}");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            
            s = s+"</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "VEHICULO <br>";
            s = s + "<tr> <td>Patente</td><td>Marca</td> <td>Modelo</td> <td>Color</td> <td>Tipo</td>  <td>isCoupe</td><td>CC</td><td>Capacidad</td></tr>";
            tmp = v.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            s+="</table><br><br>Preguntar<br><br><TEXTAREA COLS=20 ROWS =10 NAME = \"coment\"></TEXTAREA> <BR><INPUT TYPE =\"submit\">";
            s+="</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "<br>Preguntas y respuestas <br>";
            s = s + "<tr> <td>Numero</td><td>Usuario</td> <td>Pregunta</td> <td>Resuesta</td> </tr>";
            tmp = ans.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<tr>";
                String[] tm = tmp[i].split("}");
                for (int j = 0; j < tm.length; j++) {
                    s = s + "<td>" + tm[j] + "</td>";

                }   
            }
            s = s + "<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></tr>";
            s = s + "</table><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\">";

            return s;
    }
        
        public String getMessage(String message){                        
            String s = "<br><div align=\"center\">Mensaje<br><br><form action=\"/inbox\" method=\"post\">";  

            s +="<TEXTAREA COLS=20 ROWS =10 NAME = \"coment\">"+message+"</TEXTAREA>";
            s += "<br><br><div align=\"center\"><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/inbox'\" value=\"Volver\"></form></body></html>";
            s +="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /> ";

            return s;
        }
        
        
    public String getPostBySearchAdmin(String desc,String post,String v,String ans) {
            String s = "<br><div align=\"center\">POST <br><br><form action=\"/getpostbysearch\" method=\"post\">";  
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DESCRIPCION</td> </tr>";
            s = s + "<td>" + desc + "</td>";
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DUE&#209O</td><td>PROVINCIA</td><td>CIUDAD</td><td>CODIGO POSTAL</td><td>DIRECCION</td><td>NUMERO</td> </tr>";
            String [] tmp = post.split("}");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            
            s = s+"</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "VEHICULO <br>";
            s = s + "<tr> <td>Patente</td><td>Marca</td> <td>Modelo</td> <td>Color</td> <td>Tipo</td>  <td>isCoupe</td><td>CC</td><td>Capacidad</td></tr>";
            tmp = v.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            s+="</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "<br>Preguntas y respuestas <br>";
            s = s + "<tr> <td>Numero</td><td>Usuario</td> <td>Pregunta</td> <td>Resuesta</td> </tr>";
            tmp = ans.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<tr>";
                String[] tm = tmp[i].split("}");
                for (int j = 0; j < tm.length; j++) {
                    s = s + "<td>" + tm[j] + "</td>";

                }   
            }

            s = s + "<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></tr>";
            s = s + "</table><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/post'\" value=\"Volver\">";

            return s;
    }    
    public String getOwnPostBySearch(String desc,String post,String v,String ans) {
            String s = "<br><div align=\"center\">POST <br><br><form action=\"/answerpost\" method=\"post\">";  
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DESCRIPCION</td> </tr>";
            s = s + "<td>" + desc + "</td>";
            s+= "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">"; 
            s = s + "<tr> <td>DUE&#209O</td><td>PROVINCIA</td><td>CIUDAD</td><td>CODIGO POSTAL</td><td>DIRECCION</td><td>NUMERO</td> </tr>";
            String [] tmp = post.split("}");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            
            s = s+"</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "VEHICULO <br>";
            s = s + "<tr> <td>Patente</td><td>Marca</td> <td>Modelo</td> <td>Color</td> <td>Tipo</td>  <td>isCoupe</td><td>CC</td><td>Capacidad</td></tr>";
            tmp = v.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<td>" + tmp[i] + "</td>";

            }
            s+="</table><br><br>Responder preguntas<br><br>Numero de Pregunta: <input type=\"text\" name=\"question\" size=\"12\" maxlength=\"15\"><br><TEXTAREA COLS=20 ROWS =10 NAME = \"responder\"></TEXTAREA> <BR><INPUT TYPE =\"submit\">";
            s+="</table><br><table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
            s = s + "<br>Preguntas y respuestas <br>";
            s = s + "<tr> <td>Numero</td><td>Usuario</td> <td>Pregunta</td> <td>Resuesta</td> </tr>";
            tmp = ans.split(",");
            for (int i = 0; i < tmp.length; i++) {
                s = s + "<tr>";
                String[] tm = tmp[i].split("}");
                for (int j = 0; j < tm.length; j++) {
                    s = s + "<td>" + tm[j] + "</td>";

                }   
            }
            s = s + "<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></tr>";
            s = s + "</table><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\">";

             return s;
    }   
    public String getPostBy(String post) {
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
        s = s + "POST<br><br>";
        s = s + "<tr> <td>USUARIO</td><td>PATENTE</td><td>DESCRIPCION</td> </tr>";
        String[] tmp = post.split("}");
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<td>" + tmp[i] + "</td>";

        }
        s = s + " <br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\"><form><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        return s;
    }

    public String getVehicleBy(String vehicle) {
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
        s = s + "VEHICULO<br><br>";
        s = s + "<tr> <td>Patente</td><td>Marca</td> <td>Modelo</td> <td>Color</td> <td>Tipo</td> <td>ID_Due&#241o</td><td>Es Coupe?</td><td>CC</td><td>Capacidad</td> </tr>";
        String[] tmp = vehicle.split("}");
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<td>" + tmp[i] + "</td>";

        }
        s = s + " <br><br><div align=\"left\"><form><input type=\"button\" onclick=\"javascript: history.back()\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String IngresarUsuario() {
        String s = "<br><div align=\"center\">INGRESAR UN NUEVO USUARIO <br><br><form action=\"/insertuser\" method=\"post\">";
        s = s + "Nombre: <input type=\"text\" name=\"first_name\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Apellido: <input type=\"text\" name=\"last_name\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Email: <input type=\"text\" name=\"email\" size=\"25\" maxlength=\"50\"><br><br>";
         s = s + "Contrasena: <input type=\"text\" name=\"contrasena\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\"><br><br>";
        s = s + "</form>";
        s = s + " <br><br><div align=\"center\"><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/admin'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }
    
    public String RegistrarUsuario() {
        String s = "<br><div align=\"center\">Registrarse <br><br><form action=\"/registeruser\" method=\"post\">";
        s = s + "Nombre: <input type=\"text\" name=\"first_name\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Apellido: <input type=\"text\" name=\"last_name\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Email: <input type=\"text\" name=\"email\" size=\"25\" maxlength=\"50\"><br><br>";
         s = s + "Contrasena: <input type=\"text\" name=\"contrasena\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><input type=\"submit\" name=\"loginuser\" value=\"Registrar\"><input type=\"reset\"  value=\"Reset\"><br><br><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        s = s + "</form>";

        return s;
    }
    
     public String irlogin() {
        String s = "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0;URL=http://localhost:4567/loginuser\">";
        return s;
    }
        
        

    public String IngresarCiudad() {
        String s = "<br><div align=\"center\">INGRESAR UNA CIUDAD <br><br><br><form action=\"/insertaddress\" method=\"post\">";
        s = s + "Provincia: <input type=\"text\" name=\"provincia\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Ciudad: <input type=\"text\" name=\"ciudad\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Cod_Postal: <input type=\"text\" name=\"codigo_postal\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Direccion: <input type=\"text\" name=\"direccion\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Numero: <input type=\"text\" name=\"num\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><br><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\">";
        s = s + "</form>";
        s = s + " <br><br><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String IngresarPost(User user,String a,String b) {
        String s = "<br><div align=\"center\">INGRESAR UN NUEVO POST <br><br><form action=\"/insertpost\" method=\"post\">";
        s = s + "Descripcion: <input type=\"text\" name=\"descripcion\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Patente Vehiculo:<select name=\"patente\" size=\"1\">";

          String[] tmp = a.split(",");
        for (int i = 0; i < tmp.length ; i++) {
            s = s + "<option value=" + "\"" + tmp[i] + "\"" + ">" + tmp[i];

        }
        s+="<br><br></select><br><br>";
        
        s = s + "Ciudad:<select name=\"ciudad\" size=\"1\">";

        String[] tmp2 = b.split(",");
        for (int i = 0; i < tmp2.length ; i++) {
            String[] tmp3 = tmp2[i].split("}");

            s = s + "<option value=" + "\"" + tmp3[0] + "\"" + ">" + tmp3[1];

        }
        s+="<br><br></select><br><br>";
        s = s + "<br><br><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\">";
        s = s + " <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\">";
        s = s + "<form><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></form></body></html>";

        return s;
    }

    public String IngresarRespuesta() {
        String s = "<br><div align=\"center\">INGRESAR UN NUEVA RESPUESTA <br><br><form action=\"/insertanswer\" method=\"post\" enctype=\"text/plain\">";
        s = s + "Descripcion: <input type=\"text\" name=\"descripcion\" size=\"255\" maxlength=\"200\"><br><br>";
        s = s + "id usuario: <input type=\"text\" name=\"id_user\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "id pregunta: <input type=\"text\" name=\"id_pregunta\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><br><div align=\"left\"><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\">";
        s = s + "</form>";
        s = s + " <br><br><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String IngresarPregunta() {
        String s = "<br><div align=\"center\">INGRESAR UN NUEVA PREGUNTA <br><br><form action=\"/insertanswer\" method=\"post\">";
        s = s + "Id post: <input type=\"text\" name=\"id_post\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Descripcion: <input type=\"text\" name=\"descripcion\" size=\"255\" maxlength=\"200\"><br><br>";
        s = s + "id usuario: <input type=\"text\" name=\"id_user\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><br><div align=\"left\"><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\">";
        s = s + "</form>";
        s = s + " <br><br><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String IngresarAutomovil() {
        String s = "<br><div align=\"center\">INGRESAR UN NUEVO VEHICULO <br><br><br><form action=\"/insertvehicle\" method=\"post\">";
        s = s + "Marca: <input type=\"text\" name=\"marca\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Modelo: <input type=\"text\" name=\"modelo\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Patente: <input type=\"text\" name=\"patente\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Color: <input type=\"text\" name=\"color\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Tipo: Auto <input type=\"radio\" name=\"tipo\" id=\"tipo\" value=\"0\"> Moto <input type=\"radio\" name=\"tipo\" id=\"tipo\" value=\"1\"> Camion <input type=\"radio\" name=\"tipo\" id=\"tipo\" value=\"2\">";
        s = s + "<br><br>Es coupe?(Auto): <input type=\"text\" name=\"isCoupe\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "CC(moto): <input type=\"text\" name=\"CC\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s +"Capacidad(camion):<input type=\"text\" name=\"Capacity\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><br><input type=\"submit\"  value=\"ingresar\"><input type=\"reset\"  value=\"Reset\">";
        s = s + "<br><br><form><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\">";
        s = s + "<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String getAutomoviles(String autos) {
        String[] tmp = autos.split(",");
        System.out.println(tmp[0]);
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 5px solid;\">";
        s = s + "<div align=\"center\">VEHICULOS REGISTRADOS<br><br>";
        s = s + "<tr> <td>Patente</td><td>Marca</td> <td>Modelo</td> <td>Color</td> <td>Tipo</td> <td>ID_Due&#241o</td><td>Es Coupe?</td><td>CC</td><td>Capacidad</td> </tr>";
        for (int i = 0; i < tmp.length ; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
          
        } 
        s = s + "</table>";
       s = "<div align=\"center\">" +  s;
       s = s + " <br><br><div align=\"center\"><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        return  s;
    }
    
        public String getOwnAddress(String ciudades) {
        String[] tmp = ciudades.split(",");
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 5px solid;\">";
        s = s + "<div align=\"center\">CIUDADES REGISTRADAS<br><br>";
        s = s + "<tr> <td>Provincia</td><td>ciudad</td> <td>cod_postal</td> <td>Direccion</td> <td>Numero</td></tr>";
        for (int i = 0; i < tmp.length ; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
          
        }
       s = s + "</table>";
       s = "<div align=\"center\">" +  s;
       s = s + " <br><br><div align=\"center\"><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        return  s;
    }

    public String getCities(String cities) {
        String[] tmp = cities.split(",");
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 5px solid;\">";
        s = s + "<div align=\"center\">CIUDADES REGISTRADAS<br><br>";
        s = s + "<tr> <td>Provincia</td><td>ciudad</td> <td>cod_postal</td> <td>Direccion</td> <td>Numero</td></tr>";
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j<tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
//            
        }
        s = s + " <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String getAnswers(String answers) {
        String[] tmp = answers.split(",");
        System.out.println(tmp[0]);
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
        s = s + "<div align=\"center\">RESPUESTAS<br><br>";
        s = s + "<tr> <td>id_post</td><td>id_user</td> <td>Descripcion</td> </tr>";
        for (int i = 0; i < tmp.length - 1; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split(" ");
            for (int j = 0; j < 3; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
//            
        }
        s = s + " <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }

    public String getAnswersByid(String answers) {
        String[] tmp = answers.split(" ");
        String s = "<table border=\"1\"style=\"border-collapse: separate; border: red 2px solid;\">";
        s = s + "<div align=\"center\">RESPUESTAS<br><br>";
        s = s + "<tr> <td>id_post</td><td>id_user</td> <td>Descripcion</td> </tr>";

        s = s + "<tr>";
        for (int j = 0; j < 3; j++) {
            s = s + "<td>" + tmp[j] + "</td>";

        }
        s = s + "</tr>";            
        s = s + " <br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/loginuser'\" value=\"Volver\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";

        return s;
    }
    
    public String getPostUser(String post) {
        String[] tmp = post.split(",");
        String s = "<br><div align=\"center\">POST<br><br><form action=\"/post\" method=\"post\">";
         s+= "<table border=\"1\"style=\"border-collapse: separate; border: blue 2px solid;\">";
        s = s + "<tr> <td>ID</td><td>DESCRIPCION</td><td>DUE&#209O</td> <td>PATENTE</td> </tr>";
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
        
        }
        s+="</table>";
        s = s + " <br><br> Ver Post: <input type=\"text\" name=\"id_post\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"ok\"><br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\"> ";
        s+="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        return s;
    }
    
        public String getPostGuest(String post) {
        String[] tmp = post.split(",");
        String s = "<br><div align=\"center\">POST<br><br><form action=\"/post\" method=\"post\">";
         s+= "<table border=\"1\"style=\"border-collapse: separate; border: blue 2px solid;\">";
        s = s + "<tr> <td>ID</td><td>DESCRIPCION</td><td>DUE&#209O</td> <td>PATENTE</td> </tr>";
        for (int i = 0; i < tmp.length; i++) {
            s = s + "<tr>";
            String[] tm = tmp[i].split("}");
            for (int j = 0; j < tm.length; j++) {
                s = s + "<td>" + tm[j] + "</td>";

            }
            s = s + "</tr>";
        
        }
        s+="</table>";
        s = s + " <br><br> Ver Post: <input type=\"text\" name=\"id_post\" size=\"3\" maxlength=\"50\"> <input type=\"submit\"  value=\"ok\"><br><br><input type=\"button\" onclick=\"window.location.href='http://localhost:4567/guestcp'\" value=\"Volver\"> ";
        s+="<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></body></html></form>";
        return s;
    }
    
    public String contactAdminUser(){
        String s ="<html>"; 
        s+="<div align=\"center\"><head> <title>CarsApp</title> </head> <body>";
        s+="<h1>ContactarAdministrador</h1>";
        s+="<img src=\"http://techywhack.com/wp-content/uploads/2012/11/contact-us.png\" style='background-repeat:no-repeat;' />";
        s+="<form action=\"/admincontactuser\"method=\"post\">";
        s+="Mensaje:<br>";
        s+="<textarea name=\"mensaje\" cols=\"50\" rows=\"5\"></textarea>";
        s+="<br> <br><input type=\"submit\"  value=\"ingresar\">";
        s = s + "<input type=\"button\" onclick=\"window.location.href='http://localhost:4567/webpag'\" value=\"Volver\">";
        s+="</form></div></body> </html>";
        return s;
    }
        public String contactAdminGuest(){
        String s ="<html>"; 
        s+="<div align=\"center\"><head> <title>CarsApp</title> </head> <body>";
        s+="<h1>ContactarAdministrador</h1>";
        s+="<img src=\"http://techywhack.com/wp-content/uploads/2012/11/contact-us.png\" style='background-repeat:no-repeat;' />";
        s+="<form action=\"/admincontactguest\"method=\"post\">";
        s+="Mensaje:<br>";
        s+="<textarea name=\"mensaje\" cols=\"50\" rows=\"5\"></textarea>";
        s+="<br> <br><input type=\"submit\"  value=\"ingresar\">";
        s = s + "<input type=\"button\" onclick=\"window.location.href='http://localhost:4567/guestcp'\" value=\"Volver\">";
        s+="</form></div></body> </html>";
        return s;
        }

    public String loginUsuario() {
        String s = "<br> <br><br><form action=\"/loginuser\" method=\"post\"> <br><br><font size=\"7\"><div align=\"center\"><b><u>CARSAPP </font><br><br>";   
        s = s + "<div align=\"center\">Email: <input type=\" type=\"text\" name=\"email\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "Contrase&#241a: <input type=\"password\" name=\"contrasena\" size=\"25\" maxlength=\"50\"><br><br>";
        s = s + "<br><br><input type=\"submit\"  value=\"ingresar\"></b></u>";
        s = s + "<input type=\"button\" onclick=\"window.location.href='http://localhost:4567/guestcp\'\" value=\"Ingresar como Invitado\"> "; 
        s = s + "<html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /> ";
        s = s + "</form></body></html>";
        return s;
    }
    
    public String userControlPane() {
        String s = "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0;URL=http://localhost:4567/webpag\">";
        return s;
    }
      
    public String adminControlPane() {
        String s = "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0;URL=http://localhost:4567/admin\">";
        return s;
    }
      
    public String guestControlPane() {
        String s = "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0;URL=http://localhost:4567/guestcp\">";
        return s;
    }

    public String webpage() {
        String page = "<form action=\"Crear Vehiculo\" method=\"post\" >";

        page = page + "<br><br><div align=\"center\">CARS APP<br><br>";

        page += "<a href=\"http://localhost:4567/insertpost\" onclick=\"myJsFunc();\"> * Crear Post </a><br><br>";
        page += "<a href=\"http://localhost:4567/ownpost\" onclick=\"myJsFunc();\">* Ver mis Posts </a><br><br>";
        page += "<a href=\"http://localhost:4567/insertvehicle\" onclick=\"myJsFunc();\">* Agregar Vehiculos </a><br><br>";
        page += "<a href=\"http://localhost:4567/ownvehicles\" onclick=\"myJsFunc();\">* Ver mis Vehiculos </a><br><br>";
        page += "<a href=\"http://localhost:4567/insertaddress\" onclick=\"myJsFunc();\">* Agregar Direccion </a><br><br>";
        page += "<a href=\"http://localhost:4567/ownaddress\" onclick=\"myJsFunc();\">* Ver mis Direcciones </a><br><br>";
        page += "<a href=\"http://localhost:4567/post\" onclick=\"myJsFunc();\">* Ver Todos Los Posts </a><br><br>";
        page += "<a href=\"http://localhost:4567/admincontactuser\" onclick=\"myJsFunc();\">* Contactar con Admin </a></form>";
        page += "<br><br><a href=\"http://localhost:4567/loginuser\" onclick=\"myJsFunc();\"><form><input type=\"button\" value=\"Salir\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></form></body></html></a>";

        return page;
    }

   public String  admin() {
       String s ="<form action=\"Admin Control Panel\" method=\"post\">";

        s = s +"<br><br><div align=\"center\"> ADMIN CARSAPP<br><br>";

        s = s +"*<a href=\"http://localhost:4567/user\" onclick=\"myJsFunc();\"> Ver Usuarios </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/post\" onclick=\"myJsFunc();\"> Ver Post </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/insertuser\" onclick=\"myJsFunc();\"> Crear Usuario </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/admincp/blockuser\" onclick=\"myJsFunc();\"> Bloquear Usuario </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/admincp/eraseuserquestion\" onclick=\"myJsFunc();\"> Borrar pregunta de Usuario </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/admincp/eraseuseranswer\" onclick=\"myJsFunc();\"> Borrar respuesta de Usuario </a><br><br>";
        s = s +"*<a href=\"http://localhost:4567/inbox\" onclick=\"myJsFunc();\"> Bandeja de Mensajes </a><br><br>";
        s = s +"<a href=\"http://localhost:4567/loginuser\" onclick=\"myJsFunc();\"><form><input type=\"button\" value=\"Salir\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></form></body></html></a>";
        return s;
    }
    public String  guest() {
     String s ="<form action=\"Guest Control panel\" method=\"post\">";

      s = s +"<br><br><div align=\"center\"> INVITADO CARSAPP<br><br>";

      s = s + "<a href=\"http://localhost:4567/post\" onclick=\"myJsFunc();\"> * Ver todos los Posts </a><br><br>";
      s = s + "<a href=\"http://localhost:4567/admincontactguest\" onclick=\"myJsFunc();\">* Contactar con Admin </a></form>";
      s = s +"<br><br><a href=\"http://localhost:4567/loginuser\" onclick=\"myJsFunc();\"><form><input type=\"button\" value=\"Salir\"><html><body><body background=\"http://www.mis-dibujos-favoritos.com/Images/Large/Vehiculos-Coche-Ferrari-314491.png\" style='background-repeat:no-repeat;' /></form></body></html></a>";
      return s;
    }
   
}
