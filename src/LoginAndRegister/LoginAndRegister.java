package LoginAndRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;

public class LoginAndRegister {
  // define ALL USER dir
  private final String DIR_MAIN = Paths.get("").toAbsolutePath().toString() + File.separator + "USERS";
  private String userName = "guest";

  private String userPassword = "password";

  public LoginAndRegister(Scanner scan) {

    clearScreen();
    initilizeRequiredFolderAndFile();

    int selection = loginOrSignupSelection(scan);
    takeUserNameAndPassword(scan);

    boolean created = false;
    while (!created) {
      if (selection == 2) {
        created = createNewUser();
        takeUserNameAndPassword(scan);
        clearScreen();
      }
    }

    boolean doContinue = true;
    while (doContinue) {
      doContinue = checkCredentialMatch();
      doContinue = laodUserDataManually();
    }

  }

  private void initilizeRequiredFolderAndFile() {
    File USERS = new File(DIR_MAIN);
    File lastUserName = new File(USERS + File.separator + "lastUserName.txt");

    // returning early if the files exists
    if (USERS.exists() && lastUserName.exists()) {
      return;
    }

    if (!USERS.mkdir()) {
      System.out.println("Folder could not be loaded.");
    }
    System.out.println("Users dir loaded");

    try {
      if (!lastUserName.createNewFile()) {
        System.out.println("lastUserName file exists.");
      }
      System.out.println("lastUserName file created");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("initilizeRequiredFolderAndFile method: sth went wrong");
    }
  }

  private int loginOrSignupSelection(Scanner scan) {

    while (true) {
      System.out.print("Enter 1 to Log-In and 2 to Signup: ");
      String userInput = scan.nextLine().trim();

      try {
        int choice = Integer.parseInt(userInput);
        if (choice == 1 || choice == 2) {
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

    File newUser = new File(DIR_MAIN + File.separator + userName);
    if (!newUser.mkdir()) {
      System.out.println("That username is taken. Please choose something else!");
      return created;
    }

    File newUserProfile = new File(newUser + File.separator + userName + ".txt");
    File newUserData = new File(newUser + File.separator + userName + File.separator + "data.txt");

    try {
      if (newUserProfile.createNewFile()) {
        System.out.println("new user" + userName + " was created");
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
    File user = new File(DIR_MAIN + File.separator + userName + File.separator + userName + ".txt");
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

  private boolean laodUserDataManually() {
    File userData = new File(DIR_MAIN + File.separator + userName + File.separator + "data.txt");

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
  private void logOut() {

  }

  // log in or signup screen
  private void takeUserNameAndPassword(Scanner scan) {
    System.out.print("Username: ");
    this.userName = scan.nextLine();
    System.out.print("Password: ");
    this.userPassword = scan.nextLine();
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
