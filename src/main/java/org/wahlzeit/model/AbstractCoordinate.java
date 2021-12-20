package org.wahlzeit.model;

import org.wahlzeit.services.Persistent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public abstract class AbstractCoordinate implements Coordinate, Persistent {

    //list of all coordinate value objects
    protected static LinkedList<Coordinate> allCoordinates = new LinkedList<Coordinate>();

    //Maximum Distance between 2 cartesian coordinates to still be considered equal
    protected final double EPSILON = 0.01;

    public abstract void assertClassInvariants();

    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();
    public abstract double getTheta();
    public abstract double getPhi();
    public abstract double getRadius();

    //returns a equivalent cartesian coordinate
    public abstract Coordinate asCartesianCoordinate();

    //return a equivalent spherical coordiante
    public abstract Coordinate asSphericCoordinate() throws ArithmeticException;


    //returns the cartesian distance to a coordinate
    public double getCartesianDistance(Coordinate c) throws IllegalArgumentException{

        //Precondition
        if(c == null) throw new IllegalArgumentException();
        c.assertClassInvariants();

        double dx = c.getX() - this.getX();
        double dy = c.getY() - this.getY();
        double dz = c.getZ() - this.getZ();

        double result = Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));

        //Postcondition
        assert (result >= 0);

        return result;
    }


    //computes the central angle between two points on a sphere by using the Vincenty formula
    public double getCentralAngle(Coordinate c) throws IllegalArgumentException {
        double latitude1 = Math.PI - this.getTheta();
        double longitude1 = this.getPhi();
        double latitude2 = Math.PI - c.getTheta();
        double longitude2 = c.getPhi();
        double deltaLatitude = Math.abs(Math.abs(latitude1) - Math.abs(latitude2));

        double result = Math.atan2(Math.sqrt(Math.pow(Math.cos(longitude2) * Math.sin(deltaLatitude), 2) + Math.pow(Math.cos(longitude1) *
                        Math.sin(longitude2) - Math.sin(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude), 2)),
                (Math.sin(longitude1) * Math.sin(longitude2) + Math.cos(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude)));

        //Postcondition, ClassInvariants
        assert (result > -Math.PI / 2);
        assert (result < Math.PI / 2);

        return result;
    }

    public boolean isEqual(Coordinate c){
        assertClassInvariants();
        return((this == c) || (getCartesianDistance(c) < EPSILON));
    }

    public boolean equals(Object c){
        if(c == null) return false;
        if(c instanceof  Coordinate){
            return isEqual((Coordinate) c);
        }
        return false;
    }

    public abstract void readFrom(ResultSet rset) throws SQLException;

    @Override
    public void writeOn(ResultSet rset) throws SQLException, IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();
        assertClassInvariants();

        rset.updateDouble("x", this.getX());
        rset.updateDouble("y", this.getY());
        rset.updateDouble("z", this.getZ());

        assertClassInvariants();
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void incWriteCount() {

    }

    @Override
    public void resetWriteCount() {

    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
