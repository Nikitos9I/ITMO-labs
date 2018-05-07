package HomeWork.Algoritms.lab5;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * HomeWork.Algoritms.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

    public class Tree {
        public class Node {
            private int h;
            private int balance;
            int key;
            private Node left, right, father;

            public Node (int key, Node father) {
                this.key = key;
                this.left = this.right = null;
                this.father = father;
                this.h = 1;
                this.balance = 0;
            }
        }

        private Node root;

        private Node getHigherNode(int key) {
            Node p = root;
            while (p != null) {
                if (key < p.key) {
                    if (p.left != null)
                        p = p.left;
                    else
                        return p;
                } else {
                    if (p.right != null) {
                        p = p.right;
                    } else {
                        Node father = p.father;
                        Node ch = p;
                        while (father != null && ch == father.right) {
                            ch = father;
                            father = father.father;
                        }
                        return father;
                    }
                }
            }
            return null;
        }

        private Node getLowerNode(int key) {
            Node p = root;
            while (p != null) {
                if (key > p.key) {
                    if (p.right != null)
                        p = p.right;
                    else
                        return p;
                } else {
                    if (p.left != null) {
                        p = p.left;
                    } else {
                        Node father = p.father;
                        Node ch = p;
                        while (father != null && ch == father.left) {
                            ch = father;
                            father = father.father;
                        }
                        return father;
                    }
                }
            }
            return null;
        }

        private int height(Node x, Node y) {
            if (x == null && y == null) return 0;
            else if (x == null) return y.h;
            else if (y == null) return x.h;
            else return Math.max(x.h, y.h);
        }

        private int balance(Node x, Node y) {
            if (x == null && y == null) return 0;
            else if (x == null) return - y.h;
            else if (y == null) return x.h;
            else return x.h - y.h;
        }

        private Node add (Node node,int key, Node father) {
            if (node == null) {
                Node newNode = new Node(key, father);
                return newNode;
            }

            if (key > node.key) {
                node.right = add(node.right, key, node); node.h = height(node.left, node.right) + 1;
            } else if (key < node.key) {
                node.left = add(node.left, key, node); node.h = height(node.left, node.right) + 1;
            }
            node.balance = balance(node.left, node.right);
            if (node.balance == -2) {
                node = leftRotation(node);
            } else if (node.balance == 2) {
                node = rightRotation(node);
            }
            return node;
        }

        private Node leftRotation(Node node) {
            if (node.right.right == null && node.right.left != null) {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
            } else if (node.right.left == null || node.right.left.h <= node.right.right.h) {
                Node newNode = node.right;
                newNode.father = node.father;
                node.right = newNode.left;
                if (node.right != null)
                    node.right.father = node;
                node.h = height(node.left, node.right) + 1;
                node.father = newNode;
                node.balance = balance(node.left, node.right);
                newNode.left = node;
                node = newNode;
                node.balance = balance(node.left, node.right);
                node.h = height(node.left, node.right) + 1;
            } else{
                node.right = rightRotation(node.right);
                node = leftRotation(node);
            }
            return node;
        }

        private Node rightRotation(Node node) {
            if (node.left.right != null && node.left.left == null) {
                node.left = leftRotation(node.left);
                node = rightRotation(node);
            } else if (node.left.right == null || node.left.right.h <= node.left.left.h) {
                Node newNode = node.left;
                newNode.father = node.father;
                node.left = newNode.right;
                if (node.left != null)
                    node.left.father = node;
                node.h = height(node.left, node.right) + 1;
                node.father = newNode;
                node.balance = balance(node.left, node.right);
                newNode.right = node;
                node = newNode;
                node.balance = balance(node.left, node.right);
                node.h = height(node.left, node.right) + 1;
            } else{
                node.left = leftRotation(node.left);
                node = rightRotation(node);
            }
            return node;
        }

        public void add(int key) {
            root = add(root, key, null);
        }

        private Node delete(Node node, int key) {
            if (node == null) return null;

            if (key > node.key) {
                node.right = delete(node.right, key);
            } else if (key < node.key ) {
                node.left = delete(node.left, key);
            } else{
                if (node.right == null && node.left == null) {
                    node = null;
                } else if (node.right == null) {
                    node.left.father = node.father;
                    node = node.left;
                } else if (node.left == null) {
                    node.right.father = node.father;
                    node = node.right;
                } else{
                    if (node.right.left == null) {
                        node.right.left = node.left;
                        node.right.father = node.father;
                        node.right.father = node.father;
                        node.left.father = node.right;
                        node = node.right;
                    } else{
                        Node res = min(node.right);
                        node.key = res.key;
                        delete(node.right, node.key);
                    }
                }
            }
            if (node != null) {
                node.h = height(node.left, node.right) + 1;
                node.balance = balance(node.left, node.right);
                if (node.balance == -2) {
                    node = leftRotation(node);
                } else if (node.balance == 2) {
                    node = rightRotation(node);
                }
            }
            return node;
        }

        public void delete(int key) {
            root = delete(root, key);
        }

        private Node min(Node node) {
            if (node.left == null) return node;
            return min(node.left);
        }

        private Node get(Node node, int key) {
            if (node == null) return null;


            if (key == node.key) {
                return node;
            } else if (key > node.key) {
                return get(node.right, key);
            } else {
                return get(node.left, key);
            }
        }

        public Node get(int key) {
            return get(root, key);
        }

    }

    public void start() throws IOException {
//        BufferedReader buf =  new BufferedReader( new FileReader("arrange.in.txt"));

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        Tree tree = new Tree();

        String s;
        while ((s = buf.readLine()) != null) {
            st = new StringTokenizer(s);

            String in = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            switch (in) {
                case "insert":
                    tree.add(value);
                    break;
                case "exists":
                    if (tree.get(value) == null) {
                        System.out.println(false);
                    } else {
                        System.out.println(true);
                    }
                    break;
                case "next":
                    if (tree.getHigherNode(value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(tree.getHigherNode(value).key);
                    }
                    break;
                case "prev":
                    if (tree.getLowerNode(value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(tree.getLowerNode(value).key);
                    }
                    break;
                case "delete":
                    tree.delete(value);
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }
}
