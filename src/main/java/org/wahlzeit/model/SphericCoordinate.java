package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.Persistent;

import java.awt.dnd.InvalidDnDOperationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    //Spherical
    private double phi;
    private double theta;
    private double radius;


    public SphericCoordinate(double phi, double theta, double radius){
        if(radius == 0) throw new IllegalArgumentException("radius of a sphere can not be 0");
        this.phi = phi;
        this.theta = theta;
        this.radius = Math.abs(radius);
    }

    public SphericCoordinate(){
        this.phi = 0;
        this.theta = 0;
        this.radius = 1;
    }

    public double getPhi() {return phi;}
    public double getTheta() {return theta;}
    public double getRadius() {return radius;}


    public void setPhi(double phi) throws IllegalArgumentException{
        if(phi > Math.PI || phi < -Math.PI) throw new IllegalArgumentException("phi has to be between -Pi and Pi");
        this.phi = phi;
    }
    public void setTheta(double theta) throws IllegalArgumentException{
        if(theta > Math.PI || theta < 0) throw new IllegalArgumentException("theta has to be between 0 and Pi");
        this.theta = theta;
    }
    public void setRadius(double radius) throws IllegalArgumentException{
        if(radius <= 0) throw new IllegalArgumentException("radius of a sphere can not be <= 0");
        this.radius = radius;
    }


    // This method should be always true after any method completes. Radius is limited to non negativ and non zero values
    // Theta ranges from 0 to PI (180 deg) and Phi from -PI to PI (-180 to 180 deg)
    public void assertClassInvariants() throws IllegalArgumentException{
        if (radius <= 0) throw new IllegalArgumentException("radius");
        else if (theta > Math.PI || theta < 0) throw new IllegalArgumentException("theta");
        else if (phi > Math.PI || phi < -Math.PI) throw new IllegalArgumentException("phi");
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        //Precondition
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                setRadius(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                setTheta(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                setPhi(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }

        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        CartesianCoordinate cc = new CartesianCoordinate(x, y, z);

        //Postcondition, ClassInvariant
        try{
            cc.assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            cc.setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }
        return cc;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                setRadius(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                setTheta(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                setPhi(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }
        return this;
    }

    public double getCentralAngle(Coordinate c) throws IllegalArgumentException {
        SphericCoordinate sp0 = new SphericCoordinate();
        SphericCoordinate sp1 = new SphericCoordinate();
        //Precondition
        assert (c != null);
        assertClassInvariants();


        try {
            sp0 = this.asSphericCoordinate();
            sp1 = c.asSphericCoordinate();
        }
        catch (ArithmeticException e){
            System.err.println("ArithmeticException: " + e.getMessage());
            e.printStackTrace();
        }

        double latitude1 = Math.PI - sp0.getTheta();
        double longitude1 = sp0.getPhi();
        double latitude2 = Math.PI - sp1.getTheta();
        double longitude2 = sp1.getPhi();
        double deltaLatitude = Math.abs(Math.abs(latitude1) - Math.abs(latitude2));

        double result = Math.atan2(Math.sqrt(Math.pow(Math.cos(longitude2) * Math.sin(deltaLatitude), 2) + Math.pow(Math.cos(longitude1) *
                        Math.sin(longitude2) - Math.sin(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude), 2)),
                (Math.sin(longitude1) * Math.sin(longitude2) + Math.cos(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude)));

        //Postcondition, ClassInvariants
        assert (result > -Math.PI / 2);
        assert (result < Math.PI / 2);

        return result;
    }

    public void readFrom(ResultSet rset) throws SQLException,IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();
        try {
            CartesianCoordinate cc = new CartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));

            SphericCoordinate sc = cc.asSphericCoordinate();
            this.setTheta(sc.getTheta());
            this.setPhi(sc.getPhi());
            this.setRadius(sc.getRadius());
        }
        catch(ArithmeticException e){
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        //Postcondition, ClassInvariant

        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                setRadius(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                setTheta(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                setPhi(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(phi, theta, radius);
    }


}

