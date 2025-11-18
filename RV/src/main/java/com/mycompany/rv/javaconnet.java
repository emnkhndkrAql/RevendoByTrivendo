
package com.mycompany.rv;

import java.sql.* ;
public class javaconnet {
    
    Connection conn;
    public static Connection connectDB (){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/ReVendo","root","");
            System.out.println("Connected Successfull");
            return conn;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    
    
}
