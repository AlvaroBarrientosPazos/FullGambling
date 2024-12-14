package fullGambling;

public class User {

    private int id;
    private String userName, alias;
    private Friend[] friends;
    
    private int chips;

    private int matchesPlayed;
    private int wins;
    private int wRatio;

    public User(){
        id = -1;
        userName = "voidUser";
        alias = "User0";
        chips = 0;
    }

    public User( int userID, String userName){
        id = userID;
        userName = userName;
    }

    public User( int userID, String userName, int userChips){
        this();
        chips = userChips;
    }

    public User( int userID, String userName, int userChips, Friend[] userFriends ){
        this(userID, userName, userChips);
        friends = userFriends;
    }

    public String getUserName(){
        return userName;
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

    public int getMatchesPlayed(){
        return matchesPlayed;
    }

    public void displayFriends(){
        System.out.println(friends);
    }

}