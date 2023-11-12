package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.DAO.CommentDAO;
import com.juanite.model.DAO.PlaylistDAO;
import com.juanite.model.DAO.SongDAO;
import com.juanite.model.domain.Comment;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommentController {
    @FXML
    public ImageView img_avatar;
    @FXML
    public Button homeButton;
    @FXML
    public Button exploreButton;
    @FXML
    public Button myListsButton;
    @FXML
    public Button logOutButton;
    @FXML
    public Button profileButton;
    @FXML
    public TextField searchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Label messageLabel;
    @FXML
    public ListView<Comment> commentListView;
    @FXML
    public Button btn_addComment;
    @FXML
    public Button btn_showComment;
    @FXML
    public Label lbl_plName;
    @FXML
    public Label lbl_user;
    @FXML
    public Label lbl_timestamp;
    @FXML
    public Label lbl_comment;
    @FXML
    public Button btn_back;
    @FXML
    public Label lbl_userTitle;
    @FXML
    public Label lbl_timestampTitle;
    @FXML
    public Label lbl_commentTitle;
    @FXML
    public TextArea txt_newComment;
    private String botonEstiloOriginal;
    private ObservableList<Comment> plComments;

    public void initialize() {
        botonEstiloOriginal = homeButton.getStyle();

        homeButton.setOnMouseEntered(event -> changeColorButtonHome(true));
        homeButton.setOnMouseExited(event -> changeColorButtonHome(false));

        exploreButton.setOnMouseEntered(event -> changeColorButtonExplore(true));
        exploreButton.setOnMouseExited(event -> changeColorButtonExplore(false));

        myListsButton.setOnMouseEntered(event -> changeColorButtonmyLists(true));
        myListsButton.setOnMouseExited(event -> changeColorButtonmyLists(false));

        logOutButton.setOnMouseEntered(event -> changeColorButtonlogOut(true));
        logOutButton.setOnMouseExited(event -> changeColorButtonlogOut(false));

        profileButton.setOnMouseEntered(event -> changeColorButtonprofile(true));
        profileButton.setOnMouseExited(event -> changeColorButtonprofile(false));

        plComments = FXCollections.observableArrayList();
        List<Comment> comments = new ArrayList<>();
        comments.addAll(new CommentDAO(new Comment()).getByPlaylist(AppData.getCurrentPL()));
        AppData.getCurrentPL().setComments(comments);
        plComments.addAll(AppData.getCurrentPL().getComments());
        commentListView.setItems(plComments);
        commentListView.refresh();
        lbl_plName.setText(AppData.getCurrentPL().getName() + " Comments");
        lbl_user.setVisible(false);
        lbl_timestamp.setVisible(false);
        lbl_comment.setVisible(false);
        btn_back.setVisible(false);
        lbl_userTitle.setVisible(false);
        lbl_commentTitle.setVisible(false);
        lbl_timestampTitle.setVisible(false);
        commentListView.setVisible(true);
        btn_addComment.setVisible(true);
        btn_showComment.setVisible(true);
        txt_newComment.setVisible(false);

    }

    public void showComment() {
        if(commentListView.getSelectionModel().getSelectedItem() != null) {
            AppData.setCurrentComment(commentListView.getSelectionModel().getSelectedItem());
            commentListView.setVisible(false);
            btn_addComment.setVisible(false);
            btn_showComment.setVisible(false);
            lbl_plName.setText(AppData.getCurrentComment().getUser().getName() + " Comment");
            lbl_user.setText(AppData.getCurrentComment().getUser().getName());
            lbl_user.setVisible(true);
            lbl_userTitle.setVisible(true);
            lbl_timestampTitle.setVisible(true);
            lbl_commentTitle.setVisible(true);
            lbl_timestamp.setText(AppData.getCurrentComment().getDate_time().toString());
            lbl_timestamp.setVisible(true);
            lbl_comment.setText(AppData.getCurrentComment().getComment());
            lbl_comment.setVisible(true);
            btn_back.setVisible(true);
        }
    }

    public void addComment() {
        if(!txt_newComment.isVisible()) {
            commentListView.setVisible(false);
            btn_showComment.setVisible(false);
            lbl_plName.setText("New Comment");
            lbl_commentTitle.setVisible(true);
            txt_newComment.setVisible(true);
            btn_back.setVisible(true);
        }else{
            if(txt_newComment.getText() != null && !txt_newComment.getText().equals("")) {
                try(CommentDAO cdao = new CommentDAO(new Comment(-1, txt_newComment.getText(), LocalDateTime.now() , AppData.getCurrentUser(), AppData.getCurrentPL()))) {
                    cdao.save();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                initialize();
            }
        }
    }

    public void back() throws IOException {
        AppData.setCurrentComment(null);
        if(btn_addComment.isVisible() && btn_showComment.isVisible()) {
            App.setRoot("playlist");
        }else{
            initialize();
        }
    }


    /**
     * Change the color of the Home button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonHome(boolean mouseEntered) {
        if (mouseEntered) {
            homeButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            homeButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Explore button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonExplore(boolean mouseEntered) {
        if (mouseEntered) {
            exploreButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            exploreButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the My Lists button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonmyLists(boolean mouseEntered) {
        if (mouseEntered) {
            myListsButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            myListsButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Log Out button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonlogOut(boolean mouseEntered) {
        if (mouseEntered) {
            logOutButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            logOutButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Profile button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonprofile(boolean mouseEntered) {
        if (mouseEntered) {
            profileButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            profileButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Handles the action when the Home button is clicked.
     * Redirects the user to the "home" view.
     */
    @FXML
    private void handleHomeButton() {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Log Out button is clicked.
     * Redirects the user to the "signup" view.
     */
    @FXML
    private void handleLogOutButton() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the search button event. Performs a search in the database and displays the results in the "play" view.
     */
    @FXML
    private void handleSearchButton() {

        String searchTerm = searchTextField.getText();
        if (!searchTerm.isEmpty()) {
            Set<Playlist> playlistSet = new PlaylistDAO(new Playlist()).getSearchResults(searchTerm);
            List<Playlist> searchResults = new ArrayList<>();
            searchResults.addAll(playlistSet);
            if (!searchResults.isEmpty()) {
                AppData.setSearchResults(null);
                AppData.setSearchResultsPl(searchResults);
                try {
                    App.setRoot("searchResult");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                messageLabel.setText("Playlist no encontrada.");
            }
        }
    }

    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("userProfile");
    }

    public void goToPlaylists() throws IOException {
        App.setRoot("playlists");
    }
}
