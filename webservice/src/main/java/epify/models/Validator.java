package webservice.src.main.java.epify.models;

import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;

import webservice.src.main.java.epify.utils.*;

public class Validator {
    public String isSubscriptionValid(Integer creator_id, Integer subscriber_id){
        
        DBConn dbConn = new DBConn();
        
        // Create Database
        try{
            DBHandler handler = new DBHandler();
            handler.createSubscriptionDatabase(dbConn);
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed creating subscription database\", \"error\": \"%s\"}", e.getMessage());

            return response;
        }

        try{
            Connection conn = dbConn.getConnection();
            String sql = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);

            formattedSql.setInt(1, creator_id);
            formattedSql.setInt(2, subscriber_id);
            ResultSet rs = formattedSql.executeQuery();

            //if query result is APPROVED then true
            if (rs.next()){
                String status = rs.getString("status");
                if (status.equals("APPROVED")){
                    String response = String.format("{\"status\": 200, \"message\": \"subscription request approved\", \"value\": %s}", status);
                    return response;
                }
                else{
                    String response = String.format("{\"status\": 400, \"message\": \"subscription request not approved\", \"value\": %s}", status);
                    return response;
                }
            }
            else{
                String response = String.format("{\"status\": 400, \"message\": \"subscription request not found\", \"value\": %s}", dbConn.convertResultSetToArray(rs));
                return response;
            }            
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            return response;
        }

    }

    public String validateSubscription(Integer creator_id, Integer subscriber_id){
        
        DBConn dbConn = new DBConn();
        
        // Create Database
        try{
            DBHandler handler = new DBHandler();
            handler.createSubscriptionDatabase(dbConn);
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed creating subscription database\", \"error\": \"%s\"}", e.getMessage());

            return response;
        }

        try{
            Connection conn = dbConn.getConnection();
            String sql = "UPDATE subscription SET status = 'APPROVED' WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);

            formattedSql.setInt(1, creator_id);
            formattedSql.setInt(2, subscriber_id);
            Boolean result = formattedSql.execute();

            String response = String.format("{\"status\": 200, \"message\": \"subscription request approved\", result: \"%s\"}", result);
            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            return response;
        }
    
    }

}
