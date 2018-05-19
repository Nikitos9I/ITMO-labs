package HomeWork.Discret_math.lab5;

import java.io.*;
import java.util.ArrayList;

/**
 * HomeWork.Discret_math.lab1.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class C {

    public StreamTokenizer t;
    public PrintWriter writer;
    public int n;
    public State[] states;

    public boolean ans = false;

    public long count = 0;

    public ArrayList<ArrayList<Integer>> transitions;
    public ArrayList<ArrayList<Integer>> reverseTransitions;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public class State {
        boolean ending;
        boolean needTerminal;
        boolean needZero;
        boolean visited;

        int color;
        int index;

        long value;

        public State(boolean ending, int index) {
            this.color = 0;
            this.value = 0;
            this.index = index;
            this.ending = ending;
            this.needZero = false;
            this.needTerminal = false;
            this.visited = false;
        }
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("problem3.in")));
        writer = new PrintWriter("problem3.out");

        n = nextInt();
        int m = nextInt();
        int k = nextInt();

        states = new State[n];

        for (int i = 0; i < n; i++) {
            states[i] = new State(false, i);
        }

        for (int i = 0; i < k; i++) {
            int now = nextInt() - 1;
            states[now].ending = true;
            states[now].value = 1;
        }

        reverseTransitions = new ArrayList<>();
        transitions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            reverseTransitions.add(new ArrayList<>());
            transitions.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            nextString();

            transitions.get(from).add(to);
            reverseTransitions.get(to).add(from);
        }

        needZero(0);

        for (int i = 0; i < n; i++) {
            if (states[i].ending) {
                needTerminal(i);
            }
        }

//        for (int i = 0; i < states.size(); i++) {
//            if (!states[i].needZero || !states[i].needTerminal) {
//                states.remove(i--);
//            }
//        }

        dfs(0);
//        states[0].value = 1;
//        for (int i = 0; i < n; i++) {
//            if (states[i].ending && states[i].needZero) {
//                testDfs(i);
//
//                count += states[i].value;
//            }
//        }

        writer.print(ans? -1: states[0].value);
        writer.close();
    }

    public void testDfs(int v) {
        if (!states[v].needZero) return;

        states[v].color = 1;

        for (int i = 0; i < reverseTransitions.get(v).size(); i++) {
            if (states[reverseTransitions.get(v).get(i)].color == 1) {
                ans = true;
                writer.print(-1);
                writer.close();
                System.exit(0);
            } else {
                if (states[reverseTransitions.get(v).get(i)].color == 0) {
                    testDfs(reverseTransitions.get(v).get(i));
                }

                states[v].value = (states[v].value + states[reverseTransitions.get(v).get(i)].value) % 1_000_000_007;
            }
        }

        states[v].color = 2;
    }

    public void dfs(int v) {
        if (!states[v].needTerminal || !states[v].needZero) {
            return;
        }

        states[v].color = 1;

        for (int i = 0; i < transitions.get(v).size(); i++) {
            if (states[transitions.get(v).get(i)].color == 1) {
                ans = true;
                writer.print(-1);
                writer.close();
                System.exit(0);
            } else {
                if (states[transitions.get(v).get(i)].color == 0) {
                    dfs(transitions.get(v).get(i));
                }

                states[v].value = (states[v].value + states[transitions.get(v).get(i)].value) % 1_000_000_007;
            }
        }

        states[v].color = 2;
    }

    private void needZero(int v) {
        states[v].needZero = true;

        for (int i = 0; i < transitions.get(v).size(); i++) {
            if (!states[transitions.get(v).get(i)].needZero) {
                needZero(transitions.get(v).get(i));
            }
        }
    }

    private void needTerminal(int v) {
        states[v].needTerminal = true;

        for (int i = 0; i < reverseTransitions.get(v).size(); i++) {
            if (!states[reverseTransitions.get(v).get(i)].needTerminal) {
                needTerminal(reverseTransitions.get(v).get(i));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}