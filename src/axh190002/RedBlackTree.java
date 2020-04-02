/** Starter code for Red-Black Tree
 */
package axh190002;

import java.util.Comparator;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private static final Entry NILL_NODE = new RedBlackTree.Entry<>(null,null,null,false);
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }

        Entry(T x, Entry<T> left, Entry<T> right, boolean color) {
            super(x, left, right);
            this.color = color;
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
}

