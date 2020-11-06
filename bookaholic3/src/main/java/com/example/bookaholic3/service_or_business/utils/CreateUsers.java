package com.example.bookaholic3.service_or_business.utils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.LongAccumulator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.repository.Query;

public class CreateUsers {
//    String sql = "CREATE TABLE users " +
//            "(username VARCHAR(100) not NULL, " +
//            " password VARCHAR(100), NULL " +
////            " isAccountNonExpired BIT(1),  NULL" +
////            " isAccountNonLocked BIT(1),  NULL" +
////            " isCredentialsNonExpired BIT(1) , NULL"+
////            " isEnabled, BIT(1), NULL" +
//            " PRIMARY KEY ( username ))";
    String sql = "CREATE TABLE users ";

    public static Connection ConnectToDB() throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //Getting the connection
//        String mysqlUrl = "jdbc:mysql://localhost/mydatabase";
        String mysqlUrl = "jdbc:mysql://localhost:3306/autismapp?serverTimezone=UTC";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "admin");
        System.out.println("Connection established......");
        return con;
    }
    public void init() {
        //Creating a JSONParser object
        try {
            //Parsing the contents of the JSON file
            Connection con = ConnectToDB();
            //Insert a row into the MyPlayers table
            Statement statement = con.createStatement();
            statement.execute(sql);
            System.out.println("Records inserted.....");
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
