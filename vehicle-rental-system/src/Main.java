import controller.RentalServer;

/**
 * Entry point of the application.
 * Starts the embedded HTTP server that serves the DriveRent frontend
 * and handles rental calculations.
 */
public class Main {
    public static void main(String[] args) {
        try {
            RentalServer server = new RentalServer();
            server.start(8080);
            System.out.println("Open your browser at http://localhost:8080");
        } catch (Exception e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
}
