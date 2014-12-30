package src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.text.html.HTMLDocument.Iterator;



public class NetTopo {
	int NodeNum;
	int linkNum;
	ArrayList<Node> nodes;
	ArrayList<Link> links;
	
	NetTopo(int nodenum, String filename, String protocolTYPE){
		NodeNum = nodenum;
		nodes = CreateNodes(NodeNum, filename);
//		for (Node a : nodes)
//			System.out.println(String.format("Node:%d locates at X:%d,Y:%d.", a.nodeID, a.loc_x, a.loc_y));
		links = CreateLinks(nodes);
//		for (Link a : links)
//			System.out.println(String.format("The Link connects Node:%d with Node:%d has initial Weight:%d and Index:%d, and Capacity: %d.", a.Source.nodeID, a.Destination.nodeID, a.linkMaxWeight, a.maxQueueIndex, a.linkCapcity));
		linkNum = links.size();
		HopsCalculate(nodes, Simulator.Sink);
		for (Node a: nodes)
			System.out.println("Node ID: "+a.nodeID+",Hop To Sink: "+a.hop);
		ComputeVirtualBacklog (nodes, protocolTYPE);
	}	
	
	
	//Creating nodes to fulfill a arraylist
	ArrayList<Node> CreateNodes(int nodenum, String filename){
		
		File nodetopo = new File(filename);
		int[] temploc = new int[3*nodenum];
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(nodetopo));
			String[] tmploc = new String[3];
			String tempString = null;
			int n = 0;
			int line = 1;
			while ((tempString = reader.readLine()) != null){
				tmploc = tempString.split(" ");
				for (String a : tmploc){
					temploc[n] = Integer.parseInt(a);
					n++;
				}
				line++;
			}
		} catch (IOException e){
			e.printStackTrace();
		}finally {
			if (reader != null){
				try{
					reader.close();
				}catch (IOException e1){}
			}
		}
		
		//generate nodes by node_topology.txt
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < 3*nodenum; i = i+3)
			nodes.add(new Node(temploc[i], temploc[i+1], temploc[i+2], nodenum));
		
		return nodes;
	}

	
	////Creating links to fulfill a arraylist
	ArrayList<Link> CreateLinks(ArrayList<Node> nodes){

		ArrayList<Link> links = new ArrayList<Link>();
		for (Node a : nodes){
			for (Node b : nodes){
				if (a.nodeID != b.nodeID){
					if (a.isCovered(b)){
						links.add(new Link(a, b));
					//	if (a.nodeID == Simulator.Sink) 
					//		System.out.println("X:"+b.loc_x+" Y:"+b.loc_y);
					}else continue;
				}else continue;
			}
		}
		return links;
	}

	//calculate the hop-counts of a node from sink
	void HopsCalculate(ArrayList<Node> nodes, int SinkID){
		Node sink = Node.NodeOfID(SinkID, nodes);
		ArrayList<Node> searchedNodes = new ArrayList<Node>();
		ArrayList<Node> tmpNodes = new ArrayList<Node>();
		searchedNodes.add(sink);
		while (searchedNodes.size() != nodes.size()){
			for (Node a : nodes){
				if (searchedNodes.contains(a)) continue;
				else{
					for(Node b : searchedNodes){
						double distance = Math.pow(Math.pow((a.loc_x - b.loc_x), 2) + Math.pow((a.loc_y - b.loc_y), 2), 0.5);
						if (distance <= a.comm_radius){
							if (a.hop == 0 || a.hop > b.hop + 1){
								a.hop = b.hop +1;
								if (!tmpNodes.contains(a)) 
									tmpNodes.add(0, a);
							}
						}	
					}
				}
			}
			searchedNodes.addAll(0, tmpNodes);
			tmpNodes.removeAll(tmpNodes);
		}
	}
	
	void ComputeVirtualBacklog(ArrayList<Node> nodes, String str){
		for(Node a : nodes){
			if (str.equals("BP")) ;
			else if (str.equals("EDR-1"))
				a.virtual_backlog = a.hop;
			else if (str.equals("VBR-ceil")){
				a.virtual_backlog = (int)Math.ceil(10 * a.hop * Link.linkCapcity * Math.exp(Flows.lambda/Simulator.NodeNum));
				System.out.println("HOP: "+a.hop+"  Q:"+a.virtual_backlog);
			}
			else if (str.equals("VBR-Nofloor")){
				if (a.hop != 0)
					a.virtual_backlog = 6 * Link.linkCapcity * Math.pow(1.2, Flows.lambda/a.hop) * Math.pow(1.6, a.hop);
				else 
					a.virtual_backlog = 0;
			}
			else if (str.equals("EDR-5"))
				a.virtual_backlog = a.hop * 5;
			else if (str.equals("EDR-10"))
				a.virtual_backlog = a.hop * 10;
			else if (str.equals("VBR-floor")){
				//a.virtual_backlog = Math.pow(2, Link.linkCapcity) * a.hop  * Math.pow(Simulator.NodeNum, Flows.lambda/Simulator.NodeNum);
				if (a.hop != 0)
					a.virtual_backlog = 6 * Link.linkCapcity * Math.pow(1.2, Flows.lambda/a.hop) * Math.pow(1.6, a.hop) - 5;
				else 
					a.virtual_backlog = 0;
				System.out.println("HOP: "+a.hop+"  Q:"+a.virtual_backlog);
			}
			else 
				System.out.println("Wrong Protocol Name.");
		}
	}

}
