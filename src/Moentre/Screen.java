package Moentre;

import Libraries.General.Directions;
import Libraries.General.Grid;
import Libraries.General.Position;
import Libraries.General.Size;

import javax.json.Json;
import javax.json.JsonWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements ActionListener {

    public static Timer timer;
    public static int score = 0;
    public static int speed = 41;
    public static int time = speed;
    public static boolean running = true;
    public static boolean victory = false;
    public static boolean deadDisplay = false;
    public static Grid grid = new Grid(new Size(6, 6), new Size(97, 97), Color.GRAY, 3);

    public Screen() {
        Cubes.initCubes();
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        time--;
        if (time <= 0) {
            if (running) {
                Position pos1 = Cubes.ENEMY1.getPos().add(Directions.WEST, grid);
                Cubes.ENEMY1.setPos(pos1);
                Cubes.ENEMY2.setPos(Cubes.ENEMY2.getPos().add(Directions.WEST, grid));
                Cubes.ENEMY3.setPos(Cubes.ENEMY3.getPos().add(Directions.WEST, grid));
                if (pos1.getX() < grid.getXs().get(0)) {
                    pos1.setX(grid.getXs().get(4) + grid.getRectSize().getWidth() + grid.getGapSize());
                    Cubes.ENEMY2.getPos().setX(grid.getXs().get(4) + grid.getRectSize().getWidth() + grid.getGapSize());
                    Cubes.ENEMY3.getPos().setX(grid.getXs().get(4) + grid.getRectSize().getWidth() + grid.getGapSize());
                    ArrayList<Integer> ns = new ArrayList<>();
                    ns.add(0);
                    ns.add(1);
                    ns.add(2);
                    ns.add(3);
                    ns.add(4);
                    int n1 = new Random().nextInt(5);
                    Cubes.ENEMY1.getPos().setY(grid.getYs().get(ns.get(n1)));
                    ns.remove(n1);
                    int n2 = new Random().nextInt(4);
                    Cubes.ENEMY2.getPos().setY(grid.getYs().get(ns.get(n2)));
                    ns.remove(n2);
                    int n3 = new Random().nextInt(3);
                    Cubes.ENEMY3.getPos().setY(grid.getYs().get(ns.get(n3)));
                    score++;
                    try {
                        Moentre.moentre.setTitle("Score: " + score + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (speed <= 1) {
                        try {
                            Moentre.moentre.setTitle("You Won! | Score: " + score + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        running = false;
                        victory = true;
                        speed = 60;
                    } else {
                        speed--;
                    }
                }
                if (Cubes.ENEMY1.getPos().getX() == Cubes.PLAYER1.getPos().getX()) {
                    if (Cubes.ENEMY1.getPos().getY() == Cubes.PLAYER1.getPos().getY()
                            || Cubes.ENEMY2.getPos().getY() == Cubes.PLAYER1.getPos().getY()
                            || Cubes.ENEMY3.getPos().getY() == Cubes.PLAYER1.getPos().getY()
                            || Cubes.ENEMY1.getPos().getY() == Cubes.PLAYER2.getPos().getY()
                            || Cubes.ENEMY2.getPos().getY() == Cubes.PLAYER2.getPos().getY()
                            || Cubes.ENEMY3.getPos().getY() == Cubes.PLAYER2.getPos().getY()) {
                        try {
                            Moentre.moentre.setTitle("You Lose! | Score: " + score + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        running = false;
                        speed = 60;
                        try {
                            if (score > Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore")) {
                                JsonWriter writer = Json.createWriter(new FileWriter("highScore.json"));
                                writer.write(Json.createObjectBuilder().add("highScore", score).build());
                                writer.close();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                deadDisplay = !deadDisplay;
                if (deadDisplay) {
                    Moentre.moentre.setTitle("Press SPACE to start over!");
                } else {
                    if (victory) {
                        try {
                            Moentre.moentre.setTitle("You Won! | Score: " + score + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            Moentre.moentre.setTitle("You Lose! | Score: " + score + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            time = speed;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);
        grid.draw((Graphics2D) g);
        Cubes.PLAYER1.draw((Graphics2D) g);
        Cubes.PLAYER2.draw((Graphics2D) g);
        Cubes.ENEMY1.draw((Graphics2D) g);
        Cubes.ENEMY2.draw((Graphics2D) g);
        Cubes.ENEMY3.draw((Graphics2D) g);
    }

    public static class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

                Position pos1 = Cubes.PLAYER1.getPos();
                Position pos2 = Cubes.PLAYER2.getPos();
            if (running) {
                if (e.getExtendedKeyCode() == KeyEvent.VK_W) {
                    if (pos1.getY() > grid.getYs().get(0)) {
                        Cubes.PLAYER1.setPos(pos1.add(Directions.NORTH, grid));
                    }
                    if (pos2.getY() > grid.getYs().get(1)) {
                        Cubes.PLAYER2.setPos(pos2.add(Directions.NORTH, grid));
                    }
                }
                if (e.getExtendedKeyCode() == KeyEvent.VK_S) {
                    if (pos1.getY() < grid.getYs().get(3)) {
                        Cubes.PLAYER1.setPos(pos1.add(Directions.SOUTH, grid));
                    }
                    if (pos2.getY() < grid.getYs().get(4)) {
                        Cubes.PLAYER2.setPos(pos2.add(Directions.SOUTH, grid));
                    }
                }
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
                if (running) {
                    if (pos1.getY() > grid.getYs().get(0)) {
                        Cubes.PLAYER1.setPos(pos1.add(Directions.NORTH, grid));
                    }
                    if (pos2.getY() < grid.getYs().get(4)) {
                        Cubes.PLAYER2.setPos(pos2.add(Directions.SOUTH, grid));
                    }
                } else {
                    speed = 41;
                    try {
                        Moentre.moentre.setTitle("Score: " + 0 + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    Cubes.PLAYER1.getPos().setY(grid.getYs().get(1));
                    Cubes.PLAYER2.getPos().setY(grid.getYs().get(2));
                    Cubes.ENEMY1.setPos(new Position(grid.getXs().get(5), grid.getYs().get(0)));
                    Cubes.ENEMY2.setPos(new Position(grid.getXs().get(5), grid.getYs().get(3)));
                    Cubes.ENEMY3.setPos(new Position(grid.getXs().get(5), grid.getYs().get(4)));
                    victory = false;
                    running = true;
                }
            }
        }
    }
}
