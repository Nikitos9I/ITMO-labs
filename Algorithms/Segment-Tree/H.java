package HomeWork.Algoritms.lab4;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class H {

    public StreamTokenizer t;
    public int k;
    public int n;
    public boolean f = true;
    public Node[] input;
    public int[] left;
    public int[] right;
    public int[] min;

    public class Node {

        Object value;
        int left;
        int right;

        public Node() {}

        public Node(Object value, int left, int right) {
            this.value = value;
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

        t = new StreamTokenizer( new BufferedReader( new FileReader("rmq.in")));
        PrintWriter writer = new PrintWriter("rmq.out");

        n = nextInt();
        int z = nextInt();

        k = do2power(n);
        input = new Node[2*k - 1];

        for (int i = 0; i < 2 * k - 1; i++) {
            input[i] = new Node(null, i, i);
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i].left = input[i * 2 + 1].left;
            input[i].right = input[i * 2 + 2].right;
        }

        left = new int[z];
        right = new int[z];
        min = new int[z];
        for (int i = 0; i < z; i++) {
            left[i] = nextInt() + k - 2;
            right[i] = nextInt() + k - 2;
            min[i] = nextInt();

            newQuer(0, left[i], right[i], min[i]);
        }

        findAns();

        for (int i = k - 1; i < k - 1 + n; i++) {
            if (input[i].value == null) {
                input[i].value = 0;
            }
        }

        checkAns();

        if (f) {
            writer.write("consistent\n");
            for (int i = k - 1; i < k - 1 + n; i++) {
                writer.write(input[i].value + " ");
            }
        } else {
            writer.write("inconsistent");
        }

        writer.close();
    }

    public void findAns() {

        if (input[0].value != null) {
            push(0, input[0].value);
        } else {
            push(0, null);
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i].value = min(input[i * 2 + 1].value, input[i * 2 + 2].value);
        }

    }

    public void push(int i, Object x) {
        if (i >= 2 * k - 1) {
            return;
        }

        input[i].value = max(x, input[i].value);

        if (input[i].value != null) {
            push(i * 2 + 1, input[i].value);
            push(i * 2 + 2, input[i].value);
        } else {
            push(i * 2 + 1, null);
            push(i * 2 + 2, null);
        }
    }

    public void checkAns() {
        for (int i = 0; i < min.length; i++) {
            if (right[i] - left[i] > 1) {
                if (getAns(left[i], right[i]) != min[i]) {
                    f = false;
                    return;
                }
            } else {
                if (right[i] - left[i] == 0) {
                    if ((int) input[right[i]].value != min[i]) {
                        f = false;
                        return;
                    }
                } else {
                    if ((int) input[left[i]].value != min[i] && (int) input[right[i]].value != min[i]) {
                        f = false;
                        return;
                    }
                }
            }
        }
    }

    public int getAns(int left, int right) {
        int min = Integer.MAX_VALUE;

        if (left % 2 == 0) {
            min = Integer.min((int) input[left++].value, min);
        }

        if (right % 2 == 1) {
            min = Integer.min((int) input[right--].value, min);
        }

        while ((right - 1) / 2 - (left - 1) / 2 > 1) {
            if (((left - 1) / 2) % 2 == 0) {
                min = Integer.min((int) input[(left - 1) / 2].value, min);
            }

            left = (left - 1) / 2 + 1;

            if (((right - 1) / 2) % 2 == 1) {
                min = Integer.min((int) input[(right - 1) / 2].value, min);
            }

            right = (right - 1) / 2 - 1;
        }

        if ((right - 1) / 2 - (left - 1) / 2 == 0) {
            min = Integer.min((int) input[(right - 1) / 2].value, min);
        } else {
            min = Integer.min((int) input[(right - 1) / 2].value, min);
            min = Integer.min((int) input[(left - 1) / 2].value, min);
        }

        return min;
    }

    public void newQuer(int i, int left, int right, int min) {
        if (i * 2 + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                if (input[i].value != null) {
                    input[i].value = Integer.max((int) input[i].value, min);
                } else {
                    input[i].value = min;
                }
            }
            return;
        }

        if (input[i].right < left || input[i].left > right) {
            return;
        }

        if (input[i].left >= left && input[i].right <= right) {
            if (input[i].value != null) {
                input[i].value = Integer.max((int) input[i].value, min);
            } else {
                input[i].value = min;
            }
            return;
        }

        newQuer(i * 2 + 1, left, right, min);
        newQuer(i * 2 + 2, left, right, min);
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public Object min(Object a, Object b) {
        if (a == null && b == null) {
            return null;
        } else if (a == null && b != null) {
            return b;
        } else if (a != null && b == null) {
            return a;
        }

        return (int) a < (int) b? a:b;
    }

    public Object max(Object a, Object b) {
        if (a == null && b == null) {
            return null;
        } else if (a == null && b != null) {
            return b;
        } else if (a != null && b == null) {
            return a;
        }

        return (int) a > (int) b? a:b;
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }

}
