package LoginAndRegister;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Paths;
// import java.nio.file.Path;

import java.util.List;
import java.util.ArrayList;

public class LoginAndRegister {

  private String userName;
  private String userPassword;

  // path returns path of folder it is run in. it will be run from Main.java so
  // file path will be src/
  private static final String DIR_CURRENT_PATH = Paths.get("").toAbsolutePath().toString();

  private static final File DIR_ALL_USERS_DIR = new File(DIR_CURRENT_PATH + File.separator + "Users");

  private final File LOGGED_IN_USER = new File(DIR_ALL_USERS_DIR + File.separator + "loggedInUser.txt");

  private File USER_DIR() {
    return new File(DIR_ALL_USERS_DIR + File.separator + userName);
  }

  private File USER_PROFILE() {
    return new File(USER_DIR() + File.separator + userName + ".txt");
  }

  // public static void main(String[] args) {
  // neww lol = new neww("lmao", "omal");
  // }
  //
  // public neww(String userName, String userPassword) {
  // this.userName = userName;
  // initializeRequiredUserDataDirs();
  // autoLogin();
  // createNewUserCredentials();
  // }

  public LoginAndRegister(Scanner scan) {
    boolean userIsLoggedIn = false;
    while (!userIsLoggedIn) {
      clearScreen();
      initializeRequiredUserDataDirs();

      if (autoLogin()) {
        loadUserData();
        userIsLoggedIn = true;
      }

      threadSleep(2500);
      clearScreen();

      int userChoice = loginOrSignupScreenTUI(scan);

      System.out.printf("Username: ");
      this.userName = scan.nextLine();
      System.out.printf("Password: ");
      this.userPassword = scan.nextLine();
      switch (userChoice) {
        case 1 -> {
          readUserCredentials();
          userIsLoggedIn = checkUserCredentials();
          loadUserData();
          createAutoLoginUserData();
        }
        case 2 -> {
          createNewUserCredentials();
          createAutoLoginUserData();
        }

        case 3 -> {
          System.out.println("if this text appeared on your screen...\nwell you are one unlucky basterd :p");
        }
      }

    }

  }

  // makes Users dir and loggedInUser txt file
  private void initializeRequiredUserDataDirs() {
    if (DIR_ALL_USERS_DIR.exists() && LOGGED_IN_USER.exists()) {
      System.out.println("User data folders initialized");
      return;
    }
    try {
      DIR_ALL_USERS_DIR.mkdir();
      LOGGED_IN_USER.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Initializing required dirs failed");
    }
  }

  // checks if there is previously logged in user
  // if there is, user will be auto logged in
  //
  // currently there is no toggle for autologin (will make it in future)
  private boolean autoLogin() {
    boolean logInAuto = false;
    if ((LOGGED_IN_USER.length() == 0)) {
      System.out.println("Could not autoLogin. \nNo user data found!! ");
    }

    String line;
    if (USER_DIR().exists()) {
      return false;
    }

    try (BufferedReader bfr = new BufferedReader(new FileReader(LOGGED_IN_USER));) {
      line = bfr.readLine();
      // currently USER_DIR().getName() returns null because we have not initialized
      // userName in the USER_DIR() and it does not update as automatically
      if (line.matches(USER_DIR().getName())) {
        // gotta load user data
        System.out.println("autologin success");
        logInAuto = true;
      } else {
        System.out.println("user profile curropted or deleted");
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("caused by autologin method");
    }
    return logInAuto;
  }

  private void createAutoLoginUserData() {
    if (loadUserData()) {
      try (BufferedWriter bfw = new BufferedWriter(new FileWriter(LOGGED_IN_USER))) {
        bfw.write(userName);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // method to create new user
  private void createNewUserCredentials() {
    writeUserCredentials();
  }

  // prolly will have to make it static
  // make "userName".txt file to store user name and password
  private void writeUserCredentials() {
    if (USER_DIR().exists()) {
      System.out.println("This user already exists!!");
      return;
    }

    USER_DIR().mkdir();

    try (
        BufferedWriter bfw = new BufferedWriter(new FileWriter(USER_PROFILE()))) {
      bfw.write("Username=" + userName);
      bfw.newLine();
      bfw.write("Password=" + userPassword);
      bfw.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // List to save the read user credentials
  private List<String> userCredentials = new ArrayList<>();

  private void readUserCredentials() {

    if (!USER_PROFILE().exists()) {
      System.out.println("username not found");
      return;
    }

    try (BufferedReader bfr = new BufferedReader(new FileReader(USER_PROFILE()))) {
      String line;
      while ((line = bfr.readLine()) != null) {
        line = line.substring(line.indexOf("=") + 1);
        userCredentials.add(line);
      }
    } catch (IOException e) {

      e.printStackTrace();
    }

  }

  // loads List<String userCredentials and checks the user input of username and
  // password
  private boolean checkUserCredentials() {
    boolean matches = false;

    if (userCredentials.get(0).matches(userName) && userCredentials.get(1).matches(userPassword)) {
      matches = true;
      System.out.println("User credentials matched. logging In now....");
    }

    return matches;
  }

  // private boolean logIn() {
  // boolean loggedIn = false;
  // if (checkUserCredentials()) {
  // loadUserData();
  // loggedIn = true;
  // }
  // return loggedIn;
  // }

  private boolean loadUserData() {
    System.out.println(USER_PROFILE().getAbsolutePath());
    boolean loggedIn = true;
    return loggedIn;
  }

  private int loginOrSignupScreenTUI(Scanner scan) {
    int userChoice = 0;
    boolean selected = false;

    while (!selected) {
      System.out.printf("Enter 1 to login and 2 to sign-up: ");
      if (scan.hasNextInt()) {
        userChoice = scan.nextInt();
        if (userChoice == 1 || userChoice == 2) {
          selected = true;
        } else {
          wrongUserInputMessege();
          scan.nextLine();
        }
      } else {
        wrongUserInputMessege();
        scan.nextLine();
      }
    }

    scan.nextLine();
    return userChoice;
  }

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
