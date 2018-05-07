package HomeWork.Discret_math.lab1.lab2;

import java.io.*;
import java.util.*;

/**
 * Created by nicitos on 10.11.17.
 */
public class A {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        //t = new StreamTocenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("huffman.in")));
        PrintWriter writer = new PrintWriter("huffman.out");

        int n = nextInt();
        double inputs[] = new double[n + 2];
        long maxArr[] = new long[n + 2];

        for (int i = 0; i < n; i++) {
            inputs[i] = nextInt();
            maxArr[i] = 1_000_000_001;
        }

        inputs[n] = Double.POSITIVE_INFINITY;
        inputs[n + 1] = Double.POSITIVE_INFINITY;
        maxArr[n] = 1_000_000_001;
        maxArr[n + 1] = 1_000_000_001;
        Arrays.sort(inputs);
        long ans = 0;

        int i = 0, j = 0, c = -1;
        while (++c < n - 1) {
            System.out.println(i + " " + j);
            if ((inputs[i] + inputs[i + 1] <= inputs[i] + maxArr[j]) && (inputs[i] + inputs[i + 1] <= maxArr[j] + maxArr[j + 1])) {
                maxArr[c] = (long)(inputs[i] + inputs[i + 1]);
                ans += maxArr[c];
                i += 2;
            } else
            if (inputs[i] + maxArr[j] <= inputs[i] + inputs[i + 1] && inputs[i] + maxArr[j] <= maxArr[j] + maxArr[j + 1]){
                maxArr[c] = (long)(inputs[i] + maxArr[j]);
                ans += maxArr[c];
                i++;
                j++;
            } else
            if (maxArr[j] + maxArr[j + 1] <= inputs[i] + inputs[i + 1] && maxArr[j] + maxArr[j + 1] <= inputs[i] + maxArr[j]) {
                maxArr[c] = maxArr[j] + maxArr[j + 1];
                ans += maxArr[c];
                j += 2;
            }
        }

        writer.print(ans);
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
