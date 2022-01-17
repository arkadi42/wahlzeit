package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class ConsoleType extends DataObject {

    protected ConsoleManager consoleManager;
    protected HashSet<Console> instances = new HashSet<Console>();
    protected ConsoleType superType = null;
    protected HashSet<ConsoleType> subTypes = new HashSet<ConsoleType>();
    protected String typeName;

    public ConsoleType(String s){
        typeName = s;
    }
    public ConsoleType(String s, ConsoleManager m){
        typeName = s;
        consoleManager = m;
    }

    public Console createInstance(){
        Console instance = new Console(this);
        instances.add(instance);
        return instance;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public void setConsoleManager(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public ConsoleType getSuperType(){
        return superType;
    }

    public void setSuperType(ConsoleType ct){
        superType = ct;
    }

    public Iterator<ConsoleType> getSubTypeIterator(){
        return subTypes.iterator();
    }

    public void addSubType(ConsoleType ft){
        assert (ft != null) : "tried to set null sub-type";
        assert (!ft.isSubType(this)) : "tried to create circular SubType hierarchy";
        ft.setSuperType(this);
        subTypes.add(ft);
    }

    //returns true if ft is a subtype of this
    public boolean isSubType(ConsoleType ft){
        if (subTypes.contains(ft)){
            return true;
        }
        else{
            for(ConsoleType c : subTypes){
                if(c.isSubType(ft)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
