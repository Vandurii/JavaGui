package main.tools.saver;

import main.tools.Loader;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static main.Configuration.*;
import static main.Configuration.alwaysOnTop;

public class Saver {
    public void save() {
        File setingsFile = new File(settingsFile);
        try {
            setingsFile.createNewFile();
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(setingsFile));

            for(Savable<?> s: Loader.getResource()){
                switch (s.getClass().getSimpleName()){
                    case "SaveFloat":
                        bufWriter.write(String.format("%s:float:%s\n",s.idName, s.value));
                        break;
                    case "SaveColor":
                        Color tmp = (Color)s.value;
                        bufWriter.write(String.format("%s:color:%s:%s:%s:%s\n",s.idName, tmp.getRed(), tmp.getGreen(), tmp.getBlue(), tmp.getAlpha()));
                        break;
                    case "SaveBoolean":
                        bufWriter.write(String.format("%s:boolean:%s\n",s.idName, alwaysOnTop.getValue()));
                        break;
                }
            }

            bufWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
