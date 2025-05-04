package main.tree;

import java.util.Comparator;
import java.util.Objects;

public class RBTree<T extends Comparable<T>> implements Tree<T> {
    private NodeRB<T> root;
    private final NodeRB<T> tNull;

    enum Color {
        RED, BLACK
    }

    private static class NodeRB<T extends Comparable<T>> {
        T key;
        Color color;
        NodeRB<T> left;
        NodeRB<T> right;
        NodeRB<T> parent;

        NodeRB(T key) {
            this.key = key;
            this.color = Color.RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    public RBTree() {
        tNull = new NodeRB<>(null);
        tNull.color = Color.BLACK;
        root = tNull;
    }

    @Override
    public boolean insert(T key) {
        NodeRB<T> newNode = new NodeRB<>(key);
        newNode.left = tNull;
        newNode.right = tNull;
        newNode.color = Color.RED;

        NodeRB<T> y = null;
        NodeRB<T> x = this.root;

        while (x != tNull) {
            y = x;
            int cmp = compareKeys(newNode.key, x.key);
            if (cmp == 0) {
                return false;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;
        if (y == null) {
            root = newNode;
        } else if (compareKeys(newNode.key, y.key) < 0) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        if (newNode.parent == null) {
            newNode.color = Color.BLACK;
            return true;
        }

        if (newNode.parent.parent == null) {
            return true;
        }

        fixInsert(newNode);
        return true;
    }

    @Override
    public void remove(T key) {
        removeNodeHelper(this.root, key);
    }

    @Override
    public boolean contains(T key) {
        NodeRB<T> current = root;
        while (current != tNull) {
            int cmp = compareKeys(key, current.key);
            if (cmp == 0) return true;
            current = cmp < 0 ? current.left : current.right;
        }
        return false;
    }


    public void preOrder() {
        preOrderHelper(this.root);
    }

    public void inOrder() {
        inOrderHelper(this.root);
    }

    public void postOrder() {
        postOrderHelper(this.root);
    }


    private void fixInsert(NodeRB<T> k) {
        NodeRB<T> u;
        while (k.parent.color == Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = Color.BLACK;
    }

    private void removeNodeHelper(NodeRB<T> node, T key) {
        NodeRB<T> z = tNull;
        NodeRB<T> x, y;

        while (node != tNull) {
            int cmp = compareKeys(node.key, key);
            if (cmp == 0) {
                z = node;
                break;
            }
            node = cmp < 0 ? node.right : node.left;
        }

        if (z == tNull) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        Color yOriginalColor = y.color;
        if (z.left == tNull) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == tNull) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == Color.BLACK) {
            fixRemove(x);
        }
    }

    private void fixRemove(NodeRB<T> x) {
        NodeRB<T> s;
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == Color.BLACK && s.right.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    if (s.right.color == Color.BLACK) {
                        s.left.color = Color.BLACK;
                        s.color = Color.RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == Color.BLACK && s.left.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    if (s.left.color == Color.BLACK) {
                        s.right.color = Color.BLACK;
                        s.color = Color.RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void rbTransplant(NodeRB<T> u, NodeRB<T> v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private NodeRB<T> minimum(NodeRB<T> node) {
        while (node.left != tNull) {
            node = node.left;
        }
        return node;
    }

    private void leftRotate(NodeRB<T> x) {
        NodeRB<T> y = x.right;
        x.right = y.left;
        if (y.left != tNull) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(NodeRB<T> x) {
        NodeRB<T> y = x.left;
        x.left = y.right;
        if (y.right != tNull) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void preOrderHelper(NodeRB<T> node) {
        if (node != tNull) {
            System.out.print(node.key + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(NodeRB<T> node) {
        if (node != tNull) {
            inOrderHelper(node.left);
            System.out.print(node.key + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(NodeRB<T> node) {
        if (node != tNull) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.key + " ");
        }
    }

    private int compareKeys(T key1, T key2) {
        return Objects.compare(key1, key2, Comparator.naturalOrder());
    }

    @Override
    public void print() {
        printHelper(this.root, "", true);
    }

    private void printHelper(NodeRB<T> root, String indent, boolean last) {
        if (root != tNull) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String color = root.color == Color.RED ? "RED" : "BLACK";
            System.out.println(root.key + "(" + color + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }
}