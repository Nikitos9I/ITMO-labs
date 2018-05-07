package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nikitos on 08.11.17.
 */
public class F {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        PrintWriter writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("formation.in")));
        //PrintWriter writer = new PrintWriter("formation.out");

        int n = nextInt(), m = nextInt();
        List<Integer> lList = new ArrayList<>(n);
        lList.add(0);
        lList.add(1);
        lList.add(0);

        for (int i = 0; i < m; i++) {
            String input = nextString();
            if (input.equals("left")) {
                int I = nextInt();
                int J = nextInt();
                lList.add(lList.indexOf(J),I);
            }
            if (input.equals("right")) {
                int I = nextInt();
                int J = nextInt();
                lList.add(lList.indexOf(J) + 1,I);
            }
            if (input.equals("leave")) {
                int I = nextInt();
                lList.remove(lList.indexOf(I));
            }
            if (input.equals("name")) {
                int I = nextInt();
                writer.write(lList.get(lList.indexOf(I) - 1) + " " + lList.get(lList.indexOf(I) + 1) + "\n");
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }

}
