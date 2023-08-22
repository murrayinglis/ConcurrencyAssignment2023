package clubSimulation;

import java.util.concurrent.atomic.AtomicInteger;

// GridBlock class to represent a block in the club.
// only one thread at a time "owns" a GridBlock

public class GridBlock {
	private int isOccupied; 
	private final boolean isExit;  //is tthis the exit door?
	private final boolean isBar; //is it a bar block?
	private final boolean isDance; //is it the dance area?
	private int [] coords; // the coordinate of the block.
	
	GridBlock(boolean exitBlock, boolean barBlock, boolean danceBlock) throws InterruptedException {
		isExit=exitBlock;
		isBar=barBlock;
		isDance=danceBlock;
		isOccupied= -1;
	}
	
	GridBlock(int x, int y, boolean exitBlock, boolean refreshBlock, boolean danceBlock) throws InterruptedException {
		this(exitBlock,refreshBlock,danceBlock);
		coords = new int [] {x,y};
	}
	
	synchronized public  int getX() {return coords[0];}  
	
	synchronized public  int getY() {return coords[1];}
	
	synchronized public  boolean get(int threadID) throws InterruptedException {
		if (isOccupied==threadID) return true; //thread Already in this block
		if (isOccupied>=0) 
		{
			//System.out.println("Occupied");
			return false; //space is occupied
		}
		isOccupied=threadID;  //set ID to thread that had block
		return true;
	}
		
	synchronized public void release() {
		isOccupied=-1;
	}
	
	synchronized public  boolean occupied() {
		if(isOccupied==-1) return false;
		return true;
	}
	
	synchronized public boolean isExit() {
		return isExit;	
	}

	synchronized public   boolean isBar() {
		return isBar;
	}
	synchronized public   boolean isDanceFloor() {
		return isDance;
	}

}
