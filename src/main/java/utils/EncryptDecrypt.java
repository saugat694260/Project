package utils;  // Defines the package this class belongs to

public class EncryptDecrypt {  // Declaration of the EncryptDecrypt class

    // Original character set including letters, digits, symbols, and space
    private String a = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_` abcdefghijklmnopqrstuvwxyz{|}~";
    private char[] list = a.toCharArray();  // Convert the original string to a char array for easy indexing

    // Shuffled character set used for encryption corresponding to the original set
    private String b = "_DkZ8QzmVAOhHGSR|UY7\"i@bj`L,cud<3?&4Nsw:TXKx}Pv/W;!tf5^)(*I$rF6 q9lE\\0~%2aJ=y'B1onM{#>]ep[gC+-.";
    private char[] shuffeledList = b.toCharArray();  // Convert the shuffled string to a char array for indexing

    public EncryptDecrypt() {
        // Default constructor, currently does nothing
    }

    // Method to encrypt a given string using character substitution
    public String encrypt(String data) {
        char[] letters = data.toCharArray();  // Convert input string to char array for processing

        for (int i = 0; i < letters.length; i++) {  // Loop through each character in the input
            for (int j = 0; j < list.length; j++) {  // Loop through original character list
                if (letters[i] == list[j]) {  // If character matches one in original list
                    letters[i] = shuffeledList[j];  // Replace it with corresponding char in shuffled list
                    break;  // Stop inner loop once match is found and replacement done
                }
            }
        }
        return new String(letters);  // Convert char array back to String and return encrypted result
    }

    // Method to decrypt an encrypted string by reversing the substitution
    public String decrypt(String message) {
        char[] letters = message.toCharArray();  // Convert encrypted string to char array

        for (int i = 0; i < letters.length; i++) {  // Loop through each character in the encrypted message
            for (int j = 0; j < shuffeledList.length; j++) {  // Loop through shuffled character list
                if (letters[i] == shuffeledList[j]) {  // If character matches one in shuffled list
                    letters[i] = list[j];  // Replace it with corresponding original character
                    break;  // Stop inner loop after match and replacement
                }
            }
        }
        return new String(letters);  // Convert char array back to String and return decrypted result
    }
}
