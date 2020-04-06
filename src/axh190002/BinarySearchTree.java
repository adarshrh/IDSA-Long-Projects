
/**
 * IDSA Long Project 3
 * Group members:
 * Adarsh Raghupati   axh190002
 * Akash Akki         apa190001
 * Keerti Keerti      kxk190012
 * Stewart cannon     sjc160330
 */
package axh190002;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T extends Comparable<? super T>> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    Stack<Entry<T>> stck = new Stack<>();
    Entry<T> replaced;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Returns the node which contains element x. If there is no node having element x then returns the node where the search ended
     * @param node
     * @param x
     * @return
     */
    public Entry<T> find(Entry<T> node,T x){
        if(node == null || node.element == x){
            return node;
        }
        while(true){
            if (x.compareTo(node.element) < 0){
                if (null == node.left || node.left.element==null){
                    break;
                }else{
                    this.stck.push(node);
                    node = node.left;
                }
            }else if (x.compareTo(node.element) == 0){
                break;
            }else{
                if(null == node.right|| node.right.element==null){
                    break;
                }else{
                    this.stck.push(node);
                    node = node.right;
                }
            }
        }
        return node;
    }

    /**
     * Return true if tree has x else return false
     * @param x
     * @return
     */
    public boolean contains(T x) {
        Entry<T> node = this.find(root,x);
        if(null == node || node.element==null|| node.element.compareTo(x) != 0) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * Returns the node if tree contains x else returns null
     * @param x
     * @return
     */
    public T get(T x) {
        if(this.contains(x)){
            return x;
        }else{
            return null;
        }
    }

    public Entry<T> add(Entry entry){
        T x = (T) entry.element;
        if(this.size == 0){
            this.root = entry;
            this.size++;
            return root;
        }else{
            Entry<T> node = find(root,x);
            if(x.compareTo(node.element) == 0){
                node.element = x;
                return null;
            }else{
                if(x.compareTo(node.element) < 0){
                    node.left = entry;
                }else{
                    node.right = entry;
                }
                this.size++;
                return entry;
            }
        }

    }
    /**
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element to be added to tree.
     */
    public boolean add(T x) {
            Entry entry = add(new Entry(x,null,null));
            return entry==null?false:true;
            }


    /** Remove x from tree.
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if (this.size == 0){
            return null;
        }else{
            Entry<T> node = find(root,x);
            if(node.element.compareTo(x) != 0){
                return null;
            }
            T removed = node.element;
            if(null == node.left || null == node.right || node.left.element==null || node.right.element==null){
                connectChildToParent(node);
               //push deleted node
                stck.push(node);
            }else{
                this.stck.push(node);
                Entry<T> minRight = find(node.right,x);
                node.element = minRight.element;
                connectChildToParent(minRight);
                replaced = minRight;
            }
            this.size--;
            return removed;
        }
    }

    /**
     * Helper method for remove(x)
     * Attaches child to parent
     * @param node
     */
    public void connectChildToParent(Entry<T> node){
        Entry<T> parent = this.stck.peek();
        Entry<T> child = (null == node.left || node.left.element==null)?node.right:node.left;
        if(null == parent){
            this.root = child;
        }else if(parent.left == node){
            parent.left = child;
        }else{
            parent.right = child;
        }
        replaced = child;
    }

    /**
     * Returns minimum element present in the tree
     * @return
     */
    public T min() {
        if (this.size == 0){
            return null;
        }
        Entry<T> node = root;
        while(null != node.left){
            node = node.left;
        }
        return node.element;
    }

    /**
     * Returns maximum element present in the tree
     * @return
     */
    public T max() {
        if (this.size == 0){
            return null;
        }
        Entry<T> node = root;
        while(null != node.right){
            node = node.right;
        }
        return node.element;
    }

    /**
     *Store the elements of tree in array according to inorder
     * @return
     */
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[this.size];
        /* write code to place elements in array here */
        if(this.size == 0){
            System.out.println("The Binary Search Tree is Empty");
            return arr;
        }
        Stack<Entry<T>> s = new Stack<>();
        Entry<T> node = this.root;
        int ind = 0;
        while(null != node || s.size()>0){
            while(null != node){
                s.push(node);
                node=node.left;
            }
            node = s.pop();
            arr[ind++] = node.element;
            node=node.right;
        }
        return arr;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter positive number to add and negative number to remove element from the tree\n" +
            "Enter 0 to print max and min elements and exit");
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                Integer max = t.max();
                Integer min = t.min();
                if(max != null)
                    System.out.println("Max element is: "+max.toString()+ " Min element is: "+min.toString());
                return;
            }
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}