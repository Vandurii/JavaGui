package window.view.scenes;

import window.view.Window;


public class EmptySceen extends Scene{

    public EmptySceen(Window window) {
        super(window);
    }

    public EmptySceen(int paddingX, int paddingY, Window window) {
        super(paddingX, paddingY, window);
    }

    public EmptySceen(int paddingX, int paddingY, int paddingBetweenX, int paddingBetweenY, Window window) {
        super(paddingX, paddingY, paddingBetweenX, paddingBetweenY, window);
    }
}
