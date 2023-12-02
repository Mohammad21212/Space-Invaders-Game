package ir.ac.kntu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvaders extends Application {

    private Pane root = new Pane();

    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    private boolean gameOver = false;
    private boolean youWon = false;

    private double t = 0;

    private Sprite player = new Sprite(280, 750, 40, 40, "player");

    private Parent createContent() {
        root.setPrefSize(WIDTH, HEIGHT);
        Image image = new Image("C:\\Users\\ALI\\Desktop\\4001\\AP\\tamrin\\Space_Invaders\\src\\resources\\java\\ir\\ac\\kntu\\background.png");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        Background backGround = new Background(backgroundImage);
        root.setBackground(backGround);
        root.getChildren().add(player);
        createInvaders();
        timer.start();

        return root;
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            update();
        }
    };

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }

    private void update() {
        if (isGameOver() || isYouWon()) {
            timer.stop();
        }
        t += 0.016;
        sprites().forEach(s -> {
            switch (s.type) {
                case "enemyBullet":
                    s.moveDown();
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                        gameOver = true;
                        endGame();
                        //    explosion.play();
                    }
                    break;

                case "playerBullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            //    invaderKilled.play();
                        }
                    });
                    break;

                case "enemy":
                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }
                    break;

            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (sprites().stream().filter(e -> e.type.equals("enemy")).count() == 0) {
            youWon = true;
            endGame();
        }

        if (t > 2) {
            t = 0;
        }
    }

    private void createInvaders() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i * 100, 50, 30, 30, "enemy");
            root.getChildren().add(s);
        }
    }

    private void shoot(Sprite who) {
        Sprite bullet = new Sprite((int) who.getTranslateX() + (int) (who.getWidth() / 2 - 2), (int) who.getTranslateY(),
                4, 20, who.type + "Bullet");
        root.getChildren().add(bullet);
        switch (who.type) {
            case "enemy":
//                    Shoot.play();
                break;
            case "player":
//                    Shoot.play();
                break;
        }
    }

    public void endGame() {
        if(gameOver==true || youWon==true) {
            JOptionPane.showConfirmDialog(null,"EXIT");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                case Q:
                    if (player.getTranslateX() > 0) {
                        player.moveLeft();
                    }
                    break;
                case D:
                    if (player.getTranslateX() < WIDTH - 40) {
                        player.moveRight();
                    }
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });
        stage.setScene(scene);
        stage.setTitle("Space Invaders");

        stage.show();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isYouWon() {
        return youWon;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
