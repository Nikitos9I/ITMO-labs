package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 24.11.17.
 */
public class task14 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        PrintWriter writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("perm2num.in")));
        //PrintWriter writer = new PrintWriter("perm2num.out");

        int n = nextInt();
        int[] inputs = new int[n];

        for (int i = 0; i < n; i++) {
            inputs[i] = nextInt();
            writer.write(inputs[i] + " ");
        }
        writer.write("\n");

        long ans = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < i; j++) {
                if (inputs[j] < inputs[i]) count++;
            }
            ans += fact(n - i) * (inputs[i] - 1 - count);
        }

        writer.print(ans + "\n");

        long[] output = new long[n];
        for (int i = 0; i < n; i++) {
            long digit = ans / fact(n - i) + 1;
            ans %= fact(n - i);
            output[i] = digit;
            boolean f = true;
            while (f) {
                f = false;
                for (int j = 0; j < i; j++) {
                    if (output[j] == output[i]) {
                        output[i]++;
                        f = true;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            writer.write(output[i] + " ");
        }
        writer.close();

    }

    public long fact(int n) {
        long a = 1;
        for (int i = 1; i < n; i++) {
            a *= i;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task14().start();
    }

}
