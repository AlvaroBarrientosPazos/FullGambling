package fullGambling;

import java.util.ArrayList;

public class Room {

    public static enum GAME_TYPE{
        ROULETTE(1),
        LOW_ROLL(2);

        private final int value;

        GAME_TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static GAME_TYPE fromValue(int value) {
            for (GAME_TYPE gameType : GAME_TYPE.values()) {
                if (gameType.getValue() == value) {
                    return gameType;
                }
            }
            throw new IllegalArgumentException("GAME_TYPE valor Invalido: " + value);
        }

    }

    private int id;
    private GAME_TYPE type;
    private String name;
    private boolean isPrivate;
    private ArrayList<User> users = new ArrayList<User>();

    // TODO: Multi type game Rooms
    public Room(int roomId, String roomName, GAME_TYPE gameType){
        id = roomId;
        name = roomName;
        type = gameType;
    }

    public int getId() {
        return id;
    }

    public GAME_TYPE getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void populateRoomUsers(){
    }

    public String getVerboseInfo(){
        return String.format(
            "%s [ nombre: %s, juego: %s ]",
            isPrivate()?"[privada]":"[publica]",
            name, 
            type.name().toLowerCase());
    }
 
    public boolean isPrivate(){
        return isPrivate;
    }


}
