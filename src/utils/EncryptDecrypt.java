package utils;

public class EncryptDecrypt {

    String a ="!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_` abcdefghijklmnopqrstuvwxyz{|}~";
    private char[] list = a.toCharArray();
    
    String b= "_DkZ8QzmVAOhHGSR|UY7\"i@bj`L,cud<3?&4Nsw:TXKx}Pv/W;!tf5^)(*I$rF6 q9lE\\0~%2aJ=y'B1onM{#>]ep[gC+-.";
    private char[] shuffeledList = b.toCharArray();
    public EncryptDecrypt() {
     //

      
    }

    public String encrypt(String data) {
        char[] letters = data.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < list.length; j++) {
                if (letters[i] == list[j]) {
                    letters[i] = shuffeledList[j];
                    break;
                }
            }
        }
        return new String(letters);
    }

    public String decrypt(String message) {
        char[] letters = message.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < shuffeledList.length; j++) {
                if (letters[i] == shuffeledList[j]) {
                    letters[i] = list[j];
                    break;
                }
            }
        }
        return new String(letters);
    }
}
