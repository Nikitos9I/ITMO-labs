package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.Scanner;

/**
 * Created by nikitos on 04.12.17.
 */
public class task21 {

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
        Scanner scan = new Scanner( new FileReader("num2part.in"));
        PrintWriter writer = new PrintWriter("num2part.out");
        //Scanner scan = new Scanner( new FileReader("arrange.int.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = scan.nextInt();
        long r = scan.nextLong() + 1;

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

        StringBuilder ans = new StringBuilder();

        int sum = 0;
        int now = sum + 1;
        for (;now <= n && r > 0;) {
            if (dp[n - sum][now] < r) {
                r -= dp[n - sum][now++];
            } else if (dp[n - sum][now] >= r) {
                ans.append(now).append("+");
                sum += now;
            }
        }

        writer.write(ans.toString().substring(0,ans.length() - 1));
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new task21().start();
    }

}
