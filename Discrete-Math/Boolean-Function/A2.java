package HomeWork.Discret_math.lab1.lab2;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 10.11.17.
 */
public class A2 {

    private String firstMin, secondMin;
    private int firstMinValue, secondMinValue, trueCount, codeLength = 0;
    private Map<String,Integer> often;
    private Map<String,Boolean> freedom;
    private Map<String,String> older;
    private StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public static void main(String[] args) throws IOException {
        new A2().start();
    }

    private void start() throws IOException {
        often = new HashMap<String,Integer>();
        freedom = new HashMap<String,Boolean>();
        older = new HashMap<String,String>();

        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        t = new StreamTokenizer( new BufferedReader( new FileReader("huffman.in")));

        int n = nextInt();

        for (int i = 0; i < n; i++) {
            often.put("key" + (i + 1), nextInt());
            freedom.put("key" + (i + 1), true);
            older.put("key" + (i + 1), "");
        }

        trueCount = often.size();
        buildtree(often,freedom);

        for (String key : older.keySet()) {
            codeLength += older.get(key).length() * often.get(key);
        }

        //FileOutputStream output = new FileOutputStream("arrange.out.txt");
        FileOutputStream output = new FileOutputStream("huffman.out");
        output.write((codeLength+"").getBytes());
        output.close();
    }

    private void min(Map<String,Integer> fq, Map<String,Boolean> f) {
        firstMin = null;
        secondMin = null;
        int min = 1000000001;
        for (String key : fq.keySet()) {
            if (fq.get(key) < min && f.get(key)) {
                min = fq.get(key);
                firstMin = key;
                firstMinValue = min;
            }
        }
        min = 1000000001;
        for (String key : fq.keySet()) {
            if (fq.get(key) < min && !key.equals(firstMin) && f.get(key)) {
                min = fq.get(key);
                secondMin = key;
                secondMinValue = min;
            }
        }
    }

    private void buildtree(Map a, Map b) {
        while (trueCount != 1) {
            min(a, b);
            a.put(firstMin + secondMin, firstMinValue + secondMinValue);
            b.put(firstMin + secondMin, true);
            b.put(firstMin, false);
            b.put(secondMin, false);
            trueCount--;
            for (String key : older.keySet()) {
                if (firstMin.contains(key)) {
                    older.put(key, "0" + older.get(key));
                } else if ((secondMin != null) && (secondMin.contains(key))) {
                    older.put(key, "1" + older.get(key));
                }
            }
        }
    }
}
