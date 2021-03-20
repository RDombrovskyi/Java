package com.f1.main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Player  {
    public static final int MAX_V = 15;
    public static final int MAX_LEFT = 185;
    public static final int MAX_RIGHT = 470;

    Image img = new ImageIcon("res/car.png").getImage();

    float v =0;
    float dv =0;
    int s =0;

    int x = 330;
    int y = 560;
    int dx = 0;

    int layer1 = -260;
    int layer2 = -1220;

    public void move(){
        s+=v;
        v+=dv;
        x+=dx;

        if(v<=0){v=0; dx =0;img = new ImageIcon("res/car.png").getImage();}
        if(v>=MAX_V){v=MAX_V;s+=2*v+s/500;}
        if(x<=MAX_LEFT){x=MAX_LEFT; img = new ImageIcon("res/car.png").getImage();}
        if(x>=MAX_RIGHT){x=MAX_RIGHT; img = new ImageIcon("res/car.png").getImage();}
        if((layer2+260)>=0){
            layer1 = -260;
            layer2 = -1220;
        }else if(!Road.isWin()){
            layer1 += v;
            layer2 += v;
        }
    }

    public Rectangle getRec(){
        return new Rectangle(x,y,55,100);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_UP){
            dv = 0.05f;
            y= 550;
        }
        if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_SPACE){
            if(v>0)
            dv = -1f;
        }
        if(key==KeyEvent.VK_RIGHT){
            dx= 5;
            img = new ImageIcon("res/car_right.png").getImage();
        }
        if(key==KeyEvent.VK_LEFT){
            dx= -5;
            img = new ImageIcon("res/car_left.png").getImage();
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_UP||key==KeyEvent.VK_DOWN||key==KeyEvent.VK_SPACE){
            if(v>=0)
            dv = -0.025f;
            y = 560;
            img = new ImageIcon("res/car.png").getImage();
        }
        if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_RIGHT){
            dx = 0;
            if(key!=KeyEvent.VK_UP)
            img = new ImageIcon("res/car.png").getImage();
        }

    }
}
