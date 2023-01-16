package com.partyfx.controller;

import com.partyfx.model.objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListUsersController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private TableView<User> table;

    private ArrayList<User> users;


    private void createTable() {
        // TODO: Finish the function create table
        table.setEditable(true);
        table.getColumns().removeAll();
        TableColumn<User, String> colName = new TableColumn<>("Name");
        TableColumn<User, String> colEmail = new TableColumn<>("Email");
        TableColumn<User, Boolean> colBanned = new TableColumn<>("Banned");
        TableColumn<User, Integer> colReports = new TableColumn<>("Reports");
        TableColumn<User, Button> colBan = new TableColumn<>("Ban / UnBan");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBanned.setCellValueFactory(new PropertyValueFactory<>("banned"));
        colReports.setCellValueFactory(new PropertyValueFactory<>("reports"));
        colBan.setCellValueFactory(new PropertyValueFactory<>("banButton"));
        table.getColumns().addAll(colName, colEmail, colBanned, colReports, colBan);

    }

    private ArrayList<User> loadUsers() {
        User auxUser = new User();
        ArrayList<User> users;
        try {
            users = auxUser.getAll();
        }catch (Exception e) {
            users = new ArrayList<User>();
            throw new RuntimeException(e);
        }
        return users;
    }

    private void fillTable(ArrayList<User> users){
        ObservableList<User> listaVideoJuegos = FXCollections.observableArrayList(users);
        table.setItems(listaVideoJuegos);
    }

    private void loadMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        MainMenuController controller = fxmlLoader.getController();
        controller.initData("");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backButton.setOnAction(actionEvent -> {
            try {
                loadMenu(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        this.users = loadUsers();
        createTable();
        fillTable(this.users);
    }
}
