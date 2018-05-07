package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 22.11.17.
 */
public class task07 {

    public StreamTokenizer t;
    public PrintWriter writer;
    public int[] input;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("permutations.in")));
        //writer = new PrintWriter("permutations.out");

        int n = nextInt();
        input = new int[n];

        req(1);

        writer.close();
    }

    public void req(int pos) {
        if (pos > input.length) {
            for (int k = 0; k < input.length; ++k) {
                writer.write(input[k] + " ");
            }
            writer.write("\n");
            return;
        }

        for (int i = 1; i <= input.length; i++) {
            boolean con = false;
            for (int j = 0; j < pos - 1; j++) {
                if (input[j] == i) {
                    con = true;
                    break;
                }
            }
            if (con) {
                continue;
            }

            input[pos - 1] = i;

            req(pos + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        new task07().start();
    }

}
