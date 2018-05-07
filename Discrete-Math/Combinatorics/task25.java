package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 21.11.17.
 */
public class task25 {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException{
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("nextchoose.in")));
        PrintWriter writer = new PrintWriter("nextchoose.out");

        int n = nextInt();
        int k = nextInt();

        int[] inputs = new int[k];

        for (int i = 0; i < k; i++) {
            inputs[i] = nextInt();
        }

        if (inputs[k - 1] < n) {
            inputs[k - 1]++;
            for (int i = 0; i < k; i++) {
                writer.write(inputs[i] + " ");
            }
        } else {
            int i = k - 1;
            while (i > 0 && inputs[i] - inputs[i - 1] < 2) {
                i--;
            }
            if (i == 0) writer.write("-1"); else {
                inputs[--i]++;
                for (int j = i + 1; j < k; j++) {
                    inputs[j] = inputs[j - 1] + 1;
                }
                for (int j = 0; j < k; j++) {
                    writer.write(inputs[j] + " ");
                }
            }
        }

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new task25().start();
    }

}
