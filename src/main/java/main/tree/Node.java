package main.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node <T extends Comparable<T>> {
    private T key;
    private Node<T> left;
    private Node<T> right;

    public Node(T key){
        this.key = key;
    }

    public boolean isLeaf(){
        return left == null && right == null;
    }
}
