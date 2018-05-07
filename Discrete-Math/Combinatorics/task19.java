package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by nikitos on 12.12.17.
 */
public class task19 {

    public StringTokenizer t;

    public int nextInt() {
        return Integer.parseInt(t.nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(t.nextToken());
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader("num2brackets2.in"));
        t = new StringTokenizer(buf.readLine());
        PrintWriter writer = new PrintWriter("num2brackets2.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt();
        BigInteger k = new BigInteger(Long.toString(nextLong() + 1));

        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger d[][] = new BigInteger[n*2+1][n+1];

        int i = -1;
        while (++i <= 2*n) {
            int j = -1;
            while (++j <= n) {
                d[i][j] = zero;
            }
        }

        d[0][0] = one;
        i = -1;
        int capacity = 0;

        while (++i < 2*n) {
            int j = -1;
            while (++j <= n) {
                if (j > 0) {
                    d[i + 1][j - 1] = d[i + 1][j - 1].add(d[i][j]);
                    capacity++;
                }
                if (j + 1 <= n) {
                    d[i + 1][j + 1] = d[i + 1][j + 1].add(d[i][j]);
                    capacity++;
                }
            }
        }

        boolean checked = false;
        if (capacity == n) checked = true;

        StringBuilder ans = new StringBuilder();
        int openClosed = 0;

        int depth = 0;
        char [] stack = new char[n*2];
        int aro_counter = 0;
        i = n*2;
        while (--i >= 0) {
            BigInteger cur;
            cur = (n - 1 >= depth? d[i][depth + 1].shiftLeft((i-depth-1) / 2): zero);
            if (0 <= cur.compareTo(k)) {
                depth++;
                ans.append('(');
                stack[aro_counter] = '(';
                aro_counter++;
                openClosed++;
                continue;
            }
            k = k.subtract( cur );
            cur = (aro_counter > 0 && depth >= 1 && stack[aro_counter - 1] == '('? d[i][depth - 1].shiftLeft((i-depth+1) / 2): zero);
            if (0 <= cur.compareTo(k)) {
                depth--;
                ans.append(')');
                aro_counter--;
                openClosed--;
                continue;
            }
            k = k.subtract( cur );
            cur = (n - 1 >= depth? d[i][depth + 1].shiftLeft((i-depth-1) / 2): zero);
            if (0 <= cur.compareTo(k)) {
                depth++;
                ans.append('[');
                stack[aro_counter] = '[';
                aro_counter++;
                openClosed++;
                continue;
            }
            k = k.subtract( cur );
            ans.append(']');
            aro_counter--;
            depth--;
            openClosed++;
        }

        if (openClosed != 0 && checked) {
            checked = false;
        }

        writer.write(ans.toString());
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new task19().start();
    }

}
