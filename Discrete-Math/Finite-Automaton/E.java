package HomeWork.Discret_math.lab5;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * HomeWork.Discret_math.lab5
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

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    ArrayList<ArrayList<ArrayList<Integer>>> transitions;
    int[][] ans;
    boolean[][] used;

    HashMap<BitSet, Integer> number = new HashMap<>();
    ArrayList<Boolean> termN = new ArrayList<>();
    ArrayList<Integer> from = new ArrayList<>();
    ArrayList<Integer> to = new ArrayList<>();

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("problem5.in")));
        PrintWriter writer = new PrintWriter("problem5.out");

        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        int l = nextInt();

        boolean[] term = new boolean[n + 1];
        BitSet set = new BitSet(n + 1);

        int q = 1;
        transitions = new ArrayList<>();
        ans = new int[n + 1][l + 2];
        used = new boolean[n + 1][l + 2];

        readDka(n, m, k, term);

        termN.add(false);
        termN.add(term[1]);

        set.set(1);
        number.put(set, 1);

        LinkedList<BitSet> queue = new LinkedList<>();
        queue.add(set);

        while (!queue.isEmpty()) {
            BitSet curr = queue.poll();

            int i = -1;
            while (++i < 26) {
                BitSet can = new BitSet(n + 1);
                boolean flag = false;

                int j = 0;
                while (++j <= n) {
                    if (!curr.get(j)) {
                        continue;
                    }

                    int o = -1;
                    int curSize = transitions.get(j).get(i).size();

                    while(++o < curSize) {
                        int v = transitions.get(j).get(i).get(o);
                        can.set(v);

                        if (term[v]) {
                            flag = true;
                        }
                    }
                }

                from.add(number.get(curr));

                if (!number.containsKey(can)) {
                    number.put(can, ++q);
                    termN.add(flag);
                    queue.add(can);
                }

                to.add(number.get(can));
            }
        }

        int[] ans = new int[q + 1];

        int i = -1;
        while (++i < q) {
            if (termN.get(i + 1)) {
                ans[i + 1] = 1;
            }
        }

        i = -1;
        while (++i < l) {
            int[] result = new int[q + 1];

            for (int j = 0; j < from.size(); j++) {
                result[from.get(j)] = (result[from.get(j)] + ans[to.get(j)]) % 1000000007;
            }

            for (int j = 0; j < q + 1; j++) {
                ans[j] = result[j];
            }
        }

        writer.print(ans[number.get(set)]);
        writer.close();
    }

    private void readDka(int n, int m, int k, boolean term[]) throws IOException {
        for (int i = 0; i < n + 1; i++) {
            transitions.add(new ArrayList<>());

            for (int j = 0; j < 26; j++) {
                transitions.get(i).add(new ArrayList<>());
            }
        }

        for (int i = 0; i < k; i++) {
            term[nextInt()] = true;
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            int symb = nextString().charAt(0) - 'a';
            transitions.get(from).get(symb).add(to);
        }
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}
