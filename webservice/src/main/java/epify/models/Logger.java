package webservice.src.main.java.epify.models;

import java.sql.*;
import webservice.src.main.java.epify.utils.*;
import java.util.regex.*;

public class Logger {
    private String ip;
    private String endpoint;
    private String description;
    private DBConn dbConn;

    public Logger(String ip, String endpoint, String description) throws Exception{
        this.dbConn = new DBConn();

        this.ip = ip;
        this.endpoint = endpoint;
        this.description = splString(description);
        
        // Create Database
        try{
            DBHandler handler = new DBHandler();
            handler.createLoggingDatabase(dbConn);
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed creating subscription database\", \"error\":['%s]}", e.getMessage());

            throw new Exception(response);
        }

    }

    public String log() throws Exception{
        try{
            // insert to db
            Connection conn = this.dbConn.getConnection();
            String sql = "INSERT INTO logging (ip, endpoint, description) VALUES (?, ?, ?)";
            PreparedStatement formattedSql = conn.prepareStatement(sql);
            formattedSql.setString(1, this.ip);
            formattedSql.setString(2, this.endpoint);
            formattedSql.setString(3, this.description);
            formattedSql.executeUpdate();

            // return query result
            String response = String.format("{\"status\": 200, message: \"log created\"");

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }

    }

    public String splString(String str){
        String[] arrOfStr = str.split(",", 3);
        
        String result = arrOfStr[0] + ", " + arrOfStr[1] + "}";


        return result;
    }
}
