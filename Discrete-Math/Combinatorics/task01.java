package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 21.11.17.
 */
public class task01 {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("allvectors.in")));
        PrintWriter writer = new PrintWriter("allvectors.out");

        int n = nextInt();
        String pattern = Integer.toString(exp(n) - 1, 2);

        for (int i = 0; i < exp(n); i++) {
            StringBuilder output = new StringBuilder(Integer.toString(i,2));
            StringBuilder addition = new StringBuilder();
            for (int j = 0; j < pattern.length() - output.length(); ++j) {
                addition.append(0);
            }
            output = addition.append(output);
            writer.write(output.toString() + "\n");
        }

        writer.close();
    }

    public int exp(int n) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= 2;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task01().start();
    }

}
