package src;
public class Packet {
	
	long packetID;
	int sourceID;
	int destID;
	int stayNode;
	int lastNode;
	
	int generateTime;
	int arrivedTime;
	int deliveredTime;
	
	Packet(int srid, int destid, long PacketID){
		packetID = PacketID;
		sourceID = srid;
		destID = destid;
		stayNode = sourceID;
		lastNode = sourceID;
		generateTime = Simulator.curTime;
		arrivedTime = generateTime;
		deliveredTime = 0;
	}
	
	
}
