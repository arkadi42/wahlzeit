package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsolePhotoManager extends PhotoManager {

    protected Photo createObject(ResultSet rset) throws SQLException {
        return ConsolePhotoFactory.getInstance().createPhoto(rset);
    }
}
