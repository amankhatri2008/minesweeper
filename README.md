# Minesweeper
This is a command-line Minesweeper game built using Spring Boot. It allows a user to play a classic game of Minesweeper directly from the terminal. The application follows a clean, modular design to ensure it's easy to test and maintain.


# Features
 * Configurable Gameplay: Set your desired grid size and number of mines at the start of each game, runs with default grid size and default mine, which can be configured in application.properties file.

 * Interactive Interface: An intuitive command-line interface guides you through the game, from selecting a square to win or lose conditions.

 * Automatic Cascade: Empty squares are automatically revealed in a cascading effect, just like in the original game.

 * Robust Logic: The game correctly identifies wins and losses and handles all core Minesweeper mechanics.

# Tech Stack Used
 * Java
 * Spring-boot
 * Junit and Mokito
   
# Prerequisites
To run this application, you need to have the following installed:

  * Java Development Kit (JDK) 17 or higher
  * Apache Maven 3.6 or higher

 # How to Run the Application
This method is useful for development and testing. It compiles the code, runs the tests, and then executes the application directly without creating the JAR.
 
  * mvn clean test exec:java
