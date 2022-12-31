package webservice.src.main.java.epify.models;

import java.sql.*;
import webservice.src.main.java.epify.utils.*;

public class Subscription {
    private Integer creator_id;
    private Integer subscriber_id;
    private DBConn dbConn;

    public Subscription(Integer creator_id, Integer subscriber_id) throws Exception{
        this.dbConn = new DBConn();

        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;

        // Create Database
        try{
            DBHandler handler = new DBHandler();
            handler.createSubscriptionDatabase(dbConn);
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed creating subscription database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }

    }
    
    public String createSubscriptionRequest() throws Exception{
        try{
            Connection conn = this.dbConn.getConnection();
            
            // Check if same key exist
            String sql = "SELECT * FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);
            formattedSql.setInt(1, this.creator_id);
            formattedSql.setInt(2, this.subscriber_id);
            ResultSet rs = formattedSql.executeQuery();
            if (rs.next()){
                String response = String.format("{\"status\": 400, \"message\": \"subscription request already exist\"}");
                return response;
            }
            
            // Insert new subscription request
            sql = "INSERT INTO subscription (creator_id, subscriber_id) VALUES (?, ?)";
            formattedSql = conn.prepareStatement(sql);
            formattedSql.setInt(1, this.creator_id);
            formattedSql.setInt(2, this.subscriber_id);

            formattedSql.executeUpdate();

            // return query result
            String response = String.format("{\"status\": 200, \"message\": \"subscription request created creator_id : %s subscriber_id : %s\"}", creator_id, subscriber_id);
            return response;   
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }
    };

    public String isSubscriptionValid() throws Exception{
        try{
            Connection conn = this.dbConn.getConnection();
            String sql = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);

            formattedSql.setInt(1, this.creator_id);
            formattedSql.setInt(2, this.subscriber_id);
            ResultSet rs = formattedSql.executeQuery();

            //if query result is APPROVED then true
            if (rs.next()){
                String status = rs.getString("status");
                if (status.equals("APPROVED")){
                    String response = String.format("{\"status\": 200, \"message\": \"subscription request approved\", \"value\": \"%s\"}", status);
                    return response;
                }
                else{
                    String response = String.format("{\"status\": 400, \"message\": \"subscription request not approved\", \"value\": \"%s\"}", status);
                    return response;
                }
            }
            else{
                String response = String.format("{\"status\": 400, \"message\": \"subscription request not found\", \"value\": \"%s\"}", dbConn.convertResultSetToArray(rs));
                return response;
            }            
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }

    }

    public String acceptSubscription() throws Exception{
        
        try{
            Connection conn = this.dbConn.getConnection();
            // check status if pending
            String sql = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);

            formattedSql.setInt(1, this.creator_id);
            formattedSql.setInt(2, this.subscriber_id);
            ResultSet rs = formattedSql.executeQuery();

            //if query result is APPROVED then true
            if (rs.next()){
                String status = rs.getString("status");
                if (!status.equals("PENDING")){
                    // already validated
                    String response = String.format("{\"status\": 200, \"message\": \"already validated\", \"value\": \"%s\"}", status);
                    return response;
                }
                else{
                    // not yet validated
                    sql = "UPDATE subscription SET status = 'APPROVED' WHERE creator_id = ? AND subscriber_id = ?";

                    formattedSql = conn.prepareStatement(sql);
        
                    formattedSql.setInt(1, this.creator_id);
                    formattedSql.setInt(2, this.subscriber_id);
        
                    formattedSql.executeUpdate();
        
                    String response = String.format("{\"status\": 200, \"message\": \"subscription request approved\"}");
                    return response;
                }
            }else{
                String response = String.format("{\"status\": 400, \"message\": \"subscription request not found\", \"value\": %s}", dbConn.convertResultSetToArray(rs));
                return response;
            }

            
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\",\"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }
    
    }
    
    public String rejectSubscription() throws Exception{
        try{
            Connection conn = this.dbConn.getConnection();
            // check status if pending
            String sql = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement formattedSql = conn.prepareStatement(sql);

            formattedSql.setInt(1, this.creator_id);
            formattedSql.setInt(2, this.subscriber_id);

            ResultSet rs = formattedSql.executeQuery();

            //if query result is APPROVED then true
            if (rs.next()){
                String status = rs.getString("status");
                if (!status.equals("PENDING")){
                    // already validated
                    String response = String.format("{\"status\": 200, \"message\": \"already validated\", \"value\": \"%s\"}", status);
                    return response;
                }
                else{
                    // delete subscription request
                    sql = "DELETE FROM subscription WHERE creator_id = ? AND subscriber_id = ?";

                    formattedSql = conn.prepareStatement(sql);
        
                    formattedSql.setInt(1, this.creator_id);
                    formattedSql.setInt(2, this.subscriber_id);
        
                    formattedSql.executeUpdate();
        
                    String response = String.format("{\"status\": 200, \"message\": \"subscription request rejected\"}");
                    return response;
                }
            }else{
                String response = String.format("{\"status\": 400, \"message\": \"subscription request not found\", \"value\": %s}", dbConn.convertResultSetToArray(rs));
                return response;
            }
            
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }
    }

    public String listSubscription() throws Exception{

        try{
            Connection conn = this.dbConn.getConnection();

            String sql;
            PreparedStatement formattedSql;
            if (this.creator_id != null){
                sql = "SELECT * FROM subscription WHERE creator_id = ?";
                formattedSql = conn.prepareStatement(sql);
                formattedSql.setInt(1, this.creator_id);
            }
            else if (this.subscriber_id != null){
                sql = "SELECT * FROM subscription WHERE subscriber_id = ?";
                formattedSql = conn.prepareStatement(sql);
                formattedSql.setInt(1, this.subscriber_id);
            }
            else{
                sql = "SELECT * FROM subscription WHERE status = 'PENDING'";
                formattedSql = conn.prepareStatement(sql);
            }

            ResultSet rs = formattedSql.executeQuery();
            String result = dbConn.convertResultSetToArray(rs);

            // return result  
            String response = String.format("{\"status\": 200, \"message\": \"query success\", \"value\": %s}", result);

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"DB error. Failed to connect to database\", \"error\": \"%s\"}", e.getMessage());

            throw new Exception(response);
        }

    };    
}
