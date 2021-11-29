package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.Persistent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate {
    //Spherical
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

    //Getter und Setter
    public double getX(){return this.asCartesianCoordinate().getX();}
    public double getY(){return this.asCartesianCoordinate().getY();}
    public double getZ(){return this.asCartesianCoordinate().getZ();}

    public double getPhi() {return phi;}
    public double getTheta() {return theta;}
    public double getRadius() {return radius;}

    public void setPhi(double phi) {this.phi = phi;}
    public void setTheta(double theta) {this.theta = theta;}
    public void setRadius(double radius) {
        if(radius == 0) throw new IllegalArgumentException("radius of a sphere can not be 0");
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
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    public void readFrom(ResultSet rset) throws SQLException {
        CartesianCoordinate cc = new CartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));
        SphericCoordinate sc = cc.asSphericCoordinate();
        this.setTheta(sc.getTheta());
        this.setPhi(sc.getPhi());
        this.setRadius(sc.getRadius());
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

}

