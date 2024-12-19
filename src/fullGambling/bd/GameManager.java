package fullGambling.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fullGambling.Room;
import fullGambling.Room.GAME_TYPE;
import fullGambling.User;
import fullGambling.Dice;
import fullGambling.Util;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


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


    public static ArrayList<Room> DBActiveRooms(){
        Connection conexion = Conexion.conectar();
        ResultSet results = null;
        PreparedStatement statement = null;
        
        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            statement = conexion.prepareStatement("SELECT * FROM " + GAME_TABLE_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            results = statement.executeQuery();

            Room room = null;
            int index = 0;

            while (results.next()) {
                room = new Room(
                    results.getInt(ROOM.ID.getValue()), 
                    results.getString(ROOM.ROOM_NAME.getValue()), 
                    Room.GAME_TYPE.fromValue( results.getInt( ROOM.TYPE.getValue() ) )
                    );
                rooms.add(room);

                index++;
                System.out.println(index+". "+room.getVerboseInfo());
                
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return rooms;
    }


    public static ArrayList<Room> DBActiveRooms(GAME_TYPE gameType){
        Connection conexion = Conexion.conectar();
        ResultSet results = null;
        PreparedStatement statement = null;
        
        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            statement = conexion.prepareStatement("SELECT * FROM " + GAME_TABLE_NAME+" WHERE type = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, gameType.getValue());
            results = statement.executeQuery();

            Room room = null;
            int index = 0;

            while (results.next()) {
                room = new Room( results.getInt(ROOM.ID.getValue()), results.getString(ROOM.ROOM_NAME.getValue()), gameType );
                rooms.add(room);

                index++;
                System.out.println(index+". "+room.getVerboseInfo());
                
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return rooms;
    }


    public static void tragaperras(double dineroCuenta) {
        
        // Símbolos posibles en la tragaperras
        String[] simbolos = { "1", "2", "3", "7", "4",};
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("¡Bienvenido a la tragaperras!");
        System.out.println("Pulsa ENTER para jugar (o escribe 'salir' para terminar).");
        
        boolean seguir = true;
        
        while (seguir) {
            System.out.println(dineroCuenta);
            
            if (dineroCuenta > 0) {
                System.out.println("1- Jugar");
                System.out.println("2- Salir");
                int input = sc.nextInt();

                switch (input) {
                    case 1:
                        System.out.println("Cuanto fichas quieres meter?");
                        int dinero = sc.nextInt();
                        if (dinero <= dineroCuenta) {
                            dineroCuenta = dineroCuenta - dinero;
                           
                            // Generar los símbolos de las 3 ruedas 
                            String rueda1 = simbolos[random.nextInt(simbolos.length)];
                            String rueda2 = simbolos[random.nextInt(simbolos.length)];
                            String rueda3 = simbolos[random.nextInt(simbolos.length)];
                            
                            // Mostrar el resultado
                            System.out.println("-------------");
                            System.out.println("| " + rueda1 + " | " + rueda2 + " | " + rueda3 + " |");
                            System.out.println("-------------");
                            
                            // Calcular el resultado
                            if (rueda1.equals(rueda2) && rueda2.equals(rueda3)) {
                                System.out.println("¡Jackpot!  ¡Has ganado con " + rueda1 + "!");
                                System.out.println("Tu dinero se a multiplicado por 7");
                                dineroCuenta = dineroCuenta + dinero * 7;
                            } else if (rueda1.equals(rueda2) || rueda2.equals(rueda3) || rueda1.equals(rueda3)) {
                                System.out.println("¡Bien hecho! Dos símbolos coinciden. Felicidades!!");
                                System.out.println("Tu dinero se a multiplicado por 2");
                                dineroCuenta = dineroCuenta + dinero * 2;
                            } else {
                                System.out.println("No hay suerte esta vez. Inténtalo de nuevo. ");
                                System.out.println("Tu dinero se a perdido");
                                
                            }
                        } else {
                            seguir = false;
                            System.out.println("ERES UN TRAMPOSO");
                        }
                            break;
                    default:
                        seguir = false;
                        System.out.println("Cerrando Programa");
                        break;
                }
            } else {
                seguir = false;
                System.out.println("Cerrando Programa");
            }
        }
    }


    public static void adminExecuteGame(Room room){
        ArrayList<User> roomUsers = RoomsDB.getRoomUsers(room);

        switch (room.getType()) {
            case Room.GAME_TYPE.ROULETTE:
                break;

            case Room.GAME_TYPE.LOW_ROLL:
                executeLowRoll(roomUsers);
                break;
        
            default:
                break;
        }


    }

    private static void executeLowRoll(ArrayList<User> roomUsers){
        
        String topBorder="", top="", middle="", bottom="",bottomBorder="";
        Dice die;
        int roll, minRoll = 7;

        for (int i=0; i<3 ;i++){
            roll = (int)((Math.random() * 6) + 1);
            if (roll<minRoll){
                minRoll = roll;
            }
            die = new Dice(roll);
            topBorder += die.getTopBorder()+"  ";
            top += die.getTop()+"  ";
            middle += die.getMiddle()+"  ";
            bottom += die.getBottom()+"  ";
            bottomBorder += die.getBottomBorder()+"  ";

        }

        System.out.println("  "+topBorder);
        System.out.println("  "+top);
        System.out.println("  "+middle);
        System.out.println("  "+bottom);
        System.out.println("  "+bottomBorder);


        ArrayList<User> winners = new ArrayList<User>();

        int userGuess, difference, closestDifference = 7;

        for (User user : roomUsers) {
            userGuess = user.getCurrGuess();
            if (user.getCurrGuess()>minRoll){
                continue;
            }

            difference = Math.abs(userGuess - minRoll);

            if (userGuess == minRoll) {
                winners.add(user);
            }
            
            else if (difference < closestDifference) {
                closestDifference = difference;
                winners.clear();
                winners.add(user);
            }
            else if (difference == closestDifference) {
                closestDifference = difference;
                winners.add(user);
            }
        }

        if (winners.isEmpty()){
            System.out.println("No hay ganadores");
        }

        for (User winner : winners) {
            System.out.println("Ganador: "+winner.getUserName()+" "+ winner.getCurrGuess());
        }



        Util.awaitUserToContinue();
    }


}
