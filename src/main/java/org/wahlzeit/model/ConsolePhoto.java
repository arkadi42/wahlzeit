package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsolePhoto extends Photo{
    public ConsolePhoto() {
        id = PhotoId.getNextId();
        incWriteCount();
    }

    /**
     *
     * @methodtype constructor
     */
    public ConsolePhoto(PhotoId myId) {
        id = myId;

        incWriteCount();
    }

    public ConsolePhoto(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

}
