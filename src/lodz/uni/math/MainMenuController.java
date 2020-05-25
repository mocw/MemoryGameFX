package lodz.uni.math;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void logout(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("LoginRegister.fxml"));
        Scene menuScene = new Scene(menu);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    public void startGame(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("Game.fxml"));
        Scene menuScene = new Scene(menu);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
}
