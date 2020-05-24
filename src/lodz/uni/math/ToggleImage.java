package lodz.uni.math;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ToggleImage extends ToggleButton {

    private Image front;
    private boolean isClicked = false;
    private boolean isMatching = false;

    public ToggleImage(Image image) {
        super();
        this.front = image;
    }

    public ToggleImage(String s, Image image) {
        super(s);
        this.front = image;
    }

    public ToggleImage(String s, Node node, Image image) {
        super(s, node);
        this.front = image;
    }

    public void reverse() throws IOException  {
        if(isMatching) {
            return;
        }
        if(isClicked) {
//            setBackground(back);
            isClicked = false;
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


}
