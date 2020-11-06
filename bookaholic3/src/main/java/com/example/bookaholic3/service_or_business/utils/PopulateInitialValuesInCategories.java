package com.example.bookaholic3.service_or_business.utils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.atomic.LongAccumulator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class PopulateInitialValuesInCategories {
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
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("categories.json"));
            //Retrieving the array
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");
            Connection con = ConnectToDB();
            //Insert a row into the MyPlayers table
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO books values (?, ?, ?, ?, ?, ? )");
            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                long id = (Long) record.get("id");
                String name = (String) record.get("name");
                String address = (String) record.get("address");

                pstmt.setLong(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, address);

                // pstmt.setLong(7, authorId);
                pstmt.executeUpdate();
            }

            System.out.println("Records inserted.....");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}