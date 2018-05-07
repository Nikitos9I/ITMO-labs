package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task05 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("telemetry.in")));
        PrintWriter writer = new PrintWriter("telemetry.out");

        int n = nextInt();
        int k = nextInt();
        int[] output = new int[exp(n, k)];

        int[] needInc = new int[n];
        for (int i = 0; i < n; i++) {
            needInc[i] = exp(n, k) / exp(i + 1, k);
        }

        boolean f = true;
        int[] counter0 = new int[n];
        for (int i = 0; i < n; i++) {
            counter0[i] = 1;
        }
        int[] counter2 = new int[n];
        String[] ans = new String[exp(n, k)];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = "";
        }

        for (int i = 0; i < n; i++) {
            ans[0] = new StringBuilder(ans[0]).append(0).toString();
        }

        for (int j = 0; j < n; j++) {
            for (int i = 1; i < exp(n, k); i++) {
                if (output[i - 1] == 0) {
                    f = true;
                    counter0[j]++;
                } else if (output[i - 1] == k - 1) {
                    f = false;
                    counter2[j]++;
                }
                if (i % needInc[j] != 0) {
                    output[i] = output[i - 1];
                } else {
                    if (f) {
                        if (counter0[j] <= needInc[j] && output[i - 1] == 0) {
                            output[i] = output[i - 1];
                        } else {
                            output[i] = (output[i - 1] + 1) % k;
                            counter0[j] = 0;
                        }
                    } else {
                        if (counter2[j] <= needInc[j] && output[i - 1] == k - 1) {
                            output[i] = output[i - 1];
                        } else {
                            output[i] = (output[i - 1] - 1) % k;
                            counter2[j] = 0;
                        }
                    }
                }
                ans[i] = new StringBuilder(ans[i]).append(output[i]).toString();
            }
        }

        for (int i = 0; i < exp(n, k); i++) {
            writer.write(ans[i].trim() + "\n");
        }

        writer.close();

    }

    public int exp(int n, int k) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= k;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task05().start();
    }

}
