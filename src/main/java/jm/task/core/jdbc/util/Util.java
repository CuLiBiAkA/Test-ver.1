package jm.task.core.jdbc.util;

import java.sql.*;



public class Util {

    static final String URL = "jdbc:mysql://localhost:3307/test?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "culuc111653culuc";
    private static Connection con;


    public static Connection connect() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                if (!con.isClosed()) {
                    System.out.println("Соеденение с базой установлено");
                } else {
                    System.out.println("Соеденение с базой не установлено");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}

