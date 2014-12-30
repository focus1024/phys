package src;
import java.util.ArrayList;


public class Node {
	int loc_x, loc_y;
	int nodeID;
	int comm_radius = 100;	// communication radius of the node
	Queue queue;

	int hop = 0;
	
	double virtual_backlog = 0;
	
	Node(int id, int locx, int locy, int nodenum){
		loc_x = locx;
		loc_y = locy;
		nodeID = id;
		queue = new Queue();
		
	}
	
	
	
	static Node NodeOfID(int ID, ArrayList<Node> nodes){
		Node node = null;
		for (Node a : nodes)
			if (a.nodeID == ID) 
				node = a;
		if (node == null)
			System.out.println("Cannot find the node who has this ID.");
		return node;
	}
	
	
	// check for whether the two nodes can communicate 
	boolean isCovered(Node b){
		if (this.nodeID == b.nodeID) 
			return true;
		
		double distance;
		distance = Math.pow(Math.pow((this.loc_x - b.loc_x), 2) + Math.pow((this.loc_y - b.loc_y), 2), 0.5);
		if (distance <= this.comm_radius)
			return true;
		else 
			return false;
	}
	

	double getSingleQueueLength(){
		double len = 0;
		Queue q = this.queue;
				len = q.packets.size();
		return len;
	}
	

	double getQueueLength(){
		double len = 0;
			len += this.queue.packets.size();
		return len;
	}
	
}
