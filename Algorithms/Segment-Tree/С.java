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
 
public class C {
 
    public class Node {
        long value;
        boolean set;
        long add;
        int left;
        int right;
 
        public Node() {}
 
        public Node(long value, boolean set, long add, int left, int right) {
            this.value = value;
            this.set = set;
            this.add = add;
            this.left = left;
            this.right = right;
        }
    }
 
    public int k;
    public Node[] input;
    public long min;
 
    public void start() throws IOException {
//        BufferedReader buf =  new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");
 
        BufferedReader buf = new BufferedReader( new FileReader("rmq2.in"));
        PrintWriter writer = new PrintWriter("rmq2.out");
 
        int n = Integer.parseInt(buf.readLine());
        k = do2power(n);
 
        input = new Node[2 * k - 1];
 
        StringTokenizer st = new StringTokenizer(buf.readLine());
        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i] = new Node(Long.parseLong(st.nextToken()),
                    false,
                    0,
                    i,
                    i);
        }
 
        for (int i = k - 1 + n; i < 2 * k - 1; i++) {
            input[i] = new Node(Long.MAX_VALUE,
                    false,
                    0,
                    i,
                    i);
        }
 
        for (int i = k - 2; i >= 0; --i) {
            input[i] = new Node(Long.min(input[i * 2 + 1].value, input[i * 2 + 2].value),
                    false,
                    0,
                    input[i * 2 + 1].left,
                    input[i * 2 + 2].right);
        }
 
        String s;
        while ((s = buf.readLine()) != null) {
            st = new StringTokenizer(s);
            String now = st.nextToken();
 
            int left = Integer.parseInt(st.nextToken()) + k - 2;
            int right = Integer.parseInt(st.nextToken()) + k - 2;
 
            switch (now) {
                case "min":
                    min = Long.MAX_VALUE;
                    min(0, left, right);
                    writer.println(min);
                    break;
                case "add":
                    long add = Long.parseLong(st.nextToken());
                    add(0, add, left, right);
 
                    break;
                case "set":
                    long set = Long.parseLong(st.nextToken());
                    set(0, set, left, right);
                    break;
            }
        }
 
        /*  1 2 3 4 5 6 26 27 28 10
            1 22 22 22 22 6 26 27 28 10
            1 22 22 22 22 6 26 27 28 10
            1 22 22 22 22 6 33 34 28 10
            1 22 15 15 15 15 33 34 28 10
            1 22 15 15 15 15 33 34 28 10  */
 
        writer.close();
    }
 
    public void add(int i, long value, int left, int right) {
        if (2 * i + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                input[i].add += value;
            }
            return;
        }
 
        if (input[i].right < left || input[i].left > right) {
            return;
        }
 
        if (input[i].left >= left && input[i].right <= right) {
            input[i].add += value;
            return;
        }
 
        push(i);
 
        add(i * 2 + 1, value, left, right);
        add(i * 2 + 2, value, left, right);
 
        input[i].value = Long.min(
                input[2 * i + 1].value + input[2 * i + 1].add,
                input[2 * i + 2].value + input[2 * i + 2].add
        );
    }
 
    public void set(int i, long value, int left, int right) {
        if (2 * i + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                input[i].add = 0;
                input[i].value = value;
                input[i].set = true;
            }
            return;
        }
 
        if (input[i].right < left || input[i].left > right) {
            return;
        }
 
        if (input[i].left >= left && input[i].right <= right) {
            input[i].add = 0;
            input[i].value = value;
            input[i].set = true;
            return;
        }
 
        push(i);
 
        set(i * 2 + 1, value, left, right);
        set(i * 2 + 2, value, left, right);
 
        input[i].value = Long.min(
                input[2 * i + 1].value + input[2 * i + 1].add,
                input[2 * i + 2].value + input[2 * i + 2].add
        );
    }
 
    public void min(int i, int left, int right) {
        if (2 * i + 2 >= 2 * k - 1) {
            if (input[i].left >= left && input[i].right <= right) {
                min = Long.min(min, input[i].value + input[i].add);
            }
            return;
        }
 
        if (input[i].right < left || input[i].left > right) {
            return;
        }
 
        if (input[i].left >= left && input[i].right <= right) {
            min = Long.min(min, input[i].value + input[i].add);
            return;
        }
 
        push(i);
 
        min(i * 2 + 1, left, right);
        min(i * 2 + 2, left, right);
 
        input[i].value = Long.min(
                input[2 * i + 1].value + input[2 * i + 1].add,
                input[2 * i + 2].value + input[2 * i + 2].add
        );
    }
 
    public void push(int i) {
        if (input[i].set) {
            input[2 * i + 1].value = input[i].value;
            input[2 * i + 2].value = input[i].value;
            input[i].set = false;
            input[2 * i + 1].set = true;
            input[2 * i + 2].set = true;
            input[2 * i + 1].add = 0;
            input[2 * i + 2].add = 0;
        }
 
        if (input[i].add != 0) {
            input[2 * i + 1].add += input[i].add;
            input[2 * i + 2].add += input[i].add;
            input[i].add = 0;
        }
    }
 
    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }
 
        return k;
    }
 
    public static void main(String[] args) throws IOException {
        new C().start();
    }
 
}
