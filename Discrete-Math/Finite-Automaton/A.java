package HomeWork.Discret_math.lab5;

import java.io.*;
import java.util.Arrays;

/**
 * HomeWork.Discret_math.lab1.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

    public StreamTokenizer t;

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
        State a, b, c, d, e ,f ,g ,h, i, j, k, l, m, n , o, p, q, r , s, t, u, v, w, x ,y ,z = null;

        public State(boolean ending) {
            this.ending = ending;
        }
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("problem1.in")));
        PrintWriter writer = new PrintWriter("problem1.out");

        String input = nextString();

        int n = nextInt();
        int m = nextInt();
        int k = nextInt();

        State[] states = new State[n];

        for (int i = 0; i < n; i++) {
            states[i] = new State(false);
        }

        for (int i = 0; i < k; i++) {
            states[nextInt() - 1].ending = true;
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            char symbol = nextString().charAt(0);

            switch (symbol) {
                case 'a':
                    states[from].a = states[to];
                    break;
                case 'b':
                    states[from].b = states[to];
                    break;
                case 'c':
                    states[from].c = states[to];
                    break;
                case 'd':
                    states[from].d = states[to];
                    break;
                case 'e':
                    states[from].e = states[to];
                    break;
                case 'f':
                    states[from].f = states[to];
                    break;
                case 'g':
                    states[from].g = states[to];
                    break;
                case 'h':
                    states[from].h = states[to];
                    break;
                case 'i':
                    states[from].i = states[to];
                    break;
                case 'j':
                    states[from].j = states[to];
                    break;
                case 'k':
                    states[from].k = states[to];
                    break;
                case 'l':
                    states[from].l = states[to];
                    break;
                case 'm':
                    states[from].m = states[to];
                    break;
                case 'n':
                    states[from].n = states[to];
                    break;
                case 'o':
                    states[from].o = states[to];
                    break;
                case 'p':
                    states[from].p = states[to];
                    break;
                case 'q':
                    states[from].q = states[to];
                    break;
                case 'r':
                    states[from].r = states[to];
                    break;
                case 's':
                    states[from].s = states[to];
                    break;
                case 't':
                    states[from].t = states[to];
                    break;
                case 'u':
                    states[from].u = states[to];
                    break;
                case 'v':
                    states[from].v = states[to];
                    break;
                case 'w':
                    states[from].w = states[to];
                    break;
                case 'x':
                    states[from].x = states[to];
                    break;
                case 'y':
                    states[from].y = states[to];
                    break;
                case 'z':
                    states[from].z = states[to];
                    break;
            }
        }

        State current = states[0];
        for (int i = 0; i < input.length(); i++) {
            if (current == null) {
                break;
            }

            switch (input.charAt(i)) {
                case 'a':
                    current = current.a;
                    break;
                case 'b':
                    current = current.b;
                    break;
                case 'c':
                    current = current.c;
                    break;
                case 'd':
                    current = current.d;
                    break;
                case 'e':
                    current = current.e;
                    break;
                case 'f':
                    current = current.f;
                    break;
                case 'g':
                    current = current.g;
                    break;
                case 'h':
                    current = current.h;
                    break;
                case 'i':
                    current = current.i;
                    break;
                case 'j':
                    current = current.j;
                    break;
                case 'k':
                    current = current.k;
                    break;
                case 'l':
                    current = current.l;
                    break;
                case 'm':
                    current = current.m;
                    break;
                case 'n':
                    current = current.n;
                    break;
                case 'o':
                    current = current.o;
                    break;
                case 'p':
                    current = current.p;
                    break;
                case 'q':
                    current = current.q;
                    break;
                case 'r':
                    current = current.r;
                    break;
                case 's':
                    current = current.s;
                    break;
                case 't':
                    current = current.t;
                    break;
                case 'u':
                    current = current.u;
                    break;
                case 'v':
                    current = current.v;
                    break;
                case 'w':
                    current = current.w;
                    break;
                case 'x':
                    current = current.x;
                    break;
                case 'y':
                    current = current.y;
                    break;
                case 'z':
                    current = current.z;
                    break;
            }
        }

        writer.write(current != null? (current.ending? "Accepts" : "Rejects") : "Rejects");
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
