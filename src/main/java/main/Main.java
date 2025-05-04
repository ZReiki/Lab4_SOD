package main;

import main.tree.BST;
import main.tree.RBTree;
import main.tree.Tree;

import java.util.Scanner;
import java.util.Random;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public RBTree<String> rbtree = new RBTree<>();
    public BST<String> bst = new BST<>();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        while(true){
            System.out.print("""
                Menu:
                1. Bst operations
                2. RBtree operations
                3. Time calculate
                0. Exit
                Your option >> \s""");
            switch (scanner.nextInt()){
                case 1 -> bstOperations();
                case 2 -> rbtOperations();
                case 3 -> {
                    System.out.print("Enter the number of element (n): ");
                    int n = scanner.nextInt();
                    timeCalculation(n, new BST<String>(), "BST");
                    timeCalculation(n, new RBTree<>(), "RBtree");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void bstOperations(){
        exitLabel:
        while(true){
            System.out.print("""
                Menu:
                1. Add Node
                2. Remove Node
                3. Search Node
                4. Print tree
                5. Insert sample data
                6. Tree level calculation
                7. Is BST balanced?
                0. Back <-
                Your option >> \s""");
            switch (scanner.nextInt()){
                case 1 -> System.out.println((bst.insert(scanner.next())) ? "Inserted" : "Not Inserted");
                case 2 -> bst.remove(scanner.next());
                case 3 -> System.out.println((bst.contains(scanner.next())) ? "Yes" : "No");
                case 4 -> bst.print();
                case 5 -> insertSampleData(bst);
                case 6 -> System.out.println("The level of the bst is: " + bst.height());
                case 7 -> System.out.println((bst.isBalanced()) ? "Yes" : "No");
                case 0 -> {
                    break exitLabel;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void rbtOperations(){
        exitLabel:
        while(true){
            System.out.print("""
                Menu:
                1. Add Node
                2. Remove Node
                3. Search Node
                4. Print tree
                5. Insert sample data
                0. Back <-
                Your option >> \s""");
            switch(scanner.nextInt()) {
                case 1 -> System.out.println((rbtree.insert(scanner.next())) ? "Inserted" : "Not Inserted");
                case 2 -> rbtree.remove(scanner.next());
                case 3 -> System.out.println((rbtree.contains(scanner.next())) ? "Yes" : "No");
                case 4 -> rbtree.print();
                case 5 -> insertSampleData(rbtree);
                case 0 -> {
                    break exitLabel;
                }
            }
        }
    }

    public void insertSampleData(Tree<String> tree){
        tree.insert("banana");
        tree.insert("apple");
        tree.insert("Cherry");
        tree.insert("date");
        tree.insert("blueberry");
        tree.insert("kiwi");
    }

    public void timeCalculation(int n, Tree<String> tree, String treeType){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            int randomNumber = random.nextInt();
            String numberAsString = String.valueOf(randomNumber);
            tree.insert(numberAsString);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Час додавання " + n + " чисел до " + treeType + ": " + (endTime - startTime) + " мс");
    }
}