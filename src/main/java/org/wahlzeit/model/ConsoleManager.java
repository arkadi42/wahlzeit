package org.wahlzeit.model;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class ConsoleManager extends ObjectManager {
    protected HashSet<Console>  consoles= new HashSet<>();  //HashSet of all consoles
    protected HashSet<ConsoleType> consoleTypes = new HashSet<>();  //Hashset of all consoleTypes

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return null;
    }

    public Console createConsole(String typeName){
        ConsoleType ft = getConsoleType(typeName);
        Console result = ft.createInstance();
        consoles.add(result);
        return result;
    }

    protected ConsoleType getConsoleType(String s){
        ConsoleType result = null;
        for(ConsoleType ct : consoleTypes){
            if(ct.typeName.equals(s)) {
                result = ct;
            }
        }
        if(result == null){
            result = new ConsoleType(s, this);
            consoleTypes.add(result);
        }
        return result;
    }
}
