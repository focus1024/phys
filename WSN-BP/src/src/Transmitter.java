package src;
import java.util.ArrayList;


public class Transmitter {
	
	//transmit packet on activated links and consider the link capacity
	static void Transmit(ArrayList<Link> activatedLinks, Simulator simulator){
		if (activatedLinks.size() > 0){
			simulator.activatedLinkNum += activatedLinks.size();
			for (Link a : activatedLinks){
				int capacity = a.linkCapcity;

				while (capacity != 0 && a.linkMaxWeight != 0 && a.Source.queue.packets.size() != 0){
					Packet packetToTx = a.Source.queue.packets.get(0);
					if (packetToTx.destID == a.Destination.nodeID){
						packetToTx.stayNode = a.Destination.nodeID;
						packetToTx.deliveredTime = Simulator.curTime;
						simulator.DeliveredPackets.add(packetToTx);
						if (simulator.pktRemained !=0) simulator.pktRemained--;
						
					}else{
						packetToTx.lastNode = packetToTx.stayNode;
						packetToTx.stayNode = a.Destination.nodeID;
						packetToTx.arrivedTime = Simulator.curTime;
						a.Destination.queue.packets.add(packetToTx);	

					}
					a.Source.queue.packets.remove(packetToTx);
					simulator.txdNum ++;
					simulator.rcvdNum ++;

						
					capacity --;
					a.linkMaxWeight --;
				}
			}
		}
		activatedLinks.removeAll(activatedLinks);
	}

	
}
