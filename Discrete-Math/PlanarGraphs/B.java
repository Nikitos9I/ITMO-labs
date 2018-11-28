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

public class B {

    public StreamTokenizer t;
    private final double EPS = 1e-11;

    private class Point implements Comparable<Point> {
        private double x;
        private double y;

        private int index;

        private Point() {
        }

        private Point(double x, double y, int index) {
            this.x = round(x);
            this.y = round(y);
            this.index = index;
        }

        private Point(Point other) {
            this.index = other.index;
            this.x = other.x;
            this.y = other.y;
        }

        @Override
        public int compareTo(Point o) {
            if (Math.abs(this.x - o.x) < EPS) {
                return Double.compare(this.y, o.y);
            }

            return Double.compare(this.x, o.x);
        }
    }

    private class Line {
        private int A, B, C, x1, y1, x2, y2;

        private Set<Point> intersectionPoints = new TreeSet<>();

        private Line(int a, int b, int c, int x1, int y1, int x2, int y2) {
            this.A = a;
            this.B = b;
            this.C = c;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private class Edge implements Comparable<Edge> {
        private Point from;
        private Point to;

        private double fi;

        private Edge(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        private Edge(Point from, Point to, double fi) {
            this.from = from;
            this.to = to;
            this.fi = fi;
        }


        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.fi, o.fi);
        }
    }

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
        try {
            t = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

            int n = nextInt();

            Line[] lines = new Line[n];

            for (int i = 0; i < n; i++) {
                int x1 = nextInt();
                int y1 = nextInt();
                int x2 = nextInt();
                int y2 = nextInt();

                lines[i] = getLine(x1, y1, x2, y2);
            }

            TreeSet<Point> pointsOfNewGraph = new TreeSet<>();
            List<List<Integer>> graph = new ArrayList<>();
            List<Set<Integer>> uniqGraph = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Point intersection = isIntersect(lines[i], lines[j]);

                    if (intersection != null) {
                        if (!pointsOfNewGraph.contains(intersection)) {
                            pointsOfNewGraph.add(intersection);
                            intersection.index = pointsOfNewGraph.size() - 1;
                        } else {
                            intersection = pointsOfNewGraph.ceiling(intersection);
                        }

                        lines[i].intersectionPoints.add(intersection);
                        lines[j].intersectionPoints.add(intersection);
                    }
                }
            }

//        for (Point tec : pointsOfNewGraph) {
//            System.out.println(tec.x + " " + tec.y);
//        }
//        System.out.println();

            // +

//        for (int i = 0; i < n; i++) {
//            for (Point tec : lines[i].intersectionPoints) {
//                System.out.print(tec.x + " : " + tec.y + "; ");
//            }
//            System.out.println();
//        }
//        System.out.println();

            // + (непонятно только, по нужным ли ссылкам там элементы лежат, или создались новые ссылки)

            List<Edge> edges = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                Iterator<Point> iterator = lines[i].intersectionPoints.iterator();
                Point from = iterator.next();

                for (int j = 1; j < lines[i].intersectionPoints.size(); j++) {
                    Point to = iterator.next();

                    edges.add(new Edge(from, to, Math.atan(lines[i].y1 * 1.0 / lines[i].x1)));
                    from = new Point(to);
                }
            }

//        for (Edge current : edges) {
//            System.out.println(current.from.x + " " + current.from.y + " " + current.from.index + "! : " + current.to.x + " " + current.to.y + " " + current.to.index + "!");
//        }
//        System.out.println();

            for (int i = 0; i < pointsOfNewGraph.size(); i++) {
                graph.add(new ArrayList<>());
                uniqGraph.add(new HashSet<>());
            }

            for (Edge edge : edges) {
                if (!uniqGraph.get(edge.from.index).contains(edge.to.index)) {
                    graph.get(edge.from.index).add(edge.to.index);
                    uniqGraph.get(edge.from.index).add(edge.to.index);
                }

                if (!uniqGraph.get(edge.to.index).contains(edge.from.index)) {
                    graph.get(edge.to.index).add(edge.from.index);
                    uniqGraph.get(edge.to.index).add(edge.from.index);
                }
            }

            for (int i = 0; i < graph.size(); ++i) {
                int finalI = i;
                graph.get(i).sort((v1, v2) -> {
                    Point root = null;
                    Point a = null;
                    Point b = null;

                    for (Point current : pointsOfNewGraph) {
                        if (current.index == finalI) {
                            root = current;
                        }

                        if (current.index == v1) {
                            a = current;
                        }

                        if (current.index == v2) {
                            b = current;
                        }
                    }

                    return Double.compare(getCorner(b, root.x, root.y), getCorner(a, root.x, root.y));
                });
            }

//        for (int i = 0; i < graph.size(); ++i) {
//            System.out.print(i + " : ");
//            for (Integer a : graph.get(i)) {
//                System.out.print(a + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();

            // +

            List<List<Boolean>> used = new ArrayList<>();

            for (int i = 0; i < graph.size(); i++) {
                used.add(new ArrayList<>());

                for (int j = 0; j < graph.get(i).size(); j++) {
                    used.get(i).add(false);
                }
            }

            dfs(graph, used, pointsOfNewGraph);

            if (ans.size() != 0) {
//            System.out.println(ans);
//            System.out.println();

                int count = ans.size() - 1;
                for (Double dd : ans) {
                    if (dd < EPS) count--;
                }

                System.out.println(count);
                Collections.sort(ans);

                for (int i = 0; i < ans.size() - 1; ++i) {
                    if (ans.get(i) > EPS)
                        System.out.println(ans.get(i));
                }
            } else {
                System.out.println(0);
            }
        } catch (Exception e) {
            System.out.println(0);
            System.exit(0);
        }
    }

    private List<Double> ans = new ArrayList<>();

    private double getCorner(Point point, double x, double y) {
        double corner = Math.atan2(point.y - y, point.x - x);

        if (corner < 0) {
            corner += 2 * Math.PI;
        }

        return corner;
    }

    private void dfs(List<List<Integer>> graph, List<List<Boolean>> used, Set<Point> points) {
        try {
            for (int i = 0; i < graph.size(); ++i)
                for (int j = 0; j < graph.get(i).size(); ++j)
                    if (!used.get(i).get(j)) {
                        used.get(i).set(j, true);
                        int v = graph.get(i).get(j);
                        int pv = i;
                        List<Point> ans = new ArrayList<>();

                        while (true) {
                            for (Point current : points) {
                                if (current.index == v) {
                                    ans.add(current);
                                    break;
                                }
                            }

                            int it = -1;
                            for (int k = 0; k < graph.get(v).size(); k++) {
                                if (graph.get(v).get(k) == pv) {
                                    it = k;
                                    break;
                                }
                            }

                            if (++it == graph.get(v).size()) it = 0;
                            if (used.get(v).get(it)) break;

                            used.get(v).set(it, true);
                            pv = v;
                            v = graph.get(v).get(it);
                        }

                        this.ans.add(round(getArea(ans)));
                    }
        } catch (Exception e) {
            System.out.println(0);
            System.exit(0);
        }
    }

    private double getArea(List<Point> coords) {
//        for (Point cur : coords) {
//            System.out.println(cur.x + " " + cur.y + " " + cur.index);
//        }
//        System.out.println();

        double res = 0;

        for (int i = 0; i < coords.size() - 1; i++) {
            res += checkLine(coords.get(i), coords.get(i + 1));
        }

        res += checkLine(coords.get(coords.size() - 1), coords.get(0));

        return Math.abs(res) / 2;
    }

    private double checkLine(Point p1, Point p2) {
        return p1.x * p2.y - p1.y * p2.x;
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 8);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private Line getLine(int x1, int y1, int x2, int y2) {
        int A = y1 - y2;
        int B = x2 - x1;
        int C = -A * x1 - B * y1;

        int del = getGCD(A, B, C);
        A /= del;
        B /= del;
        C /= del;

        if ((A < 0 || A == 0) && B < 0) {
            A *= -1;
            B *= -1;
            C *= -1;
        }

        return new Line(A, B, C, x1, y1, x2, y2);
    }

    private int getGCD(int a, int b, int c) {
        return getGCD(getGCD(a, b), c);
    }

    private int getGCD(int a, int b) {
        while (b != 0) {
            int d = a % b;
            a = b;
            b = d;
        }

        return a;
    }

    private double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    private boolean isParallel(Line one, Line two) {
        return Math.abs(det(one.A, one.B, two.A, two.B)) < EPS;
    }

    private boolean isEquivalent(Line one, Line two) {
        return Math.abs(det(one.A, one.B, two.A, two.B)) < EPS
                && Math.abs(det(one.A, one.C, two.A, two.C)) < EPS
                && Math.abs(det(one.B, one.C, two.B, two.C)) < EPS;
    }

    private Point isIntersect(Line one, Line two) {
        Point ans = new Point();
        double zn = det(one.A, one.B, two.A, two.B);

        if (Math.abs(zn) < EPS || isParallel(one, two) || isEquivalent(one, two))
            return null;

        ans.x = -det(one.C, one.B, two.C, two.B) / zn;

        if (ans.x == -0.0)
            ans.x = 0;

        ans.y = -det(one.A, one.C, two.A, two.C) / zn;

        if (ans.y == -0.0)
            ans.y = 0;

        return ans;
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
