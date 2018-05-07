package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by nikitos on 08.12.17.
 */
public class H {

    public StreamTokenizer t;
    public int[][] connect;
    public int[] answer;
    public int n;
    public ArrayList<ArrayList<Integer>> aList = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> bList = new ArrayList<>();

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        n = nextInt();
        int inputs[] = new int[n];
        answer = new int[n];

        int kor = 0;
        for (int i = 0; i < n; i++) {
            inputs[i] = nextInt();
            if (inputs[i] == 0) kor = i;
        }

        for (int i = 0; i < n; i++) {
            aList.add(new ArrayList<Integer>());
            for (int j = 0; j < n; j++) {
                if (i != j && inputs[j] != 0 && inputs[j] - 1 == i) {
                    aList.get(i).add(j);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            bList.add(new ArrayList<Integer>());
            for (int j = 0; j < aList.get(i).size(); j++) {
                for (int k = 0; k < aList.get(aList.get(i).get(j)).size(); k++) {
                    int x = aList.get(aList.get(i).get(j)).get(k);
                    bList.get(i).add(x);
                }
            }
        }

        //System.out.println(aList);
        //System.out.println(bList);

        System.out.println(getAnswer(kor));

    }

    public int getAnswer(int u) {
        if (answer[u] != 0) return answer[u];
        int children_sum = 0;
        int underchildren_sum = 0;
        for (int i = 0; i < aList.get(u).size(); i++) {
            children_sum += getAnswer(aList.get(u).get(i));
        }
        for (int i = 0; i < bList.get(u).size(); i++) {
            underchildren_sum += getAnswer(bList.get(u).get(i));
        }
        answer[u] = max(children_sum, 1 + underchildren_sum);
        return answer[u];

    }

    public int max(int a, int b) {
        return (a > b? a: b);
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }

}
