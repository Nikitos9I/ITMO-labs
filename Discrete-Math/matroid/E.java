package HomeWork.DiscretMath;

import java.io.*;
import java.util.*;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class E {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public long nextLong() throws IOException {
        t.nextToken();
        return (long) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    private boolean[] was;

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("cycles.in")));
        PrintWriter writer = new PrintWriter("cycles.out");

        int n = nextInt();
        int m = nextInt();

        int[] weight = new int[n];

        for (int i = 0; i < n; i++) {
            weight[i] = nextInt();
        }

        was = new boolean[1 << n];
        for (int i = 0; i < m; i++) {
            int num = nextInt();
            int bMask = 0;

            for (int j = 0; j < num; j++) {
                int current = nextInt();
                bMask |= (1 << (current - 1));
            }

            removeElements(bMask, n);
        }

        int max = 0;
        for (int i = 0; i < (1 << n); i++) {
            if (!was[i]) {
                max = Integer.max(getSum(i, weight), max);
            }
        }

        writer.println(max);
        writer.close();
    }

    private void removeElements(int bMask, int n) {
        was[bMask] = true;
        for (int i = 0; i < n; i++) {
            int newMask = (bMask | (1 << i));

            if (!was[newMask]) {
                removeElements(newMask, n);
            }
        }
    }

    private int getSum(int mask, int[] weight) {
        String val = Integer.toString(mask, 2);
        int sum = 0;

        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == '1') {
                sum += weight[val.length() - i - 1];
            }
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}
