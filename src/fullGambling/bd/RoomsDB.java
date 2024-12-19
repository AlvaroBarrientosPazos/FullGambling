package fullGambling.bd;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fullGambling.Util;
import fullGambling.User;

import fullGambling.Room;

public class RoomsDB {

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

    public static enum LOW_ROLL_ROOM {
        ID(1),
        GAME_ID(2),
        USER_ID(3),
        GUESS(4),
        CHIPS(5);

        private final int value;

        LOW_ROLL_ROOM(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }


    final static String GAME_ROOM_TABLE_NAME     = "gameRoom"; 
    final static String LOW_ROLL_ROOM_TABLE_NAME = "lowRollUsers";



    public static Room createRoom(String roomName, String password, Room.GAME_TYPE game_type){
        
        String query = "INSERT INTO "+GAME_ROOM_TABLE_NAME+" (type, room_name, password) VALUES (?, ?, ?)";
        Connection conexion = Conexion.conexion;
        PreparedStatement statement = null;
        ResultSet results = null;

        Room room = null;


        try {
            

            statement = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, game_type.getValue());
            statement.setString(2, roomName);
            if (password != null){
                statement.setString(3, Util.generarStringHash2Y(password) );
            }
            else{
                statement.setString(3, password );
            }

            statement.executeUpdate();

            results = statement.getGeneratedKeys();

            if (results.next()) {
                room = new Room(results.getInt(ROOM.ID.getValue()), roomName, game_type );
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return room;
    }


    public static boolean insertUserToRoom(User user, int game_id, int chips, int guess){

        String query = "INSERT INTO "+LOW_ROLL_ROOM_TABLE_NAME+" (game_id, user_id, guess, chips) VALUES ( ?, ?, ?, ? )";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        boolean isUserAdded = false;

        try {
            statement = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, game_id);
            statement.setInt(2, user.getID());
            statement.setInt(3, guess);
            statement.setInt(4, chips);
            
            statement.executeUpdate();

            results = statement.getGeneratedKeys();

            if (results.next()) {
                isUserAdded = true;
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return isUserAdded;    
    }

    public static boolean hasRelationship(User user, Room room){
        String query = "SELECT 1 FROM "+LOW_ROLL_ROOM_TABLE_NAME+" WHERE game_id = ? AND user_id = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        boolean hasUser = false;

        try {
            statement = conexion.prepareStatement(query);
            statement.setInt(1, room.getId());
            statement.setInt(2, user.getID());

            results = statement.executeQuery();

            if (results.next()) {
                hasUser = true;
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return hasUser;
    }


    public static ArrayList<User> getRoomUsers(Room room){
        String query = "SELECT * FROM "+LOW_ROLL_ROOM_TABLE_NAME+" WHERE game_id = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        ArrayList<User> roomUsers = new ArrayList<User>();


        try {
            statement = conexion.prepareStatement(query);
            statement.setInt(1, room.getId());

            results = statement.executeQuery();

            while(results.next()) {
                roomUsers.add( UsersBD.getUserDataById(results.getInt("user_id")));
                roomUsers.get(roomUsers.size()-1).setCurrGuess(results.getInt("guess"));
                roomUsers.get(roomUsers.size()-1).setCurrBet(results.getInt("chips"));
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return roomUsers;
    }

}
