import org.apache.ignite.client.IgniteClient;

public class Main {
    public static void main(String[] args) {
        // Try-with-resources ensures the client connection is closed automatically
        // when the block finishes, even if an exception occurs
        try (IgniteClient client = IgniteClient.builder()
                // Connect to the local Ignite node running in Docker
                // 127.0.0.1:10800 -> localhost, thin client port exposed by the container
                .addresses("127.0.0.1:10800")
                .build()) {

            // If we reach this line, the connection to the Ignite cluster was successful
            System.out.println("Connection successful!");

        } catch (Exception e) {
            // Print the stack trace if connection fails (e.g. container not running,
            // wrong port, or network issue)
            e.printStackTrace();
        }
    }
}