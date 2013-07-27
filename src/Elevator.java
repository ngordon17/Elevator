
public class Elevator extends AbstractElevator implements Runnable {
	
	public EventBarrier[] myUpList;
	public EventBarrier[] myDownList;
	public boolean[] myUpRequests;
	public boolean[] myDownRequests;
	public int myFloor = 0;
	public boolean goingUp = true;
	public int myOccupants = 0;
	public int myMaxOccupants;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		myUpList = new EventBarrier[numFloors];
		myDownList = new EventBarrier[numFloors];
		myUpRequests = new boolean[numFloors];
		myDownRequests = new boolean[numFloors];
		myMaxOccupants = maxOccupancyThreshold;
		
		for (int i = 0; i < numFloors; i++) {
			myUpList[i] = new EventBarrier(0);
			myDownList[i] = new EventBarrier(0);
			myUpRequests[i] = false;
			myDownRequests[i] = false;
		}

	}
	//doors actually opened and closed in visitFloor(). EventBarrier raise() function opens then closes doors.
	@Override
	public void OpenDoors() {
		System.out.println("E" + _elevatorId + " on F" + myFloor + " opens");	
	}

	@Override
	public void ClosedDoors() {
		System.out.println("E" + _elevatorId + " on F" + myFloor + " closes");
		
		if (goingUp) {
			myUpRequests[myFloor] = false;
		}
		else {
			myDownRequests[myFloor] = false;
		}
	}

	@Override
	public void VisitFloor(int floor) {
		System.out.println("E" + _elevatorId + " moves " + ((floor > myFloor) ? "up" : "down") + " to F" + floor);
		myFloor = floor;
		OpenDoors();
		if (goingUp) {
			myUpList[myFloor].raise();
		}
		else {
			myDownList[myFloor].raise();
		}
		ClosedDoors();
	}

	//multithreading error here, need locks for max
	@Override
	public boolean Enter() {
		if (goingUp) {
			myUpList[myFloor].complete();
		}
		else {
			myDownList[myFloor].complete();
		}
		
		if (myOccupants == _maxOccupancyThreshold) {
			return false;
		}
		else {
			myOccupants++;
		}
		System.out.println("#E" + _elevatorId + " num occupants = " + myOccupants + " / " + _maxOccupancyThreshold);
		return true;
	}

	@Override
	public void Exit() {
		myOccupants--;
		if (goingUp) {
			myUpList[myFloor].complete();
		}
		else {
			myDownList[myFloor].complete();
		}
	}

	@Override
	public void RequestFloor(int floor) {
		//TODO: modify for extra credit where people can get off at wrong floor?, check to make sure we don't call a floor that
		//does not exist, i.e. 100000 floor, when building only has 50 floors.
		if (goingUp) {
			myUpRequests[floor] = true;
			myUpList[floor].arrive();
		}
		else {
			myDownRequests[floor] = true;
			myDownList[floor].arrive();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (goingUp) {
				for (int i = 0; i < _numFloors; i++) {
					if (myUpRequests[i]) {
						VisitFloor(i);
					}
				}
			}
			else {
				for (int i = _numFloors - 1; i >= 0; i--) {
					if (myDownRequests[i]) {
						VisitFloor(i);
					}
				}
			}
			goingUp = !goingUp;
		}	
	}
}