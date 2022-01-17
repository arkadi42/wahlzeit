package org.wahlzeit.model;

import java.util.HashSet;

public class Console {
    protected ConsoleManager manager;
    protected ConsoleType type;
    protected HashSet<ConsolePhoto> photos = new HashSet<>();

    public Console(ConsoleType ft){
        type = ft;
    }
}
