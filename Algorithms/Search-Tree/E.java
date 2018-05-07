package HomeWork.Algoritms.lab5;

import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class E {

    public StreamTokenizer t;
    public long sum;
    public long previousSum;
    public HashSet<Integer> elements = new HashSet<>();

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public class Node {
        Node left;
        Node right;

        int priority;
        int key;
        long sum;

        public Node(int key, int priority) {
            this.key = key;
            this.priority = priority;
            this.sum = key;
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
            r.sum = isSum(r.left) + isSum(r.right) + r.key;
            return r;
        } else {
            l.right = merge(l.right, r);
            l.sum = isSum(l.left) + isSum(l.right) + l.key;
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
        }
    }

    private Pair split(Node t, int key, int priority) {
        if (t == null) {
            return new Pair(null, null);
        }

        if (t.key < key) {
            Pair pair = split(t.right, key, priority);
            t.right = null;
            t.sum = isSum(t.left) + t.key;
            return new Pair(merge(t, pair.left), pair.right);
        } else {
            Pair pair = split(t.left, key, priority);
            t.left = null;
            t.sum = isSum(t.right) + t.key;
            return new Pair(pair.left, merge(pair.right, t));
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

    public void add(int key) {
        if (elements.contains(key)) {
            return;
        }

        elements.add(key);

        insert(key, generate());
    }

    public long sum(int left, int right) {
        sum = 0;

        getSum(left, right, root);

        return sum;
    }

    public long isSum(Node current) {
        return current == null? 0: current.sum;
    }

    public long getMin(Node current) {
        return current.left != null? getMin(current.left): current.key;
    }

    public long getMax(Node current) {
        return current.right != null? getMax(current.right): current.key;
    }

    public void getSum(int left, int right, Node current) {
        if (current == null) {
            return;
        }

        if (current.key >= left && current.key <= right) {
            if (getMin(current) >= left && getMax(current) <= right) {
                sum += current.sum;
            } else if (getMin(current) >= left) {
                sum += current.key;
                sum += current.left != null? current.left.sum: 0;
                getSum(left, right, current.right);
            } else if (getMax(current) <= right) {
                sum += current.key;
                sum += current.right != null? current.right.sum: 0;
                getSum(left, right, current.left);
            } else {
                sum += current.key;
                getSum(left, right, current.left);
                getSum(left, right, current.right);
            }
        } else if (current.key > right) {
            getSum(left, right, current.left);
        } else if (current.key < left) {
            getSum(left, right, current.right);
        }
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new InputStreamReader( System.in));

//        BufferedReader buf =  new BufferedReader(new FileReader("arrange.in.txt"));

        int n = Integer.parseInt(buf.readLine());
        StringTokenizer st;

        previousSum = 0;
        for (int i = 0; i < n; ++i) {
            st = new StringTokenizer(buf.readLine());

            String in = st.nextToken();
            String valueSt = st.nextToken();
            int value = Integer.parseInt(valueSt);

            switch (in) {
                case "+":
                    long sum = (value + previousSum) % 1000000000;
                    add((int) sum);
                    previousSum = 0;
                    break;
                case "?":
                    int right = Integer.parseInt(st.nextToken());
                    previousSum = sum(value, right);
                    System.out.println(previousSum);
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}