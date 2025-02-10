package window.view.scenes;

import window.view.Window;
import window.view.elements.Panel;

public abstract class Scene {
    protected Panel scene;

    public abstract Panel createScene(Window window);
}
