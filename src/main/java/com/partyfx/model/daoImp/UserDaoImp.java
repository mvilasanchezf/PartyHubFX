package com.partyfx.model.daoImp;

import com.partyfx.model.dao.GenericDao;
import com.partyfx.model.exceptions.UserException;
import com.partyfx.model.objects.User;
import com.partyfx.model.services.DBConnection;
import com.partyfx.model.services.PropertiesService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class UserDaoImp extends DBConnection implements GenericDao<User> {

    private final Connection connection;
    public static UserDaoImp instance = null;
    public static Properties prop;

    private UserDaoImp() throws SQLException, ClassNotFoundException {
        super();
        this.connection = DBConnection.getConnection();
        prop = PropertiesService.getProperties("i18_en");
    }

    public static UserDaoImp getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null){
            instance = new UserDaoImp();
        }
        return instance;
    }

    @Override
    public boolean update(User user) throws SQLException, UserException {
        boolean result;
        String query = "update party.users set admin = ?, name = ?, email = ?,  banned = ? where id_user = ?;";
        PreparedStatement stmt = this.connection.prepareStatement(query);
        stmt.setBoolean(1, user.isAdmin());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getEmail());
        stmt.setBoolean(4, user.isBanned());
        stmt.setInt(5, user.getIdUser());
        int rs = stmt.executeUpdate();
        if(rs>0){
            result = true;
        }else {
            result = false;
            throw new UserException(prop.getProperty("error.generic"));
        }
        return result;
    }

    @Override
    public User delete(int id) {
        return null;
    }

    @Override
    public User create(User user) throws SQLException, UserException {
        User result;
        String query = "select id_user from party.users where email=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);
        stmt.setString(1, user.getEmail());
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()){
            query = "insert into party.users (email, name, password, admin, banned) values (?,?,?,1,0);";
            PreparedStatement stmt2 = this.connection.prepareStatement(query);
            stmt2.setString(1, user.getEmail());
            stmt2.setString(2, user.getName());
            stmt2.setString(3, user.getPassword());
            int rs2 = stmt2.executeUpdate();
            if (rs2>0){
                query = "select id_user from party.users where email=?";
                stmt = this.connection.prepareStatement(query);
                stmt.setString(1, user.getEmail());
                rs = stmt.executeQuery();
                if (rs.next()){
                    result = this.read(rs.getInt("id_user"));
                }else {
                    result = null;
                    throw new UserException(prop.getProperty("error.invalidUser"));
                }
            }else {
                result = null;
                throw new UserException(prop.getProperty("error.invalidUser"));
            }
        }else {
            result = null;
            throw new UserException(prop.getProperty("error.invalidUser"));
        }
        return result;
    }

    @Override
    public User read(int id) throws SQLException, UserException {
        User result=null;
        String query = "select id_user, email, admin, name from party.users where id_user=?";
        PreparedStatement stmt2 = this.connection.prepareStatement(query);
        stmt2.setInt(1, id);
        ResultSet rs2 = stmt2.executeQuery();
        if (rs2.next()){
            result = new User(rs2.getInt("id_user"),
                    rs2.getBoolean("admin"), rs2.getString("name"),
                    rs2.getString("email"));
        }else{
            throw new UserException(prop.getProperty("error.invalidUser"));
        }
        return result;
    }

    public ArrayList<User> getAll() throws SQLException {
        ArrayList<User> result = new ArrayList<User>();
        String query = "select id_user, email, admin, name, banned, reports from party.users where admin=0;";
        PreparedStatement stmt = this.connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(new User(rs.getInt("id_user"), rs.getString("email"),
                    rs.getString("name"), rs.getBoolean("banned"), rs.getInt("reports")));
        }
        return result;
    }

    public User logIn (User userNotLogged) throws SQLException, UserException, ClassNotFoundException {
        User result;
        String query = "select id_user, email, banned, name, admin from party.users where email=? and password=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);
        stmt.setString(1, userNotLogged.getEmail());
        stmt.setString(2, userNotLogged.getPassword());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            User userExists = new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("name"), rs.getBoolean("banned"), rs.getBoolean("admin"));

            if (userExists.isBanned() || !userExists.isAdmin()){
                result = null;
                throw new UserException(prop.getProperty("error.accessDenied"));
            }else{
                result = userExists;
            }
        }else{
            result = null;
            throw new UserException(prop.getProperty("error.wrongCredentials"));
        }
        return result;
    }


}
