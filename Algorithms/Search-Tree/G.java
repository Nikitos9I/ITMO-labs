package HomeWork.Algoritms.lab5;

import java.io.*;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class G {

    public StreamTokenizer t;

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
        Node parent;

        int priority;
        int key;
        int shift;

        public Node(int key, int priority) {
            this.key = key;
            this.priority = priority;
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
        if (r == null && l == null) {
            return null;
        } else if (r == null) {
            push(l);
            return l;
        } else if (l == null) {
            push(r);
            return r;
        } else {
            push(l);
            push(r);

            if (l.priority < r.priority) {
                r.left = merge(l, r.left);
                push(l);
                push(r);
                return r;
            } else {
                l.right = merge(l.right, r);
                push(l);
                push(r);
                return l;
            }
        }
    }

    private boolean checkCurrent(Node current) {
        return (current != null);
    }

    private Pair split(Node t, int key) {
        if (t == null) {
            return new Pair(null, null);
        }

        push(t);
        if (t.key < key) {
            Pair pair = split(t.right, key);
            t.right = pair.left;
            push(t);
            return new Pair(t, pair.right);
        } else {
            Pair pair = split(t.left, key);
            t.left = pair.right;
            push(t);
            return new Pair(pair.left, t);
        }
    }

    public void push(Node node) {
        if (node == null)
            return;
        if (node.left != null)
            node.left.shift += node.shift;
        if (node.right != null)
            node.right.shift += node.shift;

        node.key += node.shift;
        node.shift = 0;
    }

    public void build(Node current, Node lastAdded) {
        if (current.priority > lastAdded.priority) {
            if (lastAdded.right != null) {
                current.left = lastAdded.right;
                lastAdded.right.parent = current;
            }

            lastAdded.right = current;
            current.parent = lastAdded;
        } else {
            if (lastAdded.parent == null) {
                root = current;
                current.left = lastAdded;
                lastAdded.parent = current;
            } else {
                build(current, lastAdded.parent);
            }
        }
    }

    public void printTree(Node current) {
        if (current == null) {
            return;
        }

        printTree(current.left);
        System.out.print(current.priority + " ");
//        System.out.print(current.key + " ");
        printTree(current.right);
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();
        int m = nextInt();

        root = new Node(1, 1);
        Node frst = root;

        for (int i = 1; i < n; i++) {
//            Node current = new Node(i + 1, i + 1);

//            build(current, lastAdded);

//            lastAdded = current;
            root.right = new Node(i + 1, i + 1);
            root = root.right;
        }

        root = frst;

//        printTree(root);
//        System.out.println();

        for (int i = 0; i < m; i++) {
            int left = nextInt();
            int right = nextInt() + 1;

            Pair splitLeft = split(root, left);
            Pair splitRight = split(splitLeft.right, right);

//            if (checkCurrent(splitRight.left)) System.out.println(" depth SplitRight.left " + splitRight.left.depth);
            if (checkCurrent(splitLeft.left)) splitLeft.left.shift += (right - left);
            if (checkCurrent(splitRight.left)) splitRight.left.shift -= (left - 1);

//            printTree(splitLeft.left);
//            System.out.println("<--- left");
//            printTree(splitRight.left);
//            System.out.println("<--- middle");
//            printTree(splitRight.right);
//            System.out.println("<--- right\n");

            root = merge(splitRight.left, merge(splitLeft.left, splitRight.right));

//            printTree(root);
//            System.out.println("<---\n");
        }

        printTree(root);

    }

    public static void main(String[] args) throws IOException {
        new G().start();
    }

}
