import java.util.*;

public class Main {
    static ArrayList<ArrayList<String>> fence = new ArrayList<ArrayList<String>>();

    public static String Encrypt(String text, int key) {
        String res = "";
        fence = new ArrayList<ArrayList<String>>(key);
        for (int i = 0; i < key; i++) {
            fence.add(new ArrayList<String>(text.length()));
        }
        int down = 1, up = 0;
        int i = 0, x = 0;
        for (char c : text.toCharArray()) {
            fence.get(i).add(x, Character.toString(c));
            for (int z = 0; z < key; z++) {
                if (z == i)
                    continue;
                else
                    fence.get(z).add(x, " ");
            }
            if (down == 1)
                i += 1;
            else
                i -= 1;
            if (i == key) {
                down = 0;
                up = 1;
                i -= 2;
            }
            if (i == -1) {
                down = 1;
                up = 0;
                i += 2;
            }
            x += 1;
        }
        for (ArrayList<String> arrayList : fence)
            System.out.println(arrayList);
        System.out.println("");
        for (i = 0; i < key; i++) {
            for (String c : fence.get(i)) {
                res += c;
            }
        }
        return res;
    }

    public static String Decrypt(String enc, int key) {
        String res = "";
        int down = 1, up = 0;
        int i = 0, x = 0;
        for (i = 0; i < enc.length(); i++) {
            res += ((ArrayList) fence.get(x)).get(i);
            if (down == 1)
                x += 1;
            else
                x -= 1;
            if (x == key) {
                down = 0;
                up = 1;
                x -= 2;
            }
            if (x == -1) {
                down = 1;
                up = 0;
                x += 2;
            }
        }
        return res;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print(("Enter The Text : "));
        String text = sc.nextLine();
        text = text.replace(" ", "");
        text = text.toUpperCase();
        System.out.print("Enter a Key : ");
        Integer key = sc.nextInt();
        System.out.println("");
        System.out.println("Menu : ");
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");
        System.out.println("0. Exit");
        System.out.print("Enter Choice : ");
        System.out.println("");
        int op = sc.nextInt();
        while (op != 0) {
            switch (op) {
                case 1:
                    System.out.println("");
                    String enc = Encrypt(text, key);
                    System.out.println("Encrypted Text : " + enc.replace(" ", ""));
                    break;
                case 2:
                    String dec = Decrypt(text, key);
                    System.out.println("");
                    System.out.println("Decrypted Text : " + dec);
                    break;
                default:
                    System.out.println("Invalid Choice !!");
            }
            System.out.println("");
            System.out.println("Menu : ");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("0. Exit");
            System.out.print("Enter Choice : ");
            System.out.println("");
            op = sc.nextInt();
        }
    }
}
