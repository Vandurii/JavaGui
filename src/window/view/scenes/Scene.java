package window.view.scenes;

import window.view.Window;
import window.view.elements.Panel;

public abstract class Scene extends Panel{
    public Scene(Window window) {
        super(window.getSceneDimension().width, window.getSceneDimension().height);
        this.addBorder(false, true, true, true);
    }

    public Scene(int paddingX, int paddingY, Window window) {
        super(window.getSceneDimension().width, window.getSceneDimension().height, paddingX, paddingY);
        this.addBorder(false, true, true, true);
    }

    public Scene(int paddingX, int paddingY, int paddingBetweenX, int paddingBetweenY, Window window) {
        super(window.getSceneDimension().width, window.getSceneDimension().height, paddingX, paddingY, paddingBetweenX, paddingBetweenY);
        this.addBorder(false, true, true, true);
    }
}
