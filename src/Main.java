import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.view.Window;
import window.view.prefabs.Prefabs;
import window.view.scenes.EmptySceen;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        Window window = new Window(1280, 720);

        EmptySceen emptySceen = new EmptySceen(window);

        window.loadScene(emptySceen);
    }
}
