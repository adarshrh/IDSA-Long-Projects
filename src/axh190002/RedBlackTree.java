/** Starter code for Red-Black Tree
 */
package axh190002;

import axh190002.BinarySearchTree.Entry;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Enumeration;
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
  //  System.out.println("Inserting :"+x.toString());
      Entry cur = new Entry<>(x,NILL_NODE,NILL_NODE);
      cur = ((Entry) super.add(cur));
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
        if(getParent(cur)==getGrandParent(cur).left){
          RedBlackTree.Entry<T> uncle = (Entry<T>) getGrandParent(cur).right;
          if(uncle.color==RED){
         //   System.out.println("left sub tree case 1");
            getParent(cur).color=BLACK;
            uncle.color=BLACK;
            cur= getGrandParent(cur);
            stck.pop();
            stck.pop();
            cur.color=RED;
          } else {
            if (cur == getParent(cur).right) {
         //     System.out.println("left sub tree case 2");
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
         //   System.out.println("left sub tree case 3");
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
        //    System.out.println("case 2");
              RedBlackTree.Entry temp =cur;
              cur = getParent(cur);
              stck.pop();
              rightRotate(cur);
              stck.push(temp);
            }
            getParent(cur).color=BLACK;
            getGrandParent(cur).color=RED;
            RedBlackTree.Entry grandParent=getGrandParent(cur);
            RedBlackTree.Entry<T> parentOfCurrent = (Entry<T>) stck.pop();
            this.stck.pop();
         //   System.out.println("case 3");
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
      return cur==null?false:true;
    }

    public T remove(T x){
      T removed=super.remove(x);
    System.out.println("after bst remove");
     printLevelOrder();
    System.out.println("Stack:");
      System.out.println("spliced color "+((Entry)replaced).color);
      System.out.println("spliced element "+replaced.element);
      Entry deleted = (Entry) this.stck.pop();
      System.out.println("deleted element color "+((Entry)deleted).color);


     // ((Entry)replaced).color = deleted.color;
       //RedBlackTree.Entry cur= (RedBlackTree.Entry) (((Entry)this.stck.peek()));
//
////      while(!stck.isEmpty())
////      System.out.println("stck pop "+stck.pop().element);
//      while(stck.peek().element!=cur.element)
//       System.out.println("stcke pop "+stck.pop().element);
//       this.stck.pop();
//      System.out.println("parent "+getParent(cur).element);




//     System.out.println("parent "+getParent(cur));
//      // System.out.println("stcke pop "+stck.pop().element);

      if(deleted.color==BLACK){
        Entry spliced = (Entry) replaced;
        spliced.color =  deleted.color;
        fixUp(spliced);
      }
      return removed;
    }

  private void fixUp(Entry cur) {
      System.out.println("in fix up");

      while(cur!=root  &&  cur.color==BLACK){
        System.out.println("in");
        RedBlackTree.Entry parent = getParent(cur);
        System.out.println("parent fix up"+getParent(cur).element);
         if(parent.left==cur){
           RedBlackTree.Entry sib= getSibling(cur);
           if(sib.color==RED){
          System.out.println("left child case 1");
             sib.color=BLACK;
             parent.color=RED;
             Entry<T> temp = (Entry<T>) stck.pop();
             leftRotate(parent);
             this.stck.push(sib);
             this.stck.push(temp);
          System.out.println("here parent "+getParent(cur).element);
             sib=getSibling(cur);
          System.out.println("Sibling "+sib.element);
           }
          if( ((Entry)sib.left).color==BLACK && ((Entry)sib.right).color==BLACK){
            System.out.println("left child case 2");
            sib.color=RED;
            cur = parent;
            stck.pop();
            parent = getParent(cur);
          }
          else {
            if(((Entry)sib.right).color == BLACK){
              System.out.println("left child case 3");
              ((Entry) sib.left).color=BLACK;
              sib.color=RED;
              rightRotate(sib);

            System.out.println("sibling 3"+sib.element);
            System.out.println("parent "+getParent(cur).element);
            sib = getSibling(cur);
            }

           System.out.println("left child case 4");
            sib.color =parent.color;
            parent.color=BLACK;
           ((Entry)sib.right).color=BLACK;
           System.out.println("parent "+parent.element);
            Entry temp = (Entry) stck.pop();
            printLevelOrder();
            leftRotate(temp);
            stck.push(temp);
            cur= (Entry) root;

//            ((Entry) sib).color = parent.color;
//            parent.color = BLACK;
//            ((Entry) sib.left).color = BLACK;
//            Entry<T> temp = (Entry<T>) stck.pop();
//            rightRotate(temp);
//            stck.push(temp);
//            cur = (Entry) root;

          }// System.out.println("parent  cur"+getParent(cur).element);
      }
       else{
           RedBlackTree.Entry sib= getSibling(cur);
           if(sib.color==RED){
             System.out.println("case 1");
             sib.color=BLACK;
             parent.color=RED;
             Entry<T> temp = (Entry<T>) stck.pop();
             rightRotate(parent);
             stck.push(sib);
             stck.push(temp);
             sib=getSibling(cur);
             System.out.println("sibling "+sib.element);
             printLevelOrder();
           }
           if( ((Entry)sib.left).color==BLACK && ((Entry)sib.right).color==BLACK){
             System.out.println("case 2");
             sib.color=RED;

          System.out.println("sib "+ sib.element);
             cur = parent;
           //  sib=getSibling(cur);
             stck.pop();
             parent = getParent(cur);
        }
           else {
          if (((Entry) sib.left).color == BLACK) {
            System.out.println("case 3");
            ((Entry) sib.right).color = BLACK;
            sib.color = RED;
            leftRotate(sib);
            sib = getSibling(cur);
            printLevelOrder();
          }

          System.out.println("case 4");
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
      System.out.println("while out");
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
  public Entry<T> getSibling(Entry<T> cur){
      Entry parent = getParent(cur);
      return (Entry<T>) (cur==parent.left?parent.right:parent.left);
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
//        rb.add(16);
//        rb.add(12);
//        rb.add(6);
//        rb.add(5);
//        rb.add(-4);
//        rb.add(-5);
//        rb.add(-17);
//        rb.add(18);
//        rb.add(20);
//        rb.add(22);
//        rb.add(24);
//        rb.add(26);
//        rb.add(28);
//        rb.add(30);
//        rb.add(32);
//        rb.add(-13);
//        rb.add(-15);
//         rb.printLevelOrder();
//         rb.remove(6);
//         rb.printLevelOrder();
   Entry root = new Entry(50,NILL_NODE,NILL_NODE);
   root.color = BLACK;
      Entry n25 = new Entry(25,NILL_NODE,NILL_NODE);
      Entry n60 = new Entry(60,NILL_NODE,NILL_NODE);
      Entry n24 = new Entry(24,NILL_NODE,NILL_NODE);
      Entry n30 = new Entry(30,NILL_NODE,NILL_NODE);
      Entry n29 = new Entry(29,NILL_NODE,NILL_NODE);
      Entry n31 = new Entry(31,NILL_NODE,NILL_NODE);
      Entry n65 = new Entry(65,NILL_NODE,NILL_NODE);
      Entry n26 = new Entry(26,NILL_NODE,NILL_NODE);
      Entry n23 = new Entry(23,NILL_NODE,NILL_NODE);
      n25.color=RED;
      n60.color=BLACK;
      n24.color=BLACK;
      n30.color=BLACK;
      n29.color=RED;
      n31.color=BLACK;
      n65.color= BLACK;
      n23.color=BLACK;
      n26.color=BLACK;

      root.left = n25;
      root.right = n60;
      n60.right =n65;
      n25.left = n24;
      n24.left =n23;
      n25.right = n30;
      n30.left = n29;
      n30.right = n31;
      n29.left =n26;
      rb.root = root;
      rb.size = 10;
      rb.remove(24);
      rb.printLevelOrder();





    }
}

