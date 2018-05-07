package HomeWork.Algoritms.lab5;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class B2 {

    private Node root;

    private static class Node {
        private int key;
        private int balance;
        private int height;
        private Node left;
        private Node right;
        private Node parent;

        Node(int key, Node parent) {
            this.key = key;
            this.parent = parent;
        }
    }

    public Node next(int key) {
        Node current = root;
        Node successor = null;
        while (current != null) {
            if (current.key > key) {
                successor = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return successor;
    }

    public Node prev(int key) {
        Node current = root;
        Node successor = null;
        while (current != null) {
            if (current.key < key) {
                successor = current;
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return successor;
    }

    private Node get(Node node, int key) {
        if (node == null) return null;

        if (key == node.key) {
            return node;
        } else if (key > node.key) {
            return get(node.right, key);
        } else {
            return get(node.left, key);
        }
    }

    public Node get(int key) {
        return get(root, key);
    }

    public boolean insert(int key) {
        if (root == null) {
            root = new Node(key, null);
            return true;
        }

        Node n = root;
        while (true) {
            if (n.key == key)
                return false;

            Node parent = n;

            boolean goLeft = n.key > key;
            n = goLeft ? n.left : n.right;

            if (n == null) {
                if (goLeft) {
                    parent.left = new Node(key, parent);
                } else {
                    parent.right = new Node(key, parent);
                }
                rebalance(parent);
                break;
            }
        }
        return true;
    }

    private void delete(Node node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else {
                Node parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                rebalance(parent);
            }
            return;
        }

        if (node.left != null) {
            Node child = node.left;
            while (child.right != null) child = child.right;
            node.key = child.key;
            delete(child);
        } else {
            Node child = node.right;
            while (child.left != null) child = child.left;
            node.key = child.key;
            delete(child);
        }
    }

    public void delete(int delKey) {
        if (root == null)
            return;

        Node child = root;
        while (child != null) {
            Node node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }

    private void rebalance(Node n) {
        setBalance(n);

        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);

        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }

    private Node rotateLeft(Node a) {

        Node b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null)
            a.right.parent = a;

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateRight(Node a) {

        Node b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null)
            a.left.parent = a;

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    private int height(Node n) {
        if (n == null)
            return -1;
        return n.height;
    }

    private void setBalance(Node... nodes) {
        for (Node n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }

    private void reheight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private void printTree(Node current) {
        if (current == null) {
            return;
        }

        printTree(current.left);
        System.out.print(" " + current.key);
        printTree(current.right);
    }

    public void start() throws IOException {
//        BufferedReader buf =  new BufferedReader( new FileReader("arrange.in.txt"));

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;

        String s;
        while ((s = buf.readLine()) != null) {
            st = new StringTokenizer(s);

            String in = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            switch (in) {
                case "insert":
                    insert(value);
//                    printTree(root);
//                    System.out.println();
                    break;
                case "exists":
                    if (get(value) == null) {
                        System.out.println(false);
                    } else {
                        System.out.println(true);
                    }
                    break;
                case "next":
                    if (next(value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(next(value).key);
                    }
                    break;
                case "prev":
                    if (prev(value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(prev(value).key);
                    }
                    break;
                case "delete":
                    delete(value);
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new B2().start();
    }
}
