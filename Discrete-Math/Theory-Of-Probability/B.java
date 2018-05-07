package HomeWork.Discret_math.lab1.lab4;

import java.io.*;
import java.text.DecimalFormat;

/**
 * HomeWork.Discret_math.lab1.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
public class B {

    public StreamTokenizer t;

    public double nextInt() throws IOException {
        t.nextToken();
        return t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("shooter.in")));
        PrintWriter writer = new PrintWriter("shooter.out");

        int n = (int) nextInt();
        int m = (int) nextInt();
        int k = (int) nextInt();
        double[] inputs = new double[n];

        for (int i = 0; i < n; i++) {
            inputs[i] = 1 - nextInt();
        }

        double ans = turbo(inputs[k - 1],m);
        double underline = 0d;

        for (int i = 0; i < n; i++) {
            underline += turbo(inputs[i], m);
        }

        String formattedDouble = new DecimalFormat("#0.0000000000000").format(ans / underline).replace(',','.');
        writer.write(formattedDouble);

        writer.close();
    }

    public double turbo(double x, int m) {
        double ans = x;

        for (int i = 1; i < m; i++) {
            ans *= x;
        }

        // TODO: if not approved, rewrite this function (C from n to k)
        return ans;
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
