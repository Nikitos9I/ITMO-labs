//package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeWork.Algorithms.lab3
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class MultiSearch {

    public StreamTokenizer t;
    private static List<Node> states = new ArrayList<>();

    private static class Node {
        private int parent;
        private int c;
        private List<Integer> patterns;

        private int[] go = new int[26];
        private int suffixLinks = -1;

        Node(int parent, int c) {
            for (int i = 0; i < 26; i++)
                go[i] = -1;
            this.parent = parent;
            this.c = c;
        }

        private int getSuffixLinks() {
            if (suffixLinks == -1)
                if (c == -1 || parent == 0)
                    suffixLinks = 0;
                else
                    suffixLinks = states.get(states.get(parent).getSuffixLinks()).go(c);
            return suffixLinks;
        }

        private int go(int c) {
            if (go[c] == -1)
                go[c] = this.c == -1 ? 0 : states.get(getSuffixLinks()).go(c);
            return go[c];
        }
    }

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

    public void start() throws IOException {
        // t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        // PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("search4.in")));
        PrintWriter writer = new PrintWriter("search4.out");

        int n = nextInt();
        create(n);

        boolean[] answer = new boolean[n];
        boolean[] dead = new boolean[states.size()];

        String t = nextString();
        int state = 0;

        for (int i = 0; i < t.length(); i++) {
            int c = t.charAt(i) - 'a';
            state = states.get(state).go(c);
            int tmpState = state;
            while (tmpState != 0 && !dead[tmpState]) {
                dead[tmpState] = true;
                if (states.get(tmpState).patterns != null)
                    for (int p : states.get(tmpState).patterns)
                        answer[p] = true;
                tmpState = states.get(tmpState).getSuffixLinks();
            }
        }

        for (boolean b : answer)
            if (b)
                writer.write("YES\n");
            else
                writer.write("NO\n");

        writer.close();
    }

    private void create(int n) throws IOException {
        states.add(new Node(0, -1));

        for (int i = 0; i < n; i++) {
            String str = nextString();
            int lastState = 0;

            for (int j = 0; j < str.length(); j++) {
                int c = str.charAt(j) - 'a';

                if (states.get(lastState).go[c] == -1) {
                    states.get(lastState).go[c] = states.size();
                    states.add(new Node(lastState, c));
                }

                lastState = states.get(lastState).go[c];
            }

            if (states.get(lastState).patterns == null) {
                states.get(lastState).patterns = new ArrayList<>();
            }

            states.get(lastState).patterns.add(i);
        }
    }

    public static void main(String[] args) throws IOException {
        new MultiSearch().start();
    }

}
