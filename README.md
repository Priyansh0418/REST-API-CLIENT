# REST-API-CLIENT

*COMPANY*: CODTECH IT SOLUTIONS

*NAME*: PRIYANSH RATHOD

*INTERN ID*: CT04DL651

*DOMAIN*: JAVA

*DURATION*: 4 WEEKS

*MENTOR*:  NEELA SANTHOSH KUMAR 

*DESCRIPTION*:
-Introduction
This task involved developing a Java application that interacts with a public REST API (specifically, OpenWeatherMap API) to fetch weather data and display it in a structured format. The goal was to demonstrate HTTP request handling, JSON parsing, and user-friendly data presentation while following Java best practices.

The application provides:
✅ Real-time weather data (temperature, humidity, wind speed, etc.)
✅ Multiple search methods (by city name, city ID, or coordinates)
✅ Error handling for API failures
✅ Clean console output for easy readability

-Tools & Technologies Used
1. Development Environment
IDE: VS Code (with Java Extension Pack for IntelliSense, debugging, and Maven support)

Build Tool: Maven (for dependency management and project structuring)

JSON Parsing: org.json library (lightweight and efficient for handling API responses)

2. API Used
OpenWeatherMap API (free tier)

Provides current weather data in JSON format

Requires an API key (registered on their website)

3. AI Assistance (DeepSeek Chat)
Code Optimization: Suggested improvements in HTTP request handling

Debugging Help: Identified issues in JSON parsing and Maven configuration

Documentation: Assisted in structuring comments and README

-Implementation Details
1. HTTP Request Handling
The application uses HttpURLConnection to:

Send GET requests to OpenWeatherMap API

Handle response codes (success/error cases)

Read JSON data from the API response

Example Code:

java
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
connection.setRequestMethod("GET");
int responseCode = connection.getResponseCode();
if (responseCode == HttpURLConnection.HTTP_OK) {
    // Read and parse JSON response
}
2. JSON Parsing
The org.json library processes the API response into usable data:

Extracts temperature, humidity, wind speed, etc.

Handles nested JSON objects (e.g., weather[0].description)

Example Parsing:

java
JSONObject weatherData = new JSONObject(response);
String city = weatherData.getString("name");
double temp = weatherData.getJSONObject("main").getDouble("temp");
3. User Interaction
Menu-driven console interface (using Scanner)

Three search options:

By city name (e.g., "London")

By city ID (OpenWeatherMap's internal ID)

By coordinates (latitude & longitude)

4. Error Handling
Invalid API responses (e.g., city not found)

Network issues (timeouts, connection errors)

User input validation (wrong city names, invalid numbers)

-Challenges & Solutions
1. Maven Build Issues
Problem: Initially, the exec-maven-plugin didn’t run correctly.

Solution: Added proper <configuration> in pom.xml and fixed tasks.json.

2. JSON Parsing Errors
Problem: Some fields (e.g., rain or snow) were optional, causing JSONException.

Solution: Used optDouble() and has() methods for safe access.

3. API Key Security
Problem: Hardcoding the API key in the source file is unsafe.

Solution: Should use environment variables or config files in production.

-My Contributions & Learning Outcomes
1. Hands-on Java Experience
Improved understanding of:

HTTP networking in Java

JSON parsing with org.json

Maven project structure

2. Debugging & Optimization
Used VS Code’s debugger to trace issues

Optimized code with DeepSeek’s suggestions (e.g., better error handling)

3. Documentation & Readability
Added clear comments for maintainability

Structured output for better user experience

-Conclusion
This project successfully demonstrates how to consume REST APIs in Java while maintaining clean code and error resilience.

Future Improvements
Add a GUI (JavaFX/Swing)

Cache responses to reduce API calls

Support weather forecasts (not just current data)

*OUTPUT*:
