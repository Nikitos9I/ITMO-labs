package HomeWork.Algoritms.lab3;

import java.io.*;

/**
 * Created by nikitos on 09.12.17.
 */
public class K {

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
        //t = new StreamTokenizer( new BufferedReader( new FileReader("INPUT.TXT")));
        //PrintWriter writer = new PrintWriter("OUTPUT.TXT");
        t = new StreamTokenizer( new BufferedReader( new FileReader("nice.in")));
        PrintWriter writer = new PrintWriter("nice.out");

        int n = nextInt();
        int m = nextInt();
        if (n > m) {
            int c = n;
            n = m;
            m = c;
        }
        int[][] counter = new int[m][exp(n)];

        int i = -1;
        while (++i < exp(n)) {
            counter[0][i] = 1;
        }

        int inc = 0;
        while (++inc < m) {
            int p = -1;
            while (++p < exp(n)) {
                int z = -1;
                while (++z < exp(n)) {
                    if (match(Integer.toString(p, 2), Integer.toString(z, 2), n)) {
                        counter[inc][p] += counter[inc - 1][z];
                    }
                }
            }
        }

        int res = 0;

        i = -1;
        while (++i < exp(n)) {
            res += counter[m - 1][i];
        }

        writer.print(res);
        writer.close();

    }

    public boolean match(String prof1, String prof2, int n) {
        if (prof1.length() < n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n - prof1.length(); i++) {
                sb.append(0);
            }
            prof1 = sb.append(prof1).toString();
        }

        if (prof2.length() < n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n - prof2.length(); i++) {
                sb.append(0);
            }
            prof2 = sb.append(prof2).toString();
        }

        for (int i = 0; i < n - 1; ++i) {
            if (prof1.charAt(i) == prof2.charAt(i) && prof1.charAt(i + 1) == prof2.charAt(i + 1) && prof1.charAt(i) == prof1.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public int exp(int n) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= 2;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new K().start();
    }

}
