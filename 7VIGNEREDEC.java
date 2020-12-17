import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("----Vigenere Cipher----");
        Scanner scan = new Scanner(System.in);
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int n = 26, x = 0;
        char matrix[][] = new char[n][n];
        for (int i = 0; i < n; i++) {
            int k = x;
            for (int j = 0; j < n; j++) {
                if (k == n)
                    k = 0;
                matrix[i][j] = alpha.charAt(k);
                k += 1;
            }
            x += 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Enter the Encrypted Text: ");
        String text = scan.next();
        System.out.println("Enter the Key Text: ");
        String key = scan.next();
        key = key.repeat(text.length() / key.length()) + key.substring(0, (text.length() % key.length()));
        System.out.println("KEY : " + key);
        String Decrypted = "";
        for (int i = 0; i < text.length(); i++) {
            Decrypted += (char) ((text.charAt(i) - key.charAt(i) + 26) % 26 + 65);
        }
        System.out.println("Decrypted Text : " + Decrypted);
        scan.close();
    }
}