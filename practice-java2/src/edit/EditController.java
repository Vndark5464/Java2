package edit;

import javafx.Main;
import javafx.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import list.ListController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAuthor;
    public TextField txtPrice;

    public static Student editedStudent;

    private int index = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.setText(editedStudent.getName());
        txtAuthor.setText(editedStudent.getAuthor());
        txtPrice.setText(editedStudent.getPrice().toString());

    }

    public void backToList(ActionEvent event) throws Exception{
        Parent listScene = FXMLLoader.load(getClass().getResource("../list/list.fxml"));
        Scene sc = new Scene(listScene,800,600);
        Main.rootStage.setScene(sc);
    }

    public void submit(ActionEvent actionEvent) {
        try {
            Integer m = Integer.parseInt(txtPrice.getText());
            if(m<0 || m > 10)
                throw new Exception("Giá tiền không hợp lệ");
            // them sv
            Student s=  new Student(null, txtName.getText(),txtAuthor.getText(),m,txtPrice.getText());

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(ListController.connectionString,ListController.user,ListController.pwd);
            Statement stt = conn.createStatement();
            String sql_txt = "insert into students(name,author,price) values('"
                    +txtName.getText()+"','"
                    +txtAuthor.getText()+"',"
                    +m
                    +",'"+txtPrice.getText()+"')"
                    ;
            stt.executeUpdate(sql_txt);

            backToList(null);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
    }

    public void delete(ActionEvent actionEvent) throws IOException {
        Alert alert  = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirm");
        alert.setHeaderText("Xác nhận xóa");
        alert.setContentText("Xóa sẽ bị mất");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            removeItem();
        }
    }

    private void removeItem() {

    }
}