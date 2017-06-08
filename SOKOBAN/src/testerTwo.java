import java.util.Iterator;
import java.util.PriorityQueue;

public class testerTwo {

	public static void main(String[] args) {

		PriorityQueue<Integer> q = new PriorityQueue<Integer>();

		q.add(1);
		q.add(5);
		q.add(3);

		Iterator<Integer> itr = q.iterator();
		System.out.println(itr.next());
		while (itr.hasNext()) {

		}

	}

}
