

//semaphore using CVs
public class Semaphore {
	private int myCount; //number active threads?
	
	public Semaphore(int count) {
		myCount = count;
	}
	
	public synchronized void P() {
		while (myCount <= 0) {
			try {
				wait();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		myCount--;
	}
	
	public synchronized void V() {
		myCount++;
		notify();
	}
}
