package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task10 {

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
        //t = new StreamTokenizer( new BufferedReader( new FileReader("partition.in")));
        //writer = new PrintWriter("partition.out");

        int n = nextInt();

        input = new int[n];
        for (int i = 0; i < n; i++) {
            input[i] = 1;
        }

        req(n, 0);

        writer.close();
    }

    public void req(int num, int pos) {
        if (num == 0) {
            for (int i = 0; i < pos - 1; i++) {
                writer.write(input[i] + "+");
            }
            writer.write(input[pos - 1] + "");
            writer.write("\n");
            return;
        }
        for (int i = 1; i <= num; i++) {
            input[pos] = i;
            if (pos > 0 && input[pos - 1] > input[pos]) continue;
            req(num - i, pos + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        new task10().start();
    }

}
