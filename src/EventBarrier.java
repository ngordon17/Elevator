import java.util.Timer;


public class EventBarrier extends AbstractEventBarrier {

	private int numWaiters = 0;
	//private int numCompleted = 0;
	private boolean gateOpened = false;
	private Timer myTimer = new Timer();
	
	public EventBarrier(int numWorkers) {
		super(numWorkers);
	}

	public synchronized void forceClose() {
		numWaiters = 0;
		System.out.println("#Time's up - doors being forced closed! " + numWaiters);
		this.notifyAll();
	}
	
	@Override
	public synchronized void arrive() {
		numWaiters++;
		while (!gateOpened) {
			try {
				this.wait();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void raise() {
		// TODO Auto-generated method stub
		//numCompleted = 0;
		gateOpened = true;
		this.notifyAll();
		
		//timer is to deal with extra credit conditions...
		myTimer = new Timer();
		myTimer.schedule(new CloseDoorTask(this), 1000);
		
		while (numWaiters > 0) {
			try {
				wait();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		myTimer.cancel();
		gateOpened = false;
		
	}

	
	//if reach max occupancy, rider thread calls complete then calls arrive again
	@Override
	public synchronized void complete() {
		// TODO Auto-generated method stub
		//System.out.println("Completed = " + numCompleted);
		//numCompleted++;
		//System.out.println("Waiting = " + numWaiters);
		numWaiters--;
		
		if (numWaiters == 0) {
			this.notifyAll();
		}
		else {
			while (numWaiters > 0) {
				//for extra credit where badly behaved rider does not enter/exit elevator after calling it
				//end extra credit section...
				try {
					this.wait();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int waiters() {
		return numWaiters;
	}
}
