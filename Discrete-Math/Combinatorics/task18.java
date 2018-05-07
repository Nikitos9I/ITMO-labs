package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 02.12.17.
 */
public class task18 {

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
        BufferedReader buf = new BufferedReader( new FileReader("brackets2num.in"));
        PrintWriter writer = new PrintWriter("brackets2num.out");

        String input = buf.readLine();
        long[][] dp = new long[input.length()][input.length() + 1];

        dp[0][0] = 1;
        for (int i = 1; i < input.length(); ++i) {
            dp[0][i] = 0;
        }

        prevCount(dp,input);

        int counter = -1;
        long number = 0;
        int open = 0;
        int close = 0;
        while (++counter < input.length()) {
            if (input.charAt(counter) == ')') {
                number += dp[input.length() - 1 - counter][open - close + 1];
                close++;
            } else {
                open++;
            }
        }

        writer.print(number);
        writer.close();

    }

    public long[][] prevCount(long[][] dp, String input) {

        for (int i = 1; i < input.length(); ++i) {
            for (int j = 0; j < input.length() - 1; ++j) {
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
        new task18().start();
    }

}
