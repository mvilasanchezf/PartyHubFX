package com.partyfx.model.objects;

import com.partyfx.controller.RegisterUserController;
import com.partyfx.model.daoImp.UserDaoImp;
import com.partyfx.model.exceptions.UserException;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.ArrayList;

public class User extends RegisterUserController {
    private int idUser;
    private boolean admin;
    private String name;
    private String email;
    private String password;
    private boolean banned;
    private Button banButton;

    public Button getBanButton() {
        return banButton;
    }

    private int reports;

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public User() {

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.banButton = new Button("ban");
    }

    public User(int idUser, boolean admin, String name, String email) {
        this.idUser = idUser;
        this.admin = admin;
        this.name = name;
        this.email = email;
        this.banButton = new Button("ban");
    }

    public User(int idUser, String email, String name, boolean banned, int reports) {
        this.idUser = idUser;
        this.email = email;
        this.name = name;
        this.banned = banned;
        this.reports = reports;
        this.banButton = new Button("ban");
        this.banButton.setOnAction(actionEvent -> {
            this.banned = !this.banned;
            try {
                this.update();
            }catch (Exception ignored) {

            }

        });
    }

    public User(int idUser, String email, String name, boolean banned, boolean admin) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.banned = banned;
        this.admin = admin;
        this.banButton = new Button("ban");
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.banButton = new Button("ban");
    }

    public User logIn() throws SQLException, ClassNotFoundException, UserException{
        return UserDaoImp.getInstance().logIn(this);
    }

    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return UserDaoImp.getInstance().getAll();
    }

    public User register() throws SQLException, ClassNotFoundException, UserException {
        return UserDaoImp.getInstance().create(this);
    }

    public boolean update() throws SQLException, ClassNotFoundException, UserException {
        return UserDaoImp.getInstance().update(this);
    }
}
