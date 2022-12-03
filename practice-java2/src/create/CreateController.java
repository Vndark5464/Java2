package create;

import javafx.event.ActionEvent;

import java.awt.*;

public class CreateController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAuthor;
    public TextField txtPrice;


    public CreateController() {
    }

    public CreateController(TextField txtId, TextField txtName, TextField txtAuthor, TextField txtPrice) {
        this.txtId = txtId;
        this.txtName = txtName;
        this.txtAuthor = txtAuthor;
        this.txtPrice = txtPrice;
    }

    public TextField getTxtId() {
        return txtId;
    }

    public void setTxtId(TextField txtId) {
        this.txtId = txtId;
    }

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtName) {
        this.txtName = txtName;
    }

    public TextField getTxtAuthor() {
        return txtAuthor;
    }

    public void setTxtAuthor(TextField txtAuthor) {
        this.txtAuthor = txtAuthor;
    }

    public TextField getTxtPrice() {
        return txtPrice;
    }

    public void setTxtPrice(TextField txtPrice) {
        this.txtPrice = txtPrice;
    }


}
