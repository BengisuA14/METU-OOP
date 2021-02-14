package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.utility.Common;

import java.util.Iterator;

public class Fixer extends Logic
{
    @Override public void run ( Robot robot )
    {

        // get the serial number of robots for printing the messages to output.txt
        int robotSerialNo = (int) Common.get(robot, "serialNo");

        Robot brokenRobot = null;
        synchronized (SimulationRunner.factory.brokenRobots) {
            try {
                System.out.printf("Robot %02d : Nothing to fix, waiting!%n", robotSerialNo);
                // wait for notification from inspector
                SimulationRunner.factory.brokenRobots.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // if there exists a broken part
            if(SimulationRunner.factory.brokenRobots.isEmpty() == false) {
                System.out.printf("Robot %02d : Fixer woke up, going back to work.%n", robotSerialNo);
                Iterator<Robot> it = SimulationRunner.factory.brokenRobots.iterator();
                // get the first broken object
                brokenRobot = it.next();
                // remove the first broken object from brokenrobots
                it.remove();
            }

        }
        // if there exists a broken object
        if(brokenRobot != null){

            synchronized (brokenRobot) {
                // get the serial no for printing
                int brokenRobotSerialNo = (int) Common.get(brokenRobot, "serialNo");
                // if payload is broken
                if (Common.get(brokenRobot, "payload") == null) {
                    // if it is a Builder, add a Welder
                    if(Common.get(brokenRobot, "logic").getClass().getSimpleName().equals("Builder") ) {
                        Common.set(brokenRobot, "payload", SimulationRunner.factory.createPart( "Welder" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if it is an Inspector, add a Camera
                    else if(Common.get(brokenRobot, "logic").getClass().getSimpleName().equals("Inspector") ) {
                        Common.set(brokenRobot, "payload", SimulationRunner.factory.createPart( "Camera" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if it is an Fixer, add a MaintenanceKit
                    else if(Common.get(brokenRobot, "logic").getClass().getSimpleName().equals("Fixer") ) {
                        Common.set(brokenRobot, "payload", SimulationRunner.factory.createPart( "MaintenanceKit" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if it is an Supplier, add a Gripper
                    else if(Common.get(brokenRobot, "logic").getClass().getSimpleName().equals("Supplier") ) {
                        Common.set(brokenRobot, "payload", SimulationRunner.factory.createPart( "Gripper" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                }
                // if logic is broken
                else if (Common.get(brokenRobot, "logic") == null) {
                    // if the payload is Welder, add a builder chip
                    if(Common.get(brokenRobot, "payload").getClass().getSimpleName().equals("Welder") ) {
                        Common.set(brokenRobot, "logic", SimulationRunner.factory.createPart( "Builder" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if the payload is Camera, add a inspector chip
                    else if(Common.get(brokenRobot, "payload").getClass().getSimpleName().equals("Camera") ) {
                        Common.set(brokenRobot, "logic", SimulationRunner.factory.createPart( "Inspector" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if the payload is MaintenanceKit, add a fixer chip
                    else if(Common.get(brokenRobot, "payload").getClass().getSimpleName().equals("MaintenanceKit") ) {
                        Common.set(brokenRobot, "logic", SimulationRunner.factory.createPart( "Fixer" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                    // if the payload is Gripper, add a Supplier chip
                    else if(Common.get(brokenRobot, "payload").getClass().getSimpleName().equals("Gripper") ) {
                        Common.set(brokenRobot, "logic", SimulationRunner.factory.createPart( "Supplier" ));
                        System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                        // wake up the fixed robot
                        brokenRobot.notify();
                    }
                }
                // if arm is broken fix it
                else if (Common.get(brokenRobot, "arm") == null) {
                    Common.set(brokenRobot, "arm", SimulationRunner.factory.createPart( "Arm" ));
                    System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", robotSerialNo, brokenRobotSerialNo);
                    // wake up the fixed robot
                    brokenRobot.notify();
                }
                else {
                    // Another fixer already fixed the robot
                    System.out.printf("Robot %02d : Nothing to fix, waiting!%n", robotSerialNo);
                }

            }
        }
        //draw
        SimulationRunner.robotsDisplay.repaint();

    }
}