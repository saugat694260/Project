package utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;

public class LoginAndRegister {
  // define ALL USER dir
  File users = FileFolderManager.getDirUsers();

  private String userName = "guest";
  private String userPassword = "password";

  public LoginAndRegister(Scanner scan) {

    clearScreen();
    initilizeRequiredFolderAndFile();

    int selection = loginOrSignupSelection(scan);

    clearScreen();
    if (selection == 0) {
      System.out.println("enjoy your next 24 hours :)\n");
      System.exit(0);
    }
    if (selection == 2) {
      System.out.println("Sign-Up");
      takeUserNameAndPassword(scan);
      if (createNewUser()) {
        System.out.println("User created successfully!");
        saveUserLogin();
      } else {
        System.out.println("Use was not  created");
        return;
      }
      clearScreen();
    }

    System.out.println("Log-In");
    takeUserNameAndPassword(scan);
    if (checkCredentialMatch()) {
      saveUserLogin();
      loadUserDataManually();
    }
  }

  private void initilizeRequiredFolderAndFile() {
    File lastUserName = new File(users + File.separator + "lastUserName.txt");

    try {
      lastUserName.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("initilizeRequiredFolderAndFile method: sth went wrong");
    }
  }

  private int loginOrSignupSelection(Scanner scan) {

    while (true) {
      System.out.println("(0 to exit)");
      System.out.print("Enter 1 to Log-In and 2 to Sign-Up: ");
      String userInput = scan.nextLine().trim();

      try {
        int choice = Integer.parseInt(userInput);
        if (choice == 1 || choice == 2 || choice == 0) {
          return choice;
        } else {
          wrongUserInputMessege();
        }
      } catch (NumberFormatException nfe) {
        wrongUserInputMessege();
      }
    }

  }

  private boolean createNewUser() {
    boolean created = false;

    File newUser = new File(users + File.separator + userName);
    if (!newUser.mkdir()) {
      System.out.println("That username is taken. Please choose something else!");
      return created;
    }

    File newUserProfile = new File(newUser + File.separator + userName + ".txt");
    File newUserData = new File(newUser + File.separator + userName + "data.txt");

    try {
      if (newUserProfile.createNewFile() && newUserData.createNewFile()) {
        System.out.println("new user " + userName + " was created");
      } else {
        System.out.println("User could not be created");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("createNewUser");
    }

    try (
        BufferedWriter bfw = new BufferedWriter(new FileWriter(newUserProfile));) {
      bfw.write("Username=" + userName);
      bfw.newLine();
      bfw.write("Password=" + userPassword);
      bfw.newLine();

      created = true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return created;
  }

  private boolean checkCredentialMatch() {
    File user = new File(users + File.separator + userName + File.separator + userName + ".txt");
    String line;
    boolean matches = false;

    if (!user.exists()) {
      System.out.println("User not found! Please make an account first.");
      return matches;
    }

    System.out.println("User found! Checking credentials...");

    List<String> userInfo = new ArrayList<>();
    try (BufferedReader bfr = new BufferedReader(new FileReader(user))) {
      while ((line = bfr.readLine()) != null) {
        line = line.substring(line.indexOf("=") + 1);
        userInfo.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (userInfo.get(0).matches(userName) && userInfo.get(1).matches(userPassword)) {
      System.out.println("Credentials matches!!!");
      matches = true;
    } else {
      System.out.println("Credentials does not match!");
    }
    return matches;
  }

  private boolean loadUserDataManually() {
    File userData = new File(users + File.separator + userName + File.separator + userName + "data.txt");

    if (!userData.exists()) {
      System.out.println("No user data found!! It might have been curropted or deleted!");
      return false;
    }

    // will add user data after creating game
    // current plan is the user data will have achivements and stats saved
    System.out.println("user data loaded!!");
    System.out.println("logged in successfully");
    return true;
  }

  // logOut option
  // private void logOut() {
  //
  // }

  // log in or signup screen
  private void takeUserNameAndPassword(Scanner scan) {
    boolean b = false;
    while (!b) {
      System.out.print("Username: ");
      this.userName = scan.nextLine();

      if (userName.length() > 10) {
        System.out.println("Username should not exceed 10 characters!");
      } else if (userName.matches(".*[^a-zA-Z0-9].*")) {
        System.out.println("Please exclude special characters");
      } else {
        b = true;
      }
    }

    System.out.print("Password: ");
    this.userPassword = scan.nextLine();
  }

  private void saveUserLogin() {
    File lastUserName = new File(users + File.separator + "lastUserName.txt");

    try (FileWriter fw = new FileWriter(lastUserName);) {
      fw.write(userName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // method to make new user dir
  // method to log in
  //
  //
  // utils
  // these works only on unix terminal
  // p.s. it will not work in windows powershell
  private void clearScreen() {
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  private void clearScreen(int seconds) {
    threadSleep(seconds);
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  private void threadSleep(int seconds) {
    try {
      Thread.sleep(seconds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void wrongUserInputMessege() {
    clearScreen();
    threadSleep(1000);
    System.out.printf("\"( – ⌓ – )*");
    threadSleep(2000);
    clearScreen();
    threadSleep(1000);
    System.out.printf("IT'S");
    threadSleep(800);
    System.out.printf(" EITHER");
    threadSleep(800);
    System.out.printf(" 1");
    threadSleep(800);
    System.out.printf(" OR");
    threadSleep(800);
    System.out.printf(" 2");
    threadSleep(800);
    System.out.printf(" MOTHER");
    threadSleep(800);
    System.out.printf(" F***ER!!!");
    threadSleep(1000);
    System.out.println("""

        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⢶⣶⣶⠼⣦⣤⣼⣼⡆⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠖⣯⠿⠟⠛⠻⢶⣿⣯⣿⣿⣃⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣖⣺⡿⠿⠷⠶⠒⢶⣶⠖⠀⠉⡻⢻⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⣴⢻⣭⣫⣿⠁⠀⠀⠀⠀⠀⠀⠀⢀⣾⠃⢀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⢀⣖⡿⠋⢙⣿⠿⢿⠿⣿⡦⠄⠀⠀⠀⣠⣾⠟⠀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⢀⣰⣿⣴⣿⡿⠿⠿⠿⢿⣦⣄⠀⠀⠀⣠⣾⣿⠃⠀⢀⣸⡿⣳⣶⣲⡄⠀⠀⠀⠀⠀⠀
        ⠀⠀⣾⣽⡿⣛⣵⠾⠿⠿⠷⣦⣌⠻⣷⣄⢰⣿⠟⠁⠀⢠⣾⠿⢡⣯⠸⠧⢽⣄⠀⠀⠀⠀⠀
        ⠀⢸⡇⡟⣴⡿⢟⣽⣾⣿⣶⣌⠻⣧⣹⣿⡿⠋⠀⠀⠀⣾⠿⡇⣽⣿⣄⠀⠀⠉⠳⣄⢀⡀⠀
        ⠀⢸⠇⢳⣿⢳⣿⣿⣿⣿⣿⣿⡆⢹⡇⣿⡇⠀⡆⣠⣼⡏⢰⣿⣿⣿⣿⣦⠀⠀⠀⠈⠳⣅⠀
        ⠀⣸⡀⢸⣿⢸⣿⣿⣿⣿⣿⣿⡇⣸⡇⣿⡇⠀⡟⣻⢳⣷⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠘⣧
        ⢰⡟⡿⡆⠹⣧⡙⢿⣿⣿⠿⡟⢡⣿⢷⣿⣧⠾⢠⣿⣾⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠘
        ⠀⠻⡽⣦⠀⠈⠙⠳⢶⣦⡶⠞⢻⡟⡸⠟⠁⢠⠟⠉⠉⠙⠿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⡴
        ⠀⠀⢸⣿⡇⠀⠀⣀⣠⠀⢀⡀⠸⣹⠇⠀⣰⡟⡀⠀⠈⠛⠻⢿⣻⣿⡿⠀⠀⠀⠀⠀⠀⡠⠁
        ⠀⠀⢸⣿⣇⣴⢿⣿⣿⣿⣮⣿⣷⡟⠀⣰⣿⢰⠀⣀⠀⠀⠀⢀⣉⣿⡇⠀⠀⠀⠀⠀⣸⠃⠀
        ⠀⠀⢸⣿⡟⣯⠸⣿⣿⣿⣿⢈⣿⡇⣼⣿⠇⣸⡦⣙⣷⣦⣴⣯⠿⠛⢷⡀⠀⠀⠀⣰⡟⠀⠀
        ⠀⠀⠘⣿⣿⡸⣷⣝⠻⠟⢋⣾⣟⣰⡏⣠⣤⡟⠀⠀⠈⠉⠁⠀⠀⠀⠀⢻⣶⠀⢀⣿⠁⠀⠀
        ⠀⠀⠀⢸⡿⣿⣦⣽⣛⣛⣛⣭⣾⣷⡶⠞⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⡟⠀⠀⠀⠀
        ⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠁⢸⢻⠁⠀⠀⠀⠀
        ⠀⠀⠀⠀⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣤⣤⣀⣀⣀⣀⣀⣠⣤⠶⠛⠁⢀⣾⡟⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⢿⣻⣿⣿⣿⣿⣿⣿⣎⣿⡅⠀⠈⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⣼⣿⠁⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡷⠟⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠙⢿⣿⣿⠻⢿⣿⣿⣟⣂⣀⣀⣀⣀⣀⣀⣤⠴⠋⠁⣾⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⣷⣷⡄⠀⠀⠀⠉⠉⠉⠉⠉⠀⠀⠀⢀⡞⠁⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣷⣤⣤⣤⣤⣄⣤⣤⡤⠴⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀

                       """);
    // switch end
    threadSleep(1000);
  }

}
