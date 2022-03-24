import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameScreen extends JPanel implements Runnable {
    static final int Bg_WIDTH = 20;
    static final int Bg_HEIGHT = 20;
    static int[][] bg = new int[Bg_WIDTH][Bg_HEIGHT];

    BufferedImage image; // Item image

    static boolean isPlaying = true;
    static boolean isWinCondition = false;
    static boolean isPressCondition = true;
    static int level = 1;
    static long t = 0; // Biến thời gian
    int speedLevel = Snake.MIN_SPEED;;

    static Snake snake;
    Thread thread;

    public GameScreen() {
        snake = new Snake(Snake.speed);
        Data.loadImage();
        Data.loadAllAnimation();

        bg[snake.getItemPoint().x][snake.getItemPoint().y] = 2; // Nơi Item xuất hiện

        thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g) {
        paintBg(g);

    }

    public void run() { // Là phương thức của Runnable, tự động gọi.
        while (true) {
            snake.update();
            if (GameScreen.isWinCondition) { // Xử lý khi rắn chui vào cổng thắng game.
                if (snake.x[0] == Bg_WIDTH - 1 && snake.y[0] == 1) {
                    snake.y[0] = 0; // Tránh việc rắn di chuyển xuyên tường
                    isPressCondition = false;
                    snake.vector = Snake.GO_DOWN;
                }
                // Khi đuôi rắn chui hết vào hang
                if (snake.x[snake.snakeLen - 1] == Bg_WIDTH - 1 && snake.y[snake.snakeLen - 1] == 0) {
                    level++;
                    snake = new Snake(Math.max(speedLevel -= 50, Snake.MAX_SPEED));
                    isPressCondition = true; // Cho phép điều khiển lại
                    isWinCondition = false;

                    // Chỉnh lại vị trí xuất hiện của mồi
                    for (int i = 0; i < GameScreen.Bg_WIDTH; i++) {
                        for (int j = 0; j < GameScreen.Bg_HEIGHT; j++) {
                            if (bg[i][j] == 2) {
                                bg[i][j] = 0;
                            }

                        }
                    }
                    bg[snake.getItemPoint().x][snake.getItemPoint().y] = 2;
                }
            }
            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

    public void paintBg(Graphics g) {

        try {
            image = ImageIO.read(new File("src/res/item.png"));
        } catch (Exception e) {
            System.out.println("No image to load.");
        }
        g.drawImage(Data.imageBackground, 0, 0, null);
        snake.drawSnake(g);
        for (int i = 0; i < Bg_WIDTH; i++) {
            for (int j = 0; j < Bg_HEIGHT; j++) {
                if (bg[i][j] == 2) {// Vẽ Item
                    g.drawImage(image, i * 20 + 1, j * 20 + 1, this);
                }
                if (i == 0 || i == Bg_WIDTH - 1 || j == 0 || j == Bg_HEIGHT - 1) { // Vẽ tường màu đen
                    g.setColor(Color.black);
                    g.fillRect(i * 20 + 1, j * 20 + 1, 20, 20);
                }
            }
        }
        // Hiển thị điểm
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(14.0f));
        g.drawString("Level: " + Integer.toString(level), 10, 16);

        // Hiển thị level
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(14.0f));
        g.drawString("Score: " + Integer.toString(snake.snakeLen - 3), 110, 16);

        if (snake.snakeLen > 3) { // Xử lý tăng level
            isWinCondition = true;
            g.setColor(Color.pink);
            g.fillRect((Bg_WIDTH - 1) * 20 + 1, 1 * 20 + 1, 20, 20);
            if (System.currentTimeMillis() - t + 200 > 600) {
                g.setColor(Color.orange);
                g.fillRect((Bg_WIDTH - 1) * 20 + 1, 1 * 20 + 1, 20, 20);
            }
            if (System.currentTimeMillis() - t + 400 > 600) {
                g.setColor(Color.red);
                g.fillRect((Bg_WIDTH - 1) * 20 + 1, 1 * 20 + 1, 20, 20);
            }
        }

        // Game over
        if (GameScreen.isPlaying == false) {
            g.drawImage(Data.imageGameOver, 0, 0, null);
            GameScreen.snake = new Snake(speedLevel);
        }
    }
}
