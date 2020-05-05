package axh190002.lp3;

import java.util.TreeSet;

/**
 * @author sjc160330
 *
 */
public class testsize
{

	/**
	 * runs through skip list, RBT, and TreeSet starting at 4 M and doubling until the memory runs out
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		skipTime(32000000);
		long len = 4000000;
		while (true)
		{
			skipTime(len);
			RBTTime(len);
			setTime(len);
			len *= 2;
		}
	}

	/**
	 * runs and times the skip list structure
	 * 
	 * @param len how many elements in the structure
	 */
	public static void skipTime(long len)
	{
		SkipList<Long> skipList = new SkipList<>();

		Timer timer = new Timer();

		long temp = 1000000;
		for (long i = 0; i < len; i++)
		{
			skipList.add((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			skipList.contains((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			skipList.remove((long) (Math.random() * len));
		}
		timer.end();
		System.out.println("\nSkip list");
		System.out.println("Size "+ len+" elements");
		System.out.println(timer);
	}
	/**
	 * runs and times the RBT structure
	 * 
	 * @param len how many elements in the structure
	 */
	public static void RBTTime(long len)
	{
		RedBlackTree<Long> redBlackTree = new RedBlackTree<>();

		Timer timer = new Timer();

		long temp = 1000000;
		for (long i = 0; i < len; i++)
		{
			redBlackTree.add((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			redBlackTree.contains((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			redBlackTree.remove((long) (Math.random() * len));
		}
		timer.end();
		System.out.println("\nRBT");
		System.out.println("Size "+ len+" elements");
		System.out.println(timer);
	}
	/**
	 * runs and times the TreeSet structure
	 * 
	 * @param len how many elements in the structure
	 */
	public static void setTime(long len)
	{
		TreeSet<Long> tree = new TreeSet<>();

		Timer timer = new Timer();

		long temp = 1000000;
		for (long i = 0; i < len; i++)
		{
			tree.add((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			tree.contains((long) (Math.random() * len));
		}
		for (long i = 0; i < temp; i++)
		{
			tree.remove((long) (Math.random() * len));
		}
		timer.end();
		System.out.println("\nTreeSet");
		System.out.println("Size "+ len+" elements");
		System.out.println(timer);
	}
}
