package HomeWork.DiscretMath;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

    public StreamTokenizer t;

    private class Task implements Comparable<Task> {
        long time;
        long cost;

        private Task(long time, long cost) {
            this.time = time;
            this.cost = cost;
        }

        @Override
        public int compareTo(Task o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    private Node[] free;
    private int k;

    public class Node {
        Integer value;
        int left;
        int right;

        private Node(Integer value, int left, int right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

    }

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public long nextLong() throws IOException {
        t.nextToken();
        return (long) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("schedule.in"));
        PrintWriter writer = new PrintWriter("schedule.out");

        long n = Long.parseLong(buf.readLine());

        Task[] tasks = new Task[(int) n];
        for (int i = 0; i < n; i++) {
            String[] literals = buf.readLine().split(" ");
            tasks[i] = new Task(Long.parseLong(literals[0]), Long.parseLong(literals[1]));
        }

        k = do2power((int) n);

        free = new Node[2 * k - 1];

        for (int i = 0; i < 2 * k - 1; i++) {
            free[i] = new Node(0, 0, 0);
        }

        for (int i = k - 1; i < k - 1 + n; i++) {
            free[i].left = free[i].right = i;
        }

        for (int i = k - 1 + (int) n; i < 2 * k - 1; i++) {
            free[i].value = null;
        }

        for (int i = k - 2; i >= 0; --i) {
            free[i].left = free[i * 2 + 1].left;
            free[i].right = free[i * 2 + 2].right;
        }

        Arrays.sort(tasks, Collections.reverseOrder());

        long fine = 0;
        for (int i = 0; i < n; i++) {
            if (tasks[i].time == 0) {
                fine += tasks[i].cost;
                continue;
            }

            if (!update(tasks[i].time, n))
                fine += tasks[i].cost;
        }

        writer.println(fine);
        writer.close();
    }

    private boolean update(long currentTime, long n) {
        if (currentTime > n) {
            return true;
        }

        currentTime += k - 2;
//        sout(free);
        return request((int) currentTime);
    }

    private boolean request(int currentPosition) {
        if (free[currentPosition].value == 0) {
            free[currentPosition].value = 1;
            toParent(currentPosition);
            return true;
        }

        int lastPos;
        do {
            lastPos = currentPosition;
            currentPosition = (currentPosition - 1) / 2;
        } while (currentPosition != 0 && (free[2 * currentPosition + 1].value == 1 || lastPos == currentPosition * 2 + 1));

        if (free[2 * currentPosition + 1].value == 1 || lastPos == currentPosition * 2 + 1) {
            return false;
        } else {
            currentPosition = currentPosition * 2 + 1;

            while (free[currentPosition].left != free[currentPosition].right) {
                if (free[currentPosition * 2 + 2].value == 1) {
                    currentPosition = currentPosition * 2 + 1;
                } else {
                    currentPosition = currentPosition * 2 + 2;
                }
            }

            free[currentPosition].value = 1;
            toParent(currentPosition);

            return true;
        }
    }

    private void toParent(int i) {
        while (i != 0) {
            i = (i - 1) / 2;

            if (free[2 * i + 1].value == null && free[2 * i + 2].value == null) {
                free[i].value = null;
            } else if (free[2 * i + 2].value == null) {
                free[i].value = free[2 * i + 1].value;
            } else {
                free[i].value = free[2 * i + 1].value & free[2 * i + 2].value;
            }
        }
    }

    private void sout(Node[] arr) {
        for (Node anArr : arr) {
            System.out.print(anArr.value + " ");
        }
        System.out.println();
    }

    private int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
