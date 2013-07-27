import java.util.Scanner;


public class BadlyBehavedElevatorRider extends ElevatorRider {

	public BadlyBehavedElevatorRider(Building building, Scanner in) {
		super(building, in);
	}
	
	@Override
	public void run() {
		while (myScanner.hasNextLine()) {
			String line = myScanner.nextLine();
			String[] info = line.split("\\s+");
			if (info.length != 3) {
				System.out.println("#ERROR: Can't read input format, must have id, source, target");
				break; 
			}
			myID = Integer.parseInt(info[0]);
			mySource = Integer.parseInt(info[1]);
			myTarget = Integer.parseInt(info[2]);
			//System.out.println("#myID = " + myID + " mySource = " + mySource + " myTarget = " + myTarget);

			if (mySource < myTarget) {
				System.out.println("BBR" + myID + " pushes U" + mySource);
				Elevator e = myBuilding.CallUp(mySource);
				System.out.println("BBR" + myID + " did not wait for elevator " + e._elevatorId);	
			}
			else {
				System.out.println("BBR" + myID + " pushes D" + mySource);
				Elevator e = myBuilding.CallDown(mySource);
				System.out.println("BBR" + myID + " did not wait for elevator " + e._elevatorId);
			}
		}
	}
}
