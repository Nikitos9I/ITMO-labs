package HomeWork.DiscretMath;

import java.io.*;
import java.util.*;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D {

    public StreamTokenizer t;
    private boolean was[];

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
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("check.in")));
        PrintWriter writer = new PrintWriter("check.out");

        nextInt();
        int m = nextInt();

        List<TreeSet<Integer>> seq = new ArrayList<>();
        boolean checkEmpty = false;
        was = new boolean[1025];

        for (int i = 0; i < m; i++) {
            int num = nextInt();

            if (num == 0) {
                checkEmpty = true;
                continue;
            }

            seq.add(new TreeSet<>());

            int bMask = 0;
            for (int j = 0; j < num; j++) {
                int current = nextInt();
                bMask |= (1 << (current - 1));
                seq.get(seq.size() - 1).add(current);

                was[bMask] = true;
            }
        }

        writer.write(checkEmpty && checkStatementThree(seq) && checkStatementTwo()? "YES" : "NO");
        writer.close();
    }

    private boolean checkStatementTwo() {
        for (int i = 0; i < was.length; i++) {
            if (was[i]) {
                String radix2 = Integer.toString(i, 2);
                for (int j = 0; j < radix2.length(); j++) {
                    if (radix2.charAt(j) == '1') {
                        String newMask = radix2.substring(0, j) + "0" + radix2.substring(j + 1);

                        int newMaskInt = Integer.valueOf(newMask, 2);
                        if (newMaskInt == 0)
                            continue;

                        if (!was[newMaskInt]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean checkStatementThree(List<TreeSet<Integer>> list) {
        boolean ans = true;

        for (int i = 0; i < list.size(); i++)
            for (TreeSet<Integer> aList : list) {
                if (list.get(i).size() == aList.size())
                    continue;

                boolean check = true;

                int bMaskFir = 0;

                for (Integer it : list.get(i)) {
                    bMaskFir |= (1 << (it - 1));
                }

                int bMaskSec = 0;

                for (Integer it : aList) {
                    bMaskSec |= (1 << (it - 1));
                }

                if (list.get(i).size() > aList.size()) {
                    check = false;

                    String radix2 = Integer.toString(bMaskFir, 2);
                    for (int k = 0; k < radix2.length(); k++) {
                        if (radix2.charAt(k) == '1') {
                            if ((bMaskSec | (1 << (radix2.length() - k - 1))) != bMaskSec && was[bMaskSec | (1 << (radix2.length() - k - 1))]) {
                                check = true;
                                break;
                            }
                        }
                    }
                }

                ans &= check;
            }

        return ans;
    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
