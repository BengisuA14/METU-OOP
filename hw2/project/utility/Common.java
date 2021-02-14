package project.utility;

import project.components.Factory;
import project.parts.Arm;
import project.parts.Base;
import project.parts.Part;
import project.parts.logics.Builder;
import project.parts.logics.Fixer;
import project.parts.logics.Inspector;
import project.parts.logics.Supplier;
import project.parts.payloads.Camera;
import project.parts.payloads.Gripper;
import project.parts.payloads.MaintenanceKit;
import project.parts.payloads.Welder;

import java.lang.reflect.Field;
import java.util.Random;

public class Common
{
    public static Random random = new Random() ;

    public static synchronized Object get (Object object , String fieldName )
    {
        // This function retrieves (gets) the private field of an object by using reflection
        // In case the function needs to throw an exception, throw this: SmartFactoryException( "Failed: get!" )

        Field field = null;
        Object retrievedObject = null;
        try {
            //get the field of the object
            field = object.getClass().getDeclaredField(fieldName);
            // set it accessible since it is private
            field.setAccessible(true);
            // get the field
            retrievedObject = field.get(object);
        } catch (Exception e) {
            // throw exception in case of failure
            throw (new SmartFactoryException( "Failed: get!" ));
        }
        return retrievedObject;

    }

    public static synchronized void set ( Object object , String fieldName , Object value )
    {
        // TODO
        // This function modifies (sets) the private field of an object by using reflection
        // In case the function needs to throw an exception, throw this: SmartFactoryException( "Failed: set!" )

        Field field = null;
        try {
            //get the field of the object
            field = object.getClass().getDeclaredField(fieldName);
            // set it accessible since it is private
            field.setAccessible(true);
            // set the field
            field.set(object, value);
        } catch (Exception e) {
            // throw exception in case of failure
            throw (new SmartFactoryException( "Failed: set!" ));
        }

    }

    public static class PartFactory {
        public static Part producePart(String partName) {
            Part part = null;
            // create part with name given
            switch (partName) {
                case "Arm":
                    part = new Arm();
                    break;
                case "Camera":
                    part = new Camera();
                    break;
                case "Gripper":
                    part = new Gripper();
                    break;
                case "MaintenanceKit":
                    part = new MaintenanceKit();
                    break;
                case "Welder":
                    part = new Welder();
                    break;
                case "Builder":
                    part = new Builder();
                    break;
                case "Fixer":
                    part = new Fixer();
                    break;
                case "Supplier":
                    part = new Supplier();
                    break;
                case "Inspector":
                    part = new Inspector();
                    break;
            }
            return part;
        }

        public static Base produceBase(int nextSerialNo) {
            // create base
            Base base = new Base(nextSerialNo);
            return base;
        }
    }
}