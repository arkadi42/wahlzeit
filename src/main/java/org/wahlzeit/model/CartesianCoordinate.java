package org.wahlzeit.model;

import java.sql.*;
import java.net.*;
import java.util.Objects;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class CartesianCoordinate extends DataObject implements Coordinate{
    private final static double EPSILON = 0.01;         //max distance that 2 Coordinates can have and still be "equal"

    private double x;       //x Coordinate
    private double y;       //y Coordinate
    private double z;       //z Coordinate

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

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
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

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate c) {
        return this.getDistance(c.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(x*x + y*y + z*z);
        double theta = Math.acos(z / radius);
        double phi = Math.atan2(y, x);


        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCentralAngle(Coordinate c) {
        SphericCoordinate sc = c.asSphericCoordinate();
        return getCentralAngle(sc);
    }

    public boolean isEqual(Coordinate c) {
        return((this == c) || (getCartesianDistance(c) < EPSILON));
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

    public double getDistance(CartesianCoordinate c){
        double dx = c.x - this.x;
        double dy = c.y - this.y;
        double dz = c.z - this.z;
        return Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
    }
    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        setX(rset.getDouble("x"));
        setY(rset.getDouble("y"));
        setY(rset.getDouble("z"));
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("x", x);
        rset.updateDouble("y", y);
        rset.updateDouble("z", z);


    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
