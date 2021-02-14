package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.parts.Arm;
import project.parts.Base;
import project.parts.Part;
import project.parts.payloads.Payload;
import project.utility.Common;

import java.util.Iterator;
import java.util.List;

public class Builder extends Logic
{
    @Override public void run ( Robot robot )
    {
        // get the serial number of robots for printing the messages to output.txt
        int robotSerialNo = (int) Common.get(robot, "serialNo");

        Robot robotToAdd = null;
        // flag to check if builder has attached anything on this step
        boolean flag = false;
        // flag to check if builder added a new robot to robots
        boolean flag2 = false;
        //flag to check if the storage is full and builder initiated stop
        boolean stop = false;
        synchronized (SimulationRunner.factory.productionLine.parts) {
            int size = SimulationRunner.factory.productionLine.parts.size();
            List<Part> parts = SimulationRunner.factory.productionLine.parts;
            System.out.printf("Robot %02d : Builder woke up, going back to work.%n", robotSerialNo);
            for (Iterator<Part> it1 = parts.iterator(); it1.hasNext();) {
                // if already performed this step, stop
                if(flag){
                    break;
                }
                Part p1 = it1.next();
                // iterate through production line
                for (Iterator<Part> it2 = parts.iterator(); it2.hasNext();){
                    Part p2 = it2.next();
                    // if the there exists a base and an arm
                    if((p1 instanceof Base) && Common.get(p1,"arm") == null && (p2 instanceof Arm)){
                        // attach the arm to the base
                        Common.set(p1, "arm", p2);
                        // remove the arm from the production line
                        it2.remove();
                        System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                        // set flag meaning completed the first possibility
                        flag = true;
                        break;
                    }
                    // if there exists a base-arm
                    else if((p1 instanceof Base) && Common.get(p1,"arm") != null && Common.get(p1,"payload") == null
                            && Common.get(p1,"logic") == null && (p2 instanceof Payload)) {
                        // attach a payload
                        Common.set(p1, "payload", p2);
                        // remove the payload from production line
                        it2.remove();
                        System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                        // set flag meaning completed the second possibility
                        flag = true;
                        break;
                    }
                    // if there exists a base-arm-payload
                    else if((p1 instanceof Base) && Common.get(p1,"arm") != null
                            && Common.get(p1,"payload") != null && Common.get(p1,"logic") == null && (p2 instanceof Logic)){
                        // if the part is builder and the payload is welder
                        if(p2.getClass().getSimpleName().equals("Builder") &&
                                Common.get(p1,"payload").getClass().getSimpleName().equals("Welder")){
                            // attach the builder chip
                            Common.set(p1, "logic", p2);
                            // remove the builder chip from production line
                            it2.remove();
                            System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                            // set flag meaning completed the third possibility
                            flag = true;
                            break;
                        }
                        // if the part is fixer and the payload is maintenanceKit
                        else if(p2.getClass().getSimpleName().equals("Fixer")&& Common.get(p1,"payload").getClass().getSimpleName().equals("MaintenanceKit")){
                            // attach the fixer chip
                            Common.set(p1, "logic", p2);
                            //remove fixer chip from production line
                            it2.remove();
                            System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                            // set flag meaning completed the third possibility
                            flag = true;
                            break;
                        }
                        // if there exists a inspector chip and payload is camera
                        else if(p2.getClass().getSimpleName().equals("Inspector")&&
                                Common.get(p1,"payload").getClass().getSimpleName().equals("Camera")){
                            // attach inspector chip
                            Common.set(p1, "logic", p2);
                            // remove inspector chip from production line
                            it2.remove();
                            System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                            // set flag meaning completed the third possibility
                            flag = true;
                            break;
                        }
                        // if there exists a supplier chip and payload is gripper
                        else if(p2.getClass().getSimpleName().equals("Supplier")&&
                                Common.get(p1,"payload").getClass().getSimpleName().equals("Gripper")){
                            // attach supplier chip
                            Common.set(p1, "logic", p2);
                            // remove supplier chip from production line
                            it2.remove();
                            System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", robotSerialNo);
                            // set flag meaning completed the third possibility
                            flag = true;
                            break;
                        }
                    }
                }
            }
            // if we did not attach anything
            if(!flag){
                // for every part in production line
                for (Iterator<Part> it3 = parts.iterator(); it3.hasNext();){
                    Part p3 = it3.next();
                    // check if there is a completed robot
                    if((p3 instanceof Base) && Common.get(p3,"arm") != null
                            && Common.get(p3,"payload") != null && Common.get(p3,"logic") != null) {
                        //if exists, hold this robot
                        robotToAdd = (Robot) p3;
                        // remove from production line
                        it3.remove();
                        break;
                    }
                }
            }
        }
        // redraw production line
        SimulationRunner.productionLineDisplay.repaint();
        // if holding a completed robot
        if(robotToAdd != null){
            synchronized (SimulationRunner.factory.robots) {
                // if the robots is not maxcapacity
                if(SimulationRunner.factory.robots.size() != SimulationRunner.factory.maxRobots){
                    // add it to working robot
                    SimulationRunner.factory.robots.add(robotToAdd);
                    // start the robot to work
                    new Thread( robotToAdd ).start() ;
                    // set flag signifying added robot to worker robots
                    flag2 = true;
                }
            }
            // redraw working robots
            SimulationRunner.robotsDisplay.repaint();
            synchronized (SimulationRunner.factory.storage.robots) {
                // if I did not add the robot to working robots and there is storage space
                if(!flag2 && SimulationRunner.factory.storage.robots.size() != SimulationRunner.factory.storage.maxCapacity){
                    // add the robot to storage space
                    SimulationRunner.factory.storage.robots.add(robotToAdd);
                }
                // if I did not add the robot to worker robots and storage space is full
                if(!flag2 && SimulationRunner.factory.storage.robots.size() == SimulationRunner.factory.storage.maxCapacity){
                    // initiate stop
                    SimulationRunner.factory.initiateStop();
                    // set stop flag
                    stop = true;
                }
            }
            // redraw storage space
            SimulationRunner.storageDisplay.repaint();
        }
        // if I did not complete anything and stop is not set
        if(!flag && !flag2 && !stop) {
            synchronized (SimulationRunner.factory.productionLine.parts){
                try {
                    System.out.printf("Robot %02d : Builder cannot build anything, waiting!%n", robotSerialNo);
                    // start waiting
                    SimulationRunner.factory.productionLine.parts.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}