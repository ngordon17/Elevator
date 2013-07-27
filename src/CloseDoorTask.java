import java.util.TimerTask;


public class CloseDoorTask extends TimerTask {
	EventBarrier myEB;

	public CloseDoorTask(EventBarrier eb) {
		super();
		myEB = eb;	
	}
	
	@Override
	public void run() {
		myEB.forceClose();

	}
	

}
