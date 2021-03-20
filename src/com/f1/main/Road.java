package com.f1.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Road extends JPanel implements ActionListener, Runnable {
    Timer mainTimer = new Timer(10, this);

    Image img = new ImageIcon("res/road.png").getImage();
    static Player p = new Player();
    static boolean isStart = false;
    public static Float t = 30.000f;

    Thread enemiesFactory = new Thread(this);

    static ArrayList<Enemy> enemies = new ArrayList<>();

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    private class MyKeyAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }

    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, p.layer1, null);
        g.drawImage(img, 0, p.layer2, null);
        g.drawImage(p.img, p.x, p.y, null);


        if (!isWin() && isStart) {
            double v = 200 / Player.MAX_V * p.v;
            g.setColor(Color.ORANGE);
            Font font = new Font("Arial", Font.BOLD, 25);
            if (p.v == Player.MAX_V) {
                g.setColor(Color.red);
                font.deriveFont(50);
            }
            g.setFont(font);
            g.drawString("Speed: " + (int) v + "km/h", 0, 20);
            g.drawString("Score: " + p.s / 10, 560, 20);
        }


        if (isStart && !isWin()) {
            int fSize = 40;
            Font f = new Font("times new roman", Font.BOLD, fSize);
            g.setFont(f);
            g.setColor(Color.white);
            if (Road.t.intValue() <= 5) {
                g.setColor(Color.red);
            }
            g.drawString("" + Road.t.intValue(), 350, 46);
            if (isStart)
                Road.t -= 0.01f;
        }

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.y <= -200 || e.y >= 700) {
                i.remove();
            } else {
                if (!isWin())
                    e.move();
                g.drawImage(e.img, e.x, e.y, null);
            }
        }

        if (!isStart) {
            g.setColor(Color.white);
            Font font1 = new Font("times new roman", Font.PLAIN, 60);
            g.setFont(font1);
            g.drawString(" Нажмите ↑ чтобы начать", 15, 350);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (p.s > 1)
            isStart = true;
        p.move();
        repaint();
        try {
            testCollisions();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

    }

    private static void gameOver() {
        p.s = 0;
        p.v = 0;
        p.x = 330;
        p.y = 560;
        p.dv = 0;
        isStart = false;
    }

    public static boolean isWin() {
        if ((p.s / 10) >= 17000) {
            gameOver();
            JOptionPane.showMessageDialog(null, "Поздравляю, ты победил за " + (30 - t.intValue()) + " секунд");
            t = 30.00f;
            return true;
        } else
            return false;
    }

    private void testCollisions() throws InterruptedException {
        ListIterator<Enemy> i = enemies.listIterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRec().intersects(e.getRec()) && e.img != null || t < 1) {
                JOptionPane.showMessageDialog(null, "Ты проиграл\n Твой счет: " + p.s / 10);
                System.exit(1);
            }
            for (Enemy enemy : enemies) {
                int x = enemies.indexOf(enemy);
                if (enemies.indexOf(e) != x && e.getRec().intersects(enemy.getRec()) && enemy.img != null && e.img != null) {
                    e.img = null;
                }
            }
        }
    }


    @Override
    public void run() {
        int hard = (2000 / ((p.s / 10) + 1)) + 500;
        while (true) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(hard));
                enemies.add(new Enemy(168 + rand.nextInt(311), -100, rand.nextInt(t.intValue()) + 1, this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
