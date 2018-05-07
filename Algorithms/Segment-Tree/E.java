package HomeWork.Algoritms.lab4;

import java.util.Stack;
import java.util.StringTokenizer;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class E {

    public Node[] input;
    public int k;
    public int r;
    public StreamTokenizer t;

    public class Node {

        int a;
        int b;
        int c;
        int d;
        int left;
        int right;

        public Node() {}

        public Node(int a, int b, int c, int d, int left, int right) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.left = left;
            this.right = right;
        }

    }

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

        t = new StreamTokenizer( new BufferedReader( new FileReader("crypto.in")));
        PrintWriter writer = new PrintWriter("crypto.out");

        r = nextInt();
        int n = nextInt();
        int m = nextInt();

        k = do2power(n);
        input = new Node[2*k - 1];

        for (int i = 0; i < 2 * k - 1; i++) {
            input[i] = new Node(0, 0, 0, 0, 0, 0);
        }

        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i].a = nextInt();
            input[i].b = nextInt();
            input[i].c = nextInt();
            input[i].d = nextInt();
            input[i].right = input[i].left = i;
        }

        for (int i = k - 1 + n; i < 2 * k - 1; i++) {
            input[i].left = input[i].right = i;
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i] = multi(input[i * 2 + 1], input[i * 2 + 2]);
            input[i].left = input[i * 2 + 1].left;
            input[i].right = input[i * 2 + 2].right;
        }

        for (int i = 0; i < m; i++) {
            int left = nextInt() + k - 2;
            int right = nextInt() + k - 2;

            if (right - left > 1) {
                Node ans = search(left, right);
                writer.println(ans.a + " " + ans.b);
                writer.println(ans.c + " " + ans.d);
            } else {
                if (right - left == 0) {
                    writer.println(input[left].a + " " + input[left].b);
                    writer.println(input[left].c + " " + input[left].d);
                } else {
                    Node ans = multi(input[left], input[right]);
                    writer.println(ans.a + " " + ans.b);
                    writer.println(ans.c + " " + ans.d);
                }
            }
        }

//        writer.write(sb.toString());
        writer.close();
    }

    public Node multi(Node a, Node b) {
        Node ans = new Node(0, 0, 0 ,0, 0, 0);
        ans.a = (a.a * b.a + a.b * b.c) % r;
        ans.b = (a.a * b.b + a.b * b.d) % r;
        ans.c = (a.c * b.a + a.d * b.c) % r;
        ans.d = (a.c * b.b + a.d * b.d) % r;
        ans.left = a.left;
        ans.right = b.right;

        return ans;
    }

    public Node search(int left, int right) {
        Node ans = null;
        Stack<Node> stack = new Stack<>();

        if (left % 2 == 0) {
            ans = input[left++];
        }

        if (right % 2 == 1) {
            stack.push(input[right--]);
        }

        while ((right - 1) / 2 - (left - 1) / 2 > 1) {
            if (((left - 1) / 2) % 2 == 0) {
                if (ans != null) {
                    ans = multi(ans, input[(left - 1) / 2]);
                } else {
                    ans = input[(left - 1) / 2];
                }
            }

            left = (left - 1) / 2 + 1;

            if (((right - 1) / 2) % 2 == 1) {
                stack.push(input[(right - 1) / 2]);
            }

            right = (right - 1) / 2 - 1;
        }

        if ((right - 1) / 2 - (left - 1) / 2 == 0) {
            if (ans != null) {
                ans = multi(ans, input[(left - 1) / 2]);
            } else {
                ans = input[(left - 1) / 2];
            }
        } else {
            if (ans != null) {
                ans = multi(ans, input[(left - 1) / 2]);
            } else {
                ans = input[(left - 1) / 2];
            }
            ans = multi(ans, input[(right - 1) / 2]);
        }

        while (!stack.isEmpty()) {
            ans = multi(ans, stack.pop());
        }

        return ans;
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}
