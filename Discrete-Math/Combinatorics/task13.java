package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.Scanner;

/**
 * Created by nikitos on 24.11.17.
 */
public class task13 {

    public void start() throws IOException {
        //Scanner scan = new Scanner( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        Scanner scan = new Scanner( new FileReader("num2perm.in"));
        PrintWriter writer = new PrintWriter("num2perm.out");

        int n = scan.nextInt();
        long k = scan.nextLong();

        int perm[] = new int[n + 1];
        boolean cp[] = new boolean[n + 1];
        boolean f = false;

        for (int i = 1 ; i <= n; ++i) {
            long z = k / fact(n - i);
            k %= fact(n - i);

            int counter = 0;
            int j = 1;
            while (j <= n) {
                if (!cp[j]) {
                    f = true;
                    counter++;
                    if (z == counter - 1) {
                        perm[i] = j;
                        cp[j] = true;
                    }
                }
                ++j;
            }
        }

        for (int i = 1; i <= n; ++i) {
            writer.write(perm[i] + " ");
        }

        writer.close();

    }

    public long fact(int n) {
        long a = 1;
        for (int i = 1; i <= n; i++) {
            a *= i;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task13().start();
    }

}
