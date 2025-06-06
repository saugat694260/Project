package LoginAndRegister;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class LoginAndRegister {
  // to avoid hardcoding file paths when other users use this program
  private static final String DIR_CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
  // file.seperator adds the file seperator symbol accoiding to the machine that
  // user is curently on
  // eg: "/" for unix and "\\" for windows
  // toAbsolutePath is required caus we aint hardcoding this folder anywhere on
  // other places as of now
  private static final String DIR_USER_DATA = DIR_CURRENT_PATH + File.separator + "userData";

  public static void userDirInitialization() {
    File userData = new File(DIR_USER_DATA);

    // .mkdirs makes directories
    // checking if the file was made, if not it will be created
    // if it exists nothing will be done
    if (userData.mkdir()) {
      System.out.println("Folder userData was made!");
    } else {
      System.out.println("Folder userData already exists!");
    }
  }

  public static void makeUserProfile(String userName, String userPassword) {
    String userProfileName = userName + ".txt";
    String userProfilePath = DIR_USER_DATA + File.separator + userProfileName;
    File userProfile = new File(userProfilePath);

    boolean userExists = false;

    try {
      // checking if the user already exists
      if (!userProfile.createNewFile()) {
        System.out.println("User with that name already exists!");
        userExists = true;
      } else {
        System.out.println("New user created");
      }
      // catching io IOException to the variable e
      // gotta use try cauz it will throw IOException if anything goes wrong while
      // creating a new file
      // pretty sure code wont run if i dont catch that IOException
      // but i have not tested it
      // so we are catching that IOException
    } catch (IOException e) {
      // prints errors making it easier to debug
      e.printStackTrace();
    }

    // executes if user did not exist prior to creating new user from the above code
    if (!userExists) {
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
      // the true is for append mode
      try (BufferedWriter bfw = new BufferedWriter(new FileWriter(userProfile, true))) {
        bfw.write("userName=" + userName + System.lineSeparator());
        bfw.write("userPassword=" + userPassword + System.lineSeparator());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
