package add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.Book;
import javafx.Main;

import java.io.*;
import java.util.ArrayList;

public class AddController {
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtAuthor;

    public static ArrayList<String> existedIds = new ArrayList<>();

    @FXML
    void goBack(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../list/list.fxml"));
        Main.rootStage.setTitle("Book store management");
        Main.rootStage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void save(ActionEvent event) {
        try {
            if (txtId.getText().isEmpty()) throw new Exception("Book ID trống");
            if (txtName.getText().isEmpty()) throw new Exception("Book Name trống");
            if (txtAuthor.getText().isEmpty()) throw new Exception("Author trống");
            if (txtPrice.getText().isEmpty()) throw new Exception("Price trống");

            String id = txtId.getText();
            for (String s: existedIds) {
                if (s.equals(id)) throw new Exception("Book ID đã tồn tại");
            }
            String name = txtName.getText();
            String author = txtAuthor.getText();
            Double price = Double.parseDouble(txtPrice.getText());
            if (price < 0) throw new Exception("Price không thể nhỏ hơn 0");

            boolean exists = new File("books.dat").exists();
            FileOutputStream fos = new FileOutputStream("books.dat", true);
            ObjectOutputStream oos = exists ?
                    new ObjectOutputStream(fos) {
                        protected void writeStreamHeader() throws IOException {
                            reset();
                        }
                    }:new ObjectOutputStream(fos);

            Book book = new Book(
                    id,
                    name,
                    author,
                    price
            );

            oos.writeObject(book);
            oos.flush();
            oos.close();

            goBack(event);
        }catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }

    }
}