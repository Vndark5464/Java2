package javafx;

import javafx.Main;
import edit.EditController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Student {
    private Integer id;
    private String name;
    private String author;
    private Integer price;

    private Button edit;

    public Student(Integer id, String name, String author, Integer price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.edit = new Button("Edit");
        this.edit.setOnAction(event -> {
            try {
                EditController.editedStudent = this;
                Parent editForm = FXMLLoader.load(getClass().getResource("edit/edit.fxml"));
                Scene sc = new Scene(editForm,800,600);
                Main.rootStage.setScene(sc);
            }catch (Exception e){

            }
        });
    }

    public Student(Object id, String text, String text1, Integer m, String text2) {
    }


    public Button getEdit() {
        return edit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getName()+"\n"+this.getAuthor()+"\n"+this.getPrice();
    }

    public Integer getId() {
        return id;
    }
}