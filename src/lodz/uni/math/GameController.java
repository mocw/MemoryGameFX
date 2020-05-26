package lodz.uni.math;

import com.sun.javafx.css.StyleCache;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import lodz.uni.math.database.DbClass;

public class GameController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private List<Integer> drawnNumbers = new ArrayList<>();
    private List<byte[]> byteImages = new ArrayList<>();
    private Random random = new Random();
    private ToggleImage selectedImage = null;
    private ToggleImage secondSelectedImage = null;
    private int score = 0;
    private boolean pairNotFound = false;
    private long startTime;
    private long endTime;
    private float time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        startGame();
    }

    private void startGame() {

        try {
            drawImages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int imageIndex = 0;
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                final ToggleImage toggleImage = new ToggleImage(new Image(byteImages.get(imageIndex)), i, j);
                imageIndex++;
                toggleImage.setMaxHeight(Double.MAX_VALUE);
                toggleImage.setMaxWidth(Double.MAX_VALUE);
                toggleImage.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if(toggleImage.isMatching()){
                            return;
                        }

                        if(selectedImage == toggleImage){
                            return;
                        }

                        if(pairNotFound){

                            selectedImage.turnBack();
                            secondSelectedImage.turnBack();
                            selectedImage = null;
                            secondSelectedImage = null;
                            pairNotFound = false;
                        }

                        if(selectedImage == null){
                            selectedImage = toggleImage;
                            try {
                                selectedImage.reverse();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            secondSelectedImage = toggleImage;
                            try {
                                secondSelectedImage.reverse();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                        if(selectedImage != null && secondSelectedImage != null){
                            if(Arrays.equals(selectedImage.getFront().getImage(),secondSelectedImage.getFront().getImage())){
                                System.out.println("Pair found!");
                                if(shouldGameEnd()){
                                    try {
                                        endGame(event);
                                    } catch (IOException | SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    selectedImage.setMatched(true);
                                    secondSelectedImage.setMatched(true);
                                    selectedImage = null;
                                    secondSelectedImage = null;
                                    score++;
                                }
                            } else {
                                pairNotFound = true;
                            }
                        }
                    }
                });
                gridPane.add(toggleImage, i, j , 1, 1);
                toggleImage.turnBack();
            }
        }
        startTime = System.currentTimeMillis();
    }

    private void endGame(ActionEvent event) throws IOException, SQLException {
        endTime = System.currentTimeMillis();
        time = (endTime - startTime)  / 1000F;

        DbClass.updateResult(time);

        Alert alert =
                new Alert(Alert.AlertType.NONE,
                        "Twój czas: " + time + "s. Czy chcesz powtórzyć?",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("Koniec gry");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            Parent menu = FXMLLoader.load(getClass().getResource("Game.fxml"));
            Scene menuScene = new Scene(menu);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(menuScene);
            window.show();
        }

        if( (result.get() == ButtonType.NO)){
            returnToMenu(event);
        }
    }

    private boolean shouldGameEnd() {
        return score == 9;
    }

    public void returnToMenu(ActionEvent event) throws  IOException{
        Parent menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene menuScene = new Scene(menu);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }

    private void drawImages() throws IOException {
        int numbers = 0;
        while(true){
            int randomNumber = random.nextInt(15 + 1 - 1) + 1;
            if(!drawnNumbers.contains(randomNumber)){
                numbers++;
                drawnNumbers.add(randomNumber);
            }
            if(numbers == 10) break;
        }
        for(Integer i : drawnNumbers){
            BufferedImage bImage = ImageIO.read(new File("./images/pic" + i + ".png"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte [] image = bos.toByteArray();
            byteImages.add(image);
            byteImages.add(image);
        }
        Collections.shuffle(byteImages);
    }

    public void leave(KeyEvent keyEvent) throws IOException{
        if(keyEvent.getCode() == KeyCode.ESCAPE){
            Parent menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene menuScene = new Scene(menu);
            Stage window = (Stage) ((Node)keyEvent.getSource()).getScene().getWindow();
            window.setScene(menuScene);
            window.show();
        }
    }
}
