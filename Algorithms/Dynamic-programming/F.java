package HomeWork.Algoritms.lab3;

import java.io.*;

/**
 * Created by nikitos on 07.12.17.
 */
public class F {

    public StreamTokenizer t;
    public int[][] dp;
    public int[][] subst;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new InputStreamReader(System.in));

        String input = buf.readLine();

        dp = new int[input.length()][input.length()];
        subst = new int[input.length()][input.length()];
        int n = input.length();

        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        int l = 0, r = 0;
        while (++r < n) {
            l = r + 1;
            while (--l >= 0) {
                if (r != l) {
                    int min = 1000000;
                    subst[l][r] = -1;
                    if (isPair(input.charAt(l),input.charAt(r))) {
                        min = dp[l + 1][r - 1];
                    }
                    int k = l - 1;
                    while (++k < r) {
                        if (dp[l][k] + dp[k + 1][r] < min) {
                            min = dp[l][k] + dp[k + 1][r];
                            subst[l][r] = k;
                        }
                    }
                    dp[l][r] = min;
                }
            }
        }

        System.out.println(input.length() - dp[0][n - 1]);

        //whereIsAnswer(0, n - 1, input);

    }

    public void whereIsAnswer(int l, int r, String input) {
        if (dp[l][r] + l == r + 1) {
            return;
        }
        if (dp[l][r] == 0) {
            for (int i = l; i <= r; i++) {
                System.out.print(input.charAt(i));
            }
            return;
        }
        if (subst[l][r] < 0) {
            System.out.print(input.charAt(l));
            whereIsAnswer(l + 1, r - 1, input);
            System.out.print(input.charAt(r));
            return;
        }
        whereIsAnswer(l, subst[l][r], input);
        whereIsAnswer(subst[l][r] + 1, r, input);
    }

    public boolean isPair(char open, char close) {
        return (open == '(' && close == ')' || open == '[' && close == ']' || open == '{' && close == '}');
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }

}
