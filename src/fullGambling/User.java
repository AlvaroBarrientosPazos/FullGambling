package fullGambling;

import java.util.ArrayList;

public class User {

    private int id;
    private String name, alias;
    private ArrayList<User> friends = new ArrayList<User>(); //No se cargan los amigos de los amigos del usuario actual.
    
    private int chips;

    private int matchesPlayed;
    private int wins;
    private int wRatio;

    // TODO: refactor this properly
    private int currGuess;
    private int currBet;


    private boolean isAdmin = false;

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

    public User( int userID, String userName, int userChips, boolean userPermissions){
        this(userID, userName);
        chips = userChips;
        isAdmin = userPermissions;
    }

    public User( int userID, String userName, int userChips, boolean userPermissions, ArrayList<User> userFriends ){
        this(userID, userName, userChips,userPermissions);
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

    public void setChips(int quantity){
        chips = quantity;
    }

    public int getWins(){
        return wins;
    }

    public void setAlias(String userAlias){
        alias = userAlias;
    }

    public void setCurrGuess(int currGuess) {
        this.currGuess = currGuess;
    }

    public void setCurrBet(int currBet) {
        this.currBet = currBet;
    }

    public int getCurrGuess() {
        return currGuess;
    }

    public int getCurrBet() {
        return currBet;
    }

    public int getMatchesPlayed(){
        return matchesPlayed;
    }

    public ArrayList<User> getFriends(){
        return friends;
    }

    public void setFriends(ArrayList<User> userFriends){
        friends = userFriends;
    }

    public boolean hasFriend(User userFriend){
        
        for (User friend : friends) {
            if (friend.getID() == userFriend.getID()) {
                return true;
            }
        }
        return false;

    }

    public boolean hasAdminPermissions(){
        return isAdmin;
    }

    public void appendFriend(User userFriend){
        friends.add(userFriend);
    }


    public void showFriends(){

        for (int i = 0; i < friends.size(); i++){

            System.out.println((i+1)+". "+friends.get(i).getUserName());
        }
    }

}