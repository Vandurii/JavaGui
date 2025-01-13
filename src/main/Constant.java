package main;

import java.awt.*;

public class Constant {

    // Window
    public static final int INITIAL_WINDOW_WIDTH = 800;
    public static final int INITIAL_WINDOW_HEIGHT = 450;
    public static int edgeMargin = 5;
    public static int minWinWidth = 100;
    public static int minWinHeight = 100;

    public static int colorPickerSize = 250;
    public static int pointerSize = 5;

    // Theme
    public static float winAlpha = 0.9f;
    public static Color firstThemeColor = new Color(0.01f, 0.01f, 0.01f, 1);
    public static Color secondThemeColor = Color.white;
    public static Color borderLineColor = new Color(89, 89, 89);
    public static int borderLineSize = 1;

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
    public static final String addButtonPath = Helper.getFromRes("add.png");
    public static final String editButtonPath = Helper.getFromRes("edit.png");
    public static final String deleteButtonPath = Helper.getFromRes("delete.png");
    public static final String settingsButtonPath = Helper.getFromRes("settings.png");
    public static final String resizeButtonPath = Helper.getFromRes("view.png");
    public static final String exitButtonPath = Helper.getFromRes("turnOff.png");
    public static final String hideButtonPath = Helper.getFromRes("hide.png");
    public static final String closeButtonPath = Helper.getFromRes("close.png");
    public static final String pointerButtonPath = Helper.getFromRes("pointer.png");
    public static final String iconPath = Helper.getFromRes("icon.jpg");
    public static final String soundPath = Helper.getFromRes("focus.wav");
    public static final String colorWheelsPath = Helper.getFromRes("colorWheels.png");
    public static final String colorSquaresPath = Helper.getFromRes("colorsSquare.png");
}