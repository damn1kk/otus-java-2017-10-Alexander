package ru.otus.homework11.dbService.jdbcService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getMySQLConnection(){
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +
                    "localhost:" +
                    "3306/" +
                    "homeworkDB?" +
                    "user=root&" +
                    "password=root&" +
                    "serverTimezone=UTC&" +
                    "useSSL=false";

            return DriverManager.getConnection(url);
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public static Connection getH2Connection(){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        String url = "jdbc:h2:./h2database/homeworkdb";

        try {
            return DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
