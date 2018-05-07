package HomeWork.Algoritms.lab4;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class F {

    public StreamTokenizer t;
    public int[][] st;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("sparse.in")));
        PrintWriter writer = new PrintWriter("sparse.out");

        int n = nextInt();
        int m = nextInt();

        int[] input = new int[n];
        input[0] = nextInt();

        for (int i = 1; i < n; i++) {
            input[i] = (23 * input[i - 1] + 21563) % 16714589;
        }

        st = new int[n][log(n) + 1];
        for (int j = 0; j < log(n) + 1; j++) {
            for (int i = 0; i < n; i++) {
                st[i][j] = (j == 0)? input[i] : Integer.min(st[i][j - 1],st[(i + (1 << (j - 1))) < n? (i + (1 << (j - 1))): i][j - 1]);
            }
        }

//        for (int j = 0; j < log(n) + 1; j++) {
//            for (int i = 0; i < n; i++) {
//                System.out.print(st[i][j] + " ");
//            }
//            System.out.println();
//        }

        int u = nextInt();
        int v = nextInt();
        int ans = min(u - 1, v - 1);

        for (int i = 1; i < m; i++) {
            u = ((17 * u + 751 + ans + 2 * i) % n) + 1;
            v = ((13 * v + 593 + ans + 5 * i) % n) + 1;

            if (u > v) {
                ans = min(v - 1, u - 1);
            } else {
                ans = min(u - 1, v - 1);
            }
        }

        writer.write(u + " " + v + " " + ans);
        writer.close();
    }

    public int min(int left, int right) {
        int k = log(right - left + 1);
        return Integer.min(st[left][k], st[right - (1 << k) + 1][k]);
    }

    public int log(int d) {
        if (d == 1 || d == 0) {
            return 0;
        } else {
            return log(d / 2) + 1;
        }
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }

}
