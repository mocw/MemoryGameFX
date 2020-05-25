package lodz.uni.math;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ToggleImage extends Button {

    private Image front;
    private boolean isClicked = false;
    private boolean isMatching = false;
    private int posX;
    private int posY;

    public ToggleImage(Image image,int x, int y) {
        super();
        this.front = image;
        this.posX = x;
        this.posY = y;
    }

    public void reverse() throws IOException  {
        if(isMatching) {
            return;
        } else {
            ByteArrayInputStream bis = new ByteArrayInputStream(this.front.getImage());
            BufferedImage bImage2 = ImageIO.read(bis);
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(bImage2, null);

            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);

            this.setBackground(background);
            isClicked = true;
        }
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean click){
        this.isClicked = click;
    }

    public Image getFront() {
        return front;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setMatched(boolean matching) {
        isMatching = matching;
    }

    public boolean isMatching() {
        return isMatching;
    }

    public void turnBack(){
        try {
            BufferedImage bis = ImageIO.read(new File("./images/reverse.png"));
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(bis, null);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);

            this.setBackground(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
