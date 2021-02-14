package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.parts.Part;
import project.utility.Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Supplier extends Logic
{
    @Override public void run ( Robot robot )
    {
        // Following messages are appropriate for this class
        // System.out.printf( "Robot %02d : Supplying a random part on production line.%n", ...);
        // System.out.printf( "Robot %02d : Production line is full, removing a random part from production line.%n", ...);
        // System.out.printf( "Robot %02d : Waking up waiting builders.%n", ...);

        // get the serial number of robots for printing the messages to output.txt
        int robotSerialNo = (int) Common.get(robot, "serialNo");
        String[] partSelection = {"Arm", "Camera", "Gripper", "MaintenanceKit", "Welder", "Builder", "Fixer", "Supplier", "Inspector", "Base" };
        // generate random number generator for creation of parts
        Random random = new Random();

        synchronized (SimulationRunner.factory.productionLine.parts) {
            // Check if production line has space
            if(SimulationRunner.factory.productionLine.maxCapacity != SimulationRunner.factory.productionLine.parts.size()){
                // generate random number
                int randomPart = random.nextInt(10);
                // if random number is 9, create base
                if(randomPart == 9){
                    Part part = SimulationRunner.factory.createBase();
                    // add base to production line
                    SimulationRunner.factory.productionLine.parts.add(part);
                }
                else{
                    //create random part
                    Part part = SimulationRunner.factory.createPart(partSelection[randomPart]);
                    //add part to production line
                    SimulationRunner.factory.productionLine.parts.add(part);
                }
                System.out.printf( "Robot %02d : Supplying a random part on production line.%n", robotSerialNo);
            }
            else {
                // if production line is full
                // generate random number for removing part from production line
                int randomObjectToRemove = random.nextInt(SimulationRunner.factory.productionLine.maxCapacity);
                int i = 0;
                for (Iterator<Part> iterator = SimulationRunner.factory.productionLine.parts.iterator(); iterator.hasNext();) {
                    iterator.next();
                    if(i == randomObjectToRemove) {
                        // remove the random part
                        iterator.remove();
                        break;
                    }
                    i++;
                }
                System.out.printf( "Robot %02d : Production line is full, removing a random part from production line.%n", robotSerialNo);
            }
            System.out.printf( "Robot %02d : Waking up waiting builders.%n", robotSerialNo);
            // notify the builder that there are supplies
            SimulationRunner.factory.productionLine.parts.notifyAll();

        }
        // redraw
        SimulationRunner.productionLineDisplay.repaint() ;

    }
}