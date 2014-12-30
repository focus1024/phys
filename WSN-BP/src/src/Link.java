package src;

public class Link {
	final static int linkCapcity = 1; 	//set LINK CAPACITY first before simulations
	double linkMaxWeight;
	int selected;		//whether this link is selected to be activated: 0 means no, 0 means selected.
	Node Source;
	Node Destination;
	
	Link(Node src, Node dest){
		Source = src;
		Destination = dest;
		linkMaxWeight = 0;

		selected = 0;
	}
	
	//the method for calculating Max Link-Weight and assign the value.
	void ComuteLinkWeight(){
		
		this.selected = 0;
		this.linkMaxWeight = 0;
		
		Queue a = this.Source.queue;
		if (a.packets.size() != 0){
			this.linkMaxWeight = a.packets.size() - this.Destination.queue.packets.size();
		}	
	}
	
	void MBR_ComputeLinkWeight(){
		this.selected = 0;
		this.linkMaxWeight = 0;
		
		Queue a = this.Source.queue;
		if (a.packets.size() != 0){
			this.linkMaxWeight = a.packets.size()+ this.Source.virtual_backlog - (this.Destination.queue.packets.size() + this.Destination.virtual_backlog);
			
		}

	}

	
	
	boolean isInterfered(Link link){
		if (this.Source.isCovered(link.Destination) || link.Source.isCovered(this.Destination)		//general interference model
		//	|| this.Source.isCovered(link.Source) || link.Destination.isCovered(this.Destination) || this.Destination.isCovered(link.Source)||this.Destination.isCovered(link.Destination)||link.Source.isCovered(this.Source)|| link.Destination.isCovered(this.Source) //2-hop interference model
			)
			return true;
		else 
			return false;
	}
	
}
