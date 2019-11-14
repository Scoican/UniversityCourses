import java.io.IOException;

public class Main {
    public static void main(String[] argv) {
        try {
            Utils.manageMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
