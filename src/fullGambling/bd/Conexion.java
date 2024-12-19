package fullGambling.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fullGambling.Util;

class Conexion {

    ///*// Base de datos de Producción (filess.io)
    static final String HOST = "7ce-h.h.filess.io";
    static final String DATABASE = "GambleApp_alphabetgo";
    static final String USER = "GambleApp_alphabetgo";
    static final String PASSWORD = "f5af64d6ee29ad25dda953a6bae1ebd758c24b00";
    //*/
    /* 
    static final String HOST = "3mazn.h.filess.io";
    static final String DATABASE = "FullGamba_comingkind";
    static final String USER = "FullGamba_comingkind";
    static final String PASSWORD = "8e0cd6e25b70c8ff35d14565b3dd62207a98d60a";
    //*/
    static final String PORT = "3305";
 

    public static Connection conexion;

    /**
     * Conecta con la base de datos
     * 
     * @return Conexión con la base de datos
     */
    public static Connection conectar() {
        Connection con = null;

        String url = "jdbc:mysql://" + Conexion.HOST + ":" + Conexion.PORT + "/" + Conexion.DATABASE;

        try {
            con = DriverManager.getConnection(url, Conexion.USER, Conexion.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD.");
        }

        return con;
    }


    public static void closeConnection(Connection conexion, PreparedStatement statement) {
        try {
            if (conexion != null) {
                conexion.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        }

    }

    
    public static void closeConnection(Connection conexion, PreparedStatement statement, ResultSet results) {
        try {
            if (conexion != null) {
                conexion.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (results != null) {
                results.close();
            }
        } catch (SQLException e) {
            Util.printStackTrace(e);
        }

    }


}