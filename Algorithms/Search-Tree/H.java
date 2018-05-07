package HomeWork.Algoritms.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class H {

    private final int[] symbols = new int[32];
    private final int[] generatedKeys = new int[60007];

    public class Node {
        int shift = 0;
        int lets;
        int let;

        int left_border;
        int value;

        int index;
        int number;
        char letter;
        int prior;

        Node left = null;
        Node right = null;
        Node parent = null;

        public Node(int a, int b, char l, int i) {
            index = a;
            left_border = index;

            number = b;
            value = number;

            letter = l;
            prior = i;

            let = getBitFromInt(letter);
            lets = let;
        }
    }

    private class Pair {
        Node first;
        Node second;

        private Pair(Node l, Node r) {
            first = l;
            second = r;
        }
    }

    private Node root;

    private int getBitFromInt(char a) {
        return symbols[a - 'a'];
    }

    private int lb(Node current) {
        return current.index + current.shift;
    }

    private int rb(Node current) {
        return current.index + current.number - 1 + current.shift;
    }

    private void correct_relations(Node current) {
        if (current.left != null)
            current.left.parent = current;
        if (current.right != null)
            current.right.parent = current;
    }

    private void correct_lets(Node current) {
        current.lets = (current.left == null ? 0 : current.left.lets) | (current.right == null ? 0 : current.right.lets) | current.let;
    }

    private int leftBorder(Node current) {
        return current.left_border + current.shift;
    }

    private int rightBorder(Node current) {
        return current.left_border + current.shift + current.value - 1;
    }

    private void correct_borders(Node current) {
        if (current.left != null) {
            current.left_border = leftBorder(current.left);
        } else {
            current.left_border = current.index;
        }

        current.value = current.number + (current.left == null ? 0 : current.left.value) + (current.right == null ? 0 : current.right.value);
    }

    private void correct_shifts(Node current) {
        if (current.shift == 0) {
            return;
        }

        if (current.left != null) {
            current.left.shift += current.shift;
        }

        if (current.right != null) {
            current.right.shift += current.shift;
        }

        current.index += current.shift;
        current.shift = 0;
        correct_borders(current);
    }

    private void refresh(Node current) {
        if (current == null) {
            return;
        }

        correct_relations(current);
        correct_shifts(current);
        correct_lets(current);
        correct_borders(current);
    }


    private void refresh_lets(Node current) {
        if (current == null) {
            return;
        }

        refresh(current);
        refresh_lets(current.parent);
    }

    private Pair split(Node current, int key) {
        if (current == null) {
            return new Pair(null, null);
        }

        refresh(current);

        if (key > current.index) {
            Pair t = split(current.right, key);
            current.right = t.first;

            refresh_lets(current);

            return new Pair(current, t.second);
        } else {
            Pair t = split(current.left, key);
            current.left = t.second;

            refresh_lets(current);

            return new Pair(t.first, current);
        }
    }

    private Node merge(Node left, Node right) {
        refresh(left);
        refresh(right);

        if (right == null)
            return left;
        if (left == null)
            return right;

        if (left.prior > right.prior) {
            left.right = merge(left.right, right);

            refresh_lets(left);

            return left;
        } else {
            right.left = merge(left, right.left);

            refresh_lets(right);

            return right;
        }
    }

    private void insert(int index, int number, char letter, int i) {
        if (root == null) {
            root = new Node(index, number, letter, generatedKeys[i]);
            refresh(root);
            return;
        }

        Pair nodes = split(root, index);
        Node danger = nodes.first;
        if (danger != null) {
            while (danger.right != null) {

                refresh(danger);

                danger = danger.right;
            }

            refresh(danger);

            if (rb(danger) == index - 1) {
                if (nodes.second != null) {
                    nodes.second.shift += number;
                }

                root = merge(merge(nodes.first, new Node(index, number, letter, generatedKeys[i])), nodes.second);
            } else {
                Node temp = new Node(index,danger.number - (index - lb(danger)), danger.letter, generatedKeys[i + 1]);

                danger.number -= temp.number;
                Node left = merge(nodes.first, new Node(index, number, letter, generatedKeys[i]));
                Node right = merge(temp, nodes.second);
                right.shift += number;

                root = merge(left, right);
            }
        } else {
            if (nodes.second != null) {
                nodes.second.shift += number;
            }

            root = merge(new Node(index, number, letter, generatedKeys[i]), nodes.second);
        }
    }

    private void remove(int l, int r) {
        Pair left = split(root, l);
        Pair right = split(left.second, r + 1);

        Node ldanger = left.first;
        Node rdanger = right.first;

        if (ldanger != null) {
            while (ldanger.right != null) {
                refresh(ldanger);

                ldanger = ldanger.right;
            }

            refresh(ldanger);

            ldanger.number -= Integer.min(r, rb(ldanger)) - l + 1;
        }

        if (rdanger != null) {
            while (rdanger.right != null) {
                refresh(rdanger);

                rdanger = rdanger.right;
            }

            refresh(rdanger);

            int diff = r - lb(rdanger) + 1;
            rdanger.number -= diff;
            rdanger.index += diff;

            if (rdanger.number > 0) {
                rdanger.left = null;
                right.second = merge(rdanger, right.second);
            }
        }

        if (right.second != null) {
            right.second.shift -= r - l + 1;
        }

        root = merge(left.first, right.second);
    }

    private int get(Node current, int left, int right, int last) {
        if (current == null || leftBorder(current) > right || rightBorder(current) < left) {
            return last;
        }

        refresh(current);

        if (leftBorder(current) >= left && rightBorder(current) <= right) {
            last |= current.lets;
            return last;
        } else {
            if (rb(current) >= right && right >= lb(current) || rb(current) >= left && left >= lb(current)
                    || right >= rb(current) && rb(current) >= left || right >= lb(current) && lb(current) >= left) {
                last |= current.let;
            }

            last |= get(current.left, left, right, last);
            last |= get(current.right, left, right, last);

            return last;
        }
    }

    private int generate() {
        long res = new Random().nextInt();

        for (int i = 0; i < 3; i++) {
            res <<= 16;
            res += new Random().nextInt();
        }

        return (int) Math.abs(res % Integer.MAX_VALUE);
    }

    public void start() throws IOException {
//        BufferedReader buf = new BufferedReader(new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader(new FileReader("log.in"));
        PrintWriter writer = new PrintWriter("log.out");

        symbols[0] = 1;
        for (int i = 1; i < 32; i++) {
            symbols[i] = symbols[i - 1] << 1;
        }

        int n = Integer.parseInt(buf.readLine());

        for (int i = 0; i < 2 * n; i++) {
            generatedKeys[i] = generate();
        }

        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(buf.readLine());

            String in = st.nextToken();
            int fVal = Integer.parseInt(st.nextToken());
            int sVal = Integer.parseInt(st.nextToken());

            switch (in) {
                case "+":
                    char letter = st.nextToken().charAt(0);
                    insert(fVal, sVal, letter, 2 * i);
                    break;
                case "?":
                    int num = get(root, fVal, sVal, 0);
                    int size = 0;

                    for (int j = 0; j < 32; j++) {
                        size += num % 2;
                        num >>= 1;
                    }

                    writer.println(size);
                    break;
                case "-":
                    remove(fVal, fVal + sVal - 1);
                    break;
            }
        }

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }
}