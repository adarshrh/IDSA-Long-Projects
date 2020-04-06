/**
 * IDSA Long Project 3
 * Group members:
 * Adarsh Raghupati   axh190002
 * Akash Akki         apa190001
 * Keerti Keerti      kxk190012
 * Stewart cannon     sjc160330
 */

// Change this to netid of any member of team
package axh190002;

import java.util.*;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    static final int maxLevel = 32;
    Entry<T> head,tail;
    int size;
    Entry<T>[] pred;
    int[] span;
    Random random;

    static class Entry<E extends Comparable<? super E>> {
	E element;
	Entry[] next;
	int[] width;
	Entry prev;

	public Entry(E x, int lev) {
	    element = x;
	    next = new Entry[lev];
	    width = new int[lev];
	}

	public E getElement() {
	    return element;
	}
    }

    /**
     * Initialize skip list
     */
    public SkipList() {
        head = new Entry<T>(null,maxLevel+1);
        tail = new Entry<T>(null,maxLevel+1 );
        size = 0;
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

    /**
     * Finds the elements last visited in each level in the search path
     * @param x
     */
    public void findPred(T x){
        Entry<T> p = head;
        int distance = 0;
        for(int i  = maxLevel-1; i>=0; i-- ){
            while (p.next[i].element != null && p.next[i].element.compareTo(x) < 0){
                p = p.next[i];
                distance = distance + p.width[i];
            }
            pred[i] = p;
            span[i] = distance;
        }
    }

    /**
     * Randomly chooses height for insertion
     * @return
     */
    public int chooseHeight(){
        int lev = 1 + Integer.numberOfTrailingZeros(random.nextInt());
        lev = Math.min(lev,maxLevel);
       // System.out.println("random num:"+(lev));
        return lev;
    }

    /**
     *  Add x to list. If x already exists, reject it. Returns true if new node is added to list
     * @param x
     * @return
     */
    public boolean add(T x) {
        if(contains(x))
            return false;
        int lev = chooseHeight();
        Entry<T> ent = new Entry<>(x,lev);
        int distance = span[0];
        for(int i=0 ; i< lev ; i++){
            ent.width[i] = distance-span[i]+1;
            ent.next[i] = pred[i].next[i];
            pred[i].next[i] = ent;
            ent.next[i].width[i] = ent.next[i].width[i] - ent.width[i] + 1;
        }
        ent.next[0].prev = ent;
        ent.prev = pred[0];
        size++;
        for(int i=lev;i<maxLevel;i++){
            pred[i].next[i].width[i] = pred[i].next[i].width[i]+1;
        }
        return true;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
	return null;
    }

    /**
     * Returns true if element is present in the list
     * @param x
     * @return
     */
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

    /**
     * Return element at index n of list.  First element is at index 0.
     * @param n
     * @return
     */
    public T get(int n) {
        return getLog(n);
    }



    /**
     * O(n) algorithm for get(n)
     * @param n
     * @return
     */
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
        if(n<0 || n > size-1 )
            throw new NoSuchElementException();
        n=n+1;
        Entry<T> p = head;
        for(int i  = maxLevel-1; i>=0; i-- ){
            while (p.next[i].element != null && n-p.next[i].width[i] >=0){
                n = n-p.next[i].width[i];
                p = p.next[i];
              //  System.out.println("val of n:"+n);
                if(n==0)
                    break;
            }

            if(n==0)
                return p.element;
           // System.out.println("level:"+i);
        }

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
       // int distance = span[0]+1;
        for(int i= 0; i < ent.next.length; i++){
            if(i!=0){
                ent.next[i].width[i] = ent.width[i] + ent.next[i].width[i]-1;
            }
            pred[i].next[i] = ent.next[i];
        }

        for(int i=ent.next.length;i<maxLevel;i++){
            pred[i].next[i].width[i]--;
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
                System.out.print(node.element + "("+node.width[i]+")"+ "\t");
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
        Set<Integer> set = new HashSet<>();
        int num = 0;
        for(int i=0;i<10;i++){
            num = random.nextInt(50);
            set.add(num);
            sl.add(num);
        }
        System.out.println("Inserted elements:"+set.toString());
        sl.remove(num);
        System.out.println("Remove :"+num);
        sl.printList();
        System.out.println("Get(0) :"+sl.get(0));

    }
}
