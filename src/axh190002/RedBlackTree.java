/** Starter code for Red-Black Tree
 */
package axh190002;

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
          boolean result=  super.add(cur);
           cur.color=RED;

        if(cur==root.left || cur==this.root.right)
        {
            return result;
        }
      while(cur != this.root && getParent(cur).color!=BLACK ){
          if(getParent(cur)==getGrandParent(cur).left){
            RedBlackTree.Entry<T> uncle = (Entry<T>) getGrandParent(cur).right;
            if(uncle.color==RED){
              getParent(cur).color=BLACK;
              uncle.color=BLACK;
                cur= getGrandParent(cur);
                stck.pop();
                stck.pop();
                cur.color=RED;
        } else {
          if (cur == getParent(cur).right) {
            cur = getParent(cur);
            leftRotate(cur);
          }
          getParent(cur).color = BLACK;
          getGrandParent(cur).color = RED;
          rightRotate(getGrandParent(cur));
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
                     cur = getParent(cur);
                     rightRotate(cur);
                   }
                   getParent(cur).color=BLACK;
                   getGrandParent(cur).color=RED;

                   rightRotate(getGrandParent(cur));
                }

          }


        }
      ((Entry)this.root).color=BLACK;
    if (cur != root) {
      System.out.println("cur element is " + cur.element);
      System.out.println("parent "+getParent(cur).element);
      System.out.println("cur color is " + cur.color);
      }
    else{
      System.out.println("cur "+cur.element);
    }
    System.out.println("last root "+this.root.element);
    return result;
    }

  private void leftRotate(Entry<T> x) {
          RedBlackTree.Entry y=(RedBlackTree.Entry)x.right;
          x.right=y.left;
          if(getParent(x)==NILL_NODE)
            this.root=y;
          else if(x==getParent(x).left)
              getParent(x).left=y;
          else
             getParent(x).right=y;
           y.left=x;
  }



  private void rightRotate(Entry<T> x) {
    RedBlackTree.Entry y = (Entry) x.left;
    x.left=y.right;
    System.out.println("x element"+x.element);
    System.out.println();
    System.out.println("parent in right rotate "+getParent(x).element);
    if(getParent(x)==NILL_NODE)
      this.root=y;
    else if(x==getParent(x).left)
        getParent(x).left=y;
    else
      getParent(x).right=y;
    System.out.println("parent "+getParent(x).element);
    y.right=x;

  }

  public Entry<T> getParent(Entry<T> cur){
        if(this.stck.peek().left==cur || this.stck.peek().right==cur)
           return ((Entry)this.stck.peek());
       return null;
    }
    public Entry<T> getGrandParent(Entry<T> cur){
       return  getParent(getParent(cur));


    }



    public static void main(String[] args){

        RedBlackTree<Integer> rb = new RedBlackTree<>();
        rb.add(6);
        rb.add(5);
        rb.add(4);




    }
}

