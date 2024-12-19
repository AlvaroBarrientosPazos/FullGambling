// 87+271+195+478+916+96+74+131+162 = 2410 lineas de codigo.

package fullGambling;

import java.io.Console;
import java.util.ArrayList;

import fullGambling.bd.GameManager;
import fullGambling.bd.RoomsDB;
import fullGambling.bd.UsersBD;
import fullGambling.Util;
import fullGambling.Room.GAME_TYPE;

public class AppFullGambling {

    enum MENU_ID{
        START_MENU,
        USER_MENU,
        FRIENDS_MENU,
        PROFILE_MENU;
    }

    private static User currUser;


    public static void main(String[] args) {
        Util.getHardwareOS();
        mainMenu();
        Util.scanner.close();
    }


    private static void tittleLogoScreen(){
        Util.clearScreen();
        System.out.println("     _.--------,..");
        System.out.println("   ;:____________ `--,_");
        System.out.println("  (_.-(o)-,-,-.. `''--,_");
        System.out.println("    ''-.__)_)_,'`\\");
        System.out.println("        /||//| )-;");
        System.out.println("        ||\\ |/");
        System.out.println("        '' ' ");

        System.out.println("  - FullGamba v.0.1a -\n");
    }


    private static void mainMenu() {

        int userInput = 0;

        do {
            tittleLogoScreen();
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Crear usuario");
            System.out.println("0. Salir");
            userInput = Util.inputInt();

            switch (userInput) {

                case 1:
                    LogInMenu();
                    break;

                case 2:
                    CreateUserMenu();
                    break;

                case 0:
                    Util.scanner.close();    
                    Util.clearScreen();
                    System.out.println("Hasta pronto");
                    return;

                default:
                Util.clearScreen();
                    System.out.println("Opción invalida");
                    break;
            }

            if (currUser != null) {
                if (currUser.hasAdminPermissions()){
                    adminMenu();
                }
                else{
                    userMenu();
                }
                
            }

        } while (userInput != 0);
    }


    private static void LogInMenu() {
        
        String userName, password;

        do {
            Util.clearScreen();
            System.out.println("Iniciando Sesión");
            System.out.println("----------------------------------------");
            System.out.print("Usuario: ");
            userName = Util.scanner.nextLine();
            System.out.print("Contraseña: ");
            password = new String(System.console().readPassword());

            currUser = UsersBD.ProcessUserLogin(userName, password);

        } while (currUser == null);
        
    }


    private static void CreateUserMenu() {

        String userName, password;
    
            do {
                System.out.println("Creando Usuario");
                System.out.println("----------------------------------------");
                System.out.print("Usuario: ");
                userName = System.console().readLine();
                System.out.print("Contraseña: ");
                password = new String(System.console().readPassword());
        
                if (UsersBD.hasUser(userName) != -1) {
                    System.out.println("El nombre de usuario " + userName + " no está disponible.");
                }

                else if (UsersBD.insertUser(userName, password)){
                    currUser = UsersBD.ProcessUserLogin(userName, password);
                }
                
            } while (currUser == null);
        }


    private static void adminMenu(){
        int userInput;

        do {
            tittleBox("Menu de Administrador",2);
            System.out.println("Admin: " + currUser.getUserName() + "\n");
            System.out.println("1. Salas");
            System.out.println("2. Usuarios");
            System.out.println("0. Cerrar sesión");
            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    adminRoomManager();
                    break;

                case 2:
                    adminUserManager();
                    break;

                case 0:
                    currUser = null;
                    return;

                default:
                    Util.warning("[ Opción invalida ]");
                    break;
            }
        } while (userInput != 0);

        System.out.println("Fin del programa");
    }


    private static void userMenu() {
        int userInput;

        do {
            tittleBox("Menu de Usuario",2);
            System.out.println("Usuario: " + currUser.getUserName());
            System.out.println("Nº Fichas: " + currUser.getChips() + "c\n");
            System.out.println("1. Jugar");
            System.out.println("2. Tienda de fichas");
            System.out.println("3. Amigos");
            System.out.println("4. Confirgurar perfil");
            System.out.println("0. Cerrar sesión");
            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    gamesMenu();
                    break;

                case 2:
                    chipsShop();
                    break;

                case 3:
                    friendsMenu();
                    break;
                    
                case 4:
                    UserConfig();
                    break;

                case 0:
                    currUser = null;
                    return;

                default:
                    Util.warning("[ Opción invalida ]");
                    break;
            }
        } while (userInput != 0);

        System.out.println("Fin del programa");
    }

    private static void gamesMenu() {

        int userInput = -1;

        do {
            tittleBox("Menu de Juegos",3);
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. Ruleta");
            System.out.println("2. Low Roll");
            System.out.println("3. Salas");
            System.out.println("0. Volver atrás");

            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                Util.warning("Ruleta\nEsta funcionalidad aún no ha sido implementada");

                    break;

                case 2:
                    lowRollGame(currUser);
                    break;

                case 3:
                    roomSelectionMenu();
                    break;

                case 0:
                    return;

                default:
                    Util.warning("[ Opción invalida ]");
            }
        } while (userInput != 0);
        
    }


    private static void tittleBox(String text, int padding) {
        String border = "";
        String sides = "";
        
        for (int i= 0; i< text.length() + padding*2 + 2; i++){
            border+="*";
        }
        
        border +="\n";

        for (int i= 0; i< padding; i++){
            sides+="*";
        }
        
        Util.clearScreen();
        System.out.println(border+sides+" "+text+" "+sides+"\n"+border);
    }

    private static void lowRollGame(User user){
        
        int userInput;
        Room room = null;

        do{
            tittleBox("Low Roll",3);
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. Buscar sala");
            System.out.println("2. Crear sala");
            System.out.println("3. Reglas");
            System.out.println("0. Volver atrás");

            userInput = Util.inputInt();

            switch (userInput) {
                case 1:
                    System.out.println("Buscando sala...");
                    roomSelectionMenu(GAME_TYPE.LOW_ROLL);
                    break;

                case 2:
                    Util.clearScreen();
                    System.out.println("Creando Sala");
                    System.out.println("----------------------------------------");
                    
                    System.out.print("Nombre: ");
                    String RoomName = Util.scanner.nextLine();
                    String RoomPassword = null;

                    if (confirmationPopUp("[ ¿Quieres ponerle una contraseña a la sala? ]")){
                        System.out.print("Contraseña: ");
                        RoomPassword = new String(System.console().readPassword());
                    }
                    
                    room = RoomsDB.createRoom(RoomName, RoomPassword, Room.GAME_TYPE.LOW_ROLL);
                    playLowRoll(room);
                    break;

                case 3:
                    Util.clearScreen();
                    System.out.println("Low Roll Reglas");
                    System.out.println("----------------------------------------");
                    System.out.println("\nLos participantes deberán apostar una cantidad de fichas al valor más bajo que va a salir en 3 tiradas de dados de 6 caras");
                    System.out.println("gana el participante que haya apostado por el número más grande sin llegar a superar el valor de la mínima tirada de dados");
                    System.out.println("ganar con una apuesta al 6 significa llevarse el bote entero y ganar con una apuesta al 1 solo permite recuperar lo invertido");
                    System.out.println("Si existen varios ganadores con el mismo número el bote se repartida por igual entre los ganadores.");
                    System.out.println();
                    Util.awaitUserToContinue();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("[ Opción invalida ]");
                    break;
            }

        }while(true);
    }


    private static boolean playLowRoll(Room room){

        int userBet   = -1;
        int userGuess = -1;
        boolean opSucessful = false;

        do {
            tittleBox("Low Roll", 2);
            System.out.println("Nº Fichas: "+currUser.getChips());
            System.out.print("\n[ Introduce la cantidad que quieres apostar ]");
            System.out.print("\nApuesta: ");
            userBet = Util.inputInt(false);

            if (currUser.getChips() < userBet){
                Util.warning("[ No tienes suficientes fichas ]");
                if (confirmationPopUp("[ ¿Quieres comprar más fichas? ]")){
                    buyChips();
                }
                userBet=-1;
            }
            else{
                UsersBD.updateChips(currUser, currUser.getChips()-userBet);
            }

        }while(userBet==-1);

        do{
            tittleBox("Low Roll", 2);
            System.out.print("\n[ El número de dado al que quieres apostar ]");
            
            System.out.print("\nNúmero: ");
            userGuess = Util.inputInt(false);

            if (userGuess < 1 || userGuess > 6){
                tittleBox("Low Roll", 2);
                System.out.println("\n[ Introduce un valor valido para un dado de 6 caras ]");
                Util.awaitUserToContinue();
            }

        }while(userGuess==-1);

        if ( RoomsDB.insertUserToRoom(currUser, room.getId(), userBet, userGuess) ){
            Util.warning( String.format("Has apostado %dc al número %d\n¡Buena suerte!\n", userBet, userGuess ) );
            opSucessful = true;
        }
        else{
            Util.warning("Error: No se ha podido procesar la apuesta");
        }

        return opSucessful;
    }


    private static void chipsShop() {
        int userInput = -1;

        do{
            tittleBox("Tienda de Fichas", 3);
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. Comprar");
            System.out.println("2. Retirar");
            System.out.println("0. Volver atrás");

            userInput = Util.inputInt();

            switch (userInput) {
                case 1:
                    buyChips();
                    break;

                case 2:
                    sellChips();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opción invalida");
                    break;
            }

        }while(true);
    }

    
    private static void buyChips(){
        
        Util.clearScreen();
        
        int userInput = -1, quantity = 0;

        do{
            Util.clearScreen();
            System.out.println("Comprar Fichas");
            System.out.println("----------------------------------------");
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. 5€\t(50c)");
            System.out.println("2. 10€\t(100c)");
            System.out.println("3. 25€\t(255c)");
            System.out.println("4. 50€\t(600c)  +20%!!");
            System.out.println("5. 100€\t(1500c) +50%!!");
            System.out.println("0. Volver Atrás");

            userInput = Util.inputInt();

            if (userInput == 0) {
                return;
            }

            quantity = switch (userInput) {
                case 1 -> 5 * 10;

                case 2 -> 10 * 10;

                case 3 -> 25 * 10;

                case 4 -> (int)(50 * 10 * 1.2);  

                case 5 -> (int)(100 * 10 * 1.5);

                default -> {
                    System.out.println("Opcion Invalida");
                    yield -1;
                }
                    
            };

            boolean opCompleted = UsersBD.updateChips(currUser, currUser.getChips()+quantity);

            if (opCompleted){
                Util.warning("¡Compra confirmada!");
            }


        }while (userInput != 0);


    }

    // TODO: --------------------------------------------------------------------------------------------------------------

    private static void sellChips(){
        
    }

    // TODO: --------------------------------------------------------------------------------------------------------------

    private static void UserConfig() {
        int userInput;

        do {
            Util.clearScreen();
            System.out.println("*****************************");
            System.out.println("** Configuración de Perfil **");
            System.out.println("*****************************");
            System.out.println("Usuario: " + currUser.getAlias() + "\n");
            System.out.println("1. Cambiar Apodo");
            System.out.println("2. Tienda de Fichas");
            System.out.println("3. Lista de Amigos");
            System.out.println("4. Configurar Perfil");
            System.out.println("0. Volver atrás");
            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    System.out.println("Cambiando Apodo...");
                    System.out.println("Esta función aún no ha sido implementada, lo sentimos por las molestias");
                    Util.awaitUserToContinue();
                    break;

                case 2:
                    chipsShop();
                    break;

                case 3:
                    friendsMenu();
                    currUser.showFriends();
                    break;

                case 4:
                    System.out.println("Configurando Perfil...");
                    System.out.println("Esta función aún no ha sido implementada, lo sentimos por las molestias");
                    Util.awaitUserToContinue();
                    break;

                case 0:
                    System.out.println("Hasta pronto");
                    break;

                default:
                    System.out.println("Opción invalida");
                    Util.awaitUserToContinue();
                    break;
            }
        } while (userInput != 0);
    }


    private static void friendsMenu(){
        int userInput;

        do {
            Util.clearScreen();
            System.out.println("*********************");
            System.out.println("** Lista de Amigos **");
            System.out.println("*********************");

            System.out.println("------------------------------------------------------------");

            if (currUser.getFriends().isEmpty()){
                System.out.println("Aún no has añadido a ningún usuario a tu lista de amigos");
            }
            else{
                currUser.showFriends();
            }

            System.out.println("------------------------------------------------------------");
        
            System.out.println("\n1. Añadir");
            System.out.println("2. Borrar");
            System.out.println("0. Volver atrás");
            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    friendRequest();
                    break;

                case 2:
                    removeFriend();
                    break;

                case 0:
                    return;

                default:
                    Util.warning("La opción seleccionada no es valida");
                    break;
            }

        } while(userInput!=0);

    }


    public static void adminRoomManager(){

        int userInput = -1;

        ArrayList<Room> rooms;
        Room room = null;

        do{
            Util.clearScreen();
            System.out.println("------------------------------------------------------------");
            rooms = GameManager.DBActiveRooms();
            if (rooms.isEmpty()){
                System.out.println("  No se han encontrado salas activas");
            }
            System.out.println("------------------------------------------------------------");

            System.out.println("\n[ Entra introduciendo el número de la sala ]\n");
            System.out.println("0. Volver atrás");
            
            userInput = Util.inputInt();

            System.out.println();

            if (userInput == 0){
                return;
            }

            userInput-=1;

            if (userInput < 0 || userInput >= rooms.size()){
                System.out.println("La opción seleccionada no es valida");
            }
            else{
                    GameManager.adminExecuteGame(rooms.get(userInput));
                }

        } while(userInput!=0);

    }

    
    public static void roomSelectionMenu(){
        
        int userInput = -1;

        ArrayList<Room> rooms;
        Room room = null;

        do{
            Util.clearScreen();
            System.out.println("------------------------------------------------------------");
            rooms = GameManager.DBActiveRooms();
            if (rooms.isEmpty()){
                System.out.println("  No se han encontrado salas activas");
            }
            System.out.println("------------------------------------------------------------");

            System.out.println("\n[ Entra introduciendo el número de la sala ]\n");
            System.out.println("0. Volver atrás");
            
            userInput = Util.inputInt();

            System.out.println();

            if (userInput == 0){
                return;
            }

            userInput-=1;

            if (userInput < 0 || userInput >= rooms.size()){
                System.out.println("La opción seleccionada no es valida");
            }
            else{
                room = rooms.get(userInput);

                if (RoomsDB.hasRelationship(currUser,room)){
                    Util.warning("Ya has realizado una apuesta en esta sala");
                }
                else if (room.isPrivate()){
                    Util.clearScreen();
                    System.out.println("Contraseña: ");
                }
                else{
                    playRoomGame(room);
                }

            }

        } while(userInput!=0);

    }


    public static void roomSelectionMenu(GAME_TYPE gameType){
        
        int userInput = -1;

        ArrayList<Room> rooms;
        Room room = null;

        do{
            Util.clearScreen();
            System.out.println("------------------------------------------------------------");
            rooms = GameManager.DBActiveRooms(gameType);
            System.out.println("------------------------------------------------------------");

            System.out.println("\n[ Entra introduciendo el número de la sala ]\n");
            System.out.println("0. Volver atrás");
            
            userInput = Util.inputInt();

            System.out.println();

            if (userInput == 0){
                return;
            }

            else if (userInput < 0 || userInput-1 >= rooms.size()){
                System.out.println("La opción seleccionada no es valida");
            }
            else{
                room = rooms.get(userInput-1);

                if (room.isPrivate()){
                    Util.clearScreen();
                    System.out.println("Contraseña: ");
                }
                else{
                    playRoomGame(room);
                }

            }

        } while(userInput!=0);

    }


    public static void playRoomGame(Room room){
        switch (room.getType()) {
            case Room.GAME_TYPE.ROULETTE:
                break;

            case Room.GAME_TYPE.LOW_ROLL:
                playLowRoll(room);
                break;
        
            default:
                break;
        }
    }
        


    public static void showRooms(ArrayList<Room> rooms){
        
        if (rooms.isEmpty()){
            Util.warning("No se han encontrado salas activas");
        }
        else{

            for (int i = 0 ;i < rooms.size(); i++) {
                System.out.println(i+". "+rooms.get(i).getVerboseInfo());
            }
        }
    }


    private static void adminUserManager(){
        
        int userInput;

        do {
            Util.clearScreen();
            System.out.println("------------------------------------------------------------");
            UsersBD.showUsers();
            System.out.println("------------------------------------------------------------");
        
            System.out.println("\n1. Cambiar Permisos");
            System.out.println("2. Cambiar nombre");
            System.out.println("3. Cambiar fichas");
            System.out.println("4. Cambiar estado de penalizacion");
            System.out.println("5. Borrar usuario");
            System.out.println("0. Volver atrás");
            userInput = Util.inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    System.out.println("Cambiando Permisos...");
                    break;

                case 2:
                    System.out.println("Cambiando Nombre...");
                    break;

                case 3:
                    System.out.println("Cambiando Fichas...");
                    break;

                case 4:
                    System.out.println("Cambiando Penalización...");
                    break;

                case 5:
                    System.out.println("Cambiando Borrar Usuario...");
                    break;

                case 0:
                    return;

                default:
                    System.out.println("La opción seleccionada no es valida");
                    break;
            }

        } while(userInput!=0);

    }


    private static void friendRequest(){
        Util.clearScreen();
        System.out.println("Añadiendo Amigo");
        System.out.println("----------------------------------------");
        System.out.print("Nombre del usuario: ");
        
        String friendName = System.console().readLine(); // Se usa para imprimir el nombre del usuario introducido aunq no exista.
        User friendUser = UsersBD.addFriend(currUser,friendName);
        
        if (friendUser != null){
            System.out.println("[ Has agregado a "+friendUser.getUserName()+" a tu lista de amigos ]");
        }
        else{
            System.out.println("\nEl usuario "+friendName+" no existe");
            Util.awaitUserToContinue();
        }
    }


    private static void removeFriend(){
        Util.clearScreen();
        System.out.println("Borrando Amigo");
        System.out.println("----------------------------------------");

        ArrayList<User> friends = currUser.getFriends();

        int friendPos = -1;

        do {
            System.out.println("------------------------------------------------------------");
            currUser.showFriends();
            System.out.println("------------------------------------------------------------");


            System.out.println("Selecciona la posición del amigo que deseas eliminar\n( 0. Cancelar ) ");
            friendPos = Util.inputInt();
            friendPos-=1;

            if (friendPos == -1){
                return;
            }
            else if (friendPos >= friends.size()){
                System.out.println("La posicion selecionada no es valida.");
                Util.awaitUserToContinue();
            }

            else{
                break;
            }

        }while(true);

        
        String removedFriendName = friends.get(friendPos).getUserName();

        boolean confirmation = confirmationPopUp("[ ¿Estás seguro de que quieres eliminar al usuario: "+removedFriendName+"? ]");

        if (confirmation){
            
            boolean deleteOk = UsersBD.deleteUserFriend(currUser,friends.get(friendPos));

            if (deleteOk){
                friends.remove(friendPos);

                System.out.println("\n[ Has eliminado a "+removedFriendName+" de tu lista de amigos ]");
            }
            else{
                Util.warning("Algo salio mal a la hora de borrar una amistad");
            }
        }
        
    }

    private static boolean confirmationPopUp(String msg){
        Util.clearScreen();
        System.out.println(msg);
        System.out.println("  1. Cancelar");
        System.out.println("  2. Confirmar");

        int input = 0;

        do{
            input = Util.inputInt();
        
            switch (input) {
                case 1:
                    return false;
                
                case 2:
                    return true;
            
                default:
                    break;
            }
            
        } while(true);
        
    }

}



