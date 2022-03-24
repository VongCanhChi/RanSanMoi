import java.io.File;

import javax.imageio.ImageIO;

import java.awt.Image;

public class Data {
    // public static BufferedImage sprite;

    public static Image imageHead;
    public static Image imageHeadUp;
    public static Image imageHeadLeft;
    public static Image imageHeadDown;
    public static Image imageHeadRight;
    public static Image imageBody;
    public static Image imageItem;
    public static Image imageGameOver;
    public static Image imageBackground;

    public static Animation headGoUp;
    public static Animation headGoDown;
    public static Animation headGoLeft;
    public static Animation headGoRight;

    public static void loadImage() {
        try {
            // sprite = ImageIO.read(new File("res/headDown.png"));

            // imageHead = sprite.getSubimage(0, 0, 30, 30);
            imageHead = ImageIO.read(new File("src/res/head.png"));
            imageHeadLeft = ImageIO.read(new File("src/res/headLeft.png"));
            imageHeadRight = ImageIO.read(new File("src/res/headRight.png"));
            imageHeadUp = ImageIO.read(new File("src/res/headUp.png"));
            imageHeadDown = ImageIO.read(new File("src/res/headDown.png"));
            imageGameOver = ImageIO.read(new File("src/res/gameOver.PNG"));
            imageBackground = ImageIO.read(new File("src/res/background.jpg"));

            imageBody = ImageIO.read(new File("src/res/body.png"));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void loadAllAnimation() {
        headGoUp = new Animation();
        headGoUp.addImage(imageHeadUp);

        headGoDown = new Animation();
        headGoDown.addImage(imageHeadDown);

        headGoLeft = new Animation();
        headGoLeft.addImage(imageHeadLeft);

        headGoRight = new Animation();
        headGoRight.addImage(imageHeadRight);
    }
}
