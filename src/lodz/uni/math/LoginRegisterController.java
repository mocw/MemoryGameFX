package lodz.uni.math;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lodz.uni.math.database.DbClass;
import lodz.uni.math.database.UserAlreadyExistAuthenticationException;

import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginRegisterController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink htRegister;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label labelError;
    @FXML
    private Label labelSuccess;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DbClass.connectDB();
            tfLogin.setText("adam"); //dla szybszego logowania
            tfPassword.setText("adam");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent actionEvent) {
        hideAlerts();
        if(isDataIsFilled()){
            try {
                DbClass.login(tfLogin.getText(), tfPassword.getText());
            } catch (SQLException e) {
                labelError.setVisible(true);
                labelError.setText("Wystąpił błąd!");
                return;
            } catch(FailedLoginException e){
                labelError.setVisible(true);
                labelError.setText("Nieprawidłowe dane!");
                return;
            }
            try {
                switchToMainMenu(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void register(ActionEvent actionEvent) {
        hideAlerts();
        if(isDataIsFilled()){
            System.out.println("Haslo: " + tfPassword.getText());
            try {
                DbClass.register(tfLogin.getText(), tfPassword.getText());
            } catch (SQLException e) {
                labelError.setVisible(true);
                labelError.setText("Wystąpił błąd!");
                return;
            } catch (UserAlreadyExistAuthenticationException e){
                labelError.setVisible(true);
                labelError.setText("Nazwa użytkownika jest już zajęta!");
                return;
            }
            labelSuccess.setVisible(true);
            labelSuccess.setText("Zarejestrowano!");
        }
    }

    private boolean isDataIsFilled(){
        if(tfLogin.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()){
            labelError.setText("Wypełnij dane!");
            labelError.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    public void hideAlerts(){
        labelSuccess.setVisible(false);
        labelError.setVisible(false);
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene menuScene = new Scene(menu);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
}
