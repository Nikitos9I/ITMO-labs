package HomeWork.Discret_math.lab5;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

/**
 * HomeWork.Discret_math.lab1.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class B {

    public StreamTokenizer t;
    private String word;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer(new BufferedReader(new FileReader("problem2.in")));
        PrintWriter writer = new PrintWriter("problem2.out");

//        t = new StreamTokenizer(new BufferedReader(new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        word = nextString();
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();

        boolean[] finish = new boolean[n];

        for (int i = 0; i < k; i++) {
            finish[nextInt() - 1] = true;
        }

        ArrayList<Pair<Integer, String>>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            g[nextInt() - 1].add(new Pair<>(nextInt() - 1, nextString()));
        }

        boolean[] position = new boolean[n];
        position[0] = true;
        boolean f = true;

        int i = -1;
        while (++i < word.length() && f) {
            f = false;
            boolean[] nPosition = new boolean[n];
            for (int e = 0; e < n; e++) {
                if (position[e]) {
                    for (int j = 0; j < g[e].size(); j++) {
                        if (Character.compare(g[e].get(j).getValue().charAt(0), word.charAt(i)) == 0) {
                            nPosition[g[e].get(j).getKey()] = true;
                            f = true;
                        }
                    }
                }
            }
            position = nPosition;
        }

        if (f) {
            f = false;
            int j = -1;
            while (++j < n && !f) {
                if (position[j] && finish[j]) {
                    f = true;
                }
            }
        }

        writer.write(f? "Accepts": "Rejects");

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
