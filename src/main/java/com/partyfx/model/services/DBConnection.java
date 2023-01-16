package com.partyfx.model.services;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3360/party";
    public static Properties prop;
    protected DBConnection() {
    }

    /**
     * Metodo que establece la conexion con la base de datos
     * @return Conexion con la base de datos
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        prop = PropertiesService.getProperties("hidden");
        if (connection == null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, prop.getProperty("db.user"), prop.getProperty("db.pass"));
        }
        return connection;
    }
}
