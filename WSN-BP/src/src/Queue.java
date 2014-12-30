package src;
import java.util.ArrayList;

public class Queue {
	int flowSourceID;
	int flowDestID;
	ArrayList<Packet> packets;
	
	Queue(){
		packets = new ArrayList<Packet>();
	}
	/*
	 * get()
	 * add()
	 * remove()
	 */
}

class BufferQueue{
	ArrayList<Packet> bufferpackets;
	
	BufferQueue(){
		bufferpackets = new ArrayList<Packet>();
	}
} 