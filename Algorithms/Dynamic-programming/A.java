package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.Arrays;

/**
 * Created by nikitos on 19.11.17.
 */
public class A {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader(new FileReader("input.txt")));
        PrintWriter writer = new PrintWriter("output.txt");

        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt();
        int k = nextInt();
        
        int[] inputs = new int[n];
        StringBuilder ans = new StringBuilder();
        ans.append(1).append(" ");

        for (int i = 1; i < n - 1; i++) {
            inputs[i] = nextInt();
        }

        int money[] = new int[n];
        int step[] = new int[n];

        for (int i = 0; i < n; i++) {
            step[i] = -1;
        }

        int count = 0;

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < k + i; ++j) {
                if (j < n) {
                    if (step[j] == -1) {
                        step[j] = i;
                        money[j] = money[i] + inputs[j];
                    } else if (money[j] < money[i] + inputs[j]) {
                        money[j] = money[i] + inputs[j];
                        step[j] = i;
                    }
                }
            }
        }

        int max = money[0];
        for (int i = 1; i < n; ++i) {
            max = (max < money[i]? money[i]: max);
        }

        writer.write(max + "\n");

        Arrays.sort(step);

        for (int i = 1; i < n; i++) {
            if (step[i] + 1 != step[i - 1] + 1 && step[i] != 0) {
                count++;
                ans.append(step[i] + 1).append(" ");
            }
        }
        ans.append(n);

        writer.write((count + 1) + "\n");
        writer.write(ans.toString());

        writer.close();

    }

    public static void main(String[] args) throws IOException  {
        new A().start();
    }

}
