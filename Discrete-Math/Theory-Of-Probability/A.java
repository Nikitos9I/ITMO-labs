package HomeWork.Discret_math.lab1.lab4;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/**
 * HomeWork.Discret_math.lab1.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

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

        t = new StreamTokenizer( new BufferedReader( new FileReader("exam.in")));
        PrintWriter writer = new PrintWriter("exam.out");

        int k = nextInt();
        int n = nextInt();
        double ans = 0;

        for (int i = 0; i < k; i++) {
            ans += (nextInt() * 1.0d) * nextInt() / 100;
        }

        String formattedDouble = new DecimalFormat("#0.00000").format(ans / n).replace(',','.');
        writer.write(formattedDouble);

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
