package main;

import main.tools.saver.SaveBoolean;
import main.tools.saver.SaveColor;
import main.tools.Pather;
import main.tools.saver.SaveFloat;

import java.awt.*;

public class Configuration {

    // Window
    public static final int INITIAL_WINDOW_WIDTH = 800;
    public static final int INITIAL_WINDOW_HEIGHT = 450;
    public static int edgeMargin = 5;
    public static int minWinWidth = 100;
    public static int minWinHeight = 100;

    public static int colorPickerSize = 250;
    public static int pointerSize = 5;

    // Theme
    public static SaveFloat<Float> winAlphaComposite = new SaveFloat<>("opacity", 1f);
    public static SaveColor<Color> primaryThemeColor = new SaveColor<>("primaryColor", new Color(0.5f, 0.5f, 0.5f, 1f));
    public static SaveColor<Color> secondaryThemeColor = new SaveColor<>("secondaryColor", new Color(0.9f, 0.2f, 0.2f, 1f));
    public static SaveColor<Color> borderLineColor = new SaveColor<>("borderColor", new Color(89, 89, 89));

    public static int borderLineWidth = 1;

    public static int initialPadding = 5;
    public static int buttonThreshold = 200;

    // Toolbar
    public static int toolbarHeight = 40;
    public static int toolbarButWidth = 30;
    public static int toolbarButHeight = 30;
    public static int toolbarButScale = 20;

    // Titlebar
    public static int titleBarHeight = 40;
    public static int titleBarButWidth = 25;
    public static int titleBarButHeight = 25;
    public static int titleBarButScale = 20;

    // Paths
    public static final String resPath = "src/main/res/";
    public static final String addButtonPath = Pather.getFromRes("add.png");
    public static final String editButtonPath = Pather.getFromRes("edit.png");
    public static final String deleteButtonPath = Pather.getFromRes("delete.png");
    public static final String settingsButtonPath = Pather.getFromRes("settings.png");
    public static final String resizeButtonPath = Pather.getFromRes("view.png");
    public static final String exitButtonPath = Pather.getFromRes("turnOff.png");
    public static final String hideButtonPath = Pather.getFromRes("hide.png");
    public static final String closeButtonPath = Pather.getFromRes("close.png");
    public static final String pointerButtonPath = Pather.getFromRes("pointer.png");
    public static final String iconPath = Pather.getFromRes("icon.jpg");
    public static final String soundPath = Pather.getFromRes("focus.wav");
    public static final String colorWheelsPath = Pather.getFromRes("colorWheels.png");
    public static final String colorSquaresPath = Pather.getFromRes("colorsSquare.png");

    // Settings
    public static Font settingsFont = new Font("Arial", Font.BOLD, 15);
    public static int minOpacityValue = 5;
    public static int sliderWidth = 100;
    public static int sliderHeight = 50;
    public static SaveBoolean<Boolean> alwaysOnTop = new SaveBoolean<>("onTop", false);
    public static int checkBoxSize = 20;
    public static String settingsFile = "settings.txt";
}