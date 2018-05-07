package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task24 {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("nextperm.in")));
        PrintWriter writer = new PrintWriter("nextperm.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt();
        int[] inputsPrev = new int[n];
        int[] inputsNext = new int[n];
        for (int i = 0; i < n; i++) {
            inputsPrev[i] = nextInt();
            inputsNext[i] = inputsPrev[i];
        }

        ArrayList<Integer> aList = new ArrayList<>();

        int maxIndex = -1;
        for (int i = 0; i < n - 1; i++) {
            if (inputsNext[i] < inputsNext[i + 1]) maxIndex = i;
        }
        if (maxIndex != -1) {
            int moreThenNow = Integer.MAX_VALUE;
            int moreThenNowIndex = -1;
            for (int i = maxIndex + 1; i < n; i++) {
                if (inputsNext[i] > inputsNext[maxIndex] && inputsNext[i] < moreThenNow) {
                    moreThenNowIndex = i;
                    moreThenNow = inputsNext[i];
                }
            }
            if (moreThenNowIndex != -1) {
                swap(inputsNext, maxIndex, moreThenNowIndex);
            }
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
        aList.clear();


        maxIndex = -1;
        for (int i = 0; i < n - 1; ++i) {
            if (inputsPrev[i + 1] < inputsPrev[i]) maxIndex = i;
        }
        if (maxIndex != -1) {
            int maxAfterNow = -1;
            int maxAfterNowIndex = -1;
            for (int i = maxIndex + 1; i < n; i++) {
                if (inputsPrev[maxIndex] > inputsPrev[i] && inputsPrev[i] > maxAfterNow) {
                    maxAfterNow = inputsPrev[i];
                    maxAfterNowIndex = i;
                }
            }
            if (maxAfterNowIndex != -1) {
                swap(inputsPrev, maxIndex, maxAfterNowIndex);
            }
            for (int i = maxIndex + 1; i < n; i++) {
                aList.add(inputsPrev[i]);
            }
            Collections.reverse(aList);
            if (aList.size() > 1) {
                for (int i = maxIndex + 1; i < n; i++) {
                    inputsPrev[i] = aList.get(i - maxIndex - 1);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                inputsPrev[i] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            writer.write(inputsPrev[i] + " ");
        }
        writer.write("\n");
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
        new task24().start();
    }

}
