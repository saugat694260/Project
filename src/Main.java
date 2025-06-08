import LoginAndRegister.*;
import utils.EncryptDecrypt;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    LoginAndRegister.userDirInitialization();
    LoginAndRegister.makeUserProfile("lmao", "lmao");
    // close scanner at the very end of the program
    scan.close();
  }
}
