package org.wahlzeit.model;

import java.sql.*;
import java.net.*;
import java.util.Objects;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class CartesianCoordinate extends AbstractCoordinate{
    //Cartesian
    private final double x;
    private final double y;
    private final double z;

    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public CartesianCoordinate(){
        this.x = 1;
        this.y = 1;
        this.z = 1;
    }

    //Getter und Setter

    public static Coordinate getCartesianCoordinate(double x, double y, double z){
        Coordinate result = new CartesianCoordinate(x,y,z);
        int resultIndex = allCoordinates.indexOf(result);
        if(resultIndex == -1){
            Object lock = AbstractCoordinate.class;
            synchronized (lock){
                resultIndex = allCoordinates.indexOf(new CartesianCoordinate(x,y,z));
                if(resultIndex == -1){

                    allCoordinates.add(result);
                }
            }
        }
        resultIndex = allCoordinates.indexOf(result);
        return allCoordinates.get(resultIndex);
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
    public double getRadius(){return Math.sqrt(x*x + y*y + z*z);}
    public double getTheta(){return Math.acos(z / getRadius());}
    public double getPhi(){return Math.atan2(y, x);}

    public Coordinate setX(double x){
        return getCartesianCoordinate(x, this.y, this.z);
    }
    public Coordinate setY(double y){
        return getCartesianCoordinate(this.x, y, this.z);
    }
    public Coordinate setZ(double z){
        return getCartesianCoordinate(this.x, this.y, z);
    }


    //every set of doubles (x,y,z) is a valid class object execpt (0,0,0)
    public void assertClassInvariants() throws IllegalArgumentException {
        if(x == 0 && y == 0 && z == 0) {
            throw new IllegalArgumentException("(0,0,0) is not a valid coordinate. ");
        };
    }

    @Override
    public Coordinate asCartesianCoordinate() {
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }
        return getCartesianCoordinate(this.x, this.y, this.z);
    }

    @Override
    public Coordinate asSphericCoordinate() throws ArithmeticException{
                //Precondition
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }

        Coordinate result = SphericCoordinate.getSphericalCoordinate(getPhi(), getTheta(), getRadius());
        //Postcondition, ClassInvaritants

        try{
            result.assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                ((SphericCoordinate)result).setRadius(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                ((SphericCoordinate)result).setTheta(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                ((SphericCoordinate)result).setPhi(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }
        return result;
    }

    public void readFrom(ResultSet rset) throws SQLException, IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();
        getCartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));
        //Postcondition, Classinvariant
        assertClassInvariants();

    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }

}
