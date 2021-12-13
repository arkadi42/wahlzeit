package org.wahlzeit.model;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate c) throws IllegalArgumentException;
    SphericCoordinate asSphericCoordinate() throws ArithmeticException;
    double getCentralAngle(Coordinate c) throws IllegalArgumentException;
    boolean isEqual(Coordinate c);
    void assertClassInvariants() throws IllegalArgumentException;
}
