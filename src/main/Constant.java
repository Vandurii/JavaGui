package main;

import java.awt.*;

public class Constant {

    public static final int INITIAL_WINDOW_WIDTH = 800;
    public static final int INITIAL_WINDOW_HEIGHT = 450;

    public static int edgeMargin = 10;
    public static int minWinWidth = 100;
    public static int minWinHeight = 100;

    public static float winAlpha = 0.5f;

    public static int initialPadding = 5;

    public static float ALPHA = 0.4f;
    public static Color THEME_COLOR = new Color(0.01f, 0.01f, 0.01f, ALPHA);

    public static Color borderLineColor = Color.red; //new Color(89, 89, 89);
    public static int borderLineSize = 4;

    public static int toolbarHeight = 40;
    public static int toolbarButWidth = 30;
    public static int toolbarButHeight = 30;
    public static int toolbarButScale = 20;

    public static int titleBarHeight = 40;
    public static int titleBarPaddingX = 5;
    public static int titleBarButWidth = 25;
    public static int titleBarButHeight = 25;
    public static int titleBarButScale = 20;
    public static Color titleBarButColNor = Color.DARK_GRAY;
    public static Color titleBarButColHov = Color.GRAY;

    //==============================

    public static final Color OPTION_BUTTON_COLOR_TEMP = new Color(0.2f, 0.2f, 0.2f, ALPHA);
    public static  Color OPTION_BUTTON_COLOR = OPTION_BUTTON_COLOR_TEMP;
    public static final Color LIGHT_GREEN = new Color(0.05f, 1f, 0.6f, ALPHA);

    public static final String TITLE = "Hello There!";

    // Buttons

    public static final String resPath = "src/main/res/";
    public static final String addButtonPath = Helper.getFromRes("add.png");
    public static final String editButtonPath = Helper.getFromRes("edit.png");
    public static final String deleteButtonPath = Helper.getFromRes("delete.png");
    public static final String settingsButtonPath = Helper.getFromRes("settings.png");
    public static final String minimizeButtonPath = Helper.getFromRes("view.png");
    public static final String exitButtonPath = Helper.getFromRes("turnOff.png");
    public static final String iconPath = Helper.getFromRes("icon.jpg");
    public static final String soundPath = Helper.getFromRes("focus.wav");

}
// add always on top button to the settings menu