package list;

import javafx.Main;
import javafx.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.sql.*;

public class ListController implements Initializable {
    public ObservableList<Student> ls = FXCollections.observableArrayList();

    public TableView<Student> tbStudents;
    public TableColumn<Student,Integer> cID;
    public TableColumn<Student,String> cName;
    public TableColumn<Student,String> cAuthor;
    public TableColumn<Student,Integer> cPrice;
    public TextField txtSearch;

    public final static String connectionString = "jdbc:mysql://localhost:3306/practice-java2";
    public final static String user = "root";
    public final static String pwd = ""; // xampp: ""   mamp: "root"

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // lay data tu database cho vao list
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionString,user,pwd);
            Statement stt = conn.createStatement();
            String sql_txt = "select * from students";
            ResultSet rs = stt.executeQuery(sql_txt);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int price = rs.getInt("price");
                Student s = new Student(id,name,author,price);
                ls.add(s);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        tbStudents.setItems(ls);
    }

    public void createStudent(ActionEvent event) throws Exception{
        Parent createForm = FXMLLoader.load(getClass().getResource("../create/create.fxml"));
        Scene sc = new Scene(createForm,800,600);
        Main.rootStage.setScene(sc);
    }

    public void search(ActionEvent actionEvent) {
        try {
            String s = txtSearch.getText();
            if(s.isEmpty()){
                tbStudents.setItems(ls);
                throw new Exception("Vui lòng nhập từ cần tìm kiếm");
            }

            ObservableList<Student> results = ls.stream()
                    .filter(student -> student.getName().toLowerCase().contains(s.toLowerCase()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tbStudents.setItems(results);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }

    }
}