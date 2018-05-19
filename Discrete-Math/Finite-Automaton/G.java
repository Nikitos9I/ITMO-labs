package HomeWork.Discret_math.lab5;

import java.io.*;
import java.util.ArrayDeque;

/**
 * HomeWork.Discret_math.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class G {
    private int[][] transitions1;
    private int[][] transitions2;

    private boolean[] terminal1;
    private boolean[] terminal2;

    private boolean[][] used = new boolean[1007][1007];

    private ArrayDeque<Pair> queue = new ArrayDeque<>();

    private class Pair {
        int first;
        int second;

        private Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

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
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("equivalence.in")));
        PrintWriter writer = new PrintWriter("equivalence.out");

        int n = nextInt() + 1;
        int m = nextInt();
        int k = nextInt();

        terminal1 = new boolean[n];
        transitions1 = new int[n][26];

        readDKA(n, m, k, terminal1, transitions1);

        n = nextInt() + 1;
        m = nextInt();
        k = nextInt();

        terminal2 = new boolean[n];
        transitions2 = new int[n][26];

        readDKA(n, m, k, terminal2, transitions2);

        if (bfs()) {
            writer.write("YES");
        } else {
            writer.write("NO");
        }

        writer.close();
    }

    private void readDKA(int n, int m, int k, boolean[] terminal, int[][] transitions) throws IOException {
        for (int i = 0; i < k; i++) {
            terminal[nextInt() - 1] = true;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                transitions[i][j] = n - 1;
            }
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            int symb = nextString().charAt(0) - 'a';

            transitions[from][symb] = to;
        }
    }

    private boolean bfs() {
        queue.add(new Pair(0, 0));
        while (!queue.isEmpty()) {
            int u = queue.peek().first;
            int v = queue.peek().second;

            queue.poll();

            if ((terminal1[u] && (!terminal2[v])) || ((!terminal1[u]) && (terminal2[v]))) {
                return false;
            }

            used[u][v] = true;

            for (int i = 0; i < 26; i++) {
                if ((!used[transitions1[u][i]][transitions2[v][i]])) {
                    queue.add(new Pair(transitions1[u][i], transitions2[v][i]));
                }

            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        new G().start();
    }

}
