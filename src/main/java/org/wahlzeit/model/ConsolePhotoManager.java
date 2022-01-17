package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConsolePhotoManager extends PhotoManager {
    protected Map<PhotoId, ConsolePhoto> consolePhotoCache = new HashMap<PhotoId, ConsolePhoto>();

    protected Photo createObject(ResultSet rset) throws SQLException {
        return ConsolePhotoFactory.getInstance().createPhoto(rset);
    }
}
