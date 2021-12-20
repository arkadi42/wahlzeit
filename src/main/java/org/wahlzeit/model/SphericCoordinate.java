package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.Persistent;

import javax.swing.text.html.HTMLDocument;
import java.awt.dnd.InvalidDnDOperationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    //Spherical
    private final double phi;
    private final double theta;
    private final double radius;


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

    public static Coordinate getSphericalCoordinate(double phi, double theta, double radius){
        Coordinate result = new SphericCoordinate(phi, theta, radius);
        int resultIndex = allCoordinates.indexOf(result);
        if(resultIndex == -1){
            Object lock = AbstractCoordinate.class;
            synchronized (lock){
                resultIndex = allCoordinates.indexOf(new SphericCoordinate(phi, theta, radius));
                if(resultIndex == -1){
                    allCoordinates.add(result);
                }
            }
        }
        resultIndex = allCoordinates.indexOf(result);
        return allCoordinates.get(resultIndex);
    }

    public double getPhi() {return phi;}
    public double getTheta() {return theta;}
    public double getRadius() {return radius;}

    public double getX(){
        return radius * Math.sin(theta) * Math.cos(phi);
    }
    public double getY(){  return radius * Math.sin(theta) * Math.sin(phi); }
    public double getZ(){
        return radius * Math.cos(theta);
    }

    public Coordinate setPhi(double phi) throws IllegalArgumentException{
        if(phi > Math.PI || phi < -Math.PI) throw new IllegalArgumentException("phi has to be between -Pi and Pi");
        return getSphericalCoordinate(phi, this.theta, this.radius);
    }
    public Coordinate setTheta(double theta) throws IllegalArgumentException{
        if(theta > Math.PI || theta < 0) throw new IllegalArgumentException("theta has to be between 0 and Pi");
        return getSphericalCoordinate(this.phi, theta, this.radius);

    }
    public Coordinate setRadius(double radius) throws IllegalArgumentException{
        if(radius <= 0) throw new IllegalArgumentException("radius of a sphere can not be <= 0");
        return getSphericalCoordinate(this.phi, this.theta, radius);
    }


    // This method should be always true after any method completes. Radius is limited to non negativ and non zero values
    // Theta ranges from 0 to PI (180 deg) and Phi from -PI to PI (-180 to 180 deg)
    public void assertClassInvariants() throws IllegalArgumentException{
        if (radius <= 0) throw new IllegalArgumentException("radius");
        else if (theta > Math.PI || theta < 0) throw new IllegalArgumentException("theta");
        else if (phi > Math.PI || phi < -Math.PI) throw new IllegalArgumentException("phi");
    }

    @Override
    public Coordinate asCartesianCoordinate() {
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
        Coordinate result = CartesianCoordinate.getCartesianCoordinate(getX(),getY(),getZ());

        //Postcondition, ClassInvariant
        try{
            result.assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            ((CartesianCoordinate)result).setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }

        return result;
    }

    @Override
    public Coordinate asSphericCoordinate() {
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
        return SphericCoordinate.getSphericalCoordinate(this.phi, this.theta, this.radius) ;
    }

    public void readFrom(ResultSet rset) throws SQLException,IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();


        Coordinate result = CartesianCoordinate.getCartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));

        //Postcondition, ClassInvariant
        try{
            result.assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }

    }

    @Override
    public int hashCode(){
        return Objects.hash(phi, theta, radius);
    }


}

