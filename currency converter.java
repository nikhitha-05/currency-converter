import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // User Input
            System.out.print("Enter Base Currency (e.g., USD, INR): ");
            String baseCurrency = sc.next().toUpperCase();

            System.out.print("Enter Target Currency (e.g., EUR, INR): ");
            String targetCurrency = sc.next().toUpperCase();

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            // API URL (Replace YOUR_API_KEY with your actual key)
            String apiKey = "YOUR_API_KEY";
            String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON Response
            JSONObject json = new JSONObject(response.toString());
            JSONObject rates = json.getJSONObject("conversion_rates");

            double exchangeRate = rates.getDouble(targetCurrency);

            // Conversion
            double convertedAmount = amount * exchangeRate;

            // Display Result
            System.out.println("\n----- Currency Conversion Result -----");
            System.out.println(amount + " " + baseCurrency + " = "
                    + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
