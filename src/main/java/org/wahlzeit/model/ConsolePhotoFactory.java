package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"PhotoFactory", "ConsolePhotoFactory"}
)

public class ConsolePhotoFactory extends PhotoFactory{
    @PatternInstance(
            patternName = "Singleton",
            participants = {"ConsolePhotoFactory"}
    )
    private static boolean isInitialized = false;

    public static synchronized PhotoFactory getInstance(){
        if (!isInitialized){
            SysLog.logSysInfo("setting specialized ConsolePhotoFactory");
            PhotoFactory.setInstance(new ConsolePhotoFactory());
            isInitialized = true;
        }
        return (ConsolePhotoFactory) PhotoFactory.getInstance();
    }

    public static void initialize(){
        getInstance();
    }

    protected ConsolePhotoFactory(){

    }

    @Override
    public Photo createPhoto(){
        return new ConsolePhoto();
    }

    @Override
    public Photo createPhoto(PhotoId id){
        return new ConsolePhoto(id);
    }

    @Override
    public Photo createPhoto(ResultSet rs) throws SQLException{
        return new ConsolePhoto(rs);
    }
}
