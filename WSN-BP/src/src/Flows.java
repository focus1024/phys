package src;

import java.util.ArrayList;
import java.util.Random;


public class Flows {
	final static double lambda = 1;
	double intervalCounter = 0.0;
	int nodeNum = Simulator.NodeNum;
	double flowIDs = 0.0;
	
	Flows(){
		
	}
	
	
	void GenerateData(ArrayList<Simulator> simulators){
		if (Flows.lambda <= 1.0){
			if (this.flowIDs == 0.0)
				this.flowIDs += PossionArrival(Flows.lambda);
			else{
				this.flowIDs --;
				if (this.flowIDs < 1){
						this.Generate_A_Pakcet(simulators);
						this.flowIDs += PossionArrival(Flows.lambda);
				}
			}
		} else
			this.Generata_Packets(simulators);
			
	}
	
	
	//generate a random interval using Exponential distribution
	double PossionArrival(double lamda){
		double intvl;
	//	Random rand = new Random(1);
	//	intvl = - (1/lamda) * Math.log(rand.nextFloat());
		intvl = - (1/lamda) * Math.log(Math.random());
		return intvl;
	}

	

	void Generate_A_Pakcet(ArrayList<Simulator> simulators){
		int i = -1;
		Random rnd = new Random();
		i = rnd.nextInt(Simulator.NodeNum);
		//i=0;
		for (Simulator simulator : simulators)
			simulator.bpnet.nodes.get(i).queue.packets.add(new Packet(i, Simulator.Sink, simulator.generatedPacketNum++));
	}
	
	void Generata_Packets(ArrayList<Simulator> simulators){
		int count =(int) lambda;
		int[] indexcount = new int[count];
		Random rnd = new Random();
		for (int i = 0; i < count; i++){
			indexcount[i] = rnd.nextInt(Simulator.NodeNum);
		}
		for (int i =0; i<count; i++){
			for (Simulator simulator : simulators)
				simulator.bpnet.nodes.get(indexcount[i]).queue.packets.add(new Packet(indexcount[i], Simulator.Sink, simulator.generatedPacketNum++));
		}
	}
}

