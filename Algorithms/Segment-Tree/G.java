package HomeWork.Algoritms.lab4;

import java.io.*;
import java.util.Arrays;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class G {

    public StreamTokenizer t;
    public int k;
    public int n;
    public boolean f = true;
    public Node[] input;
    public yNode[] tree;

    public class Node implements Comparable {

        int x;
        int yTop;
        int yDown;
        int type;


        public Node() {}

        public Node(int x, int yTop, int yDown, int type) {
            this.x = x;
            this.yTop = yTop;
            this.yDown = yDown;
            this.type = type;
        }

        @Override
        public int compareTo(Object arg) {
            final int EQUAL = 0;
            final int AFTER = 1;
            final int BEFORE = -1;

            if (this.x > ((Node)arg).x) return AFTER;
            if (this.x < ((Node)arg).x) return BEFORE;

            if (this.x == ((Node)arg).x) {
                if (this.type > ((Node)arg).type) {
                    return BEFORE;
                }

                if (this.type < ((Node)arg).type) {
                    return AFTER;
                }
            }

            return EQUAL;
        }

    }

    public class yNode {
        int value;
        int left;
        int right;
        boolean actual;

        public yNode() {}

        public yNode(int value, int left, int right, boolean actual) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.actual = actual;
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

        t = new StreamTokenizer( new BufferedReader( new InputStreamReader(System.in)));

        int n = nextInt();
        input = new Node[2 * n];

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int x1 = nextInt();
            int y1 = nextInt();
            int x2 = nextInt();
            int y2 = nextInt();
            min = min > y1? y1: min;
            max = max < y2? y2: max;

            input[2 * i] = new Node(x1, y2, y1, 1);
            input[2 * i + 1] = new Node(x2, y2, y1, -1);
        }

        Arrays.sort(input);

        if (min > 0) {
            min = 0;
        } else if (min < 0) {
            min -= 1;
        } else if (min == 0) {
            min = -1;
        }

        for (int i = 0; i < 2 * n; i++) {
            input[i].yDown -= (min);
            input[i].yTop -= (min);
        }

        int size = max - min;
        k = do2power(size);

        tree = new yNode[2 * k - 1];

        for (int i = 0; i < 2 * k - 1; i++) {
            tree[i] = new yNode(0, i, i, false);
        }

        for (int i = k - 2; i >= 0; --i) {
            tree[i].left = tree[i * 2 + 1].left;
            tree[i].right = tree[i * 2 + 2].right;
        }

        int ans = 0;
        int resultX = 0;
        int resultY = 0;
        int maxQuer = Integer.MIN_VALUE;
        for (int i = 0; i < 2 * n; i++) {
            newQuer(0, input[i].yDown + k - 2, input[i].yTop + k - 2, input[i].type);

            ans = findAns();

            if (maxQuer < tree[0].value) {
                maxQuer = tree[0].value;
                resultX = input[i].x;
                resultY = ans - k + 2 + min;
            }
        }

        System.out.println(maxQuer);
        System.out.println(resultX + " " + resultY);
    }

    public void newQuer(int i , int left, int right, int value) {
        if (i * 2 + 2 >= 2 * k - 1) {
            if (tree[i].left >= left && tree[i].right <= right) {
                tree[i].value += value;
            }
            return;
        }

        if (tree[i].right < left || tree[i].left > right) {
            return;
        }

        if (tree[i].left >= left && tree[i].right <= right) {
            tree[i].value += value;
            return;
        }

        push(i);

        newQuer(i * 2 + 1, left, right, value);
        newQuer(i * 2 + 2, left, right, value);

        tree[i].value = Integer.max(tree[2 * i + 1].value, tree[2 * i + 2].value);
    }

    public void push(int i) {
        int max = Integer.max(tree[2 * i + 1].value, tree[2 * i + 2].value);

        if (tree[i].value > max) {
            tree[2 * i + 1].value += (tree[i].value - max);
            tree[2 * i + 2].value += (tree[i].value - max);
        }

        if (tree[i].value < max) {
            tree[2 * i + 1].value -= (max - tree[i].value);
            tree[2 * i + 1].value = Integer.max(0 , tree[2 * i + 1].value);
            tree[2 * i + 2].value -= (max - tree[i].value);
            tree[2 * i + 2].value = Integer.max(0 , tree[2 * i + 2].value);
        }
    }

    public int findAns() {
        int res = 0;
        while (res * 2 + 2 < 2 * k - 1) {
            push(res);

            if (tree[res].value == tree[res * 2 + 1].value) {
                res = res * 2 + 1;
                continue;
            }

            res = res * 2 + 2;
        }

        return res;
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new G().start();
    }
}
