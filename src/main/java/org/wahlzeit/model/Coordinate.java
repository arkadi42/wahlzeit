package org.wahlzeit.model;

import java.sql.*;
import java.net.*;
import java.util.Objects;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class Coordinate extends DataObject {
    private final static double EPSILON = 0.01;         //max distance that 2 Coordinates can have and still be "equal"

    private double x;       //x Coordinate
    private double y;       //y Coordinate
    private double z;       //z Coordinate

    public Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Coordinate(){
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
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    public boolean isEqual(Coordinate c) {
        return((this == c) || (getDistance(c) < EPSILON));
    }

    @Override
    public boolean equals(Object c){
        if(c instanceof  Coordinate){
            return isEqual((Coordinate) c);
        }
        return false;
    }

    public double getDistance(Coordinate c){
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
        x = rset.getDouble("x");
        y = rset.getDouble("y");
        z = rset.getDouble("z");
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
