
package src.Tools;

public class Connectted {
	static int comm_radius = 100;
	
	static void isConnectted(int x1, int y1, int x2, int y2){
		double distance = Math.pow(Math.pow((x1- x2), 2) + Math.pow((y1- y2), 2), 0.5);
		if (distance <= comm_radius)
			System.out.println("Connectted.");
		else
			System.out.println("Not Connectted.");
			
	}
	
	  
	public static void main(String[] args){
		isConnectted(407, 213, 347, 279);
	}
	
}
