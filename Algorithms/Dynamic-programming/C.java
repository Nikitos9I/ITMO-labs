package HomeWork.Algoritms.lab3;

import java.io.*;

/**
 * Created by nikitos on 04.12.17.
 */
public class C {

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
        t = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();
        if (n == 1) System.out.println(8); else {

            long[][] ans = new long[n][10];
            for (int i = 0; i <= 9; ++i) {
                ans[1][i] = 2;
            }
            ans[1][3] = 1;
            ans[1][1] = 1;
            ans[1][5] = 0;
            for (int i = 2; i < n; i++) {
                ans[i][0] = ans[i - 1][4] + ans[i - 1][6] % 1000000000;
                ans[i][1] = ans[i - 1][8] + ans[i - 1][6] % 1000000000;
                ans[i][2] = ans[i - 1][7] + ans[i - 1][9] % 1000000000;
                ans[i][3] = ans[i - 1][4] + ans[i - 1][8] % 1000000000;
                ans[i][4] = ans[i - 1][3] + ans[i - 1][9] + ans[i - 1][0] % 1000000000;
                ans[i][6] = ans[i - 1][1] + ans[i - 1][7] + ans[i - 1][0] % 1000000000;
                ans[i][7] = ans[i - 1][2] + ans[i - 1][6] % 1000000000;
                ans[i][8] = ans[i - 1][1] + ans[i - 1][3] % 1000000000;
                ans[i][9] = ans[i - 1][2] + ans[i - 1][4] % 1000000000;
            }


            System.out.println((ans[n - 1][0] + ans[n - 1][1] + ans[n - 1][2] + ans[n - 1][3] + ans[n - 1][4] + ans[n - 1][5] + ans[n - 1][6] + ans[n - 1][7] + ans[n - 1][8] + ans[n - 1][9]) % 1000000000);
        }
    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}
