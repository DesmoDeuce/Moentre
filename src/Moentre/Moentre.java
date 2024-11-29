package Moentre;

import javax.json.Json;
import javax.swing.*;
import java.io.*;

public class Moentre extends JFrame {

    public static Moentre moentre;

    static {
        try {
            moentre = new Moentre();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Moentre() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(503 + 14, 503 + 37);
        setLocationRelativeTo(null);
        add(new Screen());
        addKeyListener(new Screen.TAdapter());
        setTitle("Score: " + 0 + " | High Score: " + Json.createReader(new FileReader("highScore.json")).readObject().getInt("highScore"));
    }

    public static void main(String[] args) {
        moentre.setVisible(true);
    }
}
