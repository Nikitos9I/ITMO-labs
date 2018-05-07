package HomeWork.Algoritms.lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class С {

    public Nodes[] input;
    public int k;

    private class Nodes {

        Object min;
        int set;
        int add;
        int left;
        int right;

        private Nodes() {}

        private Nodes(Object min, int set, int add, int left, int right) {
            this.min = min;
            this.set = set;
            this.add = add;
            this.left = left;
            this.right = right;
        }

    }

    private class Node {

        Object min;
        long set;
        long add;
        Node leftChild;
        Node rightChild;

        private Node() {}

        private Node(Object min, long set, long add, Node leftChild, Node rightChild) {
            this.min = min;
            this.set = set;
            this.add = add;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        PrintWriter writer = new PrintWriter("arrange.out.txt");

//        BufferedReader buf = new BufferedReader( new FileReader("rmq2.in"));
//        PrintWriter writer = new PrintWriter("rmq2.out");

        int n = Integer.parseInt(buf.readLine());
        StringTokenizer t = new StringTokenizer(buf.readLine());
        k = do2power(n);

        input = new Nodes[2*k - 1];

        for (int i = 0; i < 2 * k - 1; i++) {
            input[i] = new Nodes(null, 0, 0, 0, 0);
        }

        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i].min = Long.parseLong(t.nextToken());
            input[i].left = i;
            input[i].right = input[i].left;
        }

        for (int i = k - 1 + n; i < 2 * k - 1; i++) {
            input[i].left = i;
            input[i].right = i;
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i].min = min(input[i * 2 + 1].min, input[i * 2 + 2].min);
            input[i].left = input[i * 2 + 1].left;
            input[i].right = input[i * 2 + 2].right;
        }

        for (int i = 0; i < 2 * k - 1; i++) {
            System.out.print(input[i].min + " ");
        }
        System.out.println();

        String s;
        while ((s = buf.readLine()) != null) {
            t = new StringTokenizer(s);
            String in = t.nextToken();
            if (in.equals("set")) {
                int left = Integer.parseInt(t.nextToken()) + k - 2;
                int right = Integer.parseInt(t.nextToken()) + k - 2;
                long x = Long.parseLong(t.nextToken());
                setValueV2(0, left, right, x);

                System.out.println( " set");
                showTree();
            }

            if (in.equals("add")) {
                int left = Integer.parseInt(t.nextToken()) + k - 2;
                int right = Integer.parseInt(t.nextToken()) + k - 2;
                long x = Long.parseLong(t.nextToken());
                addValue(0, left, right, x);

                System.out.println( " add");
                showTree();
            }

            if (in.equals("min")) {
                int left = Integer.parseInt(t.nextToken()) + k - 2;
                int right = Integer.parseInt(t.nextToken()) + k - 2;
                if (right - left > 1) {
                    writer.println(findMinV2(left, right));
                } else {
                    if (right - left == 0) {
                        writer.println(input[left].min);
                    } else {
                        writer.println(min(input[left].min, input[right].min));
                    }
                }

                for (int i = 0; i < 2 * k - 1; i++) {
                    System.out.print(input[i].min + " ");
                }
                System.out.println();
            }
        }

        writer.close();
    }

//    public void setValue(int i, int left, int right, long x) {
//        if (i * 2 + 2 >= 2 * k - 1) {
//            if (input[i].left >= left && input[i].right <= right) {
//                input[i].set = x;
//                input[i].add = 0;
//            }
//            return;
//        }
//
//        if (input[i].right < left || input[i].left > right) {
//            return;
//        }
//
//        input[i].add = 0;
//
//        if (input[i].left >= left && input[i].right <= right) {
//            input[i].set = x;
//            return;
//        }
//
//        if (input[i].set != null) {
//            input[i * 2 + 1].set = input[i].set;
//            input[i * 2 + 1].add = 0;
//            input[i * 2 + 2].set = input[i].set;
//            input[i * 2 + 2].add = 0;
//            input[i].set = null;
//        }
//
//        setValue(i * 2 + 1, left, right, x);
//        setValue(i * 2 + 2, left, right, x);
//
//        if (input[i * 2 + 1].set != null && input[i * 2 + 2].set != null) {
//            input[i].min = min(input[i * 2 + 1].set, input[i * 2 + 2].set);
//        } else {
////            if (i == 4) {
////                System.out.println("AYE, KOTI9TA!");
////            }
//            if (input[i * 2 + 1].set == null && input[i * 2 + 2].set == null) {
//                input[i].min = min(input[i * 2 + 1].min, input[i * 2 + 2].min);
//            } else if (input[i * 2 + 2].set == null) {
//                input[i].min = min(input[i * 2 + 1].set, input[i * 2 + 2].min);
//            } else {
//                input[i].min = min(input[i * 2 + 1].min, input[i * 2 + 2].set);
//            }
//        }
//    }

    public void setValueV2(int i, int left, int right, long x) {
        if (i * 2 + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                input[i].min = x;
                input[i].set = 1;
                input[i].add = 0;
            }
            return;
        }

        if (input[i].right < left || input[i].left > right) {
            return;
        }

        input[i].add = 0;

        if (input[i].left >= left && input[i].right <= right) {
            input[i].min = x;
            input[i].set = 1;
            return;
        }

        if (input[i].set != 0) {
//            if (i == 3) System.out.println("AYE, KOT9ITA");
            input[i * 2 + 1].min = input[i].min;
            input[i * 2 + 1].set = 1;
            input[i * 2 + 1].add = 0;
            input[i * 2 + 2].min = input[i].min;
            input[i * 2 + 2].set = 1;
            input[i * 2 + 2].add = 0;
            input[i].set = 0;
        }

        setValueV2(i * 2 + 1, left, right, x);
        setValueV2(i * 2 + 2, left, right, x);

//        if (input[i * 2 + 1].set != null && input[i * 2 + 2].set != null) {
//            input[i].min = min(input[i * 2 + 1].set, input[i * 2 + 2].set);
//        } else {
//            if (i == 4) {
//                System.out.println("AYE, KOTI9TA!");
//            }
//            if (input[i * 2 + 1].set == null && input[i * 2 + 2].set == null) {
        input[i].min = min(input[i * 2 + 1].min, input[i * 2 + 2].min);
//            } else if (input[i * 2 + 2].set == null) {
//                input[i].min = min(input[i * 2 + 1].set, input[i * 2 + 2].min);
//            } else {
//                input[i].min = min(input[i * 2 + 1].min, input[i * 2 + 2].set);
//            }
//        }
    }

    public void addValue(int i, int left, int right, long x) {
        if (i * 2 + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                input[i].min = (long) input[i].min + x;
                input[i].add = 1;
            }
            return;
        }

        if (input[i].right < left || input[i].left > right) {
            return;
        }

        if (input[i].left >= left && input[i].right <= right) {
            input[i].min = (long) input[i].min + x;
            input[i].add = 1;
            return;
        }

        if (input[i].add != 0 || input[i].set != 0) {
            input[i * 2 + 1].min = (long) input[i].min + x;
            input[i * 2 + 1].add = 1;
            input[i * 2 + 2].min = (long) input[i].min + x;
            input[i * 2 + 2].add = 1;
            input[i].add = 0;
        }

        addValue(i * 2 + 1, left, right, x);
        addValue(i * 2 + 2, left, right, x);

//        if (input[i * 2 + 1].add != 0 && input[i * 2 + 2].add != 0) {
//            input[i].min = Long.min((long) input[i * 2 + 1].min + (long) input[i * 2 + 1].add, (long) input[i * 2 + 2].min + (long) input[i * 2 + 2].add);
//        } else {
//            input[i].min = (long) input[i * 2 + 1].min + (long) input[i * 2 + 1].add;
//        }

        input[i].min = Long.min((long) input[i * 2 + 1].min + input[i * 2 + 1].add, (long) input[i * 2 + 2].min + input[i * 2 + 2].add);
    }

    public long findMinV2(int left, int right) {
        long min = Long.MAX_VALUE;

        if (left % 2 == 0 && input[left].min != null) {
            min = Long.min((long) input[left++].min, min);
        }

        if (right % 2 == 1 && input[right].min != null) {
            min = Long.min((long) input[right--].min, min);
        }

        while ((right - 1) / 2 - (left - 1) / 2 > 1) {
            if (((left - 1) / 2) % 2 == 0) {
                min = Long.min((long) input[(left - 1) / 2].min, min);
            }

            left = (left - 1) / 2 + 1;

            if (((right - 1) / 2) % 2 == 1) {
                min = Long.min((long) input[(right - 1) / 2].min, min);
            }

            right = (right - 1) / 2 - 1;
        }

        if ((right - 1) / 2 - (left - 1) / 2 == 0) {
            min = Long.min((long) input[(right - 1) / 2].min, min);
        } else {
            min = Long.min((long) input[(right - 1) / 2].min, min);
            min = Long.min((long) input[(left - 1) / 2].min, min);
        }

        return min;
    }

//    public long findMin(int left, int right) {
//        long min = Long.MAX_VALUE;
//
//        if (left % 2 == 0 && input[left].min instanceof Long) {
//            if (input[left].set != null) {
//                input[left].min = input[left].set;
//            }
//
//            if (input[left].add != 0) {
//                input[left].min = (long) input[left].min + input[left].add;
//            }
//            min = Long.min((long) input[left++].min, min);
//        }
//
//        if (right % 2 == 1 && input[right].min instanceof Long) {
//            if (input[right].set != null) {
//                input[right].min = input[right].set;
//            }
//
//            if (input[right].add != 0) {
//                input[right].min = (long) input[right].min + input[right].add;
//            }
//            min = Long.min((long) input[right--].min, min);
//        }
//
//        while ((right - 1) / 2 - (left - 1) / 2 > 1) {
//            if (((left - 1) / 2) % 2 == 0) {
//                if (input[(left - 1) / 2].set != null) {
//                    min = (long) input[(left - 1) / 2].set;
//                } else {
//                    min = Long.min((long) input[(left - 1) / 2].min, min);
//                }
//
//                if (input[(left - 1) / 2].add != 0) {
//                    min += input[(left - 1) / 2].add;
//                }
//            }
//
//            left = (left - 1) / 2 + 1;
//
//            if (((right - 1) / 2) % 2 == 1) {
//                if (input[(right - 1) / 2].set != null) {
//                    min = (long) input[(right - 1) / 2].set;
//                } else {
//                    min = Long.min((long) input[(right - 1) / 2].min, min);
//                }
//
//                if (input[(right - 1) / 2].add != 0) {
//                    min += input[(right - 1) / 2].add;
//                }
//            }
//
//            right = (right - 1) / 2 - 1;
//        }
//
//        if ((right - 1) / 2 - (left - 1) / 2 == 0) {
//            if (input[(right - 1) / 2].set != null) {
//                min = (long) input[(right - 1) / 2].set;
//            } else {
//                min = Long.min((long) input[(right - 1) / 2].min, min);
//            }
//        } else {
//            if (input[(right - 1) / 2].set != null) {
//                min = (long) input[(right - 1) / 2].set;
//            } else {
//                min = Long.min((long) input[(right - 1) / 2].min, min);
//            }
//
//            if (input[(left - 1) / 2].set != null) {
//                min = (long) input[(left - 1) / 2].set;
//            } else {
//                min = Long.min((long) input[(left - 1) / 2].min, min);
//            }
//        }
//
//        return min;
//    }

    public Object min(Object a, Object b) {
        if (a == null && b == null) {
            return null;
        } else if (a == null && b != null) {
            return b;
        } else if (a != null && b == null) {
            return a;
        }

        return (Long) a < (Long) b? a:b;
    }

    public Object fillAdd(Object a, Object b) {
        if (a == null && b == null) {
            return null;
        } else {
            return 0l;
        }
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public void showTree() {
        System.out.println(input[0].min);
        for (int i = 0; i < 2; i++) {
            System.out.print(input[i + 1].min + " ");
        }
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.print(input[i + 3].min + " ");
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.print(input[i + 7].min + " ");
        }
        System.out.println();
        for (int i = 0; i < 16; i++) {
            System.out.print(input[i + 15].min + " ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) throws IOException {
        new С().start();
    }

}
