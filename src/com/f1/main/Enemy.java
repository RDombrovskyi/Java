package com.f1.main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Enemy {
    int x;
    int y;
    int v;
    Random random = new Random();
    Image img = new ImageIcon("res/another_car" + (int) (random.nextInt(4) + 1) + ".png").getImage();
    Road road;

    public Rectangle getRec(){
            return new Rectangle(x,y,50,97);
    }

    Enemy(int x, int y, int v, Road road){
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }
    public void move(){
        y = (int) (y + road.p.v - v);

    }
}
