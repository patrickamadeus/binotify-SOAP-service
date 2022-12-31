package webservice.src.main.java.epify.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class DBConn {
    private Connection conn;
    private String DB_URL;
    private String DB_Username;
    private String DB_Password;

    public DBConn(){
        Dotenv dotenv = Dotenv.load();
        this.DB_URL = dotenv.get("DB_URL");
        this.DB_Username = dotenv.get("DB_Username");
        this.DB_Password = dotenv.get("DB_Password");

        try{
            System.out.println("Connecting to MySql Server");
            this.conn = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
            System.out.println("Connected to MySql Server");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to MySql Server");
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    
    // convert query result into array
    public String convertResultSetToArray(ResultSet rs) throws Exception{
        try{
            // get total length
            String result = "[";

            while (rs.next()) {
                result += "{";

                result += "\"creator_id\": " + rs.getString(1) + ",";
                result += "\"subscriber_id\": " + rs.getString(2) + ",";
                result += "\"status\": \"" + rs.getString(3) + "\"";

                result += "}";

                if (rs.isLast()){
                    result += "";
                }
                else{
                    result += ",";
                }
            }
            result 
            += "]";
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error converting result set to array");
        }
    }
}
