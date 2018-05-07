package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nikitos on 16.12.17.
 */
public class J_with_DP {

    public StreamTokenizer t;
    public int n;
    public int res;
    public int now = 0;
    public int[][] inputs;
    public int[] towns;
    public int[][] dp;
    public int bConst = 1000000000;

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
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        n = nextInt();
        inputs = new int[n][n];
        dp = new int[n][(1 << (n))];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inputs[i][j] = nextInt();
            }
        }

        towns = new int[n];
        res = begin();
        System.out.println(res);

        getAns2();

    }

    public int begin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (1 << n); j++) {
                dp[i][j] = bConst;
            }
        }
        for (int i = 0; i < n; i++) {
            dp[i][0] = 0;
        }

        res = bConst;
        for (int i = 0; i < n; i++) {
            int z = find(i, (1 << n) - 1 - (1 << i));
            if (z < res) {
                res = z;
                now = i;
            }
        }
        return res;
    }

    public int find(int i, int mask) {
        if (dp[i][mask] != bConst) return dp[i][mask];

        for (int j = 0; j < n; j++) {
            if (((1 << j) & mask) != 0) {
                dp[i][mask] = min(dp[i][mask], find(j, mask ^ (1 << j)) + inputs[i][j]);

            }
        }

        return dp[i][mask];
    }

    public void getAns2() {
        int mask = (1 << n) - 1 - (1 << now);
        System.out.print((now + 1) + " ");
        int i = now;
        for(int q = 1; q < n; ++q){
            for(int j = 0; j < n; ++j){
                if(((1 << j) & mask) != 0){
                    if(dp[j][mask ^ (1 << j)] + inputs[i][j] == dp[i][mask]){
                        System.out.print((j + 1) + " ");
                        i = j;
                        mask = (mask ^ (1 << j));
                        break;
                    }
                }
            }
        }
    }

    public void getAns1() {
        ArrayList<Integer> aList = new ArrayList<>();
        int last_mask = (1 << n) - 1;
        int last_one = 0;
        while (last_mask != 0) {
            for (int i = 0; i < n; i++) {
                if (((last_mask >> i) & 1) != 0 && dp[last_one][last_mask] == dp[i][last_mask ^ (1 << i)] + inputs[last_one][i]) {
                    aList.add(i + 1);
                    last_one = i;
                    last_mask = last_mask ^ (1 << i);
                }
            }
        }
        System.out.println(aList);
    }

    public void getAns() {
        ArrayList<Integer> aList = new ArrayList<>();
        int i = 0;
        int mask = 0;
        //aList.add(1);
        //System.out.println(dp[i][mask] + " dp[i][mask]");
        while (mask != (1 << n) - 1) {
            for (int j = 0; j < n; j++) {
                System.out.println(dp[i][mask] + " " + dp[j][mask^(1<<j)] + " " + Integer.toBinaryString(mask) + " " + Integer.toBinaryString(1 << j));
                //System.out.println(mask & (1 << j));
                //if (inputs[i][j] != 0 && (mask & (1 << j)) == 0 && dp[i][mask] + inputs[i][j] == dp[j][mask | (1 << j)]) {
                if (inputs[i][j] != 0 && (mask & (1 << j)) == 0 && dp[j][mask | (1 << j)] == res - inputs[i][j]) {
                    System.out.println("+");
                    aList.add(j + 1);
                    i = j;
                    //mask += (1 << i);
                    res -= inputs[i][j];
                }
            }
        }
        /*int[] oops = new int[n];
        for (int j = 0; j < aList.size(); j++) {
            oops[aList.get(j) - 1]++;
        }
        int number = 0;
        for (int j = 0; j < n; j++) {
            if (oops[j] == 0) {
                number = j;
                break;
            }
        }
        int min = Integer.MAX_VALUE;
        int position = 0;
        for (int j = 0; j < aList.size() - 1; j++) {
            if (inputs[aList.get(j) - 1][number] + inputs[number][aList.get(j + 1) - 1] < min) {
                position = j;
                min = inputs[aList.get(j) - 1][number] + inputs[number][aList.get(j + 1) - 1];
            }
        }
        if (aList.size() > 1 && inputs[number][aList.get(0) - 1] + inputs[aList.get(0) - 1][aList.get(1) - 1] < min) {
            position = -1;
            min = inputs[number][aList.get(0) - 1] + inputs[aList.get(0) - 1][aList.get(1) - 1];
        }
        if (aList.size() > 1 && inputs[aList.get(aList.size() - 2) - 1][aList.get(aList.size() - 1) - 1] + inputs[aList.get(aList.size() - 1) - 1][number] < min) {
            position = aList.size();
        }

        if (position == -1) {
            aList.add(0,number + 1);
        } else if (position == aList.size()) {
            aList.add(number + 1);
        } else {
            aList.add(position,number + 1);
        }*/

        //System.out.println(aList);
        //aList.remove(aList.size() - 1);
        //Collections.reverse(aList);
        /*int pos = 0;
        for (int j = 0; j < aList.size(); j++) {
            if (aList.get(i) == 1) {
                pos = i;
            }
        }
        ArrayList<Integer> bList = new ArrayList<>();
        for (int j = pos + 1; j < aList.size(); j++) {
            bList.add(aList.get(j));
        }
        for (int j = 0; j <= pos; j++) {
            bList.add(aList.get(j));
        }*/
        for (int j = 0; j < aList.size(); j++) {
            System.out.print(aList.get(j) + " ");
        }
    }

    public int exp(int n) {
        int a = 1;
        for (int i = 0; i <= n; i++) {
            a *= 2;
        }
        return a;
    }

    public int min(int a, int b) {
        return a<b? a:b;
    }

    public static void main(String[] args) throws IOException {
        new J_with_DP().start();
    }

}
