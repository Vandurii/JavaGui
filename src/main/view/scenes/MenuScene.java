package main.view.scenes;

import main.view.View;
import main.view.elements.Panel;

public class MenuScene extends Scene{
    @Override
    public Panel createScene(View view) {
        scene = new Panel(0, 0);
        scene.addBorder(false, true, true, true);

        return scene;
    }
}
