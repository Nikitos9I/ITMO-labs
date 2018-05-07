package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.Scanner;

/**
 * Created by nikitos on 02.12.17.
 */
public class task17 {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        Scanner scan = new Scanner( new FileReader("num2brackets.in"));
        //BufferedReader buf =  new BufferedReader( new FileReader("num2brackets.in"));
        PrintWriter writer = new PrintWriter("num2brackets.out");

        int n = scan.nextInt();
        long k = scan.nextLong();
        k++;
        StringBuilder output = new StringBuilder();
        n = 2*n;

        long[][] dp = new long[n][n];
        dp[0][0] = 1;
        for (int i = 1; i < n; ++i) {
            dp[0][i] = 0;
        }

        prevCount(dp, n);

        int counter = -1;
        int open = 0;
        int close = 0;
        while (++counter < n) {
            if (k > dp[n - 1 - counter][open - close + 1]) {
                k -= dp[n - 1 - counter][open - close + 1];
                close++;
                output.append(')');
            } else {
                open++;
                output.append('(');
            }
        }

        //System.out.println(Integer.MAX_VALUE);

        if (n != 2) {
            writer.write(output.toString());
        } else {
            writer.write("()");
        }
        writer.close();

    }

    public long[][] prevCount(long[][] dp, long n) {

        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < n - 1; ++j) {
                if (j > 0) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                } else {
                    dp[i][j] = dp[i - 1][j + 1];
                }
            }

        }

        return dp;
    }

    public int exp(int n) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= 2;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task17().start();
    }

}
