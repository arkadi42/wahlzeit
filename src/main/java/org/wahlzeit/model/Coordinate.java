package org.wahlzeit.model;

public interface Coordinate {
     double getX();
     double getY();
     double getZ();
     double getTheta();
     double getPhi();
     double getRadius();
    Coordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate c) throws IllegalArgumentException;
    Coordinate asSphericCoordinate() throws ArithmeticException;
    double getCentralAngle(Coordinate c) throws IllegalArgumentException;
    boolean isEqual(Coordinate c);
    void assertClassInvariants() throws IllegalArgumentException;
}
