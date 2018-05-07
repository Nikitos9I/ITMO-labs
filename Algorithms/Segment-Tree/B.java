package HomeWork.Algoritms.lab4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class B {

    public StreamTokenizer t;
    public long[] input;

    private class Node {

        int value;
        Node leftChild;
        Node rightChild;

        private Node() {}

        private Node(int value, Node leftChild, Node rightChild) {
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
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
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("rsq.in"));
        PrintWriter writer = new PrintWriter("rsq.out");

        int n = Integer.parseInt(buf.readLine());
        StringTokenizer t = new StringTokenizer(buf.readLine());
        int k = do2power(n);

        input = new long[2*k - 1];
//        Node root = new Node();
//        Node[] input = new Node[n];

        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i] = Integer.parseInt(t.nextToken());
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i] = input[i * 2 + 1] + input[i * 2 + 2];
        }

//        for (int i = 0; i < 2 * k - 1; i++) {
//            System.out.print(input[i] + " ");
//        }
//        System.out.println();

        String s;
        while ((s = buf.readLine()) != null) {
            t = new StringTokenizer(s);
            if (t.nextToken().equals("set")) {
                int i = Integer.parseInt(t.nextToken()) + k - 2;
                long x = Integer.parseInt(t.nextToken());
                long del = x - input[i];
                input[i] = x;

                while (i != 0) {
                    input[(i - 1) / 2] += del;
                    i = (i - 1) / 2;
                }
            } else {
                int left = Integer.parseInt(t.nextToken()) + k - 2;
                int right = Integer.parseInt(t.nextToken()) + k - 2;

                if (right - left > 1) {
                    writer.println(findSum(left, right));
                } else {
                    if (right - left == 0) {
                        writer.println(input[left]);
                    } else {
                        writer.println(input[left] + input[right]);
                    }
                }
            }
        }

        writer.close();
    }

    public long findSum(int left, int right) {
        long sum = 0;

        if (left % 2 == 0) {
            sum += input[left++];
        }

        if (right % 2 == 1) {
            sum += input[right--];
        }

        while ((right - 1) / 2 - (left - 1) / 2 > 1) {
            if (((left - 1) / 2) % 2 == 0) {
                sum += input[(left - 1) / 2];
            }

            left = (left - 1) / 2 + 1;

            if (((right - 1) / 2) % 2 == 1) {
                sum += input[(right - 1) / 2];
            }

            right = (right - 1) / 2 - 1;
        }

        if ((right - 1) / 2 - (left - 1) / 2 == 0) {
            sum += input[(right - 1) / 2];
        } else {
            sum += input[(right - 1) / 2] + input[(left - 1) / 2];
        }

        return sum;
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
