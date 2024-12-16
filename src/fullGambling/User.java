package fullGambling;

public class User {

    private int id;
    private String name, alias;
    private User[] friends = new User[0];
    
    private int chips;

    private int matchesPlayed;
    private int wins;
    private int wRatio;

    public User(){
        id = -1;
        name = "voidUser";
        alias = "User0";
        chips = 0;
    }

    public User( int userID, String userName){
        id = userID;
        name = userName;
    }

    public User( int userID, String userName, int userChips){
        this(userID, userName);
        chips = userChips;
    }

    public User( int userID, String userName, int userChips, User[] userFriends ){
        this(userID, userName, userChips);
        friends = userFriends;
    }

    public int getID(){
        return id;
    }

    public String getUserName(){
        return name;
    }

    public String getAlias(){
        return alias;
    }

    public int getChips(){
        return chips;
    }

    public int getWins(){
        return wins;
    }

    public void setAlias(String userAlias){
        alias = userAlias;
    }

    public int getMatchesPlayed(){
        return matchesPlayed;
    }

    public User[] getFriends(){
        return friends;
    }

    public boolean hasFriend(User userFriend){
        
        for (User friend : friends) {
            if (friend.equals(userFriend)) {
                return true;
            }
        }
        return false;

    }

    public void displayFriends(){
        System.out.println(friends);
    }

}