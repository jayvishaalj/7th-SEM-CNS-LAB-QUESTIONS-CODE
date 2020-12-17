import java.util.Scanner;

class Main {
    int mat[][];
    int matinv[][];
    int n;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter size of matrix then the matrix ");
        int n = in.nextInt();
        int mat[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                mat[i][j] = in.nextInt();
        }
        // LNSHDLEWMTRW
        // 17 17 5 21 18 21 2 2 19
        Main m = new Main(mat);
        System.out.println("Enter String to decrypt ");
        String text = in.next();
        System.out.println("Decrypted = " + m.decrypt(text));
        in.close();
    }

    Main(int mat[][]) {
        n = mat.length;
        this.mat = new int[n][n];
        this.matinv = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                this.mat[i][j] = mat[i][j];
        }
        // System.out.println("Det = " + determinant(this.mat));
        int det = determinant(this.mat) % 26;
        if (det < 0)
            det += 26;
        System.out.println("Det = " + det);
        if (det == 0 || gcd(det, 26) != 1)
            throw new RuntimeException("Not invertible");
        matinv = inverse(this.mat);
        printmat();
    }

    private int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public int determinant(int[][] a) {
        int n = a.length;
        if (n == 1)
            return a[0][0];
        else if (n == 2)
            return (a[0][0] * a[1][1]) - (a[0][1] * a[1][0]);
        int[][] temp = new int[n - 1][n - 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                int rn = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i)
                        continue;
                    temp[j - 1][rn++] = a[j][k];
                }
            }
            ans += Math.pow(-1.0, 2.0 + i) * a[0][i] * determinant(temp);
        }
        return ans;
    }

    public int[][] adjoint(int[][] a) {
        int n = a.length;
        int adj[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int temp[][] = new int[n - 1][n - 1];
                int r = 0, c = 0;
                for (int i1 = 0; i1 < n; i1++) {
                    for (int j1 = 0; j1 < n; j1++) {
                        if (i1 == i || j1 == j)
                            continue;
                        temp[r][c++] = a[i1][j1];
                        if (c == n - 1) {
                            c = 0;
                            r++;
                        }
                    }
                }
                adj[j][i] = (int) Math.pow(-1.0, (double) i + j) * determinant(temp);
            }
        }
        return adj;
    }

    static int modpow(int x, int y, int p) {
        int res = 1;
        x = x % p;
        if (x == 0)
            return 0;
        while (y > 0) {
            if ((y & 1) == 1)
                res = (res * x) % p;
            y >>= 1;
            x = (x * x) % p;
        }
        return res;
    }

    private int[][] inverse(int[][] a) {
        int n = a.length;
        int det = determinant(a) % 26;
        if (det < 0)
            det += 26;
        int detinv = modpow(det, 11, 26);
        int adj[][] = adjoint(a);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adj[i][j] = ((adj[i][j] * detinv) % 26 + 26) % 26;
            }
        }
        return adj;
    }

    public String decrypt(String text) {
        String ans = "";
        int textn = text.length();
        for (int e = 0; e < textn; e += n) {
            String res = "";
            for (int i = 0; i < n; i++) {
                int temp = 0;
                for (int j = 0; j < n; j++)
                    temp += matinv[i][j] * ((int) text.charAt(e + j) - 97);
                temp = temp % 26;
                if (temp < 0)
                    temp += 26;
                res += (char) (97 + temp);
            }
            ans += res;
        }
        return ans;
    }

    public void printmat() {
        System.out.println("Key Matrix");
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Inverse Key Matrix");
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                System.out.print(matinv[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}