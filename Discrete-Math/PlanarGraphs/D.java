package HomeWork.DiscretMath;

import java.io.*;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D {

    public StreamTokenizer t;

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
//        BufferedReader buf =  new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("planaritycheck.in"));
        PrintWriter writer = new PrintWriter("planaritycheck.out");

        int n = Integer.parseInt(buf.readLine());

        for (int i = 0; i < n; i++) {
            String in = buf.readLine();
            wasASFDASFA = false;

            if (in.length() == 0) {
                writer.println("YES");
                continue;
            }

            int[] arr = new int[in.length()];
            for (int j = 0; j < in.length(); j++) {
                arr[j] = in.charAt(j) - '0';
            }

            if (guarantyPlanar(in)) {
                writer.println("YES");
                continue;
            } else if (in.equals("1111111111")) {
                writer.println("NO");
                continue;
            }

            int[][] matrix = new int[1][1];
            try {
                matrix = updateMatrix(getMatrix(arr));
            } catch (Exception e) {
                System.out.println("PROSTO! IDI NAHUY");
                keepKalmAndTL();
            }
//            soutMatrix(6, matrix);
            boolean isAns = false;
            int[] permutation = new int[]{0, 1, 2, 3, 4, 5};

            do {
                try {
                    checkKFiveNorm(matrix, permutation);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("КАК ЖЕ ТЫ МЕНЯ ЗАЕБАЛА, СУКА ЕБАНАЯ!!!!!!!!!!!!!!!!!!!!!");
                    keepKalmAndTL();
                    System.exit(0);
                }

                if (!wasASFDASFA && checkKThreeThree(matrix, permutation) || /*checkKFive(getNewMatrixForK5(matrix, permutation))*/
                        checkKFiveNorm(matrix, permutation)) {
                    writer.println("NO");
                    isAns = true;
                    break;
                }
            } while (nextPermutation(permutation));

            if (!isAns)
                writer.println("YES");
        }

        writer.close();
    }

    private int[][] getNewMatrixForK5(int[][] matrix, int[] arr) {
        int[][] newMatrix = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                newMatrix[i][j] = matrix[arr[i]][arr[j]];
            }
        }

        return newMatrix;
    }

    private int[][] getNewMatrixForK33(int[][] matrix, int[] arr) {
        int[][] newMatrix = new int[6][6];
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                newMatrix[i][j] = matrix[arr[i]][arr[j]];
            }
        }

        return newMatrix;
    }

    private boolean wasASFDASFA = false;

    private int[][] updateMatrix(int[][] matrix) {
        try {
            for (int i = 0; i < 6; i++) {
                int degree = 0;
                int from = -1;
                int to = -1;

                for (int j = 0; j < 6; j++) {
                    if (matrix[i][j] == 1) {
                        ++degree;

                        if (degree == 1) {
                            from = j;
                        }

                        if (degree == 2) {
                            to = j;
                        }
                    }
                }

                if (degree == 2) {
                    wasASFDASFA = true;
                    int[][] newMatrix = new int[6][6];
                    matrix[from][to] = 1;
                    matrix[to][from] = 1;

                    int in1 = 0;
                    for (int j = 0; j < 6; j++) {
                        int in2 = 0;
                        if (i != j)

                        for (int k = 0; k < 6; k++) {
                            if (k != i) {
                                newMatrix[j][k] = matrix[j][k];
                            }
                            in2++;
                        }
                        in1++;
                    }

                    return newMatrix;
                }
            }
        } catch (Exception e) {
            System.out.println("YA IDU ISKAT, EBANINA");
            keepKalmAndTL();
            System.exit(0);
        }

        return matrix;
    }

    private boolean guarantyPlanar(String st) {
        if (st.length() < 10)
            return true;

        return st.length() == 10 && !st.equals("1111111111");
    }

    private int[][] getMatrix(int[] st) {
        try {
            int[][] matrix = new int[6][6];
            //0
            //00
            //111
            //1110
            //11100

            int o = 0;
            for (int i = 1; i < 6; i++) {
                for (int j = 0; j < i; j++) {
                    matrix[i][j] = st[o];
                    matrix[j][i] = st[o++];
                }
            }

            return matrix;
        } catch (Exception e) {
            System.out.println("OSTALAS TOLKO TI, EBANINA");
            keepKalmAndTL();
            System.exit(0);
            return null;
        }
    }

    private boolean nextPermutation(int[] p) {
        try {
            for (int a = p.length - 2; a >= 0; --a)
                if (p[a] < p[a + 1])
                    for (int b = p.length - 1; ; --b)
                        if (p[b] > p[a]) {
                            int t = p[a];
                            p[a] = p[b];
                            p[b] = t;
                            for (++a, b = p.length - 1; a < b; ++a, --b) {
                                t = p[a];
                                p[a] = p[b];
                                p[b] = t;
                            }
                            return true;
                        }
            return false;
        } catch (Exception e) {
            System.out.println("ESLI OSHIBKA TUT, TI PROSTO EBLAN NIKITA");
            keepKalmAndTL();
            System.exit(0);
            return false;
        }
    }

    private boolean checkKFive(int[][] matrix) {
        boolean ans = true;
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 5; j++) {
                ans &= (matrix[i][j] == 1);
            }
        }

        return ans;
    }

    private boolean checkKFiveNorm(int[][] matrix, int [] arr) {
        boolean ans = true;
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                ans &= (matrix[arr[i]][arr[j]] == 1);
            }
        }

        return ans;
    }

    private boolean checkKThreeThree(int[][] matrix, int arr[]) {
        boolean ans = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                ans &= (matrix[arr[i]][arr[j]] == 1);
            }
        }

        return ans;
    }

    private void soutMatrix(int n, int[][] arr ) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void keepKalmAndTL() {
        for (long i = 0; i < 1000000000000000000L; i++) {
            System.out.println(i+1);
        }
    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
