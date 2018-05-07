package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by nikitos on 08.11.17.
 */
public class E {

    public StringTokenizer t;
    public int[] owner;
    public int[] min;
    public int[] max;
    public int[] r;
    public int[] num;

    public void start() throws IOException {
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader buf = new BufferedReader( new FileReader("dsu.in"));
        PrintWriter writer = new PrintWriter("dsu.out");

        int n = Integer.parseInt(buf.readLine());

        owner = new int[n + 1]; // представитель
        min = new int[n + 1]; // минимум
        max = new int[n + 1]; //максимум
        r = new int[n + 1];  //ранг
        num = new int[n + 1]; //количество
        for (int i = 1; i <= n; i++) {
            owner[i] = i;
            min[i] = i;
            max[i] = i;
            num[i] = 1;
            r[i] = 1;
        }

        String s;
        while ((s = buf.readLine()) != null) {
            t = new StringTokenizer(s);
            String input = t.nextToken();
            if (input.equals("union")) {
                int a = Integer.parseInt(t.nextToken());
                int b = Integer.parseInt(t.nextToken());
                union(a,b);
            } else if (input.equals("get")) {
                int a = Integer.parseInt(t.nextToken());
                writer.write(getAll(a) + "\n");
            }
        }

        writer.close();
    }

    public void union(int a, int b) {
        a = get(a);
        b = get(b);
        if (a == b)
            return;
        if (r[a] == r[b]) {
            r[a]++;
        }
        if (r[a] < r[b]) {
            min[owner[b]] = minimum(min[owner[a]],min[owner[b]]);
            max[owner[b]] = maximum(max[owner[a]],max[owner[b]]);
            num[owner[b]] = num[owner[a]] + num[owner[b]];
            owner[a] = b;
        }
        else {
            min[owner[a]] = minimum(min[owner[a]],min[owner[b]]);
            max[owner[a]] = maximum(max[owner[a]],max[owner[b]]);
            num[owner[a]] = num[owner[a]] + num[owner[b]];
            owner[b] = a;
        }
    }

    public int get(int x) {
        if (owner[x] != x) {
            owner[x] = get(owner[x]);
        }
        return owner[x];
    }

    public String getAll(int x) {
        StringBuilder sb = new StringBuilder();
        int owner = get(x);
        sb.append(min[owner] + " " + max[owner] + " " + num[owner]);
        return sb.toString();
    }

    public int minimum(int a, int b) {
        if (a < b) return a; else return b;
    }

    public int maximum(int a, int b) {
        if (a > b) return a; else return b;
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}
