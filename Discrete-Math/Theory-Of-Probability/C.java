package HomeWork.Discret_math.lab1.lab4;

import java.io.*;

/**
 * HomeWork.Discret_math.lab1.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
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
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("lottery.in")));
        PrintWriter writer = new PrintWriter("lottery.out");

        int n = nextInt();
        int m = nextInt();
        double sum = 0;
        int[] number = new int[m + 1];
        int[] cost = new int[m + 1];
        cost[0] = 0;

        for (int i = 1; i <= m; i++) {
            number[i] = nextInt();
            cost[i] = nextInt();
        }

        double[] pref_prob = new double[m + 1];
        pref_prob[0] = 1;

        for (int i = 1; i < m + 1; i++) {
            pref_prob[i] = pref_prob[i - 1] * (1d / number[i]);
        }

        for (int i = 1; i < m + 1; i++) {
            sum += pref_prob[i - 1] * (1 - (1d/ number[i])) * cost[i - 1];
        }
        sum += pref_prob[m] * (cost[m]);

        writer.print(n - sum);
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}
