package window.tools.saver;

import window.tools.Loader;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static window.Configuration.*;

public class Saver {
    public static void save() {
        File setingsFile = new File(settingsFile);
        try {
            setingsFile.createNewFile();
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(setingsFile));

            for(Savable<?> s: Loader.getResource()){
                switch (s.getClass().getSimpleName()){
                    case "SaveFloat":
                        bufWriter.write(String.format("%s:float:%s\n",s.idName, s.value));
                        break;
                    case "SaveInt":
                        bufWriter.write(String.format("%s:int:%s\n",s.idName, s.value));
                        break;
                    case "SaveColor":
                        Color tmp = (Color)s.value;
                        bufWriter.write(String.format("%s:color:%s:%s:%s:%s\n",s.idName, tmp.getRed(), tmp.getGreen(), tmp.getBlue(), tmp.getAlpha()));
                        break;
                    case "SaveBoolean":
                        bufWriter.write(String.format("%s:boolean:%s\n",s.idName, s.value));
                        break;
                }
            }

            bufWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
