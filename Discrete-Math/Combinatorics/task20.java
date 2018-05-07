package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by nikitos on 12.12.17.
 */
public class task20 {

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
        BufferedReader buf = new BufferedReader( new FileReader("brackets2num2.in"));
        PrintWriter writer = new PrintWriter("brackets2num2.out");
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input = buf.readLine();
        String regex = "()[]";
        int n  = input.length() / 2;

        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger d[][] = new BigInteger[n*2+1][n*2+1];

        int i = -1;
        while (++i < 2*n + 1) {
            int j = -1;
            while (++j < 2*n + 1) {
                d[i][j] = zero;
            }
        }

        d[0][0] = one;
        i = -1;

        while (++i < 2*n + 1) {
            int j = -1;
            while (++j < 2*n + 1) {
                if (j > 0 && i > 0) {
                    d[i][j] = d[i][j].add(d[i - 1][j - 1]);
                }
                if (j < n*2 && i > 0) {
                    d[i][j] = d[i][j].add(d[i - 1][j + 1]);
                }
            }
        }

        int inner = 0;
        char [] current = new char[n*2];
        BigInteger res = new BigInteger("0");

        i = n*2;
        while (--i >= 0) {
            if (regex.indexOf(input.charAt(n*2 - i - 1)) == 0) {
                current[inner++] = regex.charAt(0);
            } else {
                res = res.add(d[i][inner + 1].shiftLeft((i-inner-1) / 2));
                if (inner > 0 && regex.indexOf(current[inner - 1]) == 0 && regex.indexOf(input.charAt(n*2 - 1 - i)) == 1) {
                    inner--;
                } else {
                    if (inner > 0 && regex.indexOf(current[inner - 1]) == 0) {
                        res = res.add(d[i][inner - 1].shiftLeft((i-inner+1) / 2));
                    }
                    if (regex.indexOf(input.charAt(2*n - i - 1)) == 2) {
                        current[inner++] = regex.charAt(2);
                    } else {
                        res = res.add(d[i][inner + 1].shiftLeft((i-inner-- -1) / 2));
                    }
                }
            }
        }

        writer.print(res);
        writer.close();

    }

    public static void main(String[] args) throws IOException {
        new task20().start();
    }

}
