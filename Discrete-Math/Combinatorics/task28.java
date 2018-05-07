package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task28 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("nextmultiperm.in")));
        PrintWriter writer = new PrintWriter("nextmultiperm.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt();
        int[] inputsNext = new int[n];
        for (int i = 0; i < n; i++) {
            inputsNext[i] = nextInt();
        }

        ArrayList<Integer> aList = new ArrayList<>();

        int maxIndex = -1;
        for (int i = 0; i < n - 1; i++) {
            if (inputsNext[i] < inputsNext[i + 1]) maxIndex = i;
        }
        if (maxIndex != -1) {
            int moreThenNowIndex = maxIndex + 1;

            while (moreThenNowIndex < n - 1 && inputsNext[moreThenNowIndex + 1] > inputsNext[maxIndex]) moreThenNowIndex++;

            swap(inputsNext, maxIndex, moreThenNowIndex);

            for (int i = maxIndex + 1; i < n; i++) {
                aList.add(inputsNext[i]);
            }
            Collections.reverse(aList);
            if (aList.size() > 1) {
                for (int i = maxIndex + 1; i < n; i++) {
                    inputsNext[i] = aList.get(i - maxIndex - 1);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                inputsNext[i] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            writer.write(inputsNext[i] + " ");
        }

        writer.close();

    }

    public void swap(int[] arr, int a, int b) {
        int c = arr[a];
        arr[a] = arr[b];
        arr[b] = c;
    }

    public static void main(String[] args) throws IOException {
        new task28().start();
    }

}
