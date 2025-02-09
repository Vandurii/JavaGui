package main.view.scenes;

import main.view.View;
import main.view.elements.Panel;

public abstract class Scene {
    protected Panel scene;

    public abstract Panel createScene(View view);
}
