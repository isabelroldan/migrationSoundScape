package com.juanite.controller;

import com.juanite.model.DAO.UserDAO;
import com.juanite.util.AppData;
import com.juanite.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class UserProfileController {
    @FXML
    public TextField txtfld_searchBar;
    @FXML
    public ImageView img_alerts;
    @FXML
    public ImageView img_avatar;
    @FXML
    public Label lbl_username;
    @FXML
    public ImageView img_editUsername;
    @FXML
    public Label lbl_email;
    @FXML
    public ImageView img_editEmail;
    @FXML
    public Button btn_password;
    @FXML
    public ImageView img_changeAvatar;
    @FXML
    public Button btn_changeAvatar;
    @FXML
    public Button btn_save;
    @FXML
    public TextField txtfld_username;
    @FXML
    public TextField txtfld_email;
    @FXML
    public Button btn_username;
    @FXML
    public Button btn_email;

    public void initialize() {
        lbl_username.setText(AppData.getCurrentUser().getName());
        lbl_email.setText(AppData.getCurrentUser().getEmail());
        lbl_username.setVisible(true);
        lbl_email.setVisible(true);
        img_editUsername.setVisible(true);
        txtfld_username.setVisible(false);
        txtfld_email.setVisible(false);
        btn_username.setVisible(false);
        btn_email.setVisible(false);
        img_avatar.setImage(new Image(UserProfileController.class.getResourceAsStream(AppData.getCurrentUser().getPhoto())));
        img_changeAvatar.setImage(new Image(UserProfileController.class.getResourceAsStream(AppData.getCurrentUser().getPhoto())));
    }

    public void editUsernameMode() {
        txtfld_username.setText(lbl_username.getText());
        txtfld_username.setVisible(true);
        lbl_username.setVisible(false);
        btn_username.setVisible(true);
        img_editUsername.setVisible(false);
    }

    public void editUsername() throws SQLException {
        if(!txtfld_username.getText().equals("")) {
            if(Validator.validateUsername(txtfld_username.getText())) {
                if(!((UserDAO)AppData.getCurrentUser()).userExists(txtfld_username.getText())) {
                    AppData.getCurrentUser().setName(txtfld_username.getText());
                }
            }
        }
        lbl_username.setText(AppData.getCurrentUser().getName());
        lbl_username.setVisible(true);
        txtfld_username.setVisible(false);
        btn_username.setVisible(false);
        img_editUsername.setVisible(true);
    }

    public void editEmailMode() {
        txtfld_email.setText(lbl_email.getText());
        txtfld_email.setVisible(true);
        lbl_email.setVisible(false);
        btn_email.setVisible(true);
        img_editEmail.setVisible(false);
    }

    public void editEmail() throws SQLException {
        if(!txtfld_email.getText().equals("")) {
            if(Validator.validateEmail(txtfld_email.getText())) {
                if(!((UserDAO)AppData.getCurrentUser()).emailExists(txtfld_email.getText())) {
                    AppData.getCurrentUser().setEmail(txtfld_email.getText());
                }
            }
        }
        lbl_email.setText(AppData.getCurrentUser().getEmail());
        lbl_email.setVisible(true);
        txtfld_email.setVisible(false);
        btn_email.setVisible(false);
        img_editEmail.setVisible(true);
    }

}
