package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nikitos on 05.12.17.
 */
public class B {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt();
        int[] inputs = new int[n];

        for (int i = 0; i < n; i++) {
            inputs[i] = nextInt();
        }
        int[] d = new int[n];
        int[] parent = new int[n + 1];

        int i = -1;
        while (++i < n) {
            d[i] = 1;
            int j = -1;
            parent[i] = -1;
            while (++j < i) {
                if (inputs[j] < inputs[i]) {
                    parent[i] = (d[i] < 1 + d[j]? j: parent[i]);
                    d[i] = (d[i] < 1 + d[j]? 1 + d[j]: d[i]);
                }
            }
        }

        i = -1;
        int pos = 0;
        int max = d[0];
        while (++i < n) {
            pos = (max < d[i]? i:pos);
            max = (max < d[i]? d[i]:max);
        }

        System.out.println(max);
        
        ArrayList<Integer> aList = new ArrayList<>();
        for (;pos != -1;) {
            aList.add(inputs[pos]);
            pos = parent[pos];
        }

        Collections.reverse(aList);

        for (int j = 0; j < aList.size(); j++) {
            System.out.print(aList.get(j) + " ");
        }

    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
