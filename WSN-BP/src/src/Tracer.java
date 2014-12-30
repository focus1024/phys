package src;
import java.io.FileWriter;
import java.util.ArrayList;


public class Tracer {
	long generatedPacketNum = 0;
	long txedPacketNum = 0;
	long rcvedPacketNum = 0;
	long deliveredPacketNum;
	int acvdLinkNum;
	long simTime;
	int nodeNum;
	int linkNum;
	double queueLen;
	int simtype;
	ArrayList<Packet> DeliveredPackets = new ArrayList<Packet>();
	
	Tracer(Simulator simulator){
		generatedPacketNum = simulator.generatedPacketNum;
		deliveredPacketNum =  simulator.DeliveredPackets.size();
		txedPacketNum =  simulator.txdNum;
		rcvedPacketNum =  simulator.rcvdNum;
		acvdLinkNum =  simulator.activatedLinkNum;
		simTime = Simulator.endTime;
		nodeNum = simulator.bpnet.NodeNum;
		linkNum = simulator.bpnet.links.size();
		queueLen = simulator.queueLength;
		simtype = simulator.simType;
		DeliveredPackets = simulator.DeliveredPackets;
	}
	
	void ReportToFile(String filename){
		try {
			FileWriter fw = new FileWriter(filename,true);
			if (simtype == 0)
				fw.write(String.format("--------------------Result of BP----------------------\n"));
			else if (simtype == 3)
				fw.write(String.format("--------------------Result of EDR-1----------------------\n"));
			else if (simtype == 4)
				fw.write(String.format("--------------------Result of VBR-ceil----------------------\n"));
			else if (simtype == 5)
				fw.write(String.format("--------------------Result of VBR-nofloor----------------------\n"));
			else if (simtype == 6)
				fw.write(String.format("--------------------Result of VBR-floor----------------------\n"));
			else if (simtype == 7)
				fw.write(String.format("--------------------Result of EDR-5----------------------\n"));
			else if (simtype == 8)
				fw.write(String.format("--------------------Result of EDR-10----------------------\n"));
			fw.write(String.format("Packets Generated: %d.\n", this.generatedPacketNum));
			fw.write(String.format("Packets Delivered: %d.\n", this.deliveredPacketNum));
			fw.write(String.format("The Delivered Ratio: %f.\n", (float)this.deliveredPacketNum/(float)this.generatedPacketNum));
			fw.write(String.format("Packets Delivered: %d.\n", this.deliveredPacketNum));
			fw.write(String.format("Network Throughput: %f.\n", (float)this.deliveredPacketNum/(float)this.simTime));
			int tmpTime = 0;
			for (Packet a : DeliveredPackets){
				tmpTime += a.deliveredTime - a.generateTime;
			}
			if (tmpTime != 0)
				fw.write(String.format("Packets Average E2E Delay: %f.\n", (float)tmpTime/(float)this.deliveredPacketNum));
			fw.write(String.format("ALL Packets Average E2E Delay (Total): %f.\n", ((float)tmpTime + (this.generatedPacketNum - this.deliveredPacketNum)*this.simTime)/(float)this.generatedPacketNum));
			fw.write(String.format("The Average Link Use Efficiency: %f within Total %d Links.\n", (float)this.acvdLinkNum/(float)simTime, linkNum));
			fw.write(String.format("The Average Queue Length at Each Node: %f.\n", (float)queueLen/(float)nodeNum/(float)simTime));
			fw.write(String.format("CDF: (Delay  Ratio)"));
			for (int i = 0; i <= simTime; i+=50){
				int cdf = 0;
				for (Packet a : DeliveredPackets) {
					if (a.deliveredTime - a.generateTime <= i)
						cdf ++;
				}
				fw.write(String.format("%d   %f\n", i, (float)cdf/(float)generatedPacketNum));
			}
			if (simtype == 0)
				fw.write(String.format("--------------------Result of BP----------------------\n"));
			else if (simtype == 3)
				fw.write(String.format("--------------------Result of EDR-1----------------------\n"));
			else if (simtype == 4)
				fw.write(String.format("--------------------Result of VBR-ceil----------------------\n"));
			else if (simtype == 5)
				fw.write(String.format("--------------------Result of VBR-nofloor----------------------\n"));
			else if (simtype == 6)
				fw.write(String.format("--------------------Result of VBR-floor----------------------\n"));
			else if (simtype == 7)
				fw.write(String.format("--------------------Result of EDR-5----------------------\n"));
			else if (simtype == 8)
				fw.write(String.format("--------------------Result of EDR-10----------------------\n"));
/*			for (int i=0; i<nodeNum; i++){
				fw.write(String.format("%f\n", Simulator.nodeSingleQlen[i]));
			}
*/			
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void ReportToTerm(){
		if (simtype == 0)
			System.out.println("--------------------Result of BP----------------------");
		else if (simtype == 3)
			System.out.printf("--------------------Result of EDR-1----------------------\n");
		else if (simtype == 4)
			System.out.printf("--------------------Result of VBR-ceil----------------------\n");
		else if (simtype == 5)
			System.out.printf("--------------------Result of VBR-nofloor----------------------\n");
		else if (simtype == 6)
			System.out.printf("--------------------Result of VBR-floor----------------------\n");
		else if (simtype == 7)
			System.out.printf("--------------------Result of EDR-5----------------------\n");
		else if (simtype == 8)
			System.out.printf("--------------------Result of EDR-10----------------------\n");
		System.out.println(String.format("Packets Generated: %d.", this.generatedPacketNum));
		System.out.println(String.format("Packets Delivered: %d.", this.deliveredPacketNum));
		System.out.println(String.format("The Delivered Ratio: %f.", (float)this.deliveredPacketNum/(float)this.generatedPacketNum));
		System.out.println(String.format("Packets Delivered: %d.", this.deliveredPacketNum));
		System.out.println(String.format("Network Throughput: %f.", (float)this.deliveredPacketNum/(float)this.simTime));
		int tmpTime = 0;
		for (Packet a : DeliveredPackets){
			tmpTime += a.deliveredTime - a.generateTime;
		}
		if (tmpTime != 0)
			System.out.println(String.format("Packets Average E2E Delay: %f.", (float)tmpTime/(float)this.deliveredPacketNum));
		System.out.println(String.format("ALL Packets Average E2E Delay (Total): %f.", ((float)tmpTime + (this.generatedPacketNum - this.deliveredPacketNum)*this.simTime)/(float)this.generatedPacketNum));
		System.out.println(String.format("The Average Link Use Efficiency: %f within Total %d Links.", (float)this.acvdLinkNum/(float)simTime, linkNum));
		System.out.println(String.format("The Average Queue Length at Each Node: %f.", (float)queueLen/(float)nodeNum/(float)simTime));
		System.out.println(String.format("CDF: (Delay  Ratio)"));
		for (int i = 0; i <= simTime; i+=50){
			int cdf = 0;
			for (Packet a : DeliveredPackets) {
				if (a.deliveredTime - a.generateTime <= i)
					cdf ++;
			}
			System.out.println(String.format("%d   %f", i, (float)cdf/(float)generatedPacketNum));
		}
/*		for (int i=0; i<nodeNum; i++){
			System.out.println(String.format("%f", Simulator.nodeSingleQlen[i]));
		}
*/		if (simtype == 0)
			System.out.println("--------------------Result of BP----------------------");
		else if (simtype == 3)
			System.out.printf("--------------------Result of EDR-1----------------------\n");
		else if (simtype == 4)
			System.out.printf("--------------------Result of VBR-ceil----------------------\n");
		else if (simtype == 5)
			System.out.printf("--------------------Result of VBR-nofloor----------------------\n");
		else if (simtype == 6)
			System.out.printf("--------------------Result of VBR-floor----------------------\n");
		else if (simtype == 7)
			System.out.printf("--------------------Result of EDR-5----------------------\n");
		else if (simtype == 8)
			System.out.printf("--------------------Result of EDR-10----------------------\n");

	}
}
