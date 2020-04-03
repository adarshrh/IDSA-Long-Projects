/* Starter code for LP3 */

// Change this to netid of any member of team
package axh190002;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    static final int maxLevel = 32;
    Entry<T> head,tail;
    int size, curMaxLevel;
    Entry<T>[] pred;
    int[] span;
    Random random;

    static class Entry<E extends Comparable<? super E>> {
	E element;
	Entry[] next;
	Entry[] width;
	Entry prev;

	public Entry(E x, int lev) {
	    element = x;
	    next = new Entry[lev];
	    width = new Entry[lev];
	}

	public E getElement() {
	    return element;
	}
    }


    // Constructor
    public SkipList() {
        head = new Entry<T>(null,maxLevel);
        tail = new Entry<T>(null,maxLevel);
        size = 0;
        curMaxLevel = 1;
        pred = new Entry[maxLevel];
        span = new int[maxLevel];
        for (int i = 0; i < maxLevel; i++) {
            head.next[i] = tail;
        }
        random = new Random();
    }

    protected class SkipListIterator implements Iterator<T> {
        Entry<T> cursor;

        SkipListIterator() {
            cursor = head;
        }

        @Override
        public boolean hasNext() {
            return cursor.next[0] != tail;
        }

        @Override
        public T next() {
            cursor = cursor.next[0];
            return cursor.element;
        }
    }


    public void findPred(T x){
        Entry<T> p = head;

        for(int i  = maxLevel-1; i>=0; i-- ){
            while (p.next[i].element != null && p.next[i].element.compareTo(x) < 0){
                p = p.next[i];
            }
            pred[i] = p;
        }
    }

    public int chooseHeight(){
        int lev = 1 + Integer.numberOfTrailingZeros(random.nextInt());
        lev = Math.min(lev,curMaxLevel+1);
        if(lev > curMaxLevel)
            curMaxLevel = lev;
        return lev;
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) {
        if(contains(x))
            return false;
        int lev = chooseHeight();
        Entry<T> ent = new Entry<>(x,lev);
        for(int i=0 ; i< lev ; i++){
            ent.next[i] = pred[i].next[i];
            pred[i].next[i] = ent;
        }
        ent.next[0].prev = ent;
        ent.prev = pred[0];
        size++;
        return true;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
	return null;
    }

    // Does list contain x?
    public boolean contains(T x) {
        findPred(x);
        return pred[0].next[0].element != null && pred[0].next[0].element.compareTo(x) == 0;
    }

    // Return first element of list
    public T first() {
	return null;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
	return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return getLinear(n);
    }


    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        if(n<0 || n > size-1 )
            throw new NoSuchElementException();
        Entry<T> p = head;

        for(int i=0; i<=n; i++)
            p = p.next[0];

        return p.element;
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n).
    public T getLog(int n) {
        return null;
    }

    // Is the list empty?
    public boolean isEmpty() {
        if(size == 0)
            return true;
        return false;
    }


    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return new SkipListIterator();
    }

    // Return last element of list
    public T last() {
	return null;
    }

 
    // Not a standard operation in skip lists. 
    public void rebuild() {
	
    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        if(!contains(x))
            return null;
        Entry<T> ent = pred[0].next[0];

        for(int i= 0; i < ent.next.length; i++){
            pred[i].next[i] = ent.next[i];
        }
        size--;

        return ent.element;
    }

    // Return the number of elements in the list
    public int size() {
	return size;
    }

    public void printList() {

        Entry node = head.next[0];

        System.out.println("----------START----------");
        while (node != null && node.element != null) {
            for (int i = 0; i < node.next.length; i++) {
                System.out.print(node.element + "\t");
            }
            for (int j = node.next.length; j < maxLevel; j++) {
                System.out.print("|\t");
            }
            System.out.println();
            node = node.next[0];
        }
        System.out.println("----------END----------");
    }
    public static void main(String[] arg){
        SkipList<Integer> sl = new SkipList<>();
        Random random = new Random();
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i=0;i<10;i++){
            arr.add(i,random.nextInt(50));
            sl.add(arr.get(i));
        }
        System.out.println("Inseted elements:"+arr.toString());
        sl.printList();

    }
}
