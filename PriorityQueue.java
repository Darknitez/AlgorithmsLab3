import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {

	private ArrayList<PQNode<T>> queue;
	// constructor
	//
	public PriorityQueue()
	{
		queue = new ArrayList<PQNode<T>>();
	}

	// Return true iff the queue is empty.
	//
	public boolean isEmpty()
	{
		return queue.size() <= 0;
	}

	// Insert a pair (key, value) into the queue, and return
	// a Handle to this pair so that we can find it later in
	// constant time.
	//
	Handle<T> insert(int key, T value)
	{
		queue.add(new PQNode<T>(key, value));

		int location = queue.size()-1;
		queue.get(location).setHandIndex(location);

		while(location>0 && queue.get(location).getKey() - queue.get(parent(location)).getKey() < 0){
			swap(location, parent(location));
			location = parent(location);
		}
		//System.out.println();
		for(int i = 0; i<queue.size();i++){
			//System.out.println("index = " + i + ", value = " + queue.get(i).getKey());
		}

		return queue.get(location).getHand();

	}

	private int parent(int i){
		return (i-1)/2;
	}

	// Return the smallest key in the queue.
	//
	public int min()
	{
		if(isEmpty()){
			return -1000000;
		}
		else{
			return queue.get(0).getKey();
		}
	}

	// Extract the (key, value) pair associated with the smallest
	// key in the queue and return its "value" object.
	//
	public T extractMin()
	{
		if(isEmpty()){
			return null;
		}
		else{
			PQNode<T> c = queue.get(0);
			T min = c.getValue();
			swap(0, queue.size()-1);  //Switch first and last values of PQ so that min value can be deleted
			queue.get((queue.size()-1)).setHandIndex(-11);;
			queue.remove(queue.size()-1);
			minHeap(0); //restores minHeap property
			return min;			
		}

	}

	private int RChild(int i){ //returns index of Right child of parent with index i
		return(i*2)+2;
	}

	private int LChild(int i){ //returns index of Left child of parent with index i
		return (i*2)+1;
	}

	public void swap(int i, int j){ //swaps information of PQNodes and updates associated handles
		PQNode<T> q = queue.get(i);

		queue.set(i, queue.get(j));
		queue.get(i).setHandIndex(i);

		queue.set(j, q);
		queue.get(j).setHandIndex(j);
	}

	public void minHeap(int i){ //restores MinHeap property of PQ
		int left = LChild(i);
		int right = RChild(i);
		int small = i;

		if((left<= queue.size()-1) && (queue.get(left).getKey() < queue.get(i).getKey())){ //compare left child to parent
			small = left;
		}

		else 
			small = i;

		if((right<= queue.size()-1) && (queue.get(right).getKey() < queue.get(small).getKey())){ //comapre right child to parent
			small = right;
		}

		if(small!=i){
			swap(i, small);
			minHeap(small);
		}


	}


	// Look at the (key, value) pair referenced by Handle h.
	// If that pair is no longer in the queue, or its key
	// is <= newkey, do nothing and return false.  Otherwise,
	// replace "key" by "newkey", fixup the queue, and return
	// true.
	//
	public boolean decreaseKey(Handle<T> h, int newkey) 
	{
		if((h.getIndex()==-11) || (queue.get(h.getIndex()).getKey()<newkey)){
			return false;
		}
		queue.get(h.getIndex()).setKey(newkey);
		int n = h.getIndex();
		while(n>0 && queue.get(n).getKey() - queue.get(parent(n)).getKey() < 0){ //Compare child n to parent of n
			swap(n, parent(n));
			n = parent(n); //Bubble Up child n
		}
		return true;
	}
	// Get the key of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public int handleGetKey(Handle<T> h)
	{	
		if(h.getIndex()>=0){
			return queue.get(h.getIndex()).getKey();
		}
		else return -10000000;
	}

	// Get the value object of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public T handleGetValue(Handle<T> h)
	{
		if(h.getIndex()>=0){
			return queue.get(h.getIndex()).getValue();
		}
		else return null;
	}

	// Print every element of the queue in the order in which it appears
	// in the implementation (i.e. the array representing the heap).
	public String toString()
	{
		String output = "";
		for(int n = 0; n<queue.size(); n++){
			output+= queue.get(n).getKey() + " , " + queue.get(n).getValue() + "\n";
		}
		return output;
	}
}
