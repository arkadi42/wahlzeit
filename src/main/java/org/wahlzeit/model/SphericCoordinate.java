package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends DataObject implements Coordinate{
    private final static double EPSILON = 0.000001;

    private double phi;
    private double theta;
    private double radius;

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

    public double getPhi() {
        return phi;
    }

    public double getTheta() {
        return theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);
        return new CartesianCoordinate(x, y ,z);
    }

    @Override
    public double getCartesianDistance(Coordinate c) {
        CartesianCoordinate cc = c.asCartesianCoordinate();
        return this.asCartesianCoordinate().getCartesianDistance(cc);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    public double getDistance(Coordinate c){
        return radius * getCentralAngle(c);
    }

    @Override
    public double getCentralAngle(Coordinate c) {
        double latitude1 = Math.PI - this.theta;
        double longitude1 = this.phi;
        double latitude2 = Math.PI - c.asSphericCoordinate().theta;
        double longitude2 = c.asSphericCoordinate().phi;
        double deltaLatitude = Math.abs(Math.abs(latitude1) - Math.abs(latitude2));

        return Math.atan(Math.sqrt(Math.pow(Math.cos(longitude2) * Math.sin(deltaLatitude), 2) + Math.pow(Math.cos(longitude1) *
                Math.sin(longitude2) - Math.sin(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude), 2)) /
                (Math.sin(longitude1) * Math.sin(longitude2) + Math.cos(longitude1) * Math.cos(longitude2)* Math.cos(deltaLatitude)));
    }

    @Override
    public boolean isEqual(Coordinate c) {
        return (this == c) || this.getCentralAngle(c) < EPSILON;
    }

    @Override
    public boolean equals(Object c){
        if(c instanceof  SphericCoordinate){
            return isEqual((SphericCoordinate) c);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(phi, theta, radius);
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        CartesianCoordinate cc = new CartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));
        SphericCoordinate sc = cc.asSphericCoordinate();
        setPhi(sc.getPhi());
        setTheta(sc.getTheta());
        setRadius(sc.getRadius());
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        CartesianCoordinate cc = this.asCartesianCoordinate();
        rset.updateDouble("x", cc.getX());
        rset.updateDouble("y", cc.getY());
        rset.updateDouble("z",cc.getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}

