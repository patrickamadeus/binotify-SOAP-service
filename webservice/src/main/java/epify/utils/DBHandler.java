package webservice.src.main.java.epify.utils;

import java.lang.Thread.State;
import java.sql.*;

public class DBHandler {
    public void createSubscriptionDatabase(DBConn handler) throws Exception{
        try{
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS subscription (creator_id INT, subscriber_id INT, status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING', PRIMARY KEY (creator_id, subscriber_id))";
            statement.execute(sql);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error creating subscription database");
        }
    };

    public void createLoggingDatabase(DBConn handler) throws Exception{
        try{
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            // create logging table with timestamp
            String sql = "CREATE TABLE IF NOT EXISTS logging (id INT AUTO_INCREMENT, ip VARCHAR(255), endpoint VARCHAR(255), description VARCHAR(255), timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (id))";
            statement.execute(sql);
        }
        catch (Exception e){            
            e.printStackTrace();
            throw new Exception("Error creating logging database");
        }
    }

}
