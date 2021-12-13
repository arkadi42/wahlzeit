package org.wahlzeit.model;

import org.wahlzeit.services.Persistent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate implements Coordinate, Persistent {

    //Maximum Distance between 2 cartesian coordinates to still be considered equal
    protected final double EPSILON = 0.01;

    public abstract void assertClassInvariants();

    //returns a equivalent cartesian coordinate
    public abstract CartesianCoordinate asCartesianCoordinate();

    //return a equivalent spherical coordiante
    public abstract SphericCoordinate asSphericCoordinate() throws ArithmeticException;


    //returns the cartesian distance to a coordinate
    public double getCartesianDistance(Coordinate c) throws IllegalArgumentException{
        return this.asCartesianCoordinate().getCartesianDistance(c);
    }


    //computes the central angle between two points on a sphere by using the Vincenty formula
    public double getCentralAngle(Coordinate c) throws IllegalArgumentException {
        return this.asSphericCoordinate().getCentralAngle(c);
    }

    public boolean isEqual(Coordinate c){
        assertClassInvariants();
        return((this == c) || (getCartesianDistance(c) < EPSILON));
    }

    public boolean equals(Object c){
        if(c instanceof  SphericCoordinate){
            return isEqual((SphericCoordinate) c);
        }
        else if(c instanceof  CartesianCoordinate){
            return isEqual((CartesianCoordinate) c);
        }
        return false;
    }

    public abstract void readFrom(ResultSet rset) throws SQLException;

    @Override
    public void writeOn(ResultSet rset) throws SQLException, IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();
        assertClassInvariants();

        CartesianCoordinate cc = this.asCartesianCoordinate();

        rset.updateDouble("x", cc.getX());
        rset.updateDouble("y", cc.getY());
        rset.updateDouble("z", cc.getZ());

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