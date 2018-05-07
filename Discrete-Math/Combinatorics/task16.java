package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 29.11.17.
 */
public class task16 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("choose2num.in")));
        PrintWriter writer = new PrintWriter("choose2num.out");

        int n = nextInt();
        int k = nextInt();

        int[] inputs = new int[k + 1];

        for (int i = 1; i <= k; i++) {
            inputs[i] = nextInt();
        }

        writer.println(numberOfChoose(k,n,inputs));

        writer.close();

    }

    public long numberOfChoose(int k, int n, int[] inputs) {
        long number = 0;

        int i = 0;
        while (++i <= k) {
            if (i == 1) {
                for (int j = 1; j < inputs[i]; ++j) {
                    number += numChoose(n - j, k - 1);
                }
            } else {
                for (int j = inputs[i] - 1; j >= inputs[i - 1] + 1; --j) {
                    number += numChoose(n - j, k - i);
                }
            }
        }

        return number;
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
        new task16().start();
    }

}
