import LoginAndRegister.*;
import utils.EncryptDecrypt;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    LoginAndRegister loginSystem = new LoginAndRegister(scan);
    // close scanner at the very end of the program
    scan.close();
  }
}
