package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task11 {

    public StreamTokenizer t;
    public PrintWriter writer;

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
        //writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("subsets.in")));
        writer = new PrintWriter("subsets.out");

        int n = nextInt();

        int[] input = new int[n + 1];
        req(n, input, 1);

        writer.close();
    }

    public void req(int n, int[] input, int pos) {
        for (int i = 1; i < pos; i++) {
            writer.write(input[i] + " ");
        }
        writer.write("\n");
        for (int i = pos; i <= n; i++) {
            input[pos] = i;
            if (pos > 1 && input[pos - 1] >= input[pos]) continue;
            req(n,input,pos + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        new task11().start();
    }

}
