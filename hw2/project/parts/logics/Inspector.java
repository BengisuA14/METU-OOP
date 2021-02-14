package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.utility.Common;

import java.util.Iterator;

public class Inspector extends Logic
{
    @Override public void run ( Robot robot )
    {
        // get the serial number of robots for printing the messages to output.txt
        int robotSerialNo = (int) Common.get(robot, "serialNo");

        Robot brokenRobot = null;

        // synchronized block to check if any robot is broken in robots
        synchronized (SimulationRunner.factory.robots) {

            for (Iterator<Robot> iterator = SimulationRunner.factory.robots.iterator(); iterator.hasNext();) {
                Robot r = iterator.next();

                // if a part of the robot is broken
                if(Common.get(r, "arm") == null
                        || Common.get(r, "payload") == null
                        || Common.get(r, "logic") == null ) {
                    // declare it a broken robot
                    brokenRobot = r;
                    break;
                }
            }
        }
        SimulationRunner.robotsDisplay.repaint();
        synchronized (SimulationRunner.factory.brokenRobots) {
            // if there exists a broken robot
            if (brokenRobot != null  ) {
                // get serial no for printing to output.txt
                int brokenRobotSerialNo = (int) Common.get(brokenRobot, "serialNo");
                System.out.printf( "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n", robotSerialNo, brokenRobotSerialNo);
                // add it to broken robots
                SimulationRunner.factory.brokenRobots.add(brokenRobot);
                System.out.printf( "Robot %02d : Notifying waiting fixers.%n", robotSerialNo);
                // notify fixers that there is broken robot waiting to be fixed
                SimulationRunner.factory.brokenRobots.notifyAll();
            }
        }

    }
}