package LoginAndRegister;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class LoginAndRegister {
  // to avoid hardcoding file paths when other users use this program
  private static final String DIR_CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
  // file.seperator adds the file seperator symbol accoiding to the machine that
  // user is curently on
  // eg: "/" for unix and "\\" for windows
  // toAbsolutePath is required caus we aint hardcoding this folder anywhere on
  // other places as of now
  private static final String DIR_ALL_USERS_DIR = DIR_CURRENT_PATH + File.separator + "Users";
  private static boolean loggedIn = false;

  // public static void main(String[] args) {
  // String userName = "lmao";
  // String userPassword = "lamo2";
  // userDirInitialization();
  // makeUserProfile(userName, userPassword);
  // logIn(userName, userPassword);
  // }

  public static void userDirInitialization() {
    File allUsersDir = new File(DIR_ALL_USERS_DIR);

    // .mkdirs makes directories
    // checking if the file was made, if not it will be created
    // if it exists nothing will be done
    if (allUsersDir.mkdir()) {
      System.out.println("Folder userData was made!");
    } else {
      System.out.println("Folder userData already exists!");
    }
  }

  public static boolean makeUserProfile(String userName, String userPassword) {
    String userDirPath = DIR_ALL_USERS_DIR + File.separator + userName;
    File userDir = new File(userDirPath);

    try {
      userDir.mkdir();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("could not create folder");
    }

    String userProfileName = userName + ".txt";
    File userProfile = new File(DIR_ALL_USERS_DIR + File.separator + userName + File.separator + userProfileName);

    // creates a new buffered BufferedWriter
    // a simple version of it would be
    //
    // FileWriter fw = new FileWriter(userFilePath);
    // BufferedWriter bfw = new BufferedWriter(fw);
    //
    // difference between filewriter and BufferedWriter is that
    // buffered writer writes a whole string at once
    // while filewriter writes every character once at a time
    // we are using buffered writer because there might be other user stats that
    // will be added in future
    // System.lineSeparator adds a newline character
    try {
      if (!userProfile.createNewFile()) {
      } else {

        BufferedWriter bfw = new BufferedWriter(new FileWriter(userProfile));
        // checking if the user already exists
        bfw.write("userName=" + userName + System.lineSeparator());
        bfw.write("userPassword=" + userPassword + System.lineSeparator());
        System.out.println("New user created");

        // catching io IOException to the variable e
        // gotta use try cauz it will throw IOException if anything goes wrong while
        // creating a new file
        // pretty sure code wont run if i dont catch that IOException
        // but i have not tested it
        // so we are catching that IOException
        bfw.close();
      }
    } catch (IOException e) {
      // prints errors making it easier to debug
      e.printStackTrace();
    }
    return loggedIn = false;

  }

  public static boolean logIn(String userName, String userPassword) {
    String userProfileName = userName + ".txt";
    File userProfile = new File(DIR_ALL_USERS_DIR + File.separator + userName + File.separator + userProfileName);

    List<String> userInfo = new ArrayList<>();

    System.out.println(userProfile);
    // checking if the user profile exists
    if (!userProfile.exists()) {
      System.out.println("user not found");
      return loggedIn = false;
    }

    if (userInfo.size() < 2) {
      System.out.println("Corrupt or empty user profile.");
      loggedIn = false;
    }

    // pulling out username and password of the existing user
    try (BufferedReader bfr = new BufferedReader(new FileReader(userProfile))) {
      String line;
      while ((line = bfr.readLine()) != null) {
        line = line.substring(line.indexOf("=") + 1);
        userInfo.add(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (userName.matches(userInfo.get(0)) && userPassword.matches(userInfo.get(1))) {
      System.out.println("successfully loaded user profile");
      loggedIn = true;
    } else {
      System.out.println("invalid password");
    }
    return loggedIn;

  }

  public static boolean rememberLogInInfo() {
    if (loggedIn) {

    }
    return loggedIn;
  }
}
