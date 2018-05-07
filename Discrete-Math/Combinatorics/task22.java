package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 04.12.17.
 */
public class task22 {

    public StreamTokenizer t;
    public int n = 0;
    public long ans = 0;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader("part2num.in"));
        PrintWriter writer = new PrintWriter("part2num.out");
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input = buf.readLine();
        input += "+";

        int count = 0;
        String number = "";
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                number += input.charAt(i);
            } else {
                n += Integer.parseInt(number);
                number = "";
                count++;
            }
        }

        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int ans = 0;
                for (int k = j; k < n && i - j > 0; k++) {
                    ans += dp[i - j][k];
                }
                dp[i][j] = ans > dp[i][j]? ans: dp[i][j];
            }
        }

        int j = 1;

        long result = 0;
        int target = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                number += input.charAt(i);
            } else {
                int now = Integer.parseInt(number);
                while (j < now) {
                    result += dp[n - target][j];
                    j++;
                }
                target += now;
                number = "";
            }
        }

        writer.println(result);
        writer.close();

    }

    public long number(int n, int k) {
        if (n < 1 && k < 1) return 1;
        if (k <= n) {
            ans += number(n, k - 1) + number(n - k, k);
        } else {
            ans += number(n,n);
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        new task22().start();
    }

}
