package fullGambling.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import fullGambling.User;
import fullGambling.Util;

public class UsersBD {

    public static enum USER {
        ID(1),
        USER_NAME(2),
        PASSWORD(3),
        CREATED_AT(4),
        CHIPS(5),
        PERMISSIONS(6),
        BAN(7);

        private final int value;

        USER(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static enum USER_COMPLEXITY{
        LOW,
        HIGH,
    }


    public static final String USERS_TABLE_NAME = "user";
    public static final String FRIENDSHIP_TABLE_NAME = "friendships";


    public static int hasUser(String username) {

        String query = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE username COLLATE utf8mb4_bin = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        int userId = -1;

        try {
            statement = conexion.prepareStatement(query);
            statement.setString(1, username);
            results = statement.executeQuery();

            if (results.next()) {
                userId = results.getInt(USER.ID.getValue());
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return userId;
    }


    private static boolean hasUser(int userId) {
        
        String query = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE id = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        boolean hasUser = false;

        try {
            statement = conexion.prepareStatement(query);
            statement.setInt(1, userId);

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

    public static User getUserDataById(int userId) {
        
        String query = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE id = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        User user = null;

        try {
            statement = conexion.prepareStatement(query);
            statement.setInt(1, userId);

            results = statement.executeQuery();

            if (results.next()) {
                user = buildUser(userId, results.getString(USER.USER_NAME.getValue()), results.getInt(USER.CHIPS.getValue()), results.getBoolean(USER.PERMISSIONS.getValue()));
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return user;
    }


    /**
     * Cambia la contraseña de un usuario
     * 
     * @param username Usuario
     * @param password Nueva contraseña
     * @return true si se cambió la contraseña
     */
    public static boolean changePassword(String username, String password) {
        boolean wasPasswordChanged = false;
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement("UPDATE "+USERS_TABLE_NAME+" SET password = ? WHERE username LIKE ?");
            statement.setString(1, Util.generarStringHash2Y(password));
            statement.setString(2, username);

            if (statement.executeUpdate() == 1) {
                wasPasswordChanged = true;
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement);
        }

        return wasPasswordChanged;
    }


    public static boolean updateChips(User user, int quantity){
        boolean wereChipsUpdated = false;
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement("UPDATE "+USERS_TABLE_NAME+" SET chips = ? WHERE id LIKE ?");
            statement.setInt(1, quantity);
            statement.setInt(2, user.getID());

            if (statement.executeUpdate() == 1) {
                wereChipsUpdated = true;
                user.setChips(quantity);
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement);
        }

        return wereChipsUpdated;
    }

    /**
     * Inicia sesión de usuario
     * Solicita credenciales de inicio de sesión, y devuelve una boolean 
     * dependiendo de si la operación fue exitosa o no.
     *
     * @param userName Nombre de usuario 
     * @param password Contraseña
     * @return loginOk resultado de la operación con el resultado de la operación.
     */
    public static User ProcessUserLogin(String userName, String password) {
        String query = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE username COLLATE utf8mb4_bin = ?";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;
        
        User user = null;
        boolean loginOk = false;

        try {
            statement = conexion.prepareStatement(query);
            statement.setString(1, userName);
            results = statement.executeQuery();

            if (results.next()) {
                loginOk = Util.verifyPassword(password,results);
            }

            if (loginOk) {
                user = buildUserByComplexity(null, results, USER_COMPLEXITY.HIGH);
                populateUserFriends(user, conexion, statement, results);

            } else {
                Util.warning("Usuario o contraseña incorrectos");
            }
        } catch (SQLException e) {
            Util.printStackTrace(e);
        }
        finally{
            Conexion.closeConnection(conexion, statement,results);
        }

        return user;

    }


    public static void populateUserFriends(User user, Connection conexion, PreparedStatement statement, ResultSet results){

        int userId1;
        
        try {
            statement.clearParameters();
            
            statement = conexion.prepareStatement("SELECT * FROM " + FRIENDSHIP_TABLE_NAME + " WHERE (user_id1 = ? OR user_id2 = ?)");
            statement.setInt(1, user.getID());
            statement.setInt(2, user.getID());
            
            results = statement.executeQuery();

            while (results.next()) {
                userId1 = results.getInt(1);
                
                if (userId1 == user.getID()){
                    user.appendFriend(getUserData( null, results.getInt(2), USER_COMPLEXITY.LOW));
                    
                }
                else{
                    user.appendFriend(getUserData( null, userId1, USER_COMPLEXITY.LOW));
                }
            }
        }
        catch (SQLException e) {
            Util.printStackTrace(e);
        }
    }


    public static boolean deleteUserFriend(User user, User friend){
        String query = "DELETE FROM "+FRIENDSHIP_TABLE_NAME+" WHERE (user_id1 = ? AND user_id2 = ?)";
        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;

        int userId = user.getID();
        int friendId = friend.getID();

        boolean wasFriendDeleted = false;

        try {

            statement = conexion.prepareStatement(query);

            if (userId < friendId){
                statement.setInt(1, userId);
                statement.setInt(2, friendId);
            }
            else{
                statement.setInt(1, friendId);
                statement.setInt(2, userId);
            }
            
            if (statement.executeUpdate() == 1) {
                wasFriendDeleted = true;
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement);
        }

        return wasFriendDeleted;
    }


    /**
     * Crea un nuevo usuario
     * Solicita credenciales de nuevo usuario, y si se crea correctamente devuelve
     * el nombre del usuario
     * 
     * @return el nombre si se creó el usuario
     */
    public static boolean insertUser(String userName, String password) {

        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        boolean creationOk = false;

        try {
            String query = "INSERT INTO "+USERS_TABLE_NAME+" (username, password) VALUES (?, ?)";
            String hashedPassword = Util.generarStringHash2Y(password);

            statement = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, userName);
            statement.setString(2, hashedPassword);

            statement.executeUpdate();

            results = statement.getGeneratedKeys();

            if (results.next()) {
                creationOk = true;
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return creationOk;
    }


    public static User addFriend(User user, String friendUserName) {

        User userFriend = null;
        userFriend = getUserData(userFriend, friendUserName);

        if (userFriend != null) {

            if (user.hasFriend(userFriend)) {
                System.out.println("Ya tienes agregado al usuario: " + friendUserName);
                return null;
            } else {
                
                String query = "INSERT INTO "+FRIENDSHIP_TABLE_NAME+" (user_id1,user_id2) VALUES (?, ?)";
                Connection conexion = Conexion.conectar();
                PreparedStatement statement = null;

                try {
                    
                    statement = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

                    if (user.getID() < userFriend.getID()) {
                        statement.setInt(1, user.getID());
                        statement.setInt(2, userFriend.getID());
                    } else {
                        statement.setInt(1, userFriend.getID());
                        statement.setInt(2, user.getID());
                    }

                    if (statement.executeUpdate() == 1) {
                        user.appendFriend(userFriend);
                    }

                } catch (SQLException e) {
                    Util.printStackTrace(e);
                } finally {
                    Conexion.closeConnection(conexion, statement);
                }
            }
        }

        return userFriend;
    }


    private static User getUserData(User user, String userName) {

        int userId = hasUser(userName);

        if (userId != -1) {
            user = getUserData(user, userId, USER_COMPLEXITY.LOW);
        }

        return user;

    }


    private static User getUserData( User user, int userId, USER_COMPLEXITY userComplexity) {

        Connection conexion = Conexion.conectar();
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            statement = conexion.prepareStatement("SELECT * FROM "+USERS_TABLE_NAME+" WHERE id = ?");
            statement.setInt(1, userId);

            results = statement.executeQuery();

            if (results.next()) {
                user = buildUserByComplexity(user, results, userComplexity);
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }

        return user;
    }


    private static User buildUserByComplexity(User user, ResultSet results, USER_COMPLEXITY userComplexity){
        try{
            user = switch (userComplexity) {
                case USER_COMPLEXITY.LOW -> buildUser(results.getInt(USER.ID.getValue()), results.getString(USER.USER_NAME.getValue()));
                case USER_COMPLEXITY.HIGH -> buildUser(results.getInt(USER.ID.getValue()), results.getString(USER.USER_NAME.getValue()), results.getInt(USER.CHIPS.getValue()), results.getBoolean(USER.PERMISSIONS.getValue()));
                default -> null;
            };
        }
        catch(SQLException e){
            Util.printStackTrace(e);
        }
        return user;
    }

    private static User buildUser(int userId, String userName) {
        return new User(userId, userName);
    }

    private static User buildUser(int userId, String userName, int userChips, boolean userPermissions) {
        return new User(userId, userName, userChips, userPermissions);
    }

    /**
     * Lista los usuarios de la base de datos
     */
    public static void showUsers() {
        Connection conexion = Conexion.conectar();
        ResultSet results = null;
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement("SELECT * FROM " + USERS_TABLE_NAME);
            results = statement.executeQuery();

            int id, chips;
            String userName;
            Timestamp createdAt;

            while (results.next()) {
                id = results.getInt(USER.ID.getValue());
                userName = results.getString(USER.USER_NAME.getValue());
                createdAt = results.getTimestamp(USER.CREATED_AT.getValue());
                chips = results.getInt(USER.CHIPS.getValue());

                System.out.println(
                        "ID: " + id + ", username: " + userName + ", createdAt: " + createdAt + " chips: " + chips);
            }

        } catch (SQLException e) {
            Util.printStackTrace(e);
        } finally {
            Conexion.closeConnection(conexion, statement, results);
        }
    }

}
