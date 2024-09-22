package Moentre;

import Libraries.General.Position;
import Libraries.Sprites.RectSprite;

import java.awt.*;

public class Cubes {

    public static RectSprite PLAYER1;
    public static RectSprite PLAYER2;
    public static RectSprite ENEMY1;
    public static RectSprite ENEMY2;
    public static RectSprite ENEMY3;

    public static void initCubes() {
        PLAYER1 = new RectSprite(new Position(Screen.grid.getXs().get(1), Screen.grid.getYs().get(1)), Screen.grid.getRectSize(), false, Color.BLUE);
        PLAYER2 = new RectSprite(new Position(Screen.grid.getXs().get(1), Screen.grid.getYs().get(2)), Screen.grid.getRectSize(), false, Color.BLUE);
        ENEMY1 = new RectSprite(new Position(Screen.grid.getXs().get(5), Screen.grid.getYs().get(0)), Screen.grid.getRectSize(), false, Color.RED);
        ENEMY2 = new RectSprite(new Position(Screen.grid.getXs().get(5), Screen.grid.getYs().get(3)), Screen.grid.getRectSize(), false, Color.RED);
        ENEMY3 = new RectSprite(new Position(Screen.grid.getXs().get(5), Screen.grid.getYs().get(4)), Screen.grid.getRectSize(), false, Color.RED);
    }
}
