package window.tools;

import window.tools.saver.Savable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static window.Configuration.*;

public class Loader {

    private static Map<String, Object> resMap = new HashMap<>();
    private static List<Savable<?>> resList = new ArrayList<>();

    public static void loadFromFile(){
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(settingsFile));
            String line;
            while((line = bufReader.readLine()) != null){
                String[] args = line.split(":");
                String type = args[1];
                String name = args[0];
                switch (type){
                    case "color":
                        resMap.put(name, new Color(Float.parseFloat(args[2]) / 256, Float.parseFloat(args[3]) /256, Float.parseFloat(args[4]) / 256, Float.parseFloat(args[5])/256));
                        break;
                    case "float":
                        resMap.put(name, Float.parseFloat(args[2]));
                        break;
                    case "int":
                        resMap.put(name, Integer.parseInt(args[2]));
                        break;
                    case "boolean":
                        resMap.put(name, Boolean.valueOf(args[2]));
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void add(Savable<?> savable){
        resList.add(savable);
    }

    public static void load(){
        loadFromFile();
        for(Savable s: resList){
            s.set(resMap);
        }
    }

    public static List<Savable<?>> getResource(){
        return resList;
    }
}
