package src;
import java.util.ArrayList;


public class Schedulor{
	
	static ArrayList<Link> Schedule(ArrayList<Node> nodes, ArrayList<Link> links, int simType){
		ArrayList<Link> activatedLinks = new ArrayList<Link>();
		
		activatedLinks = BP_Schedule(links);
		
		return activatedLinks;
	}
	
	//calculate the max weight scheduled link set under TRADITIONAL BACKPRESSURE
	static ArrayList<Link> BP_Schedule(ArrayList<Link> links){
		ArrayList<Link> activatedLinks = new ArrayList<Link>();
		Link selectedMaxLink = null;
		
		selectedMaxLink = FindLinkwithMaxWeight(links);
		
		while (selectedMaxLink != null){
			activatedLinks.add(selectedMaxLink);
			for (Link a : links){
				if (a != selectedMaxLink && a.isInterfered(selectedMaxLink))
					a.linkMaxWeight = 0;
			}
			selectedMaxLink.selected = 1;
			selectedMaxLink = FindLinkwithMaxWeight(links);
		}

//		System.out.println(String.format("Selected links: %d at Time: %d",activatedLinks.size(), Simulator.curTime));
		return activatedLinks;		
	}
	
	//find a link with MAX weight in the link set
	static Link FindLinkwithMaxWeight(ArrayList<Link> links){
		Link maxLink = null;
		double tmpMax = 0;
		
		for(Link a : links){

			if (a.selected == 0 && a.linkMaxWeight != 0 && a.linkMaxWeight >= tmpMax){
				if (a.linkMaxWeight > tmpMax){
					maxLink = a;
					tmpMax = a.linkMaxWeight;
				}
				else{
					if (Math.random()>= 0.5 ){
						maxLink = a;
						tmpMax = a.linkMaxWeight;
					}
				}
				
			}
		}
//		if (maxLink != null)
//			System.out.println(String.format("The selected Link is between %d and %d, with Weight %d at Time: %d.", maxLink.Source.nodeID, maxLink.Destination.nodeID, maxLink.linkMaxWeight, Simulator.curTime));
		return maxLink;
	}
	
	
	

}
