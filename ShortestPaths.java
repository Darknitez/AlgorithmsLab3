//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {

	//
	// constructor
	//
	Multigraph graph;
	int start;
	int count;
	PriorityQueue<Vertex> pq;
	Edge[] prev_edge; // prev_edge[current vertex id] = previous edge object
	Handle[] hands;
	int[] dist;
	@SuppressWarnings("unchecked")
	public ShortestPaths(Multigraph G, int startId, 
			Input input, int startTime) 
	{
		// your code here
		pq = new PriorityQueue<Vertex>();
		graph = G;
		start = startId;
		count = 0;
		hands = new Handle[graph.nVertices()];
		Vertex s = graph.get(startId); // Initial Vertex
		Handle<Vertex> h = pq.insert(0,s);
		dist = new int[graph.nVertices()];
		prev_edge = new Edge[graph.nVertices()];  
		Handle<Vertex> m = pq.insert(Integer.MAX_VALUE,null);
		for(int n = 0; n<graph.nVertices(); n++){ //instantiates arrays of Handles and distances to maximum values so that they can be decreased later
			hands[graph.get(n).id()] = m; 
			if(graph.get(n)!=s){
				dist[graph.get(n).id()] = Integer.MAX_VALUE;
			}
			else{
				dist[s.id()] = 0;
			}
		}
		Handle<Vertex> j = pq.insert(dist[startId], s); //Handle corresponding to initial Vertex
		hands[0] = j;
		while(!pq.isEmpty()){
			Vertex a = pq.extractMin(); //extracts Vertex with minimum Key from PQ
			if(dist[a.id()] == Integer.MAX_VALUE){
				break;
			}
			Vertex.EdgeIterator e  = a.adj();			//iterates through edges adjacent to the extracted Vertex	
				while(e.hasNext()){
					
					Edge t = e.next();
					Vertex v = t.to(); //Vertex that adjacent edge points to 
					int weight = t.weight(); //"Weight" that edge has = distance traveled.

					int totalweight = dist[a.id()] + weight ; //+ weight
					
					//System.out.println(v.id());
					//System.out.println(a.id() + "+");
					if(pq.decreaseKey(hands[v.id()], dist[a.id()] + t.weight())){ //Checks to see if key of Vertex can be decreased
						dist[v.id()] = totalweight; //Changes distance value of next Vertex
						prev_edge[v.id()] = t;  //array of edges is populated so that it can be navigated in reverse by returnPath
					}

				}
			}


		}


	//
	// returnPath()
	// Return an array containing a list of edge ID's forming
	// a shortest path from the start vertex to the specified
	// end vertex.
	//
	public int [] returnPath(int endId) 
	{ 
		// your code here
		Vertex g = graph.get(endId); //makes Vertex at end of "shortestPath"
		if(endId == start){
			return new int[0];
		}
		int[] edge = new int[prev_edge.length];

		while(endId != this.start){
			Edge e = prev_edge[endId];
			edge[endId] = e.id();

			endId = e.from().id();//iterates through edges that have been traversed by ShortestPaths and returns array of edge IDs
		}

		return edge;
	}
}
