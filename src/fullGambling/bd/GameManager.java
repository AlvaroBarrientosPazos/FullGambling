package fullGambling.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import fullGambling.bd.UsuariosBD.USER;
import fullGambling.Room;
import java.util.Scanner;

public class GameManager {

    public static enum ROOM {
        ID(1),
        TYPE(2),
        ROOM_NAME(3),
        CREATED_AT(4),
        PASSWORD(5);

        private final int value;

        ROOM(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    static final String GAME_TABLE_NAME = "gameRoom";
    static final String PARTICIPANTS_TABLE_NAME = "gameParticipants";

    private static Scanner sc = new Scanner(System.in);


    public static void adminRoomManager(){

        showActiveRooms();
        int asd = sc.nextInt();

        sc.close();
    }



    private static void showActiveRooms(){
        Connection conexion = Conexion.conectar();
        ResultSet results = null;
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement("SELECT * FROM " + GAME_TABLE_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            results = statement.executeQuery();

            int arrIndex = 0;

            while (results.next()) {
                arrIndex ++;
            }

            Room[] rooms = new Room[arrIndex];

            results.beforeFirst();
            arrIndex = 0;

            while (results.next()) {
                arrIndex ++;
                rooms[arrIndex] = new Room(results.getInt(ROOM.ID.getValue()), results.getString(ROOM.ROOM_NAME.getValue()));

                System.out.println(arrIndex+". [ID: " + rooms[arrIndex].getId() + ", name: " + rooms[arrIndex].getName()+"]");
            }

            if (arrIndex == 0){
                System.out.println("No hay partidas activas\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }
    }

}
