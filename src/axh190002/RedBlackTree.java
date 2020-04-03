/** Starter code for Red-Black Tree
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
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }
        Entry(T x, Entry<T> left, Entry<T> right,boolean color){
            super(x,left,right);
            this.color=color;
        }

        boolean isRed() {
	    return color == RED;
        }

        boolean isBlack() {
	    return color == BLACK;
        }
    }

    RedBlackTree() {
	super();
    }

    public boolean add(T x) {
         return add(new RedBlackTree.Entry<>(x,NILL_NODE,NILL_NODE));
    }

    public boolean add(Entry<T> cur){
    System.out.println("inserting " +cur.element);
          boolean result=  super.add(cur);
          //System.out.println("size "+this.stck.size());

           cur.color=RED;

        if(cur==root.left || cur==this.root.right)
        {
            this.stck.clear();
            return result;
        }
      while(cur != this.root && getParent(cur).color!=BLACK ){
          if(getParent(cur)==getGrandParent(cur).left){
            RedBlackTree.Entry<T> uncle = (Entry<T>) getGrandParent(cur).right;
            if(uncle.color==RED){
          System.out.println("case 1");
              getParent(cur).color=BLACK;
              uncle.color=BLACK;
                cur= getGrandParent(cur);
                stck.pop();
                stck.pop();
                cur.color=RED;
        } else {
          if (cur == getParent(cur).right) {
            System.out.println("case 2");
            RedBlackTree.Entry temp =cur;
            cur = getParent(cur);
            this.stck.pop();
            leftRotate(cur);
            stck.push(temp);
          }
          getParent(cur).color = BLACK;
          getGrandParent(cur).color = RED;
          RedBlackTree.Entry grandParent = getGrandParent(cur);
              RedBlackTree.Entry<T> parentOfCurrent = (Entry<T>) this.stck.pop();
              this.stck.pop();
              System.out.println("case 3");
              rightRotate(grandParent);
             this.stck.push(parentOfCurrent);
            }
          }
          else{
                RedBlackTree.Entry<T> uncle = (Entry<T>)getGrandParent(cur).left;

                if(uncle.color==RED){
                  getParent(cur).color=BLACK;
                  uncle.color=BLACK;
                  cur =getGrandParent(cur);
                  stck.pop();
                  stck.pop();
                  cur.color=RED;
                }
                else{
                   if(cur == getParent(cur).left){
                     RedBlackTree.Entry temp =cur;
                     cur = getParent(cur);
                     stck.pop();
                     rightRotate(cur);
                     stck.push(temp);
                   }
                   getParent(cur).color=BLACK;
                   getGrandParent(cur).color=RED;
                    RedBlackTree.Entry grandParent=getGrandParent(cur);
                  RedBlackTree.Entry<T> parentOfCurrent = (Entry<T>) this.stck.pop();
                  this.stck.pop();
                  System.out.println("case 3");
                   leftRotate(grandParent);
                  this.stck.push(parentOfCurrent);
                }

          }


        }
      ((Entry)this.root).color=BLACK;
//    if (cur != root) {
//      System.out.println("cur element is " + cur.element);
//      System.out.println("parent "+getParent(cur).element);
//      System.out.println("cur color is " + cur.color);
//      }
//    else{
//      System.out.println("cur "+cur.element);
//    }

    this.stck.clear();
    return result;
    }
    public Entry remove(Entry x){
         RedBlackTree.Entry removed = (Entry) super.remove(x);
         System.out.println(removed.element);
         System.out.println(removed.color);
          return removed;
    }



    public T remove(T x){
         RedBlackTree.Entry removed= remove(new Entry(x,null,null));
         return (T) removed.element;
    }

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

  public Entry<T> getParent(Entry<T> cur){
    //        while(this.stck.peek().left!=cur || this.stck.peek().right!=cur)
    //           this.stck.pop();
    if (!(this.stck.isEmpty())) {
    //  System.out.println(this.stck.peek().element);
      return (Entry) this.stck.peek();
    } else {
      return NILL_NODE;
        }
    }
    public Entry<T> getGrandParent(Entry<T> cur){
      RedBlackTree.Entry parent= (Entry) this.stck.pop();
      RedBlackTree.Entry grandParent= (Entry) this.stck.peek();
      this.stck.push(parent);
      return grandParent;
    }

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



    public static void main(String[] args){

        RedBlackTree<Integer> rb = new RedBlackTree<>();
        rb.add(16);
        rb.add(12);
        rb.add(6);
        rb.add(5);
        rb.add(-4);
        rb.add(-5);
        //rb.add(-8);
      //  rb.add(-15);
        rb.add(-14);
        rb.add(18);
        rb.add(20);
        rb.add(22);
        rb.add(24);
        rb.add(26);
        rb.add(28);
        rb.add(30);
        rb.add(32);
        rb.remove(30);
       // rb.add(-13);
       rb.printLevelOrder();


//        rb.add(-3);
//        rb.add(-4);
//        rb.add(-5);
//        rb.add(-6);




    }
}

