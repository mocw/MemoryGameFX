package lodz.uni.math;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private GridPane gridPane;
    private List<ToggleButton> images = new ArrayList<>();
    private boolean isBusy = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                ToggleImage toggleImage = new ToggleImage();
                toggleImage.setMaxHeight(Double.MAX_VALUE);
                toggleImage.setMaxWidth(Double.MAX_VALUE);
                gridPane.add(toggleImage, i, j , 1, 1);
            }
        }
    }
}
