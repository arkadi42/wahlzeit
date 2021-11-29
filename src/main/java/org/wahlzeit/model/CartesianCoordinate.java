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
        this.x = 0;
        this.y = 0;
        this.z = 0;
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

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(x*x + y*y + z*z);
        double theta = Math.acos(z / radius);
        double phi = Math.atan2(y, x);
        return new SphericCoordinate(phi, theta, radius);
    }

    public void readFrom(ResultSet rset) throws SQLException {

        this.setX(rset.getDouble("x"));
        this.setY(rset.getDouble("y"));
        this.setZ(rset.getDouble("z"));
    }

    @Override
    public boolean equals(Object c){
        if(c instanceof  CartesianCoordinate){
            return isEqual((CartesianCoordinate) c);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }

}
