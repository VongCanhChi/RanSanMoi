import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FrameScreen extends JFrame {

    GameScreen gameScreen;

    public static ArrayList<User> users;

    public FrameScreen() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        users = new ArrayList<>();
        readData();

        gameScreen = new GameScreen();
        add(gameScreen);

        this.addKeyListener(new handler());

        this.setVisible(true);
    }

    public static void main(String as[]) {
        FrameScreen f = new FrameScreen();
    }

    private class handler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyPress = e.getKeyCode();

            if (GameScreen.isPressCondition) {
                switch (keyPress) {
                case KeyEvent.VK_UP:
                    gameScreen.snake.setVector(Snake.GO_UP);
                    break;
                case KeyEvent.VK_DOWN:
                    gameScreen.snake.setVector(Snake.GO_DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    gameScreen.snake.setVector(Snake.GO_LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    gameScreen.snake.setVector(Snake.GO_RIGHT);
                    break;
                case KeyEvent.VK_SPACE:
                    GameScreen.isPlaying = true;
                    break;
                default:
                    System.out.println("Notthing is pressed.");
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

    }

    public static void updateData() {

    }

    public static void readData() {
        try {
            FileReader fr = new FileReader("res/userData.txt");
            BufferedReader br = new BufferedReader(fr);

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(" ");
                users.add(new User(str[0], str[1]));
            }

            br.close();
        } catch (Exception e) {
        }

    }
}
