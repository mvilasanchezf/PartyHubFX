package com.partyfx.model.dao;

import java.sql.SQLException;

public interface GenericDao <G> {
    public boolean update(G obj) throws Exception;
    public G delete(int id);
    public G create(G obj) throws SQLException, Exception;
    public G read(int id) throws SQLException, Exception;
}

