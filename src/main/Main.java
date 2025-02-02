package main;

import main.tools.Loader;
import main.view.View;

public class Main {
    public static void main(String[] args){
        new Loader().load();
        View view = new View();
    }
}
