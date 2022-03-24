import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Snake {
    int snakeLen = 3;
    int[] x;
    int[] y;

    public static int GO_UP = 1;
    public static int GO_DOWN = -1;
    public static int GO_LEFT = 2;
    public static int GO_RIGHT = -2;
    static final int MIN_SPEED = 500;
    static final int MAX_SPEED = 100;

    int vector = Snake.GO_DOWN; // Hướng di chuyển
    static int speed = MIN_SPEED; // Tốc độ di chuyển của con rắn
    long t1 = 0; // Time

    boolean updateAfterChangeVector = true; // Tránh việc đang đi ngang mà đi lên rồi quay đầu ngược lại. Tức là trong 1
                                            // khung hình chỉ cho phép 1 di chuyển.

    public Snake(int speedLevel) {
        x = new int[(GameScreen.Bg_WIDTH - 2) * (GameScreen.Bg_HEIGHT - 2)]; // Độ dài rắn tối đa phủ hết màn hình
        y = new int[(GameScreen.Bg_WIDTH - 2) * (GameScreen.Bg_HEIGHT - 2)];
        snakeLen = 3;
        x[0] = 4;
        y[0] = 5;
        x[1] = 5;
        y[1] = 5;
        x[2] = 6;
        y[2] = 5;
        speed = speedLevel;
        t1 = 0;
        GameScreen.t = 0;
    }

    public void setVector(int v) {
        if (vector != -v && updateAfterChangeVector) {// Tránh trường hợp đi ngược lại.
            vector = v;
            updateAfterChangeVector = false;
        }
    }

    public Point getItemPoint() { // Mồi
        Random random = new Random();
        int _x = 0;
        int _y = 0;
        // Kiểm tra xem mồi có trùng thân rắn hay không.
        boolean test = true;
        while (test) {
            _x = random.nextInt(GameScreen.Bg_WIDTH - 2) + 1;
            _y = random.nextInt(GameScreen.Bg_HEIGHT - 2) + 1;
            for (int i = 0; i < snakeLen; i++) {
                if (_x != x[i] && _y != y[i]) {
                    test = false;
                }
            }
        }

        return new Point(_x, _y);
    }

    public void drawSnake(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 1; i < snakeLen; i++) { // Draw body
            // g.fillRect(x[i] * 20 + 1, y[i] * 20 + 1, 18, 18);
            g.drawImage(Data.imageBody, x[i] * 20 + 1, y[i] * 20 + 1, null);
        }

        if (vector == Snake.GO_UP) // Draw head
            g.drawImage(Data.headGoUp.getCurentImage(), x[0] * 20, y[0] * 20, null);
        else if (vector == Snake.GO_DOWN)
            g.drawImage(Data.headGoDown.getCurentImage(), x[0] * 20, y[0] * 20, null);
        else if (vector == Snake.GO_LEFT)
            g.drawImage(Data.headGoLeft.getCurentImage(), x[0] * 20, y[0] * 20, null);
        else
            g.drawImage(Data.headGoRight.getCurentImage(), x[0] * 20, y[0] * 20, null);
    }

    public void update() {
        if (System.currentTimeMillis() - t1 > speed) {
            // Ăn mồi
            if (GameScreen.bg[x[0]][y[0]] == 2) {
                snakeLen++;
                GameScreen.bg[x[0]][y[0]] = 0;
                GameScreen.bg[getItemPoint().x][getItemPoint().y] = 2;
                if (speed > MAX_SPEED)
                    speed -= 20;
            }

            // Đuôi rắn di chuyển
            for (int i = snakeLen - 1; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            // Đầu rắn di chuyển
            if (vector == Snake.GO_UP) { // Không sử dụng được swith
                y[0]--;
            } else if (vector == Snake.GO_DOWN)
                y[0]++;
            else if (vector == Snake.GO_LEFT)
                x[0]--;
            else
                x[0]++;

            // Cho rắn di chuyển xuyên màn hình chơi
            if (x[0] < 1)
                x[0] = GameScreen.Bg_WIDTH - 2;
            if ((x[0] > GameScreen.Bg_WIDTH - 2 && GameScreen.isWinCondition == false)
                    || (x[0] > GameScreen.Bg_WIDTH - 2 && GameScreen.isWinCondition == true && y[0] != 1)) {
                x[0] = 1;
            }
            if (y[0] < 1)
                y[0] = GameScreen.Bg_HEIGHT - 2;
            if (y[0] > GameScreen.Bg_HEIGHT - 2)
                y[0] = 1;
            t1 = System.currentTimeMillis();
            GameScreen.t = System.currentTimeMillis(); // Hiệu ứng chớp nháy cổng thoát khi win.

            // Đầu đụng thân rắn => Thuaaaa
            for (int i = 1; i < snakeLen; i++) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    GameScreen.isPlaying = false;
                    break;
                }
            }
            updateAfterChangeVector = true; // Tránh việc đang đi ngang mà đi lên rồi quay đầu ngược lại. Tức là trong 1
                                            // khung hình chỉ cho phép 1 di chuyển.
        }
    }
}
