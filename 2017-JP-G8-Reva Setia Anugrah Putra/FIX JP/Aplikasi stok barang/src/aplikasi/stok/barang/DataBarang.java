/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.stok.barang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kury
 */
public class DataBarang {
    
     private static Connection connection;
    
    public static Connection getConnection(){
        if(connection==null){
            try{
            String url = "jdbc:mysql://localhost:3306/tokoreva";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = (com.mysql.jdbc.Connection) DriverManager.getConnection(url,"root","");
            System.out.println("sukses koneksi");
            }catch(SQLException e){
                System.out.println("Koneksi Gagal\n"+e);
            }
        }
        return connection;
    }
    
}
