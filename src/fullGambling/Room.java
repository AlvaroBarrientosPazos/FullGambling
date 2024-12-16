package fullGambling;

public class Room {

    enum GAME_TYPE{
        ROULETTE(1),
        LOW_ROLL(2);

        private final int value;

        GAME_TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    int id;
    GAME_TYPE type;
    String name;

    // TODO: Multi type game Rooms
    public Room(int roomId, String roomName){
        id = roomId;
        name = roomName;
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

    

    
}
