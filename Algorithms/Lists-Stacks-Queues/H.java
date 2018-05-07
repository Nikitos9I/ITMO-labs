package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 11.11.17.
 */
public class H {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("saloon.in")));
        PrintWriter writer = new PrintWriter("saloon.out");

        int n = nextInt();
        int currentTime = 0;
        ArrayDeque<int[]> aDeq = new ArrayDeque<>();
        aDeq.addLast(new int[]{nextInt(), nextInt(), nextInt()});
        currentTime = aDeq.peek()[0] * 60 + aDeq.peek()[1] + 20;
        writer.write((currentTime / 60) + " " + (currentTime % 60) + "\n");

        for (int i = 1; i < n; i++) {
            aDeq.addLast(new int[]{nextInt(), nextInt(), nextInt()});
            if (aDeq.peekLast()[0]*60 + aDeq.peekLast()[1] + aDeq.peekLast()[2]*20 >= currentTime) {
                System.out.println(i);
                aDeq.pollFirst();
                currentTime = max(currentTime,aDeq.peekLast()[0]*60 + aDeq.peekLast()[1]) + 20;
                writer.write((currentTime / 60) + " " + (currentTime % 60) + "\n");
            } else {
                writer.write(aDeq.peekLast()[0] + " " + aDeq.peekLast()[1] + "\n");
                aDeq.pollLast();
            }
        }

        writer.close();

    }

    public int max(int a, int b) {
        return (a>b?a:b);
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }

}
