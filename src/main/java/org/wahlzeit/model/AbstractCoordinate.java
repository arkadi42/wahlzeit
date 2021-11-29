package org.wahlzeit.model;

import org.wahlzeit.services.Persistent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate implements Coordinate, Persistent {

    //Maximum Distance between 2 cartesian coordinates to still be considered equal
    protected final double EPSILON = 0.01;


    //abstract Getter
    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();
    public abstract double getTheta();
    public abstract double getPhi();
    public abstract double getRadius();


    //returns a equivalent cartesian coordinate
    public abstract CartesianCoordinate asCartesianCoordinate();

    //returns the cartesian distance to a coordinate
    public double getCartesianDistance(Coordinate c){
        CartesianCoordinate cc0 = this.asCartesianCoordinate();
        CartesianCoordinate cc1 = c.asCartesianCoordinate();

        double dx = cc1.getX() - cc0.getX();
        double dy = cc1.getY() - cc0.getY();
        double dz = cc1.getZ() - cc0.getZ();

        return Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
    }

    //return a equivalent spherical coordiante
    public abstract SphericCoordinate asSphericCoordinate();

    //computes the central angle between two points on a sphere by using the Vincenty formula
    public double getCentralAngle(Coordinate c){
        SphericCoordinate sp0 = this.asSphericCoordinate();
        SphericCoordinate sp1 = c.asSphericCoordinate();

        double latitude1 = Math.PI - sp0.getTheta();
        double longitude1 = sp0.getPhi();
        double latitude2 = Math.PI - sp1.getTheta();
        double longitude2 = sp1.getPhi();
        double deltaLatitude = Math.abs(Math.abs(latitude1) - Math.abs(latitude2));
        return Math.atan2(Math.sqrt(Math.pow(Math.cos(longitude2) * Math.sin(deltaLatitude), 2) + Math.pow(Math.cos(longitude1) *
                Math.sin(longitude2) - Math.sin(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude), 2)),
                (Math.sin(longitude1) * Math.sin(longitude2) + Math.cos(longitude1) * Math.cos(longitude2)* Math.cos(deltaLatitude)));
    }

    public boolean isEqual(Coordinate c){
        return((this == c) || (getCartesianDistance(c) < EPSILON));
    }

    public abstract void readFrom(ResultSet rset) throws SQLException;

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        CartesianCoordinate cc = this.asCartesianCoordinate();
        rset.updateDouble("x", cc.getX());
        rset.updateDouble("y", cc.getY());
        rset.updateDouble("z", cc.getZ());
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
