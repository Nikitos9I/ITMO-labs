package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by nikitos on 22.11.17.
 */
public class task06 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("vectors.in")));
        PrintWriter writer = new PrintWriter("vectors.out");

        int n = nextInt();
        String pattern = Integer.toString(exp(n) - 1, 2);
        ArrayList<String> aList = new ArrayList<>();

        for (int i = 0; i < exp(n); i++) {
            boolean f = false;
            StringBuilder output = new StringBuilder(Integer.toString(i,2));
            for (int j = 1; j < output.length(); ++j) {
                if (output.charAt(j) == '1' && output.charAt(j - 1) == '1') {
                    f = true;
                    continue;
                }
            }
            if (f) continue;
            StringBuilder addition = new StringBuilder();
            for (int j = 0; j < pattern.length() - output.length(); ++j) {
                addition.append(0);
            }
            output = addition.append(output);
            aList.add(output.toString());
        }

        writer.write(aList.size() + "\n");
        for (int i = 0; i < aList.size(); i++) {
            writer.write(aList.get(i) + "\n");
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
        new task06().start();
    }

}
