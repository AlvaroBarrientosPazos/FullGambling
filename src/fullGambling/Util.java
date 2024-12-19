package fullGambling;

import java.nio.charset.StandardCharsets;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;
import java.util.InputMismatchException;
import fullGambling.bd.UsersBD;


public class Util {

    static enum OS{
        WINDOWS,
        LINUX_MAC,
        OTHERS;
    }

    public static Scanner scanner = new Scanner(System.in);

    private static OS systemOS;


    public static int inputInt(){
        int input = -1;
        
        System.out.print("\nOpción: ");
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
        }

        return input;
    }

    public static int inputInt(boolean optionFlag){
        int input = -1;
        
        if (optionFlag){
            System.out.print("\nOpción: ");
        }

        try {
            input = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static void awaitUserToContinue(){
        System.out.print("Pulse \"Enter\" para continuar...");
        String buffer = scanner.nextLine();
    }

    static void getHardwareOS(){
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
            Util.printStackTrace(e);
        }
    }


    /*
     * FUNCIONES BCRYPT: generar hash y validar hash
     */
     /**
     * Genera un hash de BCrypt
     * 
     * @param password Contraseña en texto claro
     * @return Hash de BCrypt
     */
    public static String generarStringHash2Y(String password) {
        char[] bcryptChars = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(13, password.toCharArray());
        return String.valueOf(bcryptChars);
    }

    /**
     * Valida un hash de BCrypt
     * 
     * @param password Contraseña en texto claro
     * @param hash2y   Hash de BCrypt
     * @return true si la contraseña es correcta
     */
    public static boolean validarHash2Y(String password, String hash2y) {
        return BCrypt.verifyer(BCrypt.Version.VERSION_2Y)
                .verifyStrict(password.getBytes(StandardCharsets.UTF_8),
                        hash2y.getBytes(StandardCharsets.UTF_8)).verified;
    }

    public static boolean verifyPassword(String password, ResultSet results){
        boolean loginOk = false;
        
        try{
            byte[] passwordHashed = results.getString(UsersBD.USER.PASSWORD.getValue()).getBytes(StandardCharsets.UTF_8);
            BCrypt.Result resultStrict = BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verifyStrict(
                password.getBytes(StandardCharsets.UTF_8),
                passwordHashed);
            loginOk = resultStrict.verified;
            loginOk = validarHash2Y(password, results.getString(UsersBD.USER.PASSWORD.getValue()));
        } 
        catch (SQLException e) {
            Util.printStackTrace(e);
        }


        return loginOk;
    }

    public static void warning(String msg){
        clearScreen();
        System.out.println(msg);
        awaitUserToContinue();
    }

    public static void printStackTrace(Exception e){
        e.printStackTrace();
        awaitUserToContinue();
    }

}
