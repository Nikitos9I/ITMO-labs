package HomeWork.Algoritms.lab5;

import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class F {

    public class Node {
        Node left;
        Node right;

        int priority;
        int key;
        int depth;

        public Node(int key, int priority) {
            this.key = key;
            this.priority = priority;
            this.depth = 1;
        }
    }

    public class Pair {
        Node left;
        Node right;

        public Pair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    private Node merge(Node l, Node r) {
        if (r == null) {
            return l;
        } else if (l == null) {
            return r;
        } else if (l.priority < r.priority) {
            r.left = merge(l, r.left);
            r.depth = isDepth(r.left) + isDepth(r.right) + 1;
            return r;
        } else {
            l.right = merge(l.right, r);
            l.depth = isDepth(l.left) + isDepth(l.right) + 1;
            return l;
        }
    }

    public void insert(int key, int priority) {
        Node temp = root;
        while (temp != null && temp.key != key)
            if (key < temp.key)
                temp = temp.left;
            else
                temp = temp.right;
        if (temp == null) {
            Node m = new Node(key,priority);
            Pair p = split(root, key, priority);
            root = merge(p.left, merge(m, p.right));
            root.depth = isDepth(root.left) + isDepth(root.right) + 1;
        }
    }

    private Pair split(Node t, int key, int priority) {
        if (t == null) {
            return new Pair(null, null);
        }

        if (t.key < key) {
            Pair pair = split(t.right, key, priority);
            t.right = null;
            t.depth = isDepth(t.left) + 1;
            return new Pair(merge(t, pair.left), pair.right);
        } else {
            Pair pair = split(t.left, key, priority);
            t.left = null;
            t.depth = isDepth(t.right) + 1;
            return new Pair(pair.left, merge(pair.right, t));
        }
    }

    private Node delete(Node current, int key) {
        if (key == current.key) {
            return merge(current.left, current.right);
        }

        if (key < current.key) {
            current.left = delete(current.left, key);
        } else {
            current.right = delete(current.right, key);
        }

        current.depth = isDepth(current.left) + isDepth(current.right) + 1;

        return current;
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader buf = new BufferedReader(new FileReader("arrange.in.txt"));

        int n = Integer.parseInt(buf.readLine());
        StringTokenizer st;
        int nodeCounter = 0;

        for (int i = 0; i < n; ++i) {
            st = new StringTokenizer(buf.readLine());

            String in = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            switch (in) {
                case "1":
                case "+1":
                    insert(value, generate());
//                    printTree(root);
//                    System.out.println("<----");
                    nodeCounter++;
                    break;
                case "-1":
                    root = delete(root, value);
//                    printTree(root);
//                    System.out.println("<----");
                    nodeCounter--;
                    break;
                case "0":
                    int index = nodeCounter - value + 1;
                    System.out.println(getKMaximum(root, index));
                    break;
            }
        }
    }

    public int generate() {
        long res = new Random().nextInt();
        for (int i = 0; i < 3; i++) {
            res <<= 16;
            res += new Random().nextInt();
        }
        return (int) Math.abs(res % Integer.MAX_VALUE);
    }

    public int isDepth(Node current) {
        return current == null? 0: current.depth;
    }


    public int getKMaximum(Node current, int value) {
        if (value - isDepth(current.left) == 1) {
            return current.key;
        }

        if (isDepth(current.left) >= value) {
            return getKMaximum(current.left, value);
        } else {
            return getKMaximum(current.right, value - isDepth(current.left) - 1);
        }
    }

    public void printTree(Node current) {
        if (current == null) {
            return;
        }

        System.out.print(current.key + " ");

        printTree(current.left);
        printTree(current.right);
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }

}
