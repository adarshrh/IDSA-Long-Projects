/**
 * IDSA Long Project 3
 * Group members:
 * Adarsh Raghupati   axh190002
 * Akash Akki         apa190001
 * Keerti Keerti      kxk190012
 * Stewart cannon     sjc160330
 */
package axh190002;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private static Entry NILL_NODE = new RedBlackTree.Entry<>(null,null,null,false);

    static class Entry<T extends Comparable<? super T>> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }
        Entry(T x, Entry<T> left, Entry<T> right,boolean color){
            super(x,left,right);
            this.color=color;
        }

        /**
         * Returns true if the color of the node is RED
         * @return
         */
        boolean isRed() {
            return color == RED;
        }

        /**
         * Returns true if the color of the node is BLACK
         * @return
         */
        boolean isBlack() {
            return color == BLACK;
        }
    }

    RedBlackTree() {
        super();
    }

    /**
     * Inserts the element into the red black tree
     * @param x
     * @return
     */
    public boolean add(T x) {
        Entry cur = new Entry<>(x,NILL_NODE,NILL_NODE);

        cur = ((Entry) super.add(cur));
        if(cur==null)
            return false;
        cur.color=RED;

        if(cur==root){
            cur.color = BLACK;
            return true;
        }
        if(cur==root.left || cur==this.root.right )
        {
            this.stck.clear();
            return true;
        }

        Entry gP = (Entry) stck.peek();
        if(cur.element.compareTo(gP.element) > 0)
            stck.push(gP.right);
        else stck.push(gP.left);



        while(cur != this.root && getParent(cur).color!=BLACK ){
            //If parent is left child
            if(getParent(cur)==getGrandParent(cur).left){
                RedBlackTree.Entry<T> uncle = (Entry<T>) getGrandParent(cur).right;
               //Case 1: Pre-cond: Uncle is Red
                if(uncle.color==RED){
                    getParent(cur).color=BLACK;
                    uncle.color=BLACK;
                    cur= getGrandParent(cur);
                    stck.pop();
                    stck.pop();
                    cur.color=RED;
                } else {
                    //Case 2: Cursor is right child and uncle is black
                    if (cur == getParent(cur).right) {
                        RedBlackTree.Entry temp =cur;
                        cur = getParent(cur);
                        this.stck.pop();
                        leftRotate(cur);
                        stck.push(temp);
                    }
                    //Case 3: Cursor is left child and uncle is black
                    getParent(cur).color = BLACK;
                    getGrandParent(cur).color = RED;
                    RedBlackTree.Entry grandParent = getGrandParent(cur);
                    RedBlackTree.Entry<T> parentOfCurrent = (Entry<T>) this.stck.pop();
                    this.stck.pop();
                    rightRotate(grandParent);
                    this.stck.push(parentOfCurrent);
                }
            }
            //If parent is right child
            else{
                RedBlackTree.Entry<T> uncle = (Entry<T>)getGrandParent(cur).left;
                //Case 1: Pre-cond: Uncle is Red
                if(uncle.color==RED){
                    getParent(cur).color=BLACK;
                    uncle.color=BLACK;
                    cur =getGrandParent(cur);
                    stck.pop();
                    stck.pop();
                    cur.color=RED;
                }
                else{
                    //Case 2: Cursor is left child and uncle is black
                    if(cur == getParent(cur).left){
                        RedBlackTree.Entry temp =cur;
                        cur = getParent(cur);
                        stck.pop();
                        rightRotate(cur);
                        stck.push(temp);
                    }
                    //Case 3: Cursor is right child
                    getParent(cur).color=BLACK;
                    getGrandParent(cur).color=RED;
                    RedBlackTree.Entry grandParent=getGrandParent(cur);
                    RedBlackTree.Entry<T> parentOfCurrent = (Entry<T>) stck.pop();
                    this.stck.pop();
                    leftRotate(grandParent);
                    this.stck.push(parentOfCurrent);
                }

            }


        }
        ((Entry)this.root).color=BLACK;
        this.stck.clear();
        return cur==null?false:true;
    }

    /**
     * Removes the given element from the tree if present
     * @param x
     * @return
     */
    public T remove(T x){
        T removed=super.remove(x);

        if(removed==null)
            return null;
        Entry deleted = (Entry) this.stck.pop();
        if(deleted.color==RED && ((Entry) replaced).color==BLACK && ((Entry) replaced)!=NILL_NODE){
            if(((Entry) deleted.right).color == RED){
                ((Entry) deleted.right).color = BLACK;
            }else
                fixUp((Entry) deleted.right);
        }
        else if(deleted.color==BLACK ){
            Entry spliced = (Entry) replaced;
            boolean temp =  ((Entry) replaced).color;
            spliced = deleted;
            spliced.color = temp;
            fixUp(spliced);
        }
        this.stck.clear();
        return removed;
    }

    /**
     * Rearranges the tree if deletion violates RBT properties
     * @param cur
     */
    private void fixUp(Entry cur) {
        while(cur!=root  &&  cur.color==BLACK){
            RedBlackTree.Entry parent = getParent(cur);

            if(parent.left==cur){
                RedBlackTree.Entry sib= getSibling(cur);
                //Case 1: Cursor is left child and sibling is red
                if(sib.color==RED){
                    sib.color=BLACK;
                    parent.color=RED;
                    Entry<T> temp = (Entry<T>) stck.pop();
                    leftRotate(parent);
                    this.stck.push(sib);
                    this.stck.push(temp);
                    sib=getSibling(cur);
                }
                //Case 2: Cursor is left child and sibling's both children are black
                if( ((((Entry)sib) == NILL_NODE)|| ((Entry)sib.left).color==BLACK && ((Entry)sib.right).color==BLACK)){
                    sib.color=RED;
                    cur = parent;
                    stck.pop();
                    parent = getParent(cur);
                }
                else {
                    //Case 3: Cursor is left child and sibling is black  and sibling's only right child is black
                    if(((Entry)sib.right).color == BLACK){
                        ((Entry) sib.left).color=BLACK;
                        sib.color=RED;
                        rightRotate(sib);
                        sib = getSibling(cur);
                    }
                    //Case 4: Cursor is left child and sibling is black and  siblings right child is red
                    sib.color =parent.color;
                    parent.color=BLACK;
                    ((Entry)sib.right).color=BLACK;
                    Entry temp = (Entry) stck.pop();
                    leftRotate(temp);
                    stck.push(temp);
                    cur= (Entry) root;


                }
            }
            else{
                RedBlackTree.Entry sib= getSibling(cur);
                //Case 1: Cursor is right child and sibling is red
                if(sib.color==RED){
                    sib.color=BLACK;
                    parent.color=RED;
                    Entry<T> temp = (Entry<T>) stck.pop();
                    rightRotate(parent);
                    stck.push(sib);
                    stck.push(temp);
                    sib=getSibling(cur);
                }
                //Case 2: Cursor is right child and sibling's both children are black
                if( ((((Entry)sib) == NILL_NODE)|| ((Entry)sib.left).color==BLACK && ((Entry)sib.right).color==BLACK)){

                    sib.color=RED;

                    cur = parent;
                    //  sib=getSibling(cur);
                    stck.pop();
                    parent = getParent(cur);
                }
                else {
                    //Case 3: Cursor is right child and sibling is black  and sibling's only right child is black
                    if (((Entry) sib.left).color == BLACK) {
                        ((Entry) sib.right).color = BLACK;
                        sib.color = RED;
                        leftRotate(sib);
                        sib = getSibling(cur);
                    }
                    //Case 4: Cursor is right child and sibling is black and  siblings right child is red
                    ((Entry) sib).color = parent.color;
                    parent.color = BLACK;
                    ((Entry) sib.left).color = BLACK;
                    Entry<T> temp = (Entry<T>) stck.pop();
                    rightRotate(temp);
                    stck.push(temp);
                    cur = (Entry) root;
                }

            }
        }
        cur.color=BLACK;
    }

    /**
     * Helper methods for insert and delete to change the orientation.
     * Left rotate at the pivot
     * @param x
     */
    private void leftRotate(Entry<T> x) {
        RedBlackTree.Entry y=(RedBlackTree.Entry)x.right;
        x.right=y.left;
        if (getParent(x) == NILL_NODE) {
            this.root = y;
            stck.push(y);
        }
        else if(x==getParent(x).left)
            getParent(x).left=y;
        else
            getParent(x).right=y;
        y.left=x;
    }


    /**
     * Helper methods for insert and delete to change the orientation.
     * Right rotate at the pivot
     * @param x
     */
    private void rightRotate(Entry<T> x) {
        RedBlackTree.Entry y = (Entry) x.left;
        x.left=y.right;

        if(getParent(x)==NILL_NODE){
            this.root=y;
            this.stck.push(y);
        }
        else if(x==getParent(x).left)
            getParent(x).left=y;
        else
            getParent(x).right=y;
        y.right=x;

    }

    /**
     * Returns the sibling of the given node
     * @param cur
     * @return
     */
    public Entry<T> getSibling(Entry<T> cur){
        Entry parent = getParent(cur);
        return (Entry<T>) (cur==parent.left?parent.right:parent.left);
    }

    /**
     * Returns the parent of the given node
     * @param cur
     * @return
     */
    public Entry<T> getParent(Entry<T> cur){

        if (!(this.stck.isEmpty())) {
            return (Entry) this.stck.peek();
        } else {
            return NILL_NODE;
        }
    }

    /**
     * Returns the grandparent of the given node
     * @param cur
     * @return
     */
    public Entry<T> getGrandParent(Entry<T> cur){
        RedBlackTree.Entry parent= (Entry) this.stck.pop();
        RedBlackTree.Entry grandParent= (Entry) this.stck.peek();
        this.stck.push(parent);
        return grandParent;
    }

    int globalBlackCount = Integer.MIN_VALUE;

    /**
     * Verifies the RBT properties
     * 1. Root is black
     * 2. If node is red then both of its children are black
     * 3. All leaf nodes are NIL black nodes
     * 4. All paths from node to leaf should have same number of black nodes
     * @return
     */
    public boolean verifyRBT(){
        if(((Entry)root).isRed())
            return false;
        globalBlackCount = Integer.MIN_VALUE;
        return verifyRBTUtil((Entry<T>) root,BLACK,0);
    }

    /**
     * Helper function to verify RBT properties
     * @param node
     * @param isred
     * @param blackcount
     * @return
     */
    public boolean verifyRBTUtil(Entry<T> node,boolean isred,Integer blackcount){
        if(node == null || node.element==null) //change it to NIL node and if so check for black color too
        {
            if(node != NILL_NODE){
                return false;

            }

            if(globalBlackCount == Integer.MIN_VALUE) {          //chkg the blackcount in the entire path
                globalBlackCount = blackcount;
                return true;
            }
            else if(blackcount != globalBlackCount){
                return false;
            }
            else
                return true;
        }

        T val = node.element;// BST rule chk
        int count = blackcount;
        if(isred) {                                            //no consecutive red nodes
            if (node.isRed())     //if false--black node color
                return false;
        }
        if(node.isBlack())
            count++;
        if (! verifyRBTUtil((Entry<T>) node.right, node.color, count)) return false;
        if (! verifyRBTUtil((Entry<T>) node.left, node.color, count)) return false;
        return true;
    }

    /**
     * Print the tree in level order with color information
     */
    public void printLevelOrder(){


        List<List<RedBlackTree.Entry>> levels = new ArrayList<List<RedBlackTree.Entry>>();


        Queue<RedBlackTree.Entry> queue = new LinkedList<RedBlackTree.Entry>();
        queue.add((RedBlackTree.Entry)root);
        int level = 0;
        while ( !queue.isEmpty() ) {
            // start the current level
            levels.add(new ArrayList<Entry>());

            // number of elements in the current level
            int level_length = queue.size();
            for(int i = 0; i < level_length; ++i) {
                RedBlackTree.Entry node = queue.remove();

                // fulfill the current level
                levels.get(level).add( node);

                // add child nodes of the current level
                // in the queue for the next level
                if (node.left != null) queue.add((Entry) node.left);
                if (node.right != null) queue.add((Entry) node.right);
            }
            // go to next level
            level++;
        }
        for( List list : levels){
            for ( Object entry : list) {
                String col = ((Entry)entry).color==false?"B":"R";
                String ele = ((Entry)entry).element==null?"N":((Entry)entry).element.toString();
                System.out.print(" "+ ele+":" +col +" ");
            }
            System.out.println();
        }

    }

    public static void main(String[] arg){
        RedBlackTree<Integer> rb = new RedBlackTree<>();
        System.out.println("Insert 50");
        rb.add(50);
        System.out.println("Insert 60");
        rb.add(60);
        System.out.println("Insert 40");
        rb.add(40);
        System.out.println("Insert 35");
        rb.add(35);
        System.out.println("Insert 65");
        rb.add(65);
        System.out.println("Is RBT "+rb.verifyRBT());
        System.out.println("Delete 65");
        rb.remove(65);
        System.out.println("Is RBT "+rb.verifyRBT());
        rb.printLevelOrder();

    }

}

