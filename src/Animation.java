import java.awt.Image;

public class Animation {
    public Image[] images;
    public int n;
    public int curentImage;

    public Animation() {
        n = 0;
        this.curentImage = 0;
    }

    public void addImage(Image image) {
        Image[] arr = images;
        images = new Image[n + 1];
        for (int i = 0; i < n - 1; i++) {
            images[i] = arr[i];
        }
        images[n] = image;
        n++;
    }

    public void update() {
        curentImage++;
        if (curentImage >= n)
            curentImage = 0;
    }

    public Image getCurentImage() {
        return images[curentImage];
    }
}
