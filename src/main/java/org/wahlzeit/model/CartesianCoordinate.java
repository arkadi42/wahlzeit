package org.wahlzeit.model;

import java.sql.*;
import java.net.*;
import java.util.Objects;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class CartesianCoordinate extends AbstractCoordinate{
    //Cartesian
    private double x;
    private double y;
    private double z;

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
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){  this.z = z;
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


    //every set of doubles (x,y,z) is a valid class object execpt (0,0,0)
    public void assertClassInvariants() throws IllegalArgumentException {
        if(x == 0 && y == 0 && z == 0) {
            throw new IllegalArgumentException("(0,0,0) is not a valid coordinate. ");
        };
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() throws ArithmeticException{
        SphericCoordinate sp = new SphericCoordinate();

                //Precondition
        try{
            assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            setX(1.0);
            System.err.println("IllegalArgumentException: " + e.getMessage() + "Coordinate has been set to (1,0,0).");
        }


        sp.setRadius(Math.sqrt(x*x + y*y + z*z));
        sp.setTheta(Math.acos(z / sp.getRadius()));
        sp.setPhi(Math.atan2(y, x));


        //Postcondition, ClassInvaritants

        try{
            sp.assertClassInvariants();
        }
        catch (IllegalArgumentException e){
            if(e.getMessage().equals("radius")){
                sp.setRadius(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "Radius has been set to 1");
            }
            if(e.getMessage().equals("theta")){
                sp.setTheta(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "theta has been set to 1");
            }
            if(e.getMessage().equals("phi")){
                sp.setPhi(1.0);
                System.err.println("IllegalArgumentException: " + e.getMessage() + "phi has been set to 1");
            }
        }
        return sp;
    }

    //returns the cartesian distance to a coordinate
    public double getCartesianDistance(Coordinate c) throws IllegalArgumentException{
        //Precondition
        if(c == null) throw new IllegalArgumentException();
        c.assertClassInvariants();

        CartesianCoordinate cc0 = this.asCartesianCoordinate();
        CartesianCoordinate cc1 = c.asCartesianCoordinate();

        double dx = cc1.getX() - cc0.getX();
        double dy = cc1.getY() - cc0.getY();
        double dz = cc1.getZ() - cc0.getZ();

        double result = Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));

        //Postcondition
        assert (result >= 0);

        return result;
    }

    public void readFrom(ResultSet rset) throws SQLException, IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();

        this.setX(rset.getDouble("x"));
        this.setY(rset.getDouble("y"));
        this.setZ(rset.getDouble("z"));
        //Postcondition, Classinvariant
        assertClassInvariants();

    }




    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }

}
