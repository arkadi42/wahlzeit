package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.Persistent;

import java.awt.dnd.InvalidDnDOperationException;
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

    // This method should be always true after any method completes. Radius is limited to non negativ and non zero values
    // Theta ranges from 0 to PI (180 deg) and Phi from -PI to PI (-180 to 180 deg)
    public boolean assertClassInvariants(){
        if (radius <= 0) return false;
        else if (theta > Math.PI || theta < 0) return false;
        else if (phi > Math.PI || phi < -Math.PI) return false;
        else return true;
    }

    public void setPhi(double phi) {this.phi = phi;}
    public void setTheta(double theta) {this.theta = theta;}
    public void setRadius(double radius) {
        if(radius == 0) throw new IllegalArgumentException("radius of a sphere can not be 0");
        this.radius = radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        //Precondition
        assert assertClassInvariants();

        double x = radius * Math.sin(theta) * Math.cos(phi);
        double y = radius * Math.sin(theta) * Math.sin(phi);
        double z = radius * Math.cos(theta);

        CartesianCoordinate cc = new CartesianCoordinate(x, y, z);

        //Postcondition, ClassInvariant
        assert  cc.assertClassInvariants();
        return cc;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assert assertClassInvariants();
        return this;
    }

    public double getCentralAngle(Coordinate c) {
        //Precondition
        assert (c != null);
        assert c.assertClassInvariants();

        SphericCoordinate sp0 = this.asSphericCoordinate();
        SphericCoordinate sp1 = c.asSphericCoordinate();

        double latitude1 = Math.PI - sp0.getTheta();
        double longitude1 = sp0.getPhi();
        double latitude2 = Math.PI - sp1.getTheta();
        double longitude2 = sp1.getPhi();
        double deltaLatitude = Math.abs(Math.abs(latitude1) - Math.abs(latitude2));

        double result = Math.atan2(Math.sqrt(Math.pow(Math.cos(longitude2) * Math.sin(deltaLatitude), 2) + Math.pow(Math.cos(longitude1) *
                        Math.sin(longitude2) - Math.sin(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude), 2)),
                (Math.sin(longitude1) * Math.sin(longitude2) + Math.cos(longitude1) * Math.cos(longitude2) * Math.cos(deltaLatitude)));

        //Postcondition, ClassInvariants
        assert (result > -Math.PI / 2);
        assert (result < Math.PI / 2);
        assert assertClassInvariants();

        return result;
    }

    public void readFrom(ResultSet rset) throws SQLException,IllegalArgumentException {
        //Precondition
        if(rset == null) throw new IllegalArgumentException();
        CartesianCoordinate cc = new CartesianCoordinate(rset.getDouble("x"), rset.getDouble("y"), rset.getDouble("z"));

        SphericCoordinate sc = cc.asSphericCoordinate();
        this.setTheta(sc.getTheta());
        this.setPhi(sc.getPhi());
        this.setRadius(sc.getRadius());

        //Postcondition, ClassInvariant
        assert assertClassInvariants();
    }

    @Override
    public int hashCode(){
        return Objects.hash(phi, theta, radius);
    }


}

