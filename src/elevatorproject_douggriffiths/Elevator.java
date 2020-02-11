package elevatorproject_douggriffiths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author doug
 */
public class Elevator extends Thread {

    // CONSTANTS
    private final int IDLE = 1;
    private final int UP = 2;
    private final int DOWN = 3;
    
    private GUI myApp;
    private int status = IDLE;
    private int currentFloor = 1; // set to one to get started
    private int maxFloor = 11;
    private int minFloor = 1;
    
    public Elevator(GUI a)
    {
        myApp = a;
        
    }
    
    public synchronized int getCurrentFloor()
    {
        return currentFloor;
    }
    
    public synchronized void calledToFloor(int floor)
    {
        if (status == IDLE) {
            if (floor > currentFloor) {
                status = UP;
                maxFloor = floor;
                myApp.updateElevatorStatus(currentFloor, "UP");
            }
            else if (floor < currentFloor) {
                status = DOWN;
                minFloor = floor;
                myApp.updateElevatorStatus(currentFloor, "DOWN");
            }

        }
        // status - up and passed
        // status - up and not passed
        // status - down and passed
        // status - down and not passed
        // if current floor is greater than max floor, less than min floor.
    }
    
    public void run() {
        
        while (myApp.continueRunning()) // check with GUI App to see if running should continue
        {
            if (status == IDLE) {
                 myApp.updateElevatorStatus(currentFloor, "IDLE" );
            }
            else if (status == UP) {
                if (currentFloor < maxFloor) {
                   currentFloor++;
                   myApp.updateElevatorStatus(currentFloor, "UP" );
                }
                else {
                    status = IDLE;
                    myApp.updateElevatorStatus(currentFloor, "IDLE" );
                }
            }
            else // DOWN
            {
                if (currentFloor > minFloor){
                   currentFloor--;
                   myApp.updateElevatorStatus(currentFloor, "DOWN" );
                }
                else {
                    status = IDLE;
                    myApp.updateElevatorStatus(currentFloor, "IDLE" );
                }
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Elevator.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
}