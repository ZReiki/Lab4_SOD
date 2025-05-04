package main.tree;

import java.io.PrintStream;

public interface Tree<T> {
    boolean insert(T value);
    void remove(T value);
    boolean contains(T value);
    void print();
}
