import java.util.Scanner;

public class ElevatorRider implements Runnable {
	
	public int myID;
	public int mySource;
	protected int myTarget;
	protected static Building myBuilding;
	protected static volatile Scanner myScanner;
	
	public ElevatorRider(Building building, Scanner in) {
		myBuilding = building;		
		myScanner = in;
	}
	
	private static synchronized boolean hasNextLine() {
		return myScanner.hasNextLine();
	}

	public void run() {
		while (hasNextLine()) {	
			
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
				System.out.println("R" + myID + " pushes U" + mySource);
				Elevator e = myBuilding.CallUp(mySource);
				System.out.println("#R" + myID + " attempts to enter E" + e._elevatorId + " on F" + mySource);
				while (!e.Enter()) {
					System.out.println("#R" + myID + " fails to enter E" + e._elevatorId + " on F" + mySource);
					System.out.println("R" + myID + " pushes U" + mySource);
					e = myBuilding.CallUp(mySource);
					System.out.println("#R" + myID + " attempts to enter E" + e._elevatorId + " on F" + mySource);
				}
				System.out.println("R" + myID + " enters E" + e._elevatorId + " on F" + mySource);
				System.out.println("R" + myID + " pushes E" + e._elevatorId + "B" + myTarget);
				e.RequestFloor(myTarget);
				System.out.println("R" + myID + " exits E" + e._elevatorId + " on F" + myTarget);
				e.Exit();
			}
			else {
				System.out.println("R" + myID + " pushes D" + mySource);
				Elevator e = myBuilding.CallDown(mySource);
				System.out.println("#R" + myID + " attempts to enter E" + e._elevatorId + " on F" + mySource);
				while (!e.Enter()) {
					System.out.println("#R" + myID + " fails to enter E" + e._elevatorId + " on F" + mySource);
					System.out.println("R" + myID +  " pushes D" + mySource);
					e = myBuilding.CallDown(mySource);
					System.out.println("#R" + myID + " attempts to enter E" + e._elevatorId + " on F" + mySource);
				}
				System.out.println("R" + myID + " enters E" + e._elevatorId + " on F" + mySource);
				System.out.println("R" + myID + " pushes E" + e._elevatorId + "B" + myTarget);
				e.RequestFloor(myTarget);
				System.out.println("R" + myID + " exits E" + e._elevatorId + " on F" + myTarget);
				e.Exit();
			}
		}
	}
}

