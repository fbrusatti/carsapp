package com.unrc.app;

public class WebStuff {//no se me ocurria otro fucking nombre, busquemos de cambiarlo
	
	public static String link (String t,String l,String d){  //returns a link with text
		return "<html> <header> <title>" +t+"</title> </header> <body> <a href=\""+l+"\">"+d+"</a> </body> </html>";
	};

	public static String form (String t,String[] a,String action, String method){    //provides a html form with title, array of fields, an action and a method
		String f = "<html> <head> <title> "+t+" </title> </head> <body> <form action=\""+action+"\" method=\""+method+"\">";
		for (int i=0;i<a.length ;i++ ) {
			f=f+"\n "+a[i]+"\n <input type= \"text\" name=\""+a[i]+"\"><br> ";
		};
		f=f+"\n <input type=\"submit\" value= \"Guardar\"> </form> </body> </html>";
		return f;
	}
}