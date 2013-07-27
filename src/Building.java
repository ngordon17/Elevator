
public class Building extends AbstractBuilding {
	
	public static Elevator[] elevators;
	public static int maxOccupancy;

	public Building(int numFloors, int numElevators, int maxOccupants) {
		super(numFloors, numElevators);
		elevators = new Elevator[numElevators];
		maxOccupancy = maxOccupants;
		for (int i = 0; i < numElevators; i++) {
			elevators[i] = new Elevator(numFloors, i, maxOccupancy);
			Thread elevator = new Thread(elevators[i]);
			elevator.start();
		}
	}
	/*
	public void setMaxOccupancy(int maxOcc) {
		maxOccupancy = maxOcc;
	}*/
	

	@Override
	public Elevator CallUp(int fromFloor) {
		//TODO: when finding elevator check if elevator is full to capacity
		//System.out.println("Rider calling elevator going up from floor " + fromFloor);
		Elevator closest = null;
		int closestEffectiveDistance = 3*_numFloors;
		while (closest == null) {
			for (Elevator e : elevators) {
				if (findEffectiveDistance(e,fromFloor, true) < closestEffectiveDistance && (e.myOccupants != e.myMaxOccupants)) {
					closest = e;
					closestEffectiveDistance = findEffectiveDistance(e,fromFloor, true);
				}
			}
		}
		closest.myUpRequests[fromFloor] = true;	
		closest.myUpList[fromFloor].arrive();
		//System.out.println("Rider called elevator " + closest._elevatorId + " going up from floor " + fromFloor);
		return closest;
	}
		

	@Override
	public Elevator CallDown(int fromFloor) {
		//TODO: improve algorithm later to avoid starvation
		//System.out.println("Rider calling elevator going down from floor " + fromFloor);
		Elevator closest = null;
		int closestEffectiveDistance = 5*_numFloors;
		while (closest == null) {
			for (Elevator e : elevators) {
				//System.out.println(findEffectiveDistance(e, fromFloor, false) < closestEffectiveDistance);
				if (findEffectiveDistance(e, fromFloor, false) < closestEffectiveDistance && (e.myOccupants != e.myMaxOccupants)) {
					closest = e;
					closestEffectiveDistance = findEffectiveDistance(e,fromFloor, false);
				}
			}
		}
		closest.myDownRequests[fromFloor] = true;
		closest.myDownList[fromFloor].arrive();
		//System.out.println("Rider called elevator " + closest._elevatorId + " going down from floor " + fromFloor);
		return closest;
	}
	
	public int findEffectiveDistance(Elevator elevator, int fromFloor, boolean riderGoingUp) {
		if (riderGoingUp && elevator.goingUp) {
			if (elevator.myFloor <= fromFloor) {
				return elevator.myFloor - fromFloor;
			}
			else {
				return _numFloors - elevator.myFloor + _numFloors + fromFloor;
			}
		}
		else if (riderGoingUp && !elevator.goingUp) {
			return elevator.myFloor + fromFloor;
		}
		else if (!riderGoingUp && elevator.goingUp) {
			return _numFloors - elevator.myFloor + fromFloor;
		}
		else {
			if (elevator.myFloor >= fromFloor) {
				return elevator.myFloor - fromFloor;
			}
			else {
				return elevator.myFloor + _numFloors + _numFloors - fromFloor;
			}
		}
	}
}
