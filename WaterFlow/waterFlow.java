import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
public class waterFlow {
	
	class Graph {		
		int node_num;
		int edge_num;

		ArrayList<Node> node_list = new ArrayList<Node>();
		
		public int getNode_num() {
			return node_num;
		}
		public void setNode_num(int node_num) {
			this.node_num = node_num;
		}
		public int getEdge_num() {
			return edge_num;
		}
		public void setEdge_num(int edge_num) {
			this.edge_num = edge_num;
		}
		public ArrayList<Node> getNode_list() {
			return node_list;
		}
		public void setNode_list(ArrayList<Node> node_list) {
			this.node_list = node_list;
		}
		public Graph() {
			// TODO Auto-generated constructor stub		
		}
	}
	
	class Node {		
		String prenode;		
		String name;
		int rootcost;
		
		HashMap<String, Edge> adlist = new HashMap<String, Edge>();
		
		ArrayList<String> sortlist = new ArrayList<String>();
						
		public int getRootcost() {
			return rootcost;
		}

		public void setRootcost(int rootcost) {
			this.rootcost = rootcost;
		}

		public ArrayList<String> getSortlist() {
			return sortlist;
		}

		public void setSortlist(ArrayList<String> sortlist) {
			this.sortlist = sortlist;
		}

		public HashMap<String, Edge> getAdlist() {
			return adlist;
		}

		public void setAdlist(HashMap<String, Edge> adlist) {
			this.adlist = adlist;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getPrenode() {
			return prenode;
		}

		public void setPrenode(String prenode) {
			this.prenode = prenode;
		}

		public Node() {
			// TODO Auto-generated constructor stub
		}

	}
	
	class Edge {
		String node1;	
		String node2;
		int cost;	
		int offtime;	
		ArrayList<String> offtimelist = new ArrayList<String>();

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public int getOfftime() {
			return offtime;
		}

		public void setOfftime(int offtime) {
			this.offtime = offtime;
		}

		public ArrayList<String> getOfftimelist() {
			return offtimelist;
		}

		public void setOfftimelist(ArrayList<String> offtimelist) {
			this.offtimelist = offtimelist;
		}

		public String getNode1() {
			return node1;
		}

		public void setNode1(String node1) {
			this.node1 = node1;
		}

		public String getNode2() {
			return node2;
		}

		public void setNode2(String node2) {
			this.node2 = node2;
		}

		public Edge() {
			// TODO Auto-generated constructor stub
		}

	}
	
	private static int num = 0;	
	private static int rootcost = 0;	
	private static int starttime = 0;	
	private static int start = 0;	
	private static String result = "";	
	private static String resultNode = "";	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			int casenum = 0;
			String way = "";
			String root = "";
			String[] destination = null;
			String[] middle = null;
			int edgenum = 0;

			//read txt
			File filename = new File(args[1]);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			
			//write txt
			File writename = new File("output.txt");
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			// casenum
			line = br.readLine();
			casenum = Integer.parseInt(line);

			// read the case
			for (int i = 0; i < casenum; i++) {
				
				ArrayList<String> edge = new ArrayList<String>();

				// DFS, BFS, UCS
				way = br.readLine();
								
				// root
				root = br.readLine();

				// destination				
				destination = br.readLine().split(" ");				

				// middle node
				middle = br.readLine().split(" ");


				// edge number
				line = br.readLine();
				edgenum = Integer.parseInt(line);

				// edge
				for (int j = 0; j < edgenum; j++) {
					line = br.readLine();
					edge.add(line);
				}

				// start time
				line = br.readLine();
				start = Integer.parseInt(line);

				line = br.readLine();

				waterFlow rt = new waterFlow();
				waterFlow.Graph G = rt.new Graph();
				G = creatGraph(root, destination, middle, edgenum, edge);

				int[] visited = new int[G.getNode_num()];
				HashSet<String> hs = new HashSet<String>();

				if (way.equals("BFS")) {
					System.out.println("startBFS");

					Queue<String> q = new LinkedList<String>();
					q.add(root);

					BFS(G, visited, q, destination);
					if(num == -1) {
						result = "None";
						resultNode = "";
					}
					else {
						Node node = G.getNode_list().get(getNodeNum(G, resultNode));
						while(node.getPrenode() != null) {
							System.out.println(node.getPrenode());
							num++;
							node = G.getNode_list().get(getNodeNum(G, node.getPrenode()));
						}
						result = " "+(start + num)%24;
					}
					
					num = 0;
					System.out.println("result" + result);

					System.out.println("\n");
				} else if (way.equals("DFS")) {

					System.out.println("startDFS");

					Stack<String> s = new Stack<String>();
					s.push(root);

					DFS(G, visited, s, destination);
					
					if(num == -1) {
						result  = "None";
						resultNode = "";
					}
					else {
						Node node = G.getNode_list().get(getNodeNum(G, resultNode));
						while(node.getPrenode() != null) {
							num++;
							node = G.getNode_list().get(getNodeNum(G, node.getPrenode()));
						}
						result = " " + (start + num)%24;
					}
					
					num = 0;
					System.out.println("result" + result);

					System.out.println("\n");
				} else if (way.equals("UCS")) {
					
					System.out.println("startUCS");

					System.out.println("starttime: " + starttime);
					//define comparator of priority queue
					Comparator<Node> cmp;
				    cmp = new Comparator<Node>() {
				    	public int compare(Node node1, Node node2) {
					    	if(node1.getRootcost() == node2.getRootcost()) {				    		
					    		if(compareStringAB(node1.getName(), node2.getName())) {
					    			return -1;
					    		}
					    		else {
					    			return 1;
					    		}
					    	}
					    	else {
					    		return node1.getRootcost() - node2.getRootcost();
					    	}
				    	}
				    };
				    PriorityQueue<Node> queue = new PriorityQueue<Node>(G.getNode_num(), cmp);

					int first = getNodeNum(G, root);
					queue.add(G.getNode_list().get(first));

					UCS(G, hs, queue, destination);

					System.out.println("result: " + starttime);
					
					
					if(starttime == -1) {
						result = "None";
						resultNode = "";
					}
					else {
						result = " " + starttime%24;
					}
					
					starttime = 0;
					rootcost = 0;
					System.out.println("\n");

				}
				
				
				/* write txt */				 
				 
				 out.write(resultNode + "");
				 out.write(result+"");
				 out.write("\n");
				 


			}
			
			out.flush();
			out.close();
							
									

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void UCS(Graph g, HashSet<String> hs, PriorityQueue<Node> queue, String[] destination) {
		// TODO Auto-generated method stub
		if(queue.isEmpty()) {
			starttime = -1;
			return;
		}
		
		waterFlow rt = new waterFlow();	
		waterFlow.Node node = rt.new Node();
		
		node = queue.remove();
//		while(hs.contains(node.getName())) {
//			if(queue.isEmpty()) {
//				starttime = -1;
//				return;
//			}
//			node = queue.remove();
//		}
		
		//starttime
		starttime = (start + node.getRootcost())%24;
		System.out.println("现在时间是：" + starttime);
		
		//flag in the array
		hs.add(node.getName());
		
		System.out.println("remove: " + node.getName() );
		System.out.println("to this step's length: " + node.getRootcost());
		
		//if is the goal
		for(int j=0; j<destination.length; j++) {
			if(node.getName().equals(destination[j])) {
				resultNode = destination[j];
				System.out.println("reach the goal");
				return;
			}
		}	
		
		
		//insert node
		int size = node.getSortlist().size();
		for(int i=0; i<size; i++) {
			//name of the insert node
			String ss = node.getSortlist().get(i);
			System.out.println("child: " + ss);
			
			//get Node
			Node insertnode = g.getNode_list().get(getNodeNum(g, ss));
			String itsprenode = node.getName();
			
			//current root cost
			int cost = node.getAdlist().get(ss).getCost() + node.getRootcost();
			boolean ifneedinsert = true;
					
			Edge e = node.getAdlist().get(insertnode.getName());			
			
			if(e.getOfftime() != 0) {
				for(int m=0; m<e.getOfftimelist().size(); m++) {
					
					String[] off = e.getOfftimelist().get(m).split("-");
					if(Integer.parseInt(off[0]) <= starttime && Integer.parseInt(off[1]) >= starttime) {
						System.out.println("在Offtime");
						ifneedinsert = false;
						break;
					}
				}
			}
			
			//修改过的
			if(hs.contains(ss)) {
				ifneedinsert = false;
			}
			
			if(ifneedinsert) {
				
				boolean insert = false;
			
				for (Node x : queue) {
					if (x.getName().equals(insertnode.getName())) {
						insert = true;
						System.out.println("队列里面有" + x.getName());
						System.out.println("队列里面的："  + x.getRootcost() + "即将插入的" + cost);
						if (x.getRootcost() > cost) {
							System.out.println("需要插入");
							x.setRootcost(cost);
							x.setPrenode(itsprenode);					
						}
					}
				}
				
				if(!insert) {
					insertnode.setPrenode(itsprenode);
					insertnode.setRootcost(cost);
					queue.add(insertnode);
				}				
			}		
		}

		System.out.println("此时queue里面有");
		for (Node x : queue) {
			System.out.println(x.getName() + " " + x.getRootcost() + "他们的父节点: " + x.getPrenode()); 
		}
		System.out.println("\n\n\n");		
		UCS(g, hs, queue, destination);
		
	}


	private static void DFS(Graph g, int[] visited, Stack<String> s, String[] destination) {
		// TODO Auto-generated method stub
		if(s.isEmpty()) {
			num = -1;
			return;
		}
		
		
		String nodename = s.pop();
		while(visited[getNodeNum(g, nodename)] == 1) {
			if(s.isEmpty()) {
				num = -1;
				return;
			}
			nodename = s.pop();
		}
		System.out.println("name: " + nodename );
		

		
		for(int j=0; j<destination.length; j++) {
			if(nodename.equals(destination[j])) {
				resultNode = destination[j];
				return;
			}
		}
		int flag = getNodeNum(g, nodename);		
		Node n = g.getNode_list().get(flag);
		System.out.println("prenode: " + n.getPrenode()+ "\n" );
		visited[flag] = 1;
		
		
		int size = n.getSortlist().size();
		
		System.out.println("child node num: " + size);
		for(int i=0; i<size; i++) {
			
			String ss = n.getSortlist().get(size-1-i);
			
			System.out.println("child node num in list: " + getNodeNum(g, ss));
			if(visited[getNodeNum(g, ss)] != 1) {
			           
				g.getNode_list().get(getNodeNum(g, ss)).setPrenode(n.getName());
				s.push(n.getSortlist().get(size-1-i));
				System.out.println("peek:" + s.peek());
			}
		}
		
		DFS(g, visited, s, destination);
            
	}



	private static void BFS(Graph g, int[] visited, Queue<String> q, String[] destination) {
		// TODO Auto-generated method stub
		if(q.isEmpty()) {
			num = -1;
			return;
		}
		
		String nodename = q.remove();
		System.out.println("name: " + nodename );
		System.out.println("num: " + num);
		
		for(int j=0; j<destination.length; j++) {
			if(nodename.equals(destination[j])) {
				resultNode = destination[j];
				return;
			}
		}
		
		int flag = getNodeNum(g, nodename);		
		Node n = g.getNode_list().get(flag);		
		visited[flag] = 1;
		
		int size = n.getSortlist().size();
		for(int i=0; i<size; i++) {
			String ss = n.getSortlist().get(i);
			
			if(visited[getNodeNum(g, ss)] != 1) {
			     
				Node addn = g.getNode_list().get(getNodeNum(g, n.getSortlist().get(i)));
				System.out.println(addn.getName() + "  prenode:" + nodename);
				addn.setPrenode(nodename);
				q.add(n.getSortlist().get(i));
				//修改过
				visited[getNodeNum(g, ss)] = 1;
				
			}
		}
		
		
		BFS(g, visited, q, destination);
            
		
	}



	private static Graph creatGraph(String root, String[] destination, String[] middle, int edgenum, ArrayList<String> edge) {
		// TODO Auto-generated method stub
		waterFlow rt = new waterFlow();
		waterFlow.Graph g = rt.new Graph();
		g.setNode_num(1 + destination.length + middle.length);
		g.setEdge_num(edgenum);
		
		
		waterFlow.Node n = rt.new Node();
		n.setName(root);
		
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(n);
		
		for(int i=0; i<destination.length; i++) {
			waterFlow.Node n1 = rt.new Node();
			n1.setName(destination[i]);
			list.add(n1);
		}
		
		for(int i=0; i<middle.length; i++) {
			waterFlow.Node n1 = rt.new Node();
			n1.setName(middle[i]);
			list.add(n1);
		}
		
		g.setNode_list(list);

		
		for(int i=0; i<edgenum; i++) {
			
			waterFlow.Edge e1 = rt.new Edge();
			
			String[] strs = edge.get(i).split(" ");
			
			e1.setNode1(strs[0]);
			e1.setNode2(strs[1]);
			
			e1.setCost(Integer.parseInt(strs[2]));
			
			e1.setOfftime(Integer.parseInt(strs[3]));
			
			ArrayList<String> offtimelist = new ArrayList<String>();

			for (int j = 0; j < e1.getOfftime(); j++) {
				offtimelist.add(strs[j + 4]);
			}

			e1.setOfftimelist(offtimelist);

			// put in the node and edge to Adlist
			for (int k = 0; k < g.getNode_list().size(); k++) {
				
				if(strs[0].equals(g.getNode_list().get(k).getName())) {
					
					g.getNode_list().get(k).getSortlist().add(strs[1]);
					g.getNode_list().get(k).getAdlist().put(strs[1], e1);
					
				}
			}
			
		}
		
		
		//sort arraylist
		for(int m=0; m<g.getNode_num(); m++) {
			ArrayList<String> array = g.getNode_list().get(m).getSortlist();
			Collections.sort(array);
			
			g.getNode_list().get(m).setSortlist(array);
		}
		
		return g;
	}
	
	// Node n's list node
	private static int getNodeNum(Graph g, String s) {
		// TODO Auto-generated method stub

		for (int j = 0; j < g.getNode_list().size(); j++) {

			if (g.getNode_list().get(j).getName().equals(s)) {
				return j;
			}

		}
		return -1;

	}
	
	private static boolean compareStringAB(String a, String b) {
		for(int i=0; i<a.length()&&i<b.length(); i++) {
			if(a.charAt(i) > b.charAt(i)) {
				return false;
			}
		}
		if(b.length()<a.length()) {
			return false;
		}
		return true;
	}
	
	

	
}

