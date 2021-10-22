package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class Location {
    protected Coordinate coordinate;

    public Location() {
        this.coordinate = new Coordinate();
    }

    public void readFrom(ResultSet rset) throws SQLException {
        coordinate.readFrom(rset);
    }

    public void writeOn(ResultSet rset) throws SQLException {
        coordinate.writeOn(rset);
    }

}
