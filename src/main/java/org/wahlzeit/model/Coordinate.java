package org.wahlzeit.model;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate c);
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate c);
    boolean isEqual(Coordinate c);
    boolean assertClassInvariants();
}
