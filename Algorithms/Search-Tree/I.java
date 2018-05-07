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

public class I {

    private class Node {
        boolean isCycle;
        boolean isReverse;

        int value;
        int priority;
        int size;

        Node leftChild;
        Node rightChild;
        Node parent;

        private Node(int value) {
            this.value = value;
            this.priority = generate();
            this.size = 1;
        }

        private int generate() {
            long res = new Random().nextInt();

            for (int i = 0; i < 3; i++) {
                res <<= 16;
                res += new Random().nextInt();
            }

            return (int) Math.abs(res % Integer.MAX_VALUE);
        }
    }

    private class Pair {
        Node first;
        Node second;

        private Pair(Node left, Node right) {
            this.first = left;
            this.second = right;
        }
    }

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    private void start() throws IOException {
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("roads.in"));
        PrintWriter writer = new PrintWriter("roads.out");

        StringTokenizer st = new StringTokenizer(buf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        Node[] towns = new Node[n];

        for (int i = 0; i < n; i++) {
            towns[i] = new Node(i + 1);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(buf.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;

            buildRoad(towns[from], towns[to]);
        }

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(buf.readLine());
            char in = st.nextToken().charAt(0);

            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;

            switch (in) {
                case '+':
                    buildRoad(towns[from], towns[to]);
                    break;
                case '-':
                    removeRoad(towns[from], towns[to]);
                    break;
                case '?':
                    writer.println(from == to? 0: getAnswer(towns[from], towns[to]));
                    break;
            }
        }

        writer.close();
    }

    private void makeFather(Node current, Node currentChild) {
        if (current == null) {
            return;
        }

        if (currentChild != null) {
            currentChild.parent = current;
        }
    }

    private int getAnswer(Node from, Node to) {
        Node parentFrom = root(from);
        Node parentTo = root(to);

        if (parentFrom != parentTo) {
            return -1;
        }

        int indexFrom = getCurrentIndex(from);
        int indexTo = getCurrentIndex(to);

        int min = Integer.min(indexFrom, indexTo);
        int max = Integer.max(indexFrom, indexTo);

        if (parentFrom.isCycle) {
            return Integer.min(max - min - 1, parentFrom.size - 1 - max + min);
        } else {
            return max - min - 1;
        }
    }

    private void buildRoad(Node from, Node to) {
        Node parentFrom = root(from);
        Node parentTo = root(to);

        if (parentFrom.equals(parentTo)) {
            parentFrom.isCycle = true;
        } else {
            int indexFrom = getCurrentIndex(from);
            int indexTo = getCurrentIndex(to);

            if (indexFrom == 0) {
                if (indexTo == 0) {
                    parentFrom.isReverse = !parentFrom.isReverse;

                    merge(parentFrom, parentTo);
                } else {
                    merge(parentTo, parentFrom);
                }
            } else {
                if (indexTo == 0) {
                    merge(parentFrom, parentTo);
                } else {
                    parentTo.isReverse = !parentTo.isReverse;

                    merge(parentFrom, parentTo);
                }
            }
        }
    }

    private int getCurrentIndex(Node current) {
        int ans;

        if (current.leftChild == null) {
            ans = 1;
        } else {
            ans = current.leftChild.size + 1;
        }

        if (current.isReverse) {
            ans = current.size - ans + 1;
        }

        while (current.parent != null) {
            if (current.parent.isReverse) {
                ans = current.size - ans + 1;
                reverse(current.parent);
                continue;
            }

            if (current == current.parent.rightChild) {
                if (current.parent.leftChild == null) {
                    ans += 1;
                } else {
                    ans += current.parent.leftChild.size + 1;
                }
            }

            current = current.parent;
        }

        return ans - 1;
    }

    private void removeRoad(Node from, Node to) {
        if (from == to) {
            return;
        }

        Node root = root(from);

        if (root.isCycle) {
            root.isCycle = false;
            int indexFrom = getCurrentIndex(from);
            int indexTo = getCurrentIndex(to);

            if (Integer.min(indexFrom, indexTo) == 0 && Integer.max(indexFrom, indexTo) == root.size - 1) {
                return;
            }

            Pair now = split(root, Integer.min(indexFrom, indexTo) + 1);
            merge(now.second, now.first);

            Node newRoot = root(now.first);
            if (newRoot != null) {
                newRoot.isCycle = false;
            }
        } else {
            int indexFrom = getCurrentIndex(from);
            int indexTo = getCurrentIndex(to);

            Pair now = split(root, Integer.min(indexFrom, indexTo) + 1);

            if (now.first != null) {
                now.first.isCycle = false;
            }

            if (now.second != null) {
                now.second.isCycle = false;
            }
        }
    }

    private void update(Node current) {
        if (current == null) {
            return;
        }

        current.size = 1;
        if (current.leftChild != null) {
            current.size += current.leftChild.size;
            current.leftChild.parent = current;
//            makeFather(current, current.leftChild);
        }

        if (current.rightChild != null) {
            current.size += current.rightChild.size;
            current.rightChild.parent = current;
//            makeFather(current, current.rightChild);
        }
    }

    private void reverse(Node current) {
        if (current == null || !current.isReverse) {
            return;
        }

        Node swap = current.leftChild;
        current.leftChild = current.rightChild;
        current.rightChild = swap;

        if (current.leftChild != null) {
            current.leftChild.isReverse = !current.leftChild.isReverse;
        }

        if (current.rightChild != null) {
            current.rightChild.isReverse = !current.rightChild.isReverse;
        }

        update(current);

        current.isReverse = false;
    }

    private Node merge(Node left, Node right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        reverse(left);
        reverse(right);

        if (left.priority > right.priority) {
            left.rightChild = merge(left.rightChild, right);

            update(left);

            return left;
        } else {
            right.leftChild = merge(left, right.leftChild);

            update(right);

            return right;
        }
    }

    private Pair split(Node current, int value) {
        if (current == null) {
            return new Pair(null, null);
        } else {
            reverse(current);

            int index;

            if (current.leftChild == null) {
                index = 0;
            } else {
                index = current.leftChild.size;
            }
            if (index >= value) {
                Pair now = split(current.leftChild, value);

                if (current.leftChild != null)
                    current.leftChild.parent = null;
//                makeFather(null, current.leftChild);

                current.leftChild = now.second;
                if (current.leftChild != null) current.leftChild.parent = current;
//                makeFather(current, current.leftChild);

                update(current);
                update(now.first);

                now.second = current;
                return now;
            } else {
                Pair now = split(current.rightChild, value - index - 1);
                if (current.rightChild != null)
                    current.rightChild.parent = null;
//                makeFather(null, current.rightChild);

                current.rightChild = now.first;
                if (current.rightChild != null) current.rightChild.parent = current;
//                makeFather(current, current.rightChild);

                update(current);
                update(now.second);

                now.first = current;
                return now;
            }
        }
    }

    private Node root(Node current) {
        if (current.parent != null) {
            return root(current.parent);
        } else {
            return current;
        }
    }

    public static void main(String[] args) throws IOException {
        new I().start();
    }

}