import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class ElevatorTester {	
	public static void main(String[] args) {
		Scanner in = null;
		PrintStream out = null;	
		try {
			in = new Scanner(new File("/Users/yankeenjg/Documents/workspace/CPS210_Lab3/src/input.txt"));
			out = new PrintStream(new FileOutputStream("Elevator.log"));
		} catch (FileNotFoundException e) {
			System.out.println("#ERROR: file 'input.txt' not found or there was a problem creating 'Elevator.log'");
			e.printStackTrace();
		}
		System.setOut(out);

		int numFloors = in.nextInt();
		int numElevators = in.nextInt();
		int numRiders = in.nextInt();
		int maxOccupancy = in.nextInt();
		in.nextLine();
		System.out.println("#numFloors = " + numFloors + " numElevators = " + numElevators + " numRiders = " + numRiders + " maxOccupancy = " + maxOccupancy);
		
		Building building = new Building(numFloors, numElevators, maxOccupancy);
	
		for (int i = 0; i < numRiders; i++) {
			//BadlyBehavedElevatorRider rider = new BadlyBehavedElevatorRider(building, in);
			ElevatorRider rider = new ElevatorRider(building, in);
			Thread thr = new Thread(rider);
			thr.start();
		}
		
	}
}