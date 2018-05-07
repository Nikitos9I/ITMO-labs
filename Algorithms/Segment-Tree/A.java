package HomeWork.Algoritms.lab4;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

    public StreamTokenizer t;
    public PrintWriter writer;
    public int k;
    public int m;
    public long[] a;
    public int[] c;
    public long[] summa;
    public long sum = 0;
    public final int TWO_T = (1 << 30);
    public final int TWO_F = (1 << 16);

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer(new BufferedReader(new FileReader("sum0.in")));
        writer = new PrintWriter("sum0.out");

        int n = nextInt();
        int x = nextInt();
        int y = nextInt();

        a = new long[n];
        summa = new long[n + 1];

        summa[0] = 0;
        a[0] = nextInt();
        summa[1] = a[0];
        for (int i = 1; i < n; i++) {
            a[i] = (x * a[i - 1] + y) & (TWO_F - 1);
            summa[i + 1] = summa[i] + a[i];
        }

        m = nextInt();
        if (m != 0) {
            int z = nextInt();
            int t = nextInt();
            int b0 = nextInt();
            c = new int[2 * m];
            c[0] = (b0 % n);

            for (int i = 1; i < 2 * m; i++) {
                int pr = (z * b0 + t) & (TWO_T - 1);
                if (pr < 0) {
                    c[i] = ((TWO_T + pr) % n);
                } else {
                    c[i] = (pr % n);
                }
                b0 = pr;
            }

            for (int i = 0; i < m; i++) {
                int left = Integer.min(c[2 * i], c[2 * i + 1]);
                int right = Integer.max(c[2 * i], c[2 * i + 1]);

                sum += getSum(left, right);
            }
        }

        writer.print(sum);
        writer.close();
    }

    public long getSum(int left, int right) {
        return summa[right + 1] - summa[left];
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
