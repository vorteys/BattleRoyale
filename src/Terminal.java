
import java.util.Scanner;

public class Terminal {

    public static void pause(long milisecondes) {
        try {
            Thread.sleep(milisecondes);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayLife(String fullName, double health) {
        System.out.print(fullName + " [");
        int n = (int) health / 10;
        for (int i = 0; i < n; i++) {
            System.out.print(Terminal.Color.GREEN.colorize("❤"));
        }
        for (int i = 0; i < 10 - n; i++) {
            System.out.print(" ");
        }
        System.out.println("] \n");
    } // mettre quelque part

    public static Integer readInt() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        try {
            return Integer.valueOf(choice);
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide.");
            return null;
        }
    }

    public static enum Color {
        BLACK((char) 27 + "[30m"),
        RED((char) 27 + "[31m"),
        GREEN((char) 27 + "[32m"),
        YELLOW((char) 27 + "[33m"),
        BLUE((char) 27 + "[34m"),
        PURPLE((char) 27 + "[35m"),
        CYAN((char) 27 + "[36m"),
        WHITE((char) 27 + "[37m");

        private final String charCode;

        Color(String charCode) {
            this.charCode = charCode;
        }

        public String colorize(String message) {
            return charCode + message + (char) 27 + "[0m";
        }
    }
}
