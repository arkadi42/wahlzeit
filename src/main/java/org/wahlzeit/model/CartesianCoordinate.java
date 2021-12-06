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

    public double getPhi() {return this.asSphericCoordinate().getPhi();  }
    public double getTheta() {return this.asSphericCoordinate().getTheta(); }
    public double getRadius() {return this.asSphericCoordinate().getRadius();}

    //every set of doubles (x,y,z) is a valid class object execpt (0,0,0)
    public boolean assertClassInvariants() {
        if(x == 0 && y == 0 && z == 0) return false;
        return true;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assert assertClassInvariants();
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        //Precondition
        assert  assertClassInvariants();

        double radius = Math.sqrt(x*x + y*y + z*z);
        double theta = Math.acos(z / radius);
        double phi = Math.atan2(y, x);
        SphericCoordinate sp = new SphericCoordinate(phi, theta, radius);

        //Postcondition, ClassInvaritants
        assert sp.assertClassInvariants();
        return sp;
    }

    //returns the cartesian distance to a coordinate
    public double getCartesianDistance(Coordinate c){
        //Precondition
        assert (c != null);
        assert c.assertClassInvariants();

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

    public void readFrom(ResultSet rset) throws SQLException,IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();


        this.setX(rset.getDouble("x"));
        this.setY(rset.getDouble("y"));

        //Postcondition, Classinvariant
        assert assertClassInvariants();
    }



    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }

}
