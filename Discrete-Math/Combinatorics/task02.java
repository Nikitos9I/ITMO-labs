package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by nikitos on 22.11.17.
 */
public class task02 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("gray.in")));
        PrintWriter writer = new PrintWriter("gray.out");

        int n = nextInt();

        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add(new StringBuilder().append(0).toString());
        inputs.add(new StringBuilder().append(1).toString());

        for (int j = 0; j < n - 1; j++) {
            for (int i = inputs.size() - 1; i >= 0; --i) {
                inputs.add(inputs.get(i));
            }
            for (int i = 0; i < inputs.size() / 2; ++i) {
                inputs.set(i, new StringBuilder("0").append(inputs.get(i)).toString());
            }
            for (int i = inputs.size() / 2; i < inputs.size(); ++i) {
                inputs.set(i, new StringBuilder("1").append(inputs.get(i)).toString());
            }
        }

        for (int i = 0; i < inputs.size(); i++) {
            writer.write(inputs.get(i) + "\n");
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
        new task02().start();
    }

}
