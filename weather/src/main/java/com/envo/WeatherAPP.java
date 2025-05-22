package com.envo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherAPP {
    // API configuration
    private static final String API_KEY = "655694758bb34528fae910ec410af4f6"; // Replace with your OpenWeatherMap API key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Weather Data Fetcher");
        System.out.println("====================");
        
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Get weather by city name");
            System.out.println("2. Get weather by city ID");
            System.out.println("3. Get weather by coordinates");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    getWeatherByCity(scanner);
                    break;
                case 2:
                    getWeatherByCityId(scanner);
                    break;
                case 3:
                    getWeatherByCoordinates(scanner);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void getWeatherByCity(Scanner scanner) {
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        System.out.print("Enter country code (optional, press Enter to skip): ");
        String country = scanner.nextLine();
        
        String query = "q=" + city;
        if (!country.isEmpty()) {
            query += "," + country;
        }
        
        fetchAndDisplayWeather(query);
    }

    private static void getWeatherByCityId(Scanner scanner) {
        System.out.print("Enter city ID: ");
        String cityId = scanner.nextLine();
        fetchAndDisplayWeather("id=" + cityId);
    }

    private static void getWeatherByCoordinates(Scanner scanner) {
        System.out.print("Enter latitude: ");
        String lat = scanner.nextLine();
        System.out.print("Enter longitude: ");
        String lon = scanner.nextLine();
        fetchAndDisplayWeather("lat=" + lat + "&lon=" + lon);
    }

    private static void fetchAndDisplayWeather(String query) {
        try {
            // Build the API URL
            String urlString = BASE_URL + "?" + query + "&appid=" + API_KEY + "&units=metric";
            URL url = URI.create(urlString).toURL();
            
            // Make the HTTP request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Parse and display the weather data
                displayWeatherData(new JSONObject(response.toString()));
            } else {
                System.out.println("Error: API request failed with status code " + responseCode);
                // Read error stream if available
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream()))) {
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.out.println(errorLine);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }

    private static void displayWeatherData(JSONObject weatherData) {
        System.out.println("\nCurrent Weather Data");
        System.out.println("====================");
        
        // Location information
        String city = weatherData.getString("name");
        JSONObject sys = weatherData.getJSONObject("sys");
        String country = sys.getString("country");
        System.out.println("Location: " + city + ", " + country);
        
        // Coordinates
        JSONObject coord = weatherData.getJSONObject("coord");
        System.out.println("Coordinates: " + coord.getDouble("lon") + "° E, " + 
                          coord.getDouble("lat") + "° N");
        
        // Weather conditions
        JSONObject main = weatherData.getJSONObject("main");
        System.out.println("\nTemperature: " + main.getDouble("temp") + "°C");
        System.out.println("Feels like: " + main.getDouble("feels_like") + "°C");
        System.out.println("Min/Max: " + main.getDouble("temp_min") + "°C/" + 
                          main.getDouble("temp_max") + "°C");
        System.out.println("Humidity: " + main.getInt("humidity") + "%");
        System.out.println("Pressure: " + main.getInt("pressure") + " hPa");
        
        // Wind
        JSONObject wind = weatherData.getJSONObject("wind");
        System.out.println("\nWind: " + wind.getDouble("speed") + " m/s");
        if (wind.has("deg")) {
            System.out.println("Direction: " + wind.getInt("deg") + "°");
        }
        
        // Weather description
        System.out.println("\nConditions:");
        weatherData.getJSONArray("weather").forEach(item -> {
            JSONObject weather = (JSONObject) item;
            System.out.println("- " + weather.getString("main") + 
                             " (" + weather.getString("description") + ")");
        });
        
        // Additional info
        System.out.println("\nAdditional Information:");
        System.out.println("Visibility: " + weatherData.getInt("visibility") + " meters");
        if (weatherData.has("clouds")) {
            System.out.println("Cloudiness: " + 
                             weatherData.getJSONObject("clouds").getInt("all") + "%");
        }
        if (weatherData.has("rain")) {
            System.out.println("Rain volume (last hour): " + 
                             weatherData.getJSONObject("rain").optDouble("1h", 0) + " mm");
        }
        if (weatherData.has("snow")) {
            System.out.println("Snow volume (last hour): " + 
                             weatherData.getJSONObject("snow").optDouble("1h", 0) + " mm");
        }
    }
}