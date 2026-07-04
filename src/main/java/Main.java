import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.sql.IgniteSql;
import org.apache.ignite.sql.ResultSet;
import org.apache.ignite.sql.SqlRow;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Try-with-resources ensures the client connection is closed automatically
        // when the block finishes, even if an exception occurs
        try (IgniteClient client = IgniteClient.builder()
                .addresses("127.0.0.1:10800")
                .build()) {

            System.out.println("Connection successful!");

            // Get the SQL API to run queries against the cluster
            IgniteSql sql = client.sql();

            // Create the Subscriber table if it does not already exist
            sql.execute(null,
                    "CREATE TABLE IF NOT EXISTS Subscriber (" +
                            "customerId VARCHAR PRIMARY KEY, " +
                            "dataUsage DOUBLE, " +
                            "smsUsage INT, " +
                            "callUsage INT)");
            System.out.println("Table 'Subscriber' is ready.");

            // Clear the table so every run starts fresh
            sql.execute(null, "DELETE FROM Subscriber");
            System.out.println("Table cleared.");

            // Insert 5 dummy subscribers with baseline (zero) usage values
            for (int i = 1; i <= 5; i++) {
                String customerId = "CUST00" + i;
                sql.execute(null,
                        "INSERT INTO Subscriber (customerId, dataUsage, smsUsage, callUsage) VALUES (?, 0, 0, 0)",
                        customerId);
            }
            System.out.println("5 dummy subscribers inserted.");

            // Simulate usage - read each subscriber, add random usage, update the row
            Random random = new Random();
            ResultSet<SqlRow> result = sql.execute(null,
                    "SELECT customerId, dataUsage, smsUsage, callUsage FROM Subscriber");

            // Ignite's ResultSet implements Iterator, not Iterable,
            // so we must use hasNext()/next() instead of a for-each loop
            while (result.hasNext()) {
                SqlRow row = result.next();

                String id = row.stringValue("customerId");
                double newDataUsage = row.doubleValue("dataUsage") + random.nextInt(500);
                int newSmsUsage = row.intValue("smsUsage") + random.nextInt(20);
                int newCallUsage = row.intValue("callUsage") + random.nextInt(60);

                sql.execute(null,
                        "UPDATE Subscriber SET dataUsage = ?, smsUsage = ?, callUsage = ? WHERE customerId = ?",
                        newDataUsage, newSmsUsage, newCallUsage, id);
            }
            System.out.println("Usage simulation completed.");

            // Print the final state of all subscribers
            ResultSet<SqlRow> finalState = sql.execute(null, "SELECT * FROM Subscriber");
            System.out.println("Final subscriber data:");

            while (finalState.hasNext()) {
                SqlRow row = finalState.next();
                System.out.println(
                        "customerId=" + row.stringValue("customerId") +
                                ", dataUsage=" + row.doubleValue("dataUsage") +
                                ", smsUsage=" + row.intValue("smsUsage") +
                                ", callUsage=" + row.intValue("callUsage"));
            }

        } catch (Exception e) {
            // Print the stack trace if connection or query fails
            e.printStackTrace();
        }
    }
}