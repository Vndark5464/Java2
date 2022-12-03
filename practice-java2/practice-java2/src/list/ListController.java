package list;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.Book;
import javafx.Main;
import add.AddController;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    @FXML
    private TableView<Book> tbvBookList;
    @FXML
    private TableColumn<Book, String> colId;
    @FXML
    private TableColumn<Book, String> colName;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Double> colPrice;

    ArrayList<Book> allBooks = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setCellFactory(tc -> new TableCell<Book, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) setText(null);
                else setText("$" + String.format("%,.0f", price));
            }
        });

        display(null);
    }

    @FXML
    void add(ActionEvent event) throws Exception{
        AddController.existedIds.clear();
        for (Book b: allBooks) {
            AddController.existedIds.add(b.getId());
        }

        Parent root = FXMLLoader.load(getClass().getResource("../add/add.fxml"));
        Main.rootStage.setTitle("Add book");
        Main.rootStage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void exit(ActionEvent event) {
        Main.rootStage.close();
    }

    @FXML
    void display(ActionEvent event) {
        try {
            allBooks.clear();
            FileInputStream fis = new FileInputStream("books.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                try {
                    Book b = (Book) ois.readObject();
                    allBooks.add(b);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ois.close();

            tbvBookList.setItems(FXCollections.observableArrayList(allBooks));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }

    }
}