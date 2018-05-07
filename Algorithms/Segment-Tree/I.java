package HomeWork.Algoritms.lab4;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class I {

    public StreamTokenizer t;

    public class Node {
         int sum;
         int max;
         int left;
         int right;
         int value;
         boolean actual;
         Node leftChild;
         Node rightChild;

         public Node() {
             this.sum = this.max = this.left = this.right = 0;
             this.leftChild = this.rightChild = null;
         }

         public Node(int sum, int max, boolean actual, int value, int left, int right) {
             this.sum = sum;
             this.max = max;
             this.actual = actual;
             this.value = value;
             this.left = left;
             this.right = right;
         }

         public Node(int sum, int max, boolean actual, int value, int left, int right, Node leftChild, Node rightChild) {
             this.sum = sum;
             this.max = max;
             this.actual = actual;
             this.value = value;
             this.left = left;
             this.right = right;
             this.leftChild = leftChild;
             this.rightChild = rightChild;
         }
    }

    Node tree;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

//        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();

        tree = new Node(0,0,false, 0,1, n);

        String in;
        while (!(in = nextString()).equals("E")) {
            if (in.equals("Q")) {
                int height = nextInt();
                if (height >= tree.max) {
                    System.out.println(n);
//                    writer.println(n);
                } else {
                    System.out.println(get(tree, height));
//                    writer.println(get(tree, height));
                }
            } else {
                int left = nextInt();
                int right = nextInt();
                int value = nextInt();
                quer(tree, value, left, right);
            }

//            System.out.println("print <----");
//            printTree(tree);
//            System.out.println();
//            System.out.println("----> print");
        }

//        writer.close();
    }

    public void quer(Node current, int value, int left, int right) {
//        System.out.println("+++");
        if (current.right < left || current.left > right) {
            return;
        }

        if (current.left >= left && current.right <= right) {
            current.max = current.sum = value * (current.right - current.left + 1);
            current.actual = true;
            current.value = value;
            return;
        }

        push(current);

        quer(current.leftChild, value, left, right);
        quer(current.rightChild, value, left, right);

        current.sum = current.leftChild.sum + current.rightChild.sum;
        current.max = Integer.max(current.leftChild.max, current.rightChild.max + current.leftChild.sum);
    }

    public void push(Node current) {
//        System.out.println("++");

        int middle = (current.left + current.right) / 2;

        if (current.leftChild == null) {
            current.leftChild = new Node(current.value * (middle - current.left + 1),
                    current.value * (middle - current.left + 1),
                    true, current.value, current.left, middle);
        } else {
//            if (current.sum != current.leftChild.sum + current.rightChild.sum) {
            if (current.actual) {
                current.leftChild.sum = current.leftChild.max = current.value * (current.leftChild.right - current.leftChild.left + 1);
                current.leftChild.value = current.value;
                current.leftChild.actual = true;
            }
        }

        if (current.rightChild == null) {
            current.rightChild = new Node(current.value * (current.right - middle),
                    current.value * (current.right - middle),
                    true, current.value, middle + 1, current.right);
            current.actual = false;
        } else {
//            if (current.sum != current.leftChild.sum + current.rightChild.sum) {
            if (current.actual) {
                current.rightChild.sum = current.rightChild.max = current.value * (current.rightChild.right - current.rightChild.left + 1);
                current.rightChild.value = current.value;
                current.rightChild.actual = true;
                current.actual = false;
            }
        }
    }

    public int get(Node current, int value) {
        if (current.left == current.right) {
            return current.left - 1;
        }

        push(current);

        if (current.leftChild.max > value) {
            return get(current.leftChild, value);
        } else {
            return get(current.rightChild, value - current.leftChild.sum);
        }
    }

    public void printTree(Node current) {
        System.out.println("sum = " + current.sum + " max = " + current.max + " left = " + current.left + " right = " + current.right);

        if (current.leftChild != null) {
            printTree(current.leftChild);
        }

        if (current.rightChild != null) {
            printTree(current.rightChild);
        }
//        System.out.print(tree.sum + " ");
//        if (tree.leftChild != null) {
//            System.out.print(tree.leftChild.sum + " " + tree.rightChild.sum + " ");
//            System.out.print(tree.leftChild.leftChild.sum + " " + tree.leftChild.rightChild.sum + " ");
//            System.out.print(tree.leftChild.leftChild.leftChild.sum + " " + tree.leftChild.leftChild.rightChild.sum);
////            System.out.print(tree.rightChild.rightChild.sum + " " /*tree.rightChild.rightChild.sum*/);
//        }
    }

    public static void main(String[] args) throws IOException {
        new I().start();
    }

}
