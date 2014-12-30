package src.Tools;

import java.io.FileWriter;
import java.util.Random;

public class Topo_Generator {

	
	static void Generate_Topo_Random(int nodeNum, int x_range, int y_range, String filename){
		try {
			FileWriter fw1 = new FileWriter(filename);
			FileWriter fw2 = new FileWriter("resource/node_to_plot.txt");
			Random rand = new Random();
			for (int i = 0; i < nodeNum; i++) {
				int x = rand.nextInt(x_range);
				int y = rand.nextInt(y_range);
				fw1.write(String.format("%d %d %d\n", i, x, y));
				fw2.write(String.format("%d %d\n", x, y));
			}
				
			fw1.close();
			fw2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void Generate_Topo_Linear(int nodeNum, String filename, int radius){
		try {
			FileWriter fw1 = new FileWriter(filename);
			int loc = 0;
			for (int i = 0; i < nodeNum; i++) {
				fw1.write(String.format("%d %d %d\n", i, loc, 0));
				loc += radius;
			}
				
			fw1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
	//	Generate_Topo_Linear(100, "resource/100linear_topology.txt", 100);
	//	System.out.println("OK!"); 
		Generate_Topo_Random(100, 500, 500, "resource/100random_topology.txt");
		System.out.println("OK!"); 
		
	}
}