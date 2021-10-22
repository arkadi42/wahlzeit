package org.wahlzeit.model;

import java.sql.*;
import java.net.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.*;

public class Coordinate extends DataObject {
    private double x;
    private double y;
    private double z;

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        x = rset.getDouble("x");
        y = rset.getDouble("y");
        z = rset.getDouble("z");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("x", x);
        rset.updateDouble("y", y);
        rset.updateDouble("z", z);


    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
