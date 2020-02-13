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
        else if (status == UP) {
            if (floor > currentFloor) {
                if (floor > maxFloor) {
                    maxFloor = floor;
                }
            } 
            else {
                if (floor < minFloor) {
                    minFloor = floor;
                }
            }
        }
        else // status == DOWN
        {
            if (floor < currentFloor) {
                if (floor < minFloor) {
                    minFloor = floor;
                }

            } 
            else {
                if (floor > maxFloor) {
                    maxFloor = floor;
                }
            }
        }
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
                   minFloor = 11;
                   
                    if ( (maxFloor == 1) && (minFloor == 11) ){ // FINISH THIS IF. TODOOOOOOOOO.
                        status = IDLE;
                        myApp.updateElevatorStatus(currentFloor, "IDLE");
                    }
                    else {
                        minFloor = 1;
                        status = DOWN;
                        myApp.updateElevatorStatus(currentFloor, "DOWN" );
                    }
                    
                }
            }
            else // DOWN
            {
                if (currentFloor > minFloor){
                   currentFloor--;
                   myApp.updateElevatorStatus(currentFloor, "DOWN" );
                }
                else {
                   maxFloor = 1;
                    if ( (maxFloor == 1) && (minFloor == 11) ){ // FINISH THIS IF. TODOOOOOOOOO.
                        status = IDLE;
                        myApp.updateElevatorStatus(currentFloor, "IDLE");
                    }
                    else {
                        status = UP;
                        myApp.updateElevatorStatus(currentFloor, "UP" );
                    }
                }
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Elevator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        myApp.updateElevatorStatus(currentFloor, "IDLE");
    }
}