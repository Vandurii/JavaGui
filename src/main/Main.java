package main;

import main.tools.Loader;
import main.view.View;

public class Main {
    public static View window;

    public static void main(String[] args){
        new Loader().load();
        window = new View();
    }
}
