package lodz.uni.math;

import javafx.fxml.Initializable;
import lodz.uni.math.database.DbClass;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DbClass.connectDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
