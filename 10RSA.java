import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;

class Main {
    String byteToString(byte[] sample) {
        String result = "";
        for (byte b : sample) {
            result += Byte.toString(b);
        }
        return result;
    }

    byte[] encrypt(String plainText, BigInteger E, BigInteger N) {
        byte[] toEncrypt = plainText.getBytes();
        BigInteger m = new BigInteger(toEncrypt);
        BigInteger cipher;
        cipher = m.modPow(E, N);
        System.out.println("The Cipher is: " + cipher);
        return cipher.toByteArray();
    }

    byte[] decrypt(byte[] encrypted, BigInteger D, BigInteger N) {
        BigInteger m = new BigInteger(encrypted);
        BigInteger plain;
        plain = m.modPow(D, N);
        return plain.toByteArray();
    }

    int power(int a, int b, int mod) {
        if (b == 0)
            return 1;
        if (b == 1)
            return a % mod;
        int temp = power(a, b / 2, mod);
        temp = (temp * temp);
        if (b % 2 != 0)
            temp *= a;
        return temp % mod;
    }

    boolean millerTest(int d, int n) {
        int a = 2 + (int) (Math.random() % (n - 4));
        int x = power(a, d, n);
        if (x == 1 || x == n - 1)
            return true;
        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;
            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }
        return false;
    }

    boolean isPrime(int n) {
        if (n <= 1 || n == 4)
            return false;
        if (n == 3)
            return true;
        int d = n - 1;
        int k = n - 2;
        while (d % 2 == 0) {
            d /= 2;
            // k++;
        }
        for (int i = 0; i < k; i++)
            if (!millerTest(d, n))
                return false;
        return true;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Main rsa = new Main();
        Scanner sc = new Scanner(System.in);
        BigInteger P, Q, N, Phi, E, D;
        byte[] encryptedBytes = new byte[10000];
        byte[] decryptedBytes = new byte[10000];
        P = BigInteger.probablePrime(10, random);
        Q = BigInteger.probablePrime(10, random);
        while (Q.compareTo(P) == 0) {
            Q = BigInteger.probablePrime(10, random);
        }
        while (!rsa.isPrime(P.intValue())) {
            P = BigInteger.probablePrime(10, random);
        }
        while (!rsa.isPrime(Q.intValue())) {
            Q = BigInteger.probablePrime(10, random);
        }
        N = P.multiply(Q);
        Phi = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));
        E = BigInteger.probablePrime(5, random);
        while (Phi.gcd(E).compareTo(BigInteger.ONE) > 0 && E.compareTo(Phi) < 0) {
            E.add(BigInteger.ONE);
        }
        D = E.modInverse(Phi);
        while (D.compareTo(Phi) >= 0) {
            D = E.modInverse(Phi);
        }
        String plainText = "";
        System.out.println("Random Key Generated!");
        System.out.println("Public Key: (\nN: " + N + ", \nE: " + E + "\n)");
        System.out.println("Private Key: (\nD: " + D + ",\nP: " + P + ",\nQ: " + Q + "\n)");
        System.out.println("\nMENU: ");
        System.out.println("1. Get Plain Text");
        System.out.println("2. Encrypt");
        System.out.println("3. Decrypt");
        while (true) {
            char ch;
            System.out.print("Enter the choice: ");
            ch = sc.next().charAt(0);
            if (ch == '1') {
                // get input
                System.out.print("Enter the plain Text: ");
                sc.nextLine();
                plainText = sc.nextLine();
            } else if (ch == '2') {
                // encrypt
                if (plainText.equals("")) {
                    System.out.println("Enter the plain text first.");
                    continue;
                } else {
                    encryptedBytes = rsa.encrypt(plainText, E, N);
                }
            } else if (ch == '3') {
                decryptedBytes = rsa.decrypt(encryptedBytes, D, N);
                System.out.println("The decrypted String is: " + new String(decryptedBytes));
            } else {
                break;
            }
        }
    }
}
