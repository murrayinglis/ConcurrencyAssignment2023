package clubSimulation;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Andre extends Thread{
    public static ClubGrid club; //shared club
	public static AtomicBoolean paused = new AtomicBoolean(true);

	GridBlock currentBlock;
	
	private PeopleLocation myLocation;
	private boolean inRoom;
		
	Andre(PeopleLocation loc) {
		this.myLocation = loc; //for easy lookups
		inRoom=false; //not in room yet
	}
	
	//getter
	public  boolean inRoom() {
		return inRoom;
	}
	
	//getter
	public   int getX() { return currentBlock.getX();}	
	
	//getter
	public   int getY() {	return currentBlock.getY();	}

	//check to see if user pressed pause button
    public void checkPause() {
        synchronized (paused) {
            while (paused.get()) {
                try {
                    paused.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	public static void pauseAllThreads() {
        paused.set(true);
    }

    public static void resumeAllThreads() {
        synchronized (paused) {
            paused.set(false);
            paused.notifyAll();
        }
    }

	private void startSim() {
		synchronized (paused) {
			while(paused.get()) {
				try{
					paused.wait();	
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
    }
	
	//wandering about  in the club (restricted to up and down bar)
		private void wander() throws InterruptedException {		
            currentBlock = club.moveAndre(this,currentBlock,myLocation);
            myLocation.setLocation(currentBlock);
            sleep(1000);
		}
	
	//set Andre's location in the club
        public void enterClub() throws InterruptedException {
            currentBlock = club.enterBar(myLocation);
            myLocation.setLocation(currentBlock);
            inRoom=true;
            System.out.println("Andre entered club at position: " + currentBlock.getX()  + " " +currentBlock.getY() );
        }

	public void run() {
		try {
			myLocation.setInRoom(true);
            System.out.println("Andre arrived at club"); //output for checking
            enterClub();
            startSim(); 
			checkPause();

		
			while (inRoom) {	
				checkPause(); //check every step
				wander();
				//System.out.println("Andre wandering about " );
				}
				
			}
			

		catch (InterruptedException e1) {  //do nothing
		}
        System.out.println("Andre is done");
	}
	
    
}
