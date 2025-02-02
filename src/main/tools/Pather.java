package main.tools;

import static main.Configuration.resPath;

public class Pather {
    public static String getFromRes(String name){
        return resPath + name;
    }
}
