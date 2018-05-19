package HomeWork.Discret_math.lab5;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * HomeWork.Discret_math.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class H {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    private final int ALPHABET_SIZE = 26;

    private int n;
    private int m;
    private int counter;
    private int countTransitions;
    private int countTerminal;

    private boolean[] visited;
    private boolean[] terminal;
    private boolean[] newTerminal;

    private int[][] transitions;
    private int[][] newTransitions;

    private ArrayList<Integer>[][] transitionsVert;
    private ArrayList<HashSet<Integer>> handler = new ArrayList<>();

    private ArrayDeque<Pair> queue = new ArrayDeque<>();

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("minimization.in")));
        PrintWriter writer = new PrintWriter("minimization.out");

        n = nextInt() + 1;
        m = nextInt();
        int k = nextInt();

        terminal = new boolean[n];
        transitions = new int[n][ALPHABET_SIZE + 1];
        newTransitions = new int[n][ALPHABET_SIZE + 1];
        transitionsVert = new ArrayList[n][ALPHABET_SIZE + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ALPHABET_SIZE + 1; j++) {
                transitionsVert[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < k; i++) {
            terminal[nextInt()] = true;
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            int symb = nextString().charAt(0) - 'a';
            transitions[from][symb] = to;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                transitionsVert[transitions[i][j]][j].add(i);
            }
        }

        visited = new boolean[n];

        dfs(1);
        solveEquals();

        int[] mainstream = new int[n];

        for (int i = 0; i < n; i++) {
            mainstream[i] = -1;
        }

        for (HashSet<Integer> x: handler) {
            if (x.contains(0)) {
                for (int i: x) {
                    mainstream[i] = 0;
                }
            }

            if (x.contains(1) && mainstream[1] == -1) {
                for (int i: x) {
                    mainstream[i] = 1;
                }
                ++counter;
            }
        }

        for (HashSet<Integer> x: handler) {
            final int itr = x.stream().findFirst().get();

            if (!visited[itr] || mainstream[itr] != -1) {
                continue;
            }

            ++counter;
            mainstream[itr] = counter;

            for (int j: x) {
                mainstream[j] = counter;
            }
        }

        newTerminal = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (terminal[i] && mainstream[i] != -1 && !newTerminal[mainstream[i]]) {
                newTerminal[mainstream[i]] = true;
                ++countTerminal;
            }
        }

        if (newTerminal[0]) --countTerminal;

        try {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < ALPHABET_SIZE; j++) {
                    if (mainstream[i] > 0 && mainstream[transitions[i][j]] > 0 && newTransitions[mainstream[i]][j] == 0) {
                        newTransitions[mainstream[i]][j] = mainstream[transitions[i][j]];
                        countTransitions++;
                    }
                }
            }

        } catch (Exception e) {
            System.exit(0);
        }

        writer.println(counter + " " + countTransitions + " " + countTerminal);

        for (int i = 1; i < n; i++) {
            if (newTerminal[i])
                writer.print(i + " ");
        }

        writer.println();

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                if (newTransitions[i][j] != 0) {
                    writer.println(i + " " + newTransitions[i][j] + " " + (char) (j + 'a'));
                }
            }
        }

        writer.close();
    }

    private void dfs(int v) {
        visited[v] = true;

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (!visited[transitions[v][i]]) {
                dfs(transitions[v][i]);
            }
        }
    }

    private void solveEquals() {
        HashSet<Integer> terminalClass = new HashSet<>();
        HashSet<Integer> notTerminalClass = new HashSet<>();

        ArrayList<HashSet<Integer>> aka = new ArrayList<>();

        int[] classes = new int[n];

        for (int i = 0; i < n; i++) {
            if (terminal[i]) {
                terminalClass.add(i);
                classes[i] = 0;
            } else {
                notTerminalClass.add(i);
                classes[i] = 1;
            }
        }

        handler.add(terminalClass);
        handler.add(notTerminalClass);

        aka.add(terminalClass);
        aka.add(notTerminalClass);

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            queue.addLast(new Pair(0, i));
            queue.addLast(new Pair(1, i));
        }

        while (!queue.isEmpty()) {
            Pair now = queue.pollFirst();

            Map<Integer, ArrayList<Integer>> hMap = new HashMap<>();

            for (Integer q: aka.get((int) now.getKey())) {
                for (Integer r: transitionsVert[q][(int) now.getValue()]) {
                    int i = classes[r];
                    if (!hMap.containsKey(i)) {
                        hMap.put(i, new ArrayList<>());
                    }

                    hMap.get(i).add(r);
                }
            }

            for (Map.Entry<Integer, ArrayList<Integer>> current: hMap.entrySet()) {
                if (current.getValue().size() == 0) {
                    continue;
                }

                int currentKey = current.getKey();

                if (hMap.get(currentKey).size() < handler.get(currentKey).size()) {
                    handler.add(new HashSet<>());

                    int size = handler.size() - 1;
                    for (Integer it: hMap.get(currentKey)) {
                        handler.get(currentKey).remove(it);
                        handler.get(size).add(it);
                    }

                    for (Integer it: handler.get(size)) {
                        classes[it] = size;
                    }

                    aka.add(handler.get(size));

                    for (int j = 0; j < ALPHABET_SIZE; j++) {
                        queue.add(new Pair(aka.size() - 1, j));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }

}