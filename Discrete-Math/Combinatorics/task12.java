package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 28.11.17.
 */
public class task12 {

    public StreamTokenizer t;
    public PrintWriter writer;
    public int n;
    public int k;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("part2sets.in")));
        writer = new PrintWriter("part2sets.out");

        n = nextInt();
        k = nextInt();

        ArrayList<ArrayList<Integer>> aList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> bList = new ArrayList<>();
        for (int i = 0; i < k - 1; i++) {
            ArrayList<Integer> neW = new ArrayList<>();
            neW.add(i + 1);
            aList.add(neW);
            ArrayList<Integer> feW = new ArrayList<>();
            feW.add(i + 1);
            bList.add(feW);
        }

        aList.add(new ArrayList<>());
        bList.add(new ArrayList<>());
        for (int i = k; i <= n; i++) {
            aList.get(aList.size() - 1).add(i);
            bList.get(bList.size() - 1).add(i);
        }

        boolean f = false;
        while (!f) {

            nextSep(aList);

            for (int i = 0; i < aList.size(); i++) {
                if (aList.get(i).size() == 0) {
                    aList.remove(i);
                    i--;
                }
            }

            if (aList.size() != k) continue;

            for (int i = 0; i < aList.size(); i++) {
                if (aList.get(i).equals(bList.get(i))) {
                    f = true;
                } else {
                    f = false;
                }
            }

            for (int i = 0; i < aList.size(); i++) {
                for (int j = 0; j < aList.get(i).size(); j++) {
                    writer.write(aList.get(i).get(j) + " ");
                }
                writer.write("\n");
            }

            writer.write("\n");
        }

        writer.close();

    }

    public ArrayList<ArrayList<Integer>> nextSep(ArrayList<ArrayList<Integer>> aList) {
        ArrayList<Integer> elem = new ArrayList<>();
        boolean exit = false;
        for (int i = aList.size() - 1; i >= 0 ; i--) {
            int max = 1000000000;
            int maxIndex = 0;
            for (int k = 0; k < elem.size(); k++) {
                maxIndex = elem.get(k) > aList.get(i).get(aList.get(i).size() - 1) && elem.get(k) < max? k: maxIndex;
                max = elem.get(k) > aList.get(i).get(aList.get(i).size() - 1) && elem.get(k) < max? elem.get(k): max;
            }
            if (elem.size() > 0 && max > aList.get(i).get(aList.get(i).size() - 1) && max != 1000000000) {
                aList.get(i).add(max);
                elem.remove(maxIndex);
                break;
            }
            for (int j = aList.get(i).size() - 1; j >= 0 ; j--) {
                if (j > 0 && elem.size() > 0 && elem.get(elem.size() - 1) > aList.get(i).get(j)) {
                    int maxN = 1000000000;
                    int maxIndexN = 0;
                    for (int k = 0; k < elem.size(); k++) {
                        maxIndexN = elem.get(k) > aList.get(i).get(j) && elem.get(k) < maxN? k: maxIndexN;
                        maxN = elem.get(k) > aList.get(i).get(j) && elem.get(k) < maxN? elem.get(k): maxN;
                    }
                    int c = aList.get(i).get(j);
                    aList.get(i).set(j, maxN);
                    elem.set(maxIndexN, c);
                    exit = true;
                    break;
                }
                elem.add(aList.get(i).get(j));
                aList.get(i).remove(j);
            }
            if (exit) break;
        }

        Collections.sort(elem);
        for (int i = 0; i < elem.size(); i++) {
            ArrayList<Integer> adding = new ArrayList<>();
            adding.add(elem.get(i));
            aList.add(adding);
        }

        return aList;
    }

    public static void main(String[] args) throws IOException {
        new task12().start();
    }

}
