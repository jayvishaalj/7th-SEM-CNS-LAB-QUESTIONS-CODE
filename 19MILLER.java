import java.io.*;
import java.math.*;
import java.util.*;

class Main {
    static int power(int x, int y, int p) {
        int res = 1;
        x = x % p;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    static boolean millerTest(int d, int n) {
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

    static boolean isPrime(int n, int k) {
        if (n <= 1 || n == 4)
            return false;
        if (n <= 3)
            return true;
        int d = n - 1;
        while (d % 2 == 0)
            d /= 2;
        for (int i = 0; i < k; i++)
            if (!millerTest(d, n))
                return false;
        return true;
    }

    public static void main(String args[]) {
        int k = 4;
        System.out.println("Enter the number :  ");
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        System.out.println(isPrime(num, k));
    }
}
