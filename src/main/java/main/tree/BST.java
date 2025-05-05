package main.tree;

public class BST <T extends Comparable<T>> implements Tree<String> {
    private Node<String> root = null;

    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean insert(String value) {
        if(isEmpty()){
            root = new Node<>(value);
            return true;
        }
        return insert(value, root);
    }
    private boolean insert(String value, Node<String> root) {
        if(value.length() == root.getKey().length()){
            if(value.compareToIgnoreCase(root.getKey()) == 0){
                return false;
            } else if(value.compareToIgnoreCase(root.getKey()) > 0){
                if(root.getLeft() == null){
                    root.setLeft(new Node<>(value));
                    return true;
                } else {
                    return insert(value, root.getLeft());
                }
            } else {
                if(root.getRight() == null){
                    root.setRight(new Node<>(value));
                    return true;
                } else {
                    return insert(value, root.getRight());
                }
            }
        } else if(value.length() > root.getKey().length()){
            if(root.getLeft() == null){
                root.setLeft(new Node<>(value));
                return true;
            } else {
                return insert(value, root.getLeft());
            }
        } else {
            if(root.getRight() == null){
                root.setRight(new Node<>(value));
                return true;
            } else {
                return insert(value, root.getRight());
            }
        }
    }

    @Override
    public boolean contains(String value) {
        return find(value) != null;
    }
    private Node<String> find(String value) {
        if(isEmpty()){
            return null;
        }
        return find(value, root);
    }
    private Node<String> find(String value, Node<String> root) {
        if(root == null || value.equals(root.getKey())){
            return root;
        }
        if(value.length() == root.getKey().length()){
            if(value.compareToIgnoreCase(root.getKey()) > 0){
                return find(value, root.getLeft());
            } else {
                return find(value, root.getRight());
            }
        } else if(value.length() > root.getKey().length()){
            return find(value, root.getLeft());
        } else {
            return find(value, root.getRight());
        }
    }

    @Override
    public void remove(String value) {
        remove(value, root);
    }
    private Node<String> remove(String value, Node<String> root) {
        if(root == null) return root;
        if(value.equals(root.getKey())){
            if(root.isLeaf()){
                root = null;
            } else if (root.getLeft() != null){
                root.setKey(predecessor(root));
                root.setLeft(remove(root.getKey(), root.getLeft()));
            } else {
                root.setKey(successor(root));
                root.setRight(remove(root.getKey(), root.getRight()));
            }
        } else if(value.length() == root.getKey().length()){
            if(value.compareToIgnoreCase(root.getKey()) > 0){
                root.setLeft(remove(value, root.getLeft()));
            } else {
                root.setRight(remove(value, root.getRight()));
            }
        } else if(value.length() > root.getKey().length()){
            root.setLeft(remove(value, root.getLeft()));
        } else {
            root.setRight(remove(value, root.getRight()));
        }
        return root;
    }
    private String successor(Node<String> root) {
        root = root.getRight();
        while(root.getLeft() != null){
            root = root.getLeft();
        }
        return root.getKey();
    }
    private String predecessor(Node<String> root) {
        root = root.getLeft();
        while(root.getRight() != null){
            root = root.getRight();
        }
        return root.getKey();
    }

    public int height(){
        return height(root) - 1;
    }
    private int height(Node<String> root){
        if(root == null) return 0;
        return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }
    private boolean isBalanced(Node<String> root) {
        if(root == null) return true;

        int leftHeight = height(root.getLeft());
        int rightHeight = height(root.getRight());

        if(Math.abs(leftHeight - rightHeight) > 1){
            return false;
        }

        return isBalanced(root.getLeft()) && isBalanced(root.getRight());
    }

    public void traverse(StringBuilder sb, String padding, String pointer, Node<String> node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getKey());
            sb.append("\n");

            String paddingForBoth = padding + "│   ";
            String pointerForLeft = "├── ";
            String pointerForRight = "└── ";

            traverse(sb, paddingForBoth, pointerForLeft, node.getLeft());
            traverse(sb, paddingForBoth, pointerForRight, node.getRight());
        }
    }

    @Override
    public void print() {
        StringBuilder sb = new StringBuilder();
        traverse(sb, "", "", root);
        System.out.println(sb);
    }
}
