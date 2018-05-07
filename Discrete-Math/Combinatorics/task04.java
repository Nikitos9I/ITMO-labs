package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.HashSet;

/**
 * Created by nikitos on 22.11.17.
 */
public class task04 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("chaincode.in")));
        PrintWriter writer = new PrintWriter("chaincode.out");

        int n = nextInt();

        HashSet<String> hSet = new HashSet<>();

        String[] out = new String[exp(n)];
        for (int i = 0; i < exp(n); i++) {
            out[i] = "";
        }
        for (int i = 0; i < n; ++i) {
            out[0] += "0";
        }
        writer.write(out[0] + "\n");
        for (int i = 1; i < exp(n); ++i) {
            out[i] = out[i - 1].substring(1, out[i - 1].length());
            String patternOne = out[i] + "1";
            String patternZero = out[i] + "0";

            if (!hSet.contains(patternOne)) {
                out[i] = new StringBuilder(out[i]).append(1).toString();
                writer.write(out[i] + "\n");
                hSet.add(out[i]);
            } else if (!hSet.contains(patternZero)) {
                out[i] = new StringBuilder(out[i]).append(0).toString();
                writer.write(out[i] + "\n");
                hSet.add(out[i]);
            }
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
        new task04().start();
    }

}
