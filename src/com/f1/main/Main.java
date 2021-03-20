package com.f1.main;

import javax.swing.*;


public class Main {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {

        JFrame f = new JFrame("Racer");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        Road road = new Road();
        f.add(road);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }
}
