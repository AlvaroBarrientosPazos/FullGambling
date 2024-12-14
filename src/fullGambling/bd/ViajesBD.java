package fullGambling.bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViajesBD {

    public static boolean crearViaje(String usuario, String fechaHora, String origen, String destino, int plazas) {
        Connection conexion = Conexion.conectar();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate("INSERT INTO viaje (usuario, fecha_hora, origen, destino, plazas) VALUES ('"
                    + usuario + "', '" + fechaHora + "', '" + origen + "', '" + destino + "', " + plazas + ")");
            sentencia.close();
            conexion.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int listarProximosViajesConPlazas() {
        Connection conexion = Conexion.conectar();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            sentencia.executeQuery("SELECT * FROM viaje WHERE fecha_hora > NOW() AND plazas > 0");
            ResultSet resultado = sentencia.getResultSet();
            int numRegistros = 0;
            if (resultado != null) {
                // Imprime cabecera
                System.out.println("Viajes disponibles:");
                System.out.println("ID\tUsuario\tFecha y hora\t\tPlazas\tOrigen\tDestino");
                while (resultado.next()) {
                    // Obtiene los datos del registro actual
                    int id = resultado.getInt("id");
                    String usuario = resultado.getString("usuario");
                    String fechaHora = resultado.getString("fecha_hora");
                    String origen = resultado.getString("origen");
                    String destino = resultado.getString("destino");
                    int plazas = resultado.getInt("plazas");

                    // Procesa los datos
                    System.out.println(id + "\t" + usuario + "\t" + fechaHora
                            + "\t" + plazas + "\t" + origen + "\t" + destino);
                    numRegistros++;
                }
            }
            resultado.close();
            sentencia.close();
            conexion.close();
            return numRegistros;
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean anhadirPasajero(int numViaje, String usuario) {
        Connection conexion = Conexion.conectar();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(
                    "INSERT INTO pasajero (viaje, usuario) VALUES (" + numViaje + ", '" + usuario + "')");
            sentencia.close();
            conexion.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
