package src;
import java.io.IOException;
import java.util.ArrayList;


public class Simulator {

	static int NodeNum = 100; 
	static String FileName ="./resource/100random_topology.txt";
	static long endTime = 1000;
	static int Sink = NodeNum -1;
	
	static double[] nodeSingleQlen = new double[NodeNum];
	
	static int curTime = 1;
	NetTopo bpnet;
	int simType;
	
	static int FlowNum = 1;

	
	
	//for trace:
	long generatedPacketNum = 0;
	long txdNum = 0;
	long rcvdNum = 0;
	int activatedLinkNum = 0;
	ArrayList<Packet> DeliveredPackets = new ArrayList<Packet>();
	double queueLength = 0.0;

	long pktRemained = 0;
	
	
	Simulator(int nodenum, String nodetopo, String type, int buftime){
		if (type.equals("BP")) simType = 0;
		else if (type.equals("EDR-1")) simType = 3;
		else if (type.equals("VBR-ceil")) simType = 4;
		else if (type.equals("VBR-Nofloor")) simType = 5;
		else if (type.equals("VBR-floor")) simType = 6;
		else if (type.equals("EDR-5")) simType = 7;
		else if (type.equals("EDR-10")) simType = 8;
		
		
		bpnet = new NetTopo (nodenum, nodetopo, type);
	}
	
	public static void main(String[] args) throws IOException{
		ArrayList<Simulator> simulators = new ArrayList<Simulator>();
		
		Flows flow = new Flows();
		Simulator simulator_bp = new Simulator(NodeNum, FileName, "BP", 0);	
		simulators.add(simulator_bp);
		Simulator simulator_nbpif = new Simulator(NodeNum, FileName, "EDR-1", 0);	
		simulators.add(simulator_nbpif);
		Simulator simulator_nbp102 = new Simulator(NodeNum, FileName, "EDR-5", 0);	
		simulators.add(simulator_nbp102);
		Simulator simulator_nbp105 = new Simulator(NodeNum, FileName, "EDR-10", 0);	
		simulators.add(simulator_nbp105);
	//	Simulator simulator_nbpif3 = new Simulator(NodeNum, FileName, "VBR-ceil", 0);	
	//	simulators.add(simulator_nbpif3);
		Simulator simulator_nbp103 = new Simulator(NodeNum, FileName, "VBR-floor", 0);	
		simulators.add(simulator_nbp103);
		Simulator simulator_nbp101 = new Simulator(NodeNum, FileName, "VBR-Nofloor", 0);	
		simulators.add(simulator_nbp101);
		

				
		while (Simulator.curTime < Simulator.endTime){
			flow.GenerateData(simulators);

			for (Simulator simulator : simulators){
				int nodeIndex = 0;
				for (Node a: simulator.bpnet.nodes){
					simulator.queueLength += a.getQueueLength();
					nodeSingleQlen[nodeIndex] = (nodeSingleQlen[nodeIndex]+ a.getSingleQueueLength())/2;
					nodeIndex++;
			}
				
				for (Link a : simulator.bpnet.links){
					if (simulator.simType != 0)
						a.MBR_ComputeLinkWeight();
					else 
						a.ComuteLinkWeight();
					//a.ComuteLoopFreeLinkWeight();  //only for traditional BP to calculate loop-free link weight
				}

				ArrayList<Link> activatedLinks = Schedulor.Schedule(simulator.bpnet.nodes, simulator.bpnet.links, simulator.simType);
				//transmit
				Transmitter.Transmit(activatedLinks, simulator);			
			}
			
			//time slot++
			System.out.println(Simulator.curTime);
			Simulator.curTime ++;
		}
		//trace
		if (Simulator.curTime == Simulator.endTime){
			for (Simulator simulator : simulators){
				Tracer tracer = new Tracer(simulator);
				String writeto ="d:/result/random" + String.valueOf(Flows.lambda)+"_linkrate"+String.valueOf(Link.linkCapcity)+".txt";
				tracer.ReportToFile(writeto);
				tracer.ReportToTerm();
			}
		}
		
	}
}
