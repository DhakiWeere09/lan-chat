
public class Main {

    static final String os = System.getProperty("os.name");
    static int operationMode;

    // console formatted graphic output
    private static void consoleGraphic(String titleMessage) {

        // clearing the screen
        // does NOT work in virtual environments like IDE Runtimes Environments
        try {
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else {
                Runtime.getRuntime().exec("clear");

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // formatted Title Message
        String formattedTitle = String.format("%s%s%s", titleMessage, new String(new char[68 - titleMessage.length()]).replace("\0", " "), "|");

        // Main Header OutPut
        String mainOut = """
                +---------------------------------------------------------------------+
                | 88        88888888  8888  88          88888888  88    88  88888888  |
                | 88        88    88  88 8  88          88        88    88     88     |
                | 88        88888888  88  8 88   ===    88        88888888     88     |
                | 88        88    88  88   888          88        88    88     88     |
                | 88888888  88    88  88    88          88888888  88    88     88     |
                |                                                       version 1.0.0 |
                +---------------------------------------------------------------------+
                | %s
                +---------------------------------------------------------------------+
                """.formatted(formattedTitle);

        System.out.println(mainOut);

    }


    public static void main(String[] args) {
        consoleGraphic("Welcome to LAN-CHAT! use [START] to enter (SERVER) or (CLIENT) mode");



    }
}
