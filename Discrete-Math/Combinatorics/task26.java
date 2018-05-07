package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 10.12.17.
 */
public class task26 {

    public StringTokenizer t;

    public int nextInt() throws IOException {
        return Integer.parseInt(t.nextToken());
    }

    public String nextString() throws IOException {
        return t.nextToken();
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader("nextsetpartition.in"));
        PrintWriter writer = new PrintWriter("nextsetpartition.out");
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        ArrayList<ArrayList<Integer>> aList = new ArrayList<>();
        String s = "";
        while ((s = buf.readLine()) != null) {
            if (s.equals("") || s.equals("0 0")) continue;
            t = new StringTokenizer(s);

            int n = nextInt();
            int k = nextInt();

            for (int i = 0; i < k; i++) {
                aList.add(new ArrayList<Integer>());
                s = buf.readLine();
                t = new StringTokenizer(s);
                while (t.hasMoreTokens()) {
                    aList.get(i).add(nextInt());
                }
            }

            nextSep(aList);

            for (int i = 0; i < aList.size(); i++) {
                if (aList.get(i).size() == 0) {
                    aList.remove(i);
                    i--;
                }
            }

            writer.write(n + " " + aList.size() + "\n");
            for (int i = 0; i < aList.size(); i++) {
                for (int j = 0; j < aList.get(i).size(); j++) {
                    writer.write(aList.get(i).get(j) + " ");
                }
                writer.write("\n");
            }

            writer.write("\n");
            aList.clear();
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
        new task26().start();
    }

}
