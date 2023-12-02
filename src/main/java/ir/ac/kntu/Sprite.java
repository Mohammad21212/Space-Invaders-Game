package ir.ac.kntu;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    boolean dead = false;
    final String type;

    Sprite(int x, int y, int w, int h, String type) {
        super(w, h);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        switch (type) {
            case "enemy":
                Image invaderImg = new Image("file:C:\\Users\\ALI\\Desktop\\4001\\AP\\Projects\\P3\\src\\main\\resources\\assets/alien_one_white.png");
                setFill(new ImagePattern(invaderImg));
                break;
            case "player":
                Image PlayerImg = new Image("file:C:\\Users\\ALI\\Desktop\\4001\\AP\\Projects\\P3\\src\\main\\resources\\assets\\spaceship.png");
                setFill(new ImagePattern(PlayerImg));
                break;
            case "enemyBullet":
            case "playerBullet":
                setFill(Color.RED);
                break;
        }
    }

    void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }
}
