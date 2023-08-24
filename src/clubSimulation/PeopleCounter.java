package clubSimulation;
import java.util.concurrent.atomic.*;

public class PeopleCounter {
	private AtomicInteger peopleOutSide; //counter for people arrived but not yet in the building
	private AtomicInteger peopleInside; //counter for patrons inside club
	private AtomicInteger peopleLeft; //counter for patrons who have left the club
	private AtomicInteger maxPeople; //maximum patrons allowed in the club at one time
	
	PeopleCounter(int max) {
		peopleOutSide= new AtomicInteger(0);
		peopleInside=new AtomicInteger(0);
		peopleLeft=new AtomicInteger(0);
		maxPeople=new AtomicInteger(max);
	}
		
	public synchronized int getWaiting() {
		return peopleOutSide.get();
	}

	public synchronized int getInside() {
		return peopleInside.get();
	}
	
	public synchronized int getTotal() {
		return (peopleOutSide.get()+peopleInside.get()+peopleLeft.get());
	}

	public synchronized int getLeft() {
		return peopleLeft.get();
	}
	
	public synchronized int getMax() {
		return maxPeople.get();
	}
	
	//someone arrived outside
	public synchronized void personArrived() {
		peopleOutSide.getAndIncrement();
	}
	
	//someone got inside
	public synchronized void personEntered() {
		peopleOutSide.getAndDecrement();
		peopleInside.getAndIncrement();
	}

	//someone left
	public synchronized void personLeft() {
		peopleInside.getAndDecrement();
		peopleLeft.getAndIncrement();
		
	}
	//too many people inside
	public synchronized boolean overCapacity() {
		if(peopleInside.get()>=maxPeople.get())
			return true;
		return false;
	}
	
	//not used
	public synchronized void resetScore() {
		peopleInside.set(0);
		peopleOutSide.set(0);
		peopleLeft.set(0);
	}
}
