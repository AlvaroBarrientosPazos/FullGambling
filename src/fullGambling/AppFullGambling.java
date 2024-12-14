package fullGambling;

import java.util.Scanner;

import fullGambling.bd.UsuariosBD;
import fullGambling.bd.ViajesBD;

public class AppFullGambling {

    private static Scanner sc = new Scanner(System.in);;
    private static User currUser;

    public static void main(String[] args) {
        System.out.println("***************************");
        System.out.println("**  Full Gambling v.0.1  **");
        System.out.println("***************************");

        currUser = startMenu();

        if (currUser != null) {
            userMenu();
        }
    }

    private static User startMenu() {

        int userOption = 0;

        do {
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear usuario");
            System.out.println("0. Salir");
            userOption = sc.nextInt();
            sc.nextLine();

            switch (userOption) {

                case 1:
                    return UsuariosBD.logInUser();

                case 2:
                    return UsuariosBD.createUser();

                case 0:
                    System.out.println("Hasta pronto");
                    return null;

                default:
                    System.out.println("Opción invalida");
            }

        } while (userOption != 0);

        return null;

    }

    static void userMenu() {
        int selectedIndex;

        do {
            System.out.println("\n*********************");
            System.out.println("** Menu de Usuario **");
            System.out.println("*********************");
            System.out.println("Usuario: " + currUser.getUserName() + "\n");
            System.out.println("1. Jugar");
            System.out.println("2. Comprar fichas");
            System.out.println("3. Lista de Amigos");
            System.out.println("4. Confirgurar Perfil");
            System.out.println("0. SALIR");
            selectedIndex = sc.nextInt();
            sc.nextLine();

            switch (selectedIndex) {
                case 1:
                    System.out.println("Jugando...");
                    // publicarViaje(userName);
                    break;

                case 2:
                    System.out.println("Comprando...");
                    chipsShop();
                    // solicitarAsiento(userName);
                    break;

                case 3:
                    System.out.println("Lista de amigos...");
                    currUser.displayFriends();
                    // solicitarAsiento(userName);
                    break;

                case 4:
                    System.out.println("Configurando Perfil...");
                    UserConfig();
                    // solicitarAsiento(userName);
                    break;

                case 0:
                    System.out.println("Hasta pronto");
                    break;

                default:
                    System.out.println("Opción invalida");
            }
        } while (selectedIndex != 0);

        System.out.println("Fin del programa");
        sc.close();
    }

    private static void chipsShop() {
        System.out.println("******************");
        System.out.println("*** Chips Shop ***");
        System.out.println("*****************");
        System.out.println("Nº Chips: " + currUser.getChips() + "\n");
        System.out.println("1. Comprar");
        System.out.println("2. Retirar");
        System.out.println("0. SALIR");
    }

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

    private static void UserConfig() {
        int selectedIndex;

        do {
            System.out.println("***************************");
            System.out.println("* Configuración de Perfil *");
            System.out.println("***************************");
            System.out.println("Usuario: " + currUser.getAlias() + "\n");
            System.out.println("1. Cambiar Apodo");
            System.out.println("2. Comprar fichas");
            System.out.println("3. Lista de Amigos");
            System.out.println("4. Confirgurar Perfil");
            System.out.println("0. SALIR");
            selectedIndex = sc.nextInt();
            sc.nextLine();

            switch (selectedIndex) {
                case 1:
                    System.out.println("Jugando...");
                    // publicarViaje(userName);
                    break;

                case 2:
                    System.out.println("Comprando...");
                    chipsShop();
                    // solicitarAsiento(userName);
                    break;

                case 3:
                    System.out.println("Lista de amigos...");
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
        } while (selectedIndex != 0);
    }

}
