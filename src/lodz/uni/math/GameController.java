package lodz.uni.math;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;

import javafx.event.ActionEvent;

public class GameController implements Initializable {

    @FXML
    private GridPane gridPane;
    private boolean isBusy = false;
    private List<Integer> drawnNumbers = new ArrayList<>();
    private List<byte[]> byteImages = new ArrayList<>();
    private Random random = new Random();
    private ToggleImage selectedImage = null;
    private ToggleImage secondSelectedImage = null;
    private int scores = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
                int finalImageIndex = imageIndex;
                toggleImage.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(isBusy){
                            return;
                        }

                        if(toggleImage.isMatching()){
                            return;
                        }

                        if(selectedImage == toggleImage){
                            return;
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
                                    System.out.println("KONIEC GRY!");
                                }
                                selectedImage.setMatched(true);
                                secondSelectedImage.setMatched(true);
                                selectedImage = null;
                                secondSelectedImage = null;
                                scores++;
                            } else {
                                System.out.println("Pair NOT found!");
                                isBusy = true;
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                selectedImage.turnBack();
                                secondSelectedImage.turnBack();
                                selectedImage = null;
                                secondSelectedImage = null;
                                isBusy = false;
                            }
                        }
                    }
                });
                gridPane.add(toggleImage, i, j , 1, 1);
                toggleImage.turnBack();
            }
        }
    }

    private boolean shouldGameEnd() {
        return scores == 9;
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
}
