package lodz.uni.math;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lodz.uni.math.database.DbClass;
import lodz.uni.math.database.RankItem;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RankController implements Initializable {

    @FXML
    private TableView rankTab;
    @FXML
    private TableColumn colPos;
    @FXML
    private TableColumn colNick;
    @FXML
    private TableColumn colTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            DbClass.getRank();
        }catch (SQLException e){
            e.printStackTrace();
        }
        colPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        colNick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        DbClass.getRankItemList();
        for(RankItem r : DbClass.getRankItemList()){
            rankTab.getItems().add(r);
        }
    }

    public void leave(ActionEvent actionEvent) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene menuScene = new Scene(menu);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }
}
