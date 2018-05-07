package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 29.11.17.
 */
public class task15 {

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
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("num2choose.in")));
        PrintWriter writer = new PrintWriter("num2choose.out");

        int n = nextInt();
        int k = nextInt();
        int m = nextInt();

        writer.write(choose(m, n, k));

        writer.close();

    }

    public String choose(int m, int n, int k) {
        StringBuilder ans = new StringBuilder();
        int number = 1;

        for (; k > 0 ;) {
            if (m >= numChoose(n - 1, k - 1)) {
                m -= numChoose(n - 1, k - 1);
                number++;
                n--;
            } else {
                ans.append(number).append(" ");
                k--;
                number++;
                n--;
            }
        }

        return ans.toString().trim();
    }

    public long numChoose(int n, int k) {
        long under = 1;
        long up = 1;

        int maximum = max(k, n - k);
        for (int i = maximum + 1; i <= n; i++) {

            if (i % (i - maximum) != 0) {
                under *= (i - maximum);
                up *= i;
            } else {
                up *= (i / (i - maximum));
            }

        }

        return up / under;
    }

    public int max(int a, int b) {
        return (a>b?a:b);
    }

    public static void main(String[] args) throws IOException {
        new task15().start();
    }

}
