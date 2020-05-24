package lodz.uni.math;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

public class ToggleImage extends ToggleButton {

    private Image front;

    public ToggleImage() {
        super();
    }

//    public ToggleImage(Image image) {
//        super();
//        this.front = image;
//    }

    public ToggleImage(String s, Image image) {
        super(s);
        this.front = image;
    }

    public ToggleImage(String s, Node node, Image image) {
        super(s, node);
        this.front = image;
    }



}
