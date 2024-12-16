package fullGambling;

import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fullGambling.bd.GameManager;
import fullGambling.bd.UsuariosBD;
import fullGambling.Dice;

public class AppFullGambling {

    enum MENU_ID{
        START_MENU,
        USER_MENU,
        FRIENDS_MENU,
        PROFILE_MENU;
    }

    static enum OS{
        WINDOWS,
        LINUX_MAC,
        OTHERS;
    }

    private static OS systemOS;

    private static User currUser;
    private static Scanner sc = new Scanner(System.in);;
    

    public static void main(String[] args) {
        getSystemOS();
        mainMenu();

    }

    private static void mainMenu() {

        int userInput = 0;

        do {
            tittleBox("Full Gambling v.0.1",2);
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear usuario");
            System.out.println("0. Salir");
            userInput = inputInt();

            switch (userInput) {

                case 1:
                    LogInMenu();
                    break;

                case 2:
                    CreateUserMenu();
                    break;

                case 0:
                    clearScreen();
                    System.out.println("Hasta pronto");
                    sc.close();
                    return;

                default:
                    clearScreen();
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
        
        sc.close();

    }


    private static void LogInMenu() {
        
    String userName, password;

        do {
            clearScreen();
            System.out.println("Iniciando Sesión");
            System.out.println("----------------------------------------");
            System.out.print("Usuario: ");
            userName = System.console().readLine();

            System.out.print("Contraseña: ");
            password = new String(System.console().readPassword());

            currUser = UsuariosBD.ProcessUserLogin(userName, password);

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
        
                if (UsuariosBD.hasUser(userName) != -1) {
                    System.out.println("El nombre de usuario " + userName + " no está disponible.");
                }

                else if (UsuariosBD.createUser(userName, password)){
                    currUser = UsuariosBD.ProcessUserLogin(userName, password);
                }
                
            } while (currUser == null);
        }


    private static void adminMenu(){
        int userInput;

        do {
            tittleBox("Menu de Administrador",2);
            System.out.println("Admin: " + currUser.getUserName() + "\n");
            System.out.println("1. Partidas");
            System.out.println("2. Usuarios");
            System.out.println("0. Cerrar Sesión");
            userInput = inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    GameManager.adminRoomManager();
                    break;

                case 2:
                    chipsShop();
                    break;

                case 0:
                    currUser = null;
                    return;

                default:
                    System.out.println("Opción invalida");
            }
        } while (userInput != 0);

        System.out.println("Fin del programa");
    }


    private static void userMenu() {
        int userInput;

        do {
            tittleBox("Menu de Usuario",2);
            System.out.println("Usuario: " + currUser.getUserName() + "\n");
            System.out.println("1. Jugar");
            System.out.println("2. Tienda de fichas");
            System.out.println("3. Amigos");
            System.out.println("4. Confirgurar Perfil");
            System.out.println("0. Cerrar Sesión");
            userInput = inputInt();

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
                    System.out.println("Opción invalida");
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
            System.out.println("0. Volver Atrás");

            userInput = inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    System.out.println("Ruleta");
                    break;

                case 2:
                    lowRollGame(currUser);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opción invalida");
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
        
        clearScreen();
        System.out.println(border+sides+" "+text+" "+sides+"\n"+border);
    }

    private static void lowRollGame(User user){
        do{
            tittleBox("Low Roll",3);

        }while(true);
    }


    private static void chipsShop() {
        int userInput = -1;

        do{
            tittleBox("Tienda de Fichas", 3);
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. Comprar");
            System.out.println("2. Retirar");
            System.out.println("0. Volver Atrás");

            userInput = inputInt();

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
        
        clearScreen();
        
        int userInput = -1, quantity = 0;

        do{
            System.out.println("Comprar Fichas");
            System.out.println("----------------------------------------");
            System.out.println("Nº Fichas: " + currUser.getChips() + "\n");
            System.out.println("1. 5€\t(50c)");
            System.out.println("2. 10€\t(100c)");
            System.out.println("3. 25€\t(255c)");
            System.out.println("4. 50€\t(600c)  +20%!!");
            System.out.println("5. 100€\t(1500c) +50%!!");
            System.out.println("0. Volver Atrás");

            userInput = inputInt();

            if (userInput == 0) {
                return;
            }

            quantity = switch (userInput) {
                case 1 -> 5 * 10;

                case 2 -> 5 * 10;

                case 3 -> 5 * 10;

                case 4 -> (int)(50 * 10 * 1.2);  

                case 5 -> (int)(100 * 10 * 1.5);

                default -> {
                    System.out.println("Opcion Invalida");
                    yield -1;
                }
                    
            };

            UsuariosBD.updateChips(currUser, quantity);

        }while (userInput != 0);


    }

    // TODO: --------------------------------------------------------------------------------------------------------------

    private static void sellChips(){
        
    }

    // TODO: --------------------------------------------------------------------------------------------------------------

    private static void UserConfig() {
        int userInput;

        do {
            clearScreen();
            System.out.println("*****************************");
            System.out.println("** Configuración de Perfil **");
            System.out.println("*****************************");
            System.out.println("Usuario: " + currUser.getAlias() + "\n");
            System.out.println("1. Cambiar Apodo");
            System.out.println("2. Tienda de Fichas");
            System.out.println("3. Lista de Amigos");
            System.out.println("4. Configurar Perfil");
            System.out.println("0. Volver Atrás");
            userInput = inputInt();

            System.out.println();

            switch (userInput) {
                case 1:
                    System.out.println("Cambiando Apodo...");
                    break;

                case 2:
                    chipsShop();
                    break;

                case 3:
                    friendsMenu();
                    currUser.displayFriends();
                    // solicitarAsiento(userName);
                    break;

                case 4:
                    System.out.println("Configurando Perfil...");
                    // solicitarAsiento(userName);
                    break;

                case 0:
                    System.out.println("Hasta pronto");
                    break;

                default:
                    System.out.println("Opción invalida");
            }
        } while (userInput != 0);
    }


    private static void friendsMenu(){
        int choiceId;

        do {
            clearScreen();
            System.out.println("*********************");
            System.out.println("** Lista de Amigos **");
            System.out.println("*********************");

            

            System.out.println("------------------------------------------------------------");

            if (currUser.getFriends().length == 0){
                System.out.println("Aún no has añadido a ningún usuario a tu lista de amigos");
            }
            else{
                currUser.displayFriends();
            }
            
            System.out.println("------------------------------------------------------------");
        
            System.out.println("\n1. Añadir Amigo");
            System.out.println("2. Borrar Amigo");
            System.out.println("0. Volver Atrás");
            choiceId = inputInt();

            System.out.println();

            switch (choiceId) {
                case 1:
                    friendRequest();
                    break;

                case 2:
                    removeFriend();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("La opción seleccionada no es valida");
                    break;
            }

        } while(choiceId!=0);

    }


    private static void friendRequest(){
        clearScreen();
        System.out.println("Añadiendo Amigo...");
        System.out.println("----------------------------------------");
        System.out.print("Nombre del usuario: ");
        
        //sc.nextLine();
        String friendName = sc.nextLine(); // Se usa para imprimir el nombre del usuario introducido aunq no exista.
        User friendUser = UsuariosBD.addFriend(currUser,friendName);
        
        if (friendUser != null){
            System.out.println("[ Has agregado a "+friendUser.getUserName()+" a tu lista de amigos ]");
        }
        else{
            System.out.println("\nEl usuario "+friendName+" no existe");
        }


    }

    private static void removeFriend(){
        clearScreen();
        System.out.println("Borrando Amigo...");
        System.out.println("----------------------------------------");

        User[] friends = currUser.getFriends();

        int friendPos = -1;

        do {
            System.out.println("------------------------------------------------------------");
            currUser.displayFriends();
            System.out.println("------------------------------------------------------------");


            System.out.println("Selecciona la posición del amigo que deseas eliminar\n( 0. Cancelar ) ");
            friendPos = inputInt();
            friendPos-=1;

            if (friendPos == -1){
                return;
            }
            else if (friendPos >= friends.length){
                System.out.println("La posicion selecionada no es valida.");
            }

            else{
                break;
            }

        }while(true);

        
        String removedFriendName = friends[friendPos].getUserName();

        boolean confirmation = confirmationPopUp("[ ¿Estás seguro de que quieres eliminar al usuario: "+removedFriendName+"? ]");

        if (confirmation){
            
            boolean deleteOk = UsuariosBD.deleteUserFriend(currUser,friends[friendPos]);

            if (deleteOk){
                User[] newFriends = new User[friends.length-1];

                System.arraycopy(friends, 0, newFriends, 0, friendPos);
                System.arraycopy(friends, friendPos + 1, newFriends, friendPos, friends.length - friendPos - 1);
                
                currUser.setFriends(newFriends);
        
                System.out.println("\n[ Has eliminado a "+removedFriendName+" de tu lista de amigos ]");
            }
            else{
                System.out.println("Algo salio mal");
            }
        }
        
    }

    private static boolean confirmationPopUp(String msg){
        clearScreen();
        System.out.println(msg);
        System.out.println("  1. Cancelar");
        System.out.println("  2. Confirmar");

        int input = 0;

        do{
            input = inputInt();
        
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


    private static int inputInt(){
        int input = -1;
        
        try {
            input = sc.nextInt();
            sc.nextLine();
            
        }
        catch (InputMismatchException e) {
        }

        return input;
    }


    private static void getSystemOS(){
        String operatingSystem = System.getProperty("os.name").toLowerCase();

        if (operatingSystem.contains("win")) {
            systemOS = OS.WINDOWS;
        } 
        else if (operatingSystem.contains("nix") || operatingSystem.contains("nux") || operatingSystem.contains("mac")) {
            systemOS = OS.LINUX_MAC;
        } 
        else {
            systemOS = OS.OTHERS;
        }

    }

    public static void clearScreen() {
        
        try{
            switch (systemOS) {
                case OS.WINDOWS:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    break;

                case OS.LINUX_MAC:
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    break;

                case OS.OTHERS:
                    System.out.print("\n\n\n\n");
                    break;

                default:
                    break;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

       

    /* 
    private static void publicarViaje(String usuario) {
        System.out.print("Escribe la fecha y hora del viaje (AAAA-MM-DD HH:MM): ");
        String fechaHora = sc.nextLine();
        System.out.print("Lugar de origen: ");
        String origen = sc.nextLine();
        System.out.print("Lugar de destino: ");
        String destino = sc.nextLine();
        System.out.print("Número de plazas libres: ");
        int plazas = sc.nextInt();
        sc.nextLine();

        if (ViajesBD.crearViaje(usuario, fechaHora, origen, destino, plazas)) {
            System.out.println("Viaje creado");
        } else {
            System.out.println("Error al crear el viaje");
        }
    }

    private static void solicitarAsiento(String usuario) {
        int numViajesProximos = ViajesBD.listarProximosViajesConPlazas();
        if (numViajesProximos > 0) {
            System.out.print("Indica el ID del viaje que quieres solicitar: ");
            int numViaje = sc.nextInt();
            sc.nextLine();
            if (ViajesBD.anhadirPasajero(numViaje, usuario)) {
                System.out.println("Asiento solicitado");
            } else {
                System.out.println("Error al solicitar el asiento");
            }
        } else {
            System.out.println("No hay viajes próximos con plazas libres");
        }
    }
    */



}



